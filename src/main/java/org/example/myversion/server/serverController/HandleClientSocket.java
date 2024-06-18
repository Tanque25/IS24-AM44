package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidGameStateException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;
import org.example.myversion.server.Server;

import java.io.*;
import java.net.Socket;

import jakarta.json.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


public class HandleClientSocket implements CommunicationInterface, Runnable {

    private final Socket clientSocket;
    private GameController controller;
    public Thread listenThread;
    public final BufferedReader reader;
    public BufferedWriter writer;
    private String nickname;


    public HandleClientSocket(Socket clientSocket, GameController controller) {
        this.clientSocket = clientSocket;
        this.controller = controller;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listenThread = new Thread(() -> {
            String clientMessageString;
            while (true) {
                try {
                    synchronized (reader) {
                        clientMessageString = reader.readLine();
                        JsonObject jsonObject = null;
                        try (JsonReader jsonReader = Json.createReader(new StringReader(clientMessageString))) {
                            jsonObject = jsonReader.readObject();
                        } catch (Exception e) {
                            System.out.println("Error parsing JSON: " + clientMessageString);
                        }

                        Message message = new Message(jsonObject);

                        receiveMessageTCP(message, this);
                    }
                } catch (IOException | IllegalAccessException | InvalidNicknameException | InvalidMoveException |
                         InvalidChoiceException e) {
                    System.err.println("Error while reading from client. IO");
                    break;
                }
            }
        });
    }

    public void run() {
        listenThread.start();
    }

    /**
     * Stops the client handler by closing the socket and associated streams.
     */
    public void stop() {
        try {
            // Close the client socket
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }

            // Close the input and output streams
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }

            // Interrupt the listening thread if it's still running
            if (listenThread != null && listenThread.isAlive()) {
                listenThread.interrupt();
            }

            System.out.println("Stopped handling client: " + nickname);
        } catch (IOException e) {
            System.err.println("Error stopping client handler: " + e.getMessage());
        }
    }

    public void sendMessageToClient(Message message) {
        try {
            System.out.println("Sending " + message.getMessageCode() + " " + nickname);
            String jsonString = message.getJson().toString() + "\n";
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error sending message to client: " + e.getMessage());
        }
    }

    /**
     * Receives a TCP message and processes it based on the message type.
     *
     * @param message The message received over TCP.
     * @throws IllegalAccessException   If there's an illegal access attempt.
     * @throws InvalidNicknameException If the nickname provided is invalid.
     * @throws InvalidMoveException     If the move performed is invalid.
     * @throws InvalidChoiceException   If the choice made is invalid.
     */
    @Override
    public void receiveMessageTCP(Message message, HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
        String messageCode = message.getMessageCode();

        if (!messageCode.equals("Ping"))
            System.out.println("Received TCP message: with messageCode " + messageCode);

        switch (messageCode) {

            case "Ping" -> {
                System.out.println("Received Ping from " + client.getNickname());
                controller.pong(client.getNickname());
                controller.addPongLost(client.getNickname());
                client.sendMessageToClient(new Message("Pong"));
            }

            // This case verifies the selected nickname and handles the different cases
            case "Login" -> {
                String nickname = message.getArgument();
                int checkNicknameStatus = controller.checkNickname(nickname);
                checkNickname(client, nickname, checkNicknameStatus);
            }

            // This case verify the selected number of players and ank it again if necessary
            case "NumberOfPlayers" -> {
                int numberOfPlayers = message.getNumber();
                System.out.println("Number of players: " + numberOfPlayers);

                // check the validity of the players' number
                if (!controller.checkNumberOfPlayer(numberOfPlayers))
                    sendMessageToClient(new Message("InvalidNumberOfPlayers"));
                else
                    controller.setPlayersNumber(numberOfPlayers);
            }

            // This case is used to place the starter card in the Player's playArea on the selected side
            case "StarterCard" -> {
                System.out.println("Starter card side received from " + nickname);
                controller.playStarterCard(controller.getPlayerFromNickname(nickname), message.getStarterCard());
            }

            // This case is used to set the secret objective card in the game model
            case "ObjectiveCardChoice" -> {
                System.out.println("Secret objective card received from " + nickname);
                controller.chooseObjectiveCard(controller.getPlayerFromNickname(nickname), message.getObjectiveCard());

                // When the server receives the player's secret objective choice, the readyPlayersNumber is updated
                controller.updateReadyPlayersNumber();

                // When all the players are ready, the servers sends every player the other players' hands and play areas and starts the turns cycle
                if (controller.getReadyPlayersNumber() == controller.getPlayersNumber()) {
                    sendStartCondition();
                }
            }

            case "CardToPlayChoice" -> {
                try {
                    // If it's a PlayableCard call getPlayableCard(), otherwise call getGoldCard()
                    if (message.getJson().containsKey("playableCard")) {
                        controller.playCard(nickname, message.getPlayableCard(), message.getCoordinates());
                        updateClientsPlayedCard(message.getPlayableCard(), message.getCoordinates());
                    } else {
                        controller.playCard(nickname, message.getGoldCard(), message.getCoordinates());
                        updateClientsPlayedCard(message.getGoldCard(), message.getCoordinates());
                    }

                    if (controller.isLastRound()) {
                        updateScores();
                        changeTurn();
                    } else {
                        sendMessageToClient(new Message("VisibleCards", controller.getVisibleResourceCards(), controller.getRsourceDeckPeek(), controller.getVisibleGoldCards(), controller.getGoldDeckPeek()));
                        sendMessageToClient(new Message("DrawCard"));
                    }

                } catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                    sendMessageToClient(new Message("InvalidMove"));
                } catch (InvalidNicknameException | InvalidGameStateException e) {
                    // The nickname will always be valid
                    // The game state will always be valid
                    // sendMessageToClient(new Message("InvalidMove"));
                }
            }

            case "CardToDrawChoice" -> {
                int cardToDrawChoice = message.getNumber();

                switch (cardToDrawChoice) {
                    case 0, 1 -> {
                        try {
                            PlayableCard chosenCard = controller.getVisibleResourceCards().get(cardToDrawChoice);
                            controller.drawCard(client.nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 2, 3 -> {
                        try {
                            GoldCard chosenCard = controller.getVisibleGoldCards().get(cardToDrawChoice - 2);
                            controller.drawCard(client.nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            PlayableCard chosenCard = controller.getRsourceDeckPeek();
                            controller.drawCard(client.nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> {
                        try {
                            GoldCard chosenCard = controller.getGoldDeckPeek();
                            controller.drawCard(client.nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                // Send the updated scores to all clients
                updateScores();

                // The current turn is finished, it passes to the next player.
                changeTurn();

            }
        }
    }

    public void checkNickname(HandleClientSocket client, String nickname, int checkNicknameStatus) throws RemoteException {
        switch (checkNicknameStatus) {
            case 1 -> {
                if (controller.getGameState().equals(GameState.IN_GAME)) {
                    client.sendMessageToClient(new Message("GameAlreadyStarted"));
                } else {
                    client.sendMessageToClient(new Message("Nickname", nickname));

                    controller.addPlayer(nickname);
                    System.out.println(nickname + " logged in.");

                    setNickname(nickname);
                    controller.addClientTCP(nickname, client);
                    // TODO: Implement the connection check on a different channel
                    // startPingThread(client);

                    if (controller.isFirst()) {
                        client.sendMessageToClient(new Message("PlayersNumber"));
                        client.sendMessageToClient(new Message("WaitForOtherPlayers"));
                    } else {
                        client.sendMessageToClient(new Message("WaitForOtherPlayers"));
                        System.out.println("Max number of players set to: " + controller.getPlayersNumber());
                        // If this is the last player to reach the max player number, the game starts
                        if (controller.getPlayersNumber() != 0 && controller.gameIsFull()) {
                            System.out.println("Game is full.");
                            startGame();
                            System.out.println("Game started.");
                        }
                    }
                }
            }
        }
    }

    public void updateClientsPlayedCard(PlayableCard playedCard, Coordinates coordinates) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdatePlayedCard", nickname, playedCard, coordinates);

        sendMessageToAll(updateMessage);
    }

    public void updateClientsPlayedCard(GoldCard playedCard, Coordinates coordinates) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdatePlayedCard", nickname, playedCard, coordinates);

        sendMessageToAll(updateMessage);
    }

    public void updateClientsDrawnCard(PlayableCard drawnCard) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll(updateMessage);
    }

    public void updateClientsDrawnCard(GoldCard drawnCard) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll(updateMessage);
    }

    public void updateScores() throws RemoteException {
        Map<String, Integer> scores = controller.getScores();

        Message scoresMessage = new Message("Scores", scores);

        sendMessageToAll(scoresMessage);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}

