package org.example.myversion.server.serverController;

import org.example.myversion.client.Client;
import org.example.myversion.messages.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;

import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

public interface ServerInterface extends Remote{

    GameController controller = new GameController();

    int RMI_PORT = 70;

    public default void start() {
    }

    public default void stop() {
    }

    default void receiveMessageTCP(Message message,HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
    }

    default void receiveMessageRMI(Message message, Client client) throws RemoteException {
        String messageType = message.getMessageCode();

        switch (messageType) {

            case "Login" -> {
                System.out.println("Received Login request from " + client.getNickname()); //per CLI, debug
                String nickname = message.getArgument(); // nickname

                //CASO 1: nickname libero
                if (controller.checkNickname(nickname) == 1) {

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

                        //controller.addClientRMI(username, client);

                        startPingThread(client);

                        if(controller.isFirst()){//se è il primo giocatore
                            try {//chiedo numeri giocatori e gestisco eccezione
                                client.handleMessage(new Message("ChooseNumOfPlayer"));
                            } catch (RemoteException e) {
                                System.err.println("Error while sending message to " + nickname);
                                throw new RemoteException();
                            }
                        }
                        else{//se la lobby è piena si parte con la partita
                            if (controller.gameIsFull()) {
                                controller.newGame();//si occupa questo di tutto? startGame()
                                System.out.println("Game started.");

                            } else if (!controller.gameIsFull()) {//altrimenti:

                                System.err.println("Aspettando altri giocatori..." );
                                //qui devo fare qualcosa??
                            }
                        }
                    }

                    //CASO 2: nel caso in cui il Nickname sia gia in uso
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
                        //controller.addClientRMI(nickname, client);
                        //resendGameToReconnectedClient(client);
                    }

                    //CASO 3: l'username è gia utilizzato, ma il giocatore era disconnesso e prova a riconnettersi
                } else {

                    System.out.println(nickname + " reconnected.");
                    client.setNickname(nickname);
                    controller.addClientRMI(nickname, client);
                    //gestione della PERSISTENZA:
                    /*if (!controller.isGameLoaded) {
                        resendGameToReconnectedClient(client);
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

            case "numOfPlayersMessage" -> {
                int numberOfPlayers = message.getMaxPlayers();//numero di giocatori
                boolean isValidNumberOfPlayers = controller.checkNumberOfPlayer(numberOfPlayers);

                //numero di giocatori NON è valido:
                if(isValidNumberOfPlayers){
                    try {
                        client.handleMessage(new Message("numOfPlayersNotOK"));
                    } catch (RemoteException e) {
                        System.err.println("Error while sending numOfPlayersNotOK to client.");
                    }

                    //se il numero di giocatori è valido:
                } else {
                    controller.setPlayersNumber(numberOfPlayers);//setta il numero di giocatori
                    if (controller.gameIsFull()) {
                        controller.newGame();
                        //startGame();????
                    } else {
                        try {
                            client.handleMessage(new Message("Lobby...in attesa di altri giocatori..."));
                        } catch (RemoteException e) {
                            System.err.println("Error while sending waitingRoom to client.");
                        }
                    }
                }
            }
            /*
            case "DrawCard" -> {
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
            }*/
        }
    }
    default void startPingThread(Client client) throws RemoteException {
        String username = null;
        username = client.getNickname();
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
    /**
     * It is going to start the game so that each player receives the common objective cards and gets to choose his secret objective card.
     */
    default void startGame() {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        List<ObjectiveCard> commonObjectiveCards = controller.getCommonObjectiveCards();

        for (String nickname : tcpClients.keySet()) {
            Message starterCardMessage = new Message("StarterCard", controller.getStarterCard());
            tcpClients.get(nickname).sendMessageToClient(starterCardMessage);

            Message commmonObjectiveCardsMessage = new Message("CommonObjectiveCards", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            tcpClients.get(nickname).sendMessageToClient(commmonObjectiveCardsMessage);

            List<ObjectiveCard> secretObjectiveCardsOptions = controller.getSecretObjectiveCardsOptions();
            Message secretObjectiveCardsOptionsMessage = new Message("SecretObjectiveCardsOptions", secretObjectiveCardsOptions.get(0), secretObjectiveCardsOptions.get(1));
            tcpClients.get(nickname).sendMessageToClient(secretObjectiveCardsOptionsMessage);
        }

        // TODO: do the same for the RMI clients
    }

    default void sendStartCondition() {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            Message startConditionMessage = new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap());
            tcpClients.get(nickname).sendMessageToClient(startConditionMessage);
        }

        // TODO: do the same for the RMI clients

        startTurn();
    }

    default void startTurn() {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message myTurn = new Message("MyTurn");

        if (controller.isTCP(nickname))
            controller.getTcpClients().get(nickname).sendMessageToClient(myTurn);

        // TODO: handle RMI client's turn

        Message otherTurn = new Message("OtherTurn", nickname);
        sendMessageToAllExcept(nickname, otherTurn);
    }

    default void sendMessageToAllExcept(String currentPlayerNickname, Message message) {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            if(!nickname.equals(currentPlayerNickname)) {
                tcpClients.get(nickname).sendMessageToClient(message);
            }
        }

        // TODO: do the same for the RMI clients
    }


}