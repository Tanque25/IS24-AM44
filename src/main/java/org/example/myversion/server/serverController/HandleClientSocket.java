package org.example.myversion.server.serverController;

import org.example.myversion.client.ClientCommunicationInterface;
import org.example.myversion.client.view.PlayAreaView;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
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
import java.util.List;


public class HandleClientSocket implements CommunicationInterface, Runnable {

    private final Socket clientSocket;
    private GameController controller;

    public Thread listenThread;

    public final BufferedReader reader;
    public BufferedWriter writer;

    private Server server;

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

                        receiveMessageTCP(message,this);
                    }
                } catch (IOException | IllegalAccessException | InvalidNicknameException | InvalidMoveException |
                         InvalidChoiceException  e) {
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

        if(!messageCode.equals("Ping"))
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
                if(controller.getReadyPlayersNumber() == controller.getPlayersNumber()){
                    sendStartCondition();
                }
            }

            case "CardToPlayChoice" -> {
                try {
                    // If it's a PlayableCard call getPlayableCard(), otherwise call getGoldCard()
                    if (message.getJson().containsKey("playableCard"))
                        controller.playCard(nickname, message.getPlayableCard(), message.getCoordinates());
                    else
                        controller.playCard(nickname, message.getGoldCard(), message.getCoordinates());

                    // Checking if the card has been actually placed - to remove
                    PlayAreaView playAreaView = new PlayAreaView();
                    playAreaView.displayMyPlayArea(controller.getPlayerFromNickname(nickname).getPlayArea());

                    // Saving the played card in the Player instance
                    controller.getGame().getCurrentPlayer().setLastPlayedCard(message.getPlayableCard(), message.getCoordinates());

                    sendMessageToClient(new Message("VisibleCards", controller.getVisibleResourceCards(), controller.getRsourceDeckPeek(), controller.getVisibleGoldCards(), controller.getGoldDeckPeek()));

                    sendMessageToClient(new Message("DrawCard"));
                } catch (InvalidMoveException e) {
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
                        } catch (InvalidGameStateException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2, 3 -> {
                        try {
                            GoldCard chosenCard = controller.getVisibleGoldCards().get(cardToDrawChoice-2);
                            controller.drawCard(client.nickname, chosenCard);
                        } catch (InvalidGameStateException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        try {
                            controller.drawCard(client.nickname, controller.getRsourceDeckPeek());
                        } catch (InvalidGameStateException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        try {
                            controller.drawCard(client.nickname, controller.getGoldDeckPeek());
                        } catch (InvalidGameStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

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

    public void checkNickname(HandleClientSocket client, String nickname, int checkNicknameStatus) throws RemoteException {
        switch (checkNicknameStatus) {
            case 1 -> {
                if(controller.getGameState().equals(GameState.IN_GAME)) {
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

    public void startGame() {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        List<ObjectiveCard> commonObjectiveCards = controller.getCommonObjectiveCards();

        List<PlayableCard> visibleResourceCards = controller.getVisibleResourceCards();
        PlayableCard coveredResourceCard = controller.getRsourceDeckPeek();
        List<GoldCard> visibleGoldCards = controller.getVisibleGoldCards();
        GoldCard coveredGoldCard = controller.getGoldDeckPeek();


        for (String nickname : tcpClients.keySet()) {
            Message visibleCardsMessage = new Message("VisibleCards", visibleResourceCards, coveredResourceCard, visibleGoldCards, coveredGoldCard);
            tcpClients.get(nickname).sendMessageToClient(visibleCardsMessage);

            Message starterCardMessage = new Message("StarterCard", controller.getStarterCard());
            tcpClients.get(nickname).sendMessageToClient(starterCardMessage);

            Message commmonObjectiveCardsMessage = new Message("CommonObjectiveCards", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            tcpClients.get(nickname).sendMessageToClient(commmonObjectiveCardsMessage);

            List<ObjectiveCard> secretObjectiveCardsOptions = controller.getSecretObjectiveCardsOptions();
            Message secretObjectiveCardsOptionsMessage = new Message("SecretObjectiveCardsOptions", secretObjectiveCardsOptions.get(0), secretObjectiveCardsOptions.get(1));
            tcpClients.get(nickname).sendMessageToClient(secretObjectiveCardsOptionsMessage);
        }

    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}

