package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;
import org.example.myversion.server.Server;

import java.io.*;
import java.net.Socket;
import jakarta.json.*;

import java.rmi.RemoteException;


public class HandleClientSocket implements CommunicationInterface, Runnable {

    private final Socket clientSocket;
    private GameController controller;

    public Thread listenThread;

    public final BufferedReader reader;
    public BufferedWriter writer;

    private Server server;

    private String Nickname;

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
                int numberOfPlayers = message.getMaxPlayers();
                System.out.println("Number of players: " + numberOfPlayers);

                // check the validity of the players' number
                if (!controller.checkNumberOfPlayer(numberOfPlayers))
                    sendMessageToClient(new Message("InvalidNumberOfPlayers"));
                else
                    controller.setPlayersNumber(numberOfPlayers);
            }

            // This case is used to place the starter card in the Player's playArea on the selected side
            case "StarterCard" -> {
                System.out.println("Starter card side received from " + Nickname);
                controller.playStarterCard(controller.getPlayerFromNickname(Nickname), message.getStarterCard());
            }

            // This case is used to set the secret objective card in the game model
            case "ObjectiveCardChoice" -> {
                System.out.println("Secret objective card received from " + Nickname);
                controller.chooseObjectiveCard(controller.getPlayerFromNickname(Nickname), message.getObjectiveCard());

                // When the server receives the player's secret objective choice, the readyPlayersNumber is updated
                controller.updateReadyPlayersNumber();

                // When all the players are ready, the servers sends every player the other players' hands and play areas and starts the turns cycle
                if(controller.getReadyPlayersNumber() == controller.getPlayersNumber()){
                    sendStartCondition();
                }
            }

            case "CardToPlayChoice" -> {
                // TODO
            }

        }
    }

    public void sendMessageToClient(Message message) {
        try {
            System.out.println("Sending " + message.getMessageCode() + " " + Nickname);
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

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getNickname() {
        return Nickname;
    }
}

