package org.example.myversion.server.serverController;

import org.example.myversion.client.RMIClient;
import org.example.myversion.client.Client;
import org.example.myversion.messages.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Objects;

import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

public interface ServerInterface extends Remote{

    GameController controller = new GameController();

    public default void start() {
    }

    ;

    public default void stop() {
    }

    ;

    public default void receiveMessageTCP(Message message,HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
    }

    default void receiveMessage(Message message, Client client) throws RemoteException {
        String messageType = message.getMessageCode();

        switch (messageType) {

            case "Login" -> {
                System.out.println("Received ping from " /*+ client.getUsername()*/); //per CLI
                String nickname = message.getArgument(); // nickname

                if (controller.checkNickname(nickname) == 1) { // nickname non usato

                    if(!controller.isGameStarted()){//se gioco non iniziato ancora
                        try {//gestisco eccezione Remote
                            client.handleMessage(new Message("GameAlreadyStarted"));
                        } catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            throw new RemoteException();
                        }
                    }else{//gioco gia iniziato
                        try {//gestisco eccezione Remote
                            client.handleMessage(new Message("Nickname", nickname));
                        } catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            throw new RemoteException();
                        }

                        controller.addPlayer(nickname);//aggiungo player
                        System.out.println(nickname + " logged in."); //per debug

                        //controller.addClient(username, client);

                        startPingThread(client);

                        if(!Objects.equals(controller.getFirstPlayer().getNickname(), nickname)){//se è il primo giocatore
                            try {//chiedo numeri giocatori e gestisco eccezione
                                client.handleMessage(new Message("ChooseNumOfPlayer"));
                            } catch (RemoteException e) {
                                System.err.println("Error while sending message to " + nickname);
                                throw new RemoteException();
                            }
                        }
                        else{//se è piena si parte con la partita
                            if (controller.checkLobby()) {
                                controller.newGame();//si occupa questo di tutto?
                                System.out.println("Game started.");
                            } else if (!controller.checkLobby()) {

                                System.err.println("Aspettando altri giocatori..." );
                                //qui devo fare qulcosa??
                            }
                        }
                    }
                //nel caso in cui il Nickname sia gia in uso
                } else if (controller.checkNickname(nickname) == 0) {

                    try {
                        client.handleMessage(new Message("CheckingDisconnection"));
                    } catch (RemoteException e) {
                        System.err.println("Error while sending message to " + nickname);
                        throw new RemoteException();
                    }
                    try {//thread per fare cosa ??
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        System.err.println("Error while sleeping.");
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
                    client.handleMessage(new Message("Pong", "Nickname is  already in use by a disconnected player"));
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

    default void startPingThread(Client client) throws RemoteException {
        String username = null;
        try {
            username = client.getNickname();
        } catch (RemoteException e) {
            System.err.println("Error while getting username from client.");
        }
        String finalUsername = username;

        Thread checkThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    if (controller.game == null) {
                        break;
                    }
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

                }
            }
        });
        //controller.addCheckThread(checkThread);
        checkThread.start();
    }


}