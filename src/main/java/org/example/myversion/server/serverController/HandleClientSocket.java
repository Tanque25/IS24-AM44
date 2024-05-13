package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
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
import java.util.Objects;


public class HandleClientSocket implements ServerInterface, Runnable {

    private Socket clientSocket;
    private GameController controller;

    private BufferedReader reader;
    private BufferedWriter writer;
    private Server server;

    private String Nickname;

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

                        receiveMessageTCP(message,this);
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
     * Receives a TCP message and processes it based on the message type.
     *
     * @param message The message received over TCP.
     * @throws IllegalAccessException   If there's an illegal access attempt.
     * @throws InvalidNicknameException If the nickname provided is invalid.
     * @throws InvalidMoveException     If the move performed is invalid.
     * @throws InvalidChoiceException   If the choice made is invalid.
     */
    //ha senso gestire questi tipi di eccezioni qui?
    @Override
    public void receiveMessageTCP(Message message, HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
        String messageType = message.getMessageCode();

        switch (messageType) {

            case "Login" -> {
                System.out.println("Received ping from " /*+ client.getUsername()*/); //per CLI
                String nickname = message.getArgument(); //nel ping c'è anche nickname
                //controller.pong(nickname);
                if (controller.checkNickname(nickname) == 1) { // nickname non usato
                    if (controller.getGameState()!=GameState.LOGIN){ //se il gioco non è iniziato
                        sendMessageToClient(new Message("Pong", "The Game is already started"));
                    }
                    else{
                        if (!Objects.equals(controller.getFirstPlayer().getNickname(), nickname)){ //se è il primo giocatore
                            sendMessageToClient(new Message("Pong", "Nickname is valid"));
                            controller.addPlayer(nickname);//aggiungo il player
                            setNickname(nickname);
                            startPingThread(client); //thread che ogni 20 sec controlla se il giocatore è connesso
                            //se la lobby ha il numero di giocatori = maxnumberOfplayer, se non
                            if (controller.checkLobby()) { //se la lobby è piena inizia il gioco
                                controller.newGame();
                            }
                        }
                        else{
                            //if(controller.i)se la lobby non è piena, lo aggiungo
                            //altrimenti mando messaggio che è piena
                            //modificare add player, meglio gestire qui
                        }

                    }
                    //non controllo che sia il primo giocatore, ma il server si aspetterà numero di giocatori

                } else if (controller.checkNickname(nickname) == 0) {
                    sendMessageToClient(new Message("Pong", "Nickname is  already in use"));
                    try {//percheè??
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if ( controller.checkNickname(nickname) == 0) {
                        System.out.println(nickname + " requested login, but the username is already taken.");
                        //client.sendMessageToClient(new Message("UsernameRetry"));
                    } else {
                        System.out.println(nickname + " reconnected.");
                        //controller.addClient(nickname, client);
                        //resendGameToReconnectedClient(client);
                    }
                } else {
                    sendMessageToClient(new Message("Pong", "Nickname is  already in use by a disconnected player"));
                    System.out.println(nickname + " reconnected.");
                    //client.sendMessageToClient(new Message("username", username));
                    //setUsername(username);
                    //controller.addClient(username, client);
                    /*if (!controller.isGameLoaded) {
                        //resendGameToReconnectedClient(client);
                    } else {
                        resendToReconnectAfterServerDown(client);
                    }*/
                }

            }

            case "p?" -> {
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
    }

    /*public void checkNickname(HandleClientSocket client, String username, boolean firstGame, int checkStatus) throws RemoteException {

    }*/

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getNickname() {
        return Nickname;
    }

    public void startPingThread(HandleClientSocket client) throws RemoteException {
        String finalUsername = client.getNickname();

        Thread checkThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    if (!controller.pongReceived.contains(finalUsername) && !controller.disconnectedPlayers.contains(finalUsername)) {
                        System.err.println("Ping not received from " + finalUsername + ". Disconnecting.");
                        Thread.sleep(10000);
                        if (!controller.pongReceived.contains(finalUsername)) {
                            //disconnect(finalUsername);
                            break;
                        }
                    } else {
                        controller.pongReceived.remove(finalUsername);
                    }
                } catch (InterruptedException ignored) {
                    System.out.println("Ping thread interrupted.");
                }
            }
        });
        //controller.addCheckThread(checkThread);
        checkThread.start();
    }
}

