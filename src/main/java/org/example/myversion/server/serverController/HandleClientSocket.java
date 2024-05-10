package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.exceptions.InvalidGameStateException;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;
import org.example.myversion.server.Server;

import java.io.*;
import java.net.Socket;
import jakarta.json.*;


public class HandleClientSocket implements ServerInterface, Runnable {

    private Socket clientSocket;
    private GameController controller;

    private BufferedReader reader;
    private BufferedWriter writer;
    private Server server;

    // private PrintWriter writer;
    // private Scanner reader;

    public Thread listenThread;


    public HandleClientSocket(Socket clientSocket, GameController controller) {
        this.clientSocket = clientSocket;
        this.controller = controller;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // EDO'S VERSION
        // Listen for messages coming from the client
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

                        receiveMessageTCP(message);
                    }
                } catch (IOException | IllegalAccessException | InvalidNicknameException | InvalidMoveException |
                         InvalidChoiceException | InvalidGameStateException e) {
                    System.err.println("Error while reading from client. IO");
                    break;
                }

            }
        });

    }

    public void run() {
        listenThread.start();

        // RICHI'S VERSION
        /*try {
            String clientString;
            while ((clientString = reader.readLine()) != null) {
                JsonObject jsonObject = parseJsonObject(clientString);

                if (jsonObject != null) {
                    Message message = new Message(jsonObject);
                    //receiveMessageTCP(message); devo mettere aposto
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from client: " + e.getMessage());
        } finally {
            // chiudi-->rivedere????
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Receives a TCP message and processes it based on the message type.
     *
     * @param message The message received over TCP.
     * @throws IllegalAccessException   If there's an illegal access attempt.
     * @throws InvalidNicknameException If the nickname provided is invalid.
     * @throws InvalidMoveException     If the move performed is invalid.
     * @throws InvalidChoiceException   If the choice made is invalid.
     * @throws InvalidGameStateException      If an extra round condition is encountered.
     */
    //ha senso gestire questi tipi di eccezioni qui?
    @Override
    public void receiveMessageTCP(Message message) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, InvalidGameStateException {
        String messageType = message.getMessageCode();

        switch (messageType) {

            case "Ping" -> {
                System.out.println("Received ping from " /*+ client.getUsername()*/); //per CLI
                String nickname = message.getArgument(); //nel ping c'è anche nickname
                //controller.pong(nickname);
                if (controller.checkNickname(nickname) == 1) {
                    sendMessageToClient(new Message("Pong", "Nickname is valid"));
                    controller.addPlayer(nickname);//aggiungo il player
                    //se la lobby ha il numero di giocatori = maxnumberOfplayer, se non
                    if (controller.checkLobby()) {
                        controller.newGame();
                    }

                } else if (controller.checkNickname(nickname) == 0) {
                    sendMessageToClient(new Message("Pong", "Nickname is  already in use"));
                } else {
                    sendMessageToClient(new Message("Pong", "Nickname is  already in use by a disconnected player"));
                }

            }

            case "Login" -> {
                String nickname = message.getArgument();
                // TODO check nickname
                controller.addPlayer(nickname);
            }

            case "NumberOfPlayer" -> {
                int numberOfPlayer = message.getMaxPlayers();
                //controllo sia valido
                if (controller.checkNumberOfPlayer(numberOfPlayer)) {
                    sendMessageToClient(new Message("Valid Number of Player"));
                    //setto numero giocatori del controller
                    controller.setNumberOfPlayer(numberOfPlayer);

                } else {
                    sendMessageToClient(new Message("Invalid number of Player"));
                }
            }

            case "DrawCard" -> {//ne faccio un caso diverso a seconda della carta che vuole prendere? (da che mazzo)
                //non ci vuole una checkDraw di sicurezza !!!! controllare
                PlayableCard chosenCard = message.getPlayableCard();
                String nickname = message.getArgument(); //get del nickname ? tolgo nickname da draw
                controller.drawCard(nickname, chosenCard); //la pesco

                sendMessageToClient(new Message("Card drew successfully"));//pescata

            }

            case "PlayCard" -> {
                String nickname = message.getArgument();
                PlayableCard chosenCard = message.getPlayableCard();
                if (controller.isValidMove()) {//ho aggiunto isValidMove --> implementarla
                    System.out.println("la mossa è valida");//per debug
                    //la gestione dell'eccezioni l'ho messa nel controller (in PlayCard) ha senso?
                    controller.playCard(nickname, chosenCard, message.getCoordinates());
                    sendMessageToClient(new Message("Move executed successfully"));//posizionata
                } else {
                    //è da mettere qui ?
                    while (!controller.isValidMove()) {
                        //ha senso ? ricontrollare
                        sendMessageToClient(new Message("Coordinates not valid"));
                    }
                }
            }

        }
    }

    private JsonObject parseJsonObject(String jsonString) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            return reader.readObject();
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    private void sendMessageToClient(Message message) {
        try {
            String jsonString = message.getJson().toString();
            writer.write(jsonString);
        } catch (IOException e) {
            System.err.println("Error sending message to client: " + e.getMessage());
        }

        // RICHI'S VERSION
        /*try (JsonWriter writer = Json.createWriter(writer)) {
            //JsonObject messageJson = message.getJsonObject();
            //writer.writeObject(messageJson);
        } catch (IOException e) {
            System.err.println("Error sending response to client: " + e.getMessage());
        }*/
    }
}

