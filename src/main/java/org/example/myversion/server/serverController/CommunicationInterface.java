package org.example.myversion.server.serverController;

import jakarta.json.Json;
import org.example.myversion.client.Client;
import org.example.myversion.client.ClientCommunicationInterface;
import org.example.myversion.client.RMIClient;
import org.example.myversion.messages.Message;

import java.io.StringReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.HashMap;

import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

public interface CommunicationInterface extends Remote {

    GameController controller = new GameController();

    int TCP_PORT = 8080;
    int RMI_PORT = 70;

    default void receiveMessageTCP(Message message, HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
    }

    default void receiveMessageRMI(String messageString, ClientCommunicationInterface client) throws RemoteException {
        Message message = new Message(Json.createReader(new StringReader(messageString)).readObject());
        String messageType = message.getMessageCode();

        switch (messageType) {
            case "Login" -> {
                System.out.println("Received Login request from " + client.getNickname());
                String nickname = message.getArgument();
                int checkNicknameStatus = controller.checkNickname(nickname);
                checkNickname(client, nickname, checkNicknameStatus);

            }
        }
    }

    default void rispostatest ()throws RemoteException{
        System.out.println("bellaaa");
    }

    default void receiveMessageRMIOld(String messageString, ClientCommunicationInterface client) throws RemoteException {
        Message message = new Message(Json.createReader(new StringReader(messageString)).readObject());

        String messageType = message.getMessageCode();
        switch (messageType) {

            case "Login" -> {
                System.out.println("Received Login request from " + client.getNickname()); //per CLI, debug
                String nickname = message.getArgument(); // nickname
                int checkNicknameStatus = controller.checkNickname(nickname);
                checkNickname(client, nickname, checkNicknameStatus);
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
        }
    }



    default void startGame() throws RemoteException {
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

    default void sendStartCondition() throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            Message startConditionMessage = new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap());
            tcpClients.get(nickname).sendMessageToClient(startConditionMessage);
        }

        // TODO: do the same for the RMI clients

        startTurn();
    }

    default void startTurn() throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message myTurn = new Message("MyTurn");

        if (controller.isTCP(nickname))
            controller.getTcpClients().get(nickname).sendMessageToClient(myTurn);

        // TODO: handle RMI client's turn

        Message otherTurn = new Message("OtherTurn", nickname);
        sendMessageToAllExcept(nickname, otherTurn);
    }

    default void sendMessageToAllExcept (String currentPlayerNickname, Message message) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            if(!nickname.equals(currentPlayerNickname)) {
                tcpClients.get(nickname).sendMessageToClient(message);
            }
        }

        // TODO: do the same for the RMI clients
    }

    default void checkNickname(ClientCommunicationInterface client, String nickname, int checkNicknameStatus) throws RemoteException {
        switch (checkNicknameStatus) {
            case 1 -> {
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

                    // startPingThread(client);

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
            }
            case 0 ->{//CASO 2: nel caso in cui il Nickname sia gia in uso

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
            }
            case -1 ->{
                //CASO 3: l'username è gia utilizzato, ma il giocatore era disconnesso e prova a riconnettersi

                System.out.println(nickname + " reconnected.");
                client.setNickname(nickname);

                // TODO: Commentato perchè da problemi mentre provo la sendMessage dal client
                // controller.addClientRMI(nickname, client);

                //gestione della PERSISTENZA:
                    /*if (!controller.isGameLoaded) {
                        resendGameToReconnectedClient(client);
                    } else {
                        resendToReconnectAfterServerDown(client);
                    }*/
            }
        }
    }
}