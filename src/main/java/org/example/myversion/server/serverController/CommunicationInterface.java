package org.example.myversion.server.serverController;

import jakarta.json.Json;
import org.example.myversion.client.Client;
import org.example.myversion.client.ClientCommunicationInterface;
import org.example.myversion.client.RMIClient;
import org.example.myversion.messages.Message;

import java.io.BufferedWriter;
import java.io.IOException;
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

    default void receiveMessageRMInew(String messageString,ClientCommunicationInterface client) throws RemoteException {
        Message message = new Message(Json.createReader(new StringReader(messageString)).readObject());
        String messageType = message.getMessageCode();

        switch (messageType) {
            case "Login" -> {
                System.out.println("Received Login request from " + client.getNickname());
                String nickname = message.getArgument();
                int checkNicknameStatus = controller.checkNickname(nickname);
                checkNicknameNew(client, nickname, checkNicknameStatus);
            }
            case "due" ->{
                numberOfPlayers(2,client);
                System.out.println("Impostato numero giocatori a 2");
            }
            case "tre" ->{
                numberOfPlayers(3,client);
                System.out.println("Impostato numero giocatori a 3");
            }
            case "quattro" ->{
                numberOfPlayers(3,client);
                System.out.println("Impostato numero giocatori a 4");
            }
            case "uno" -> {//per test
                numberOfPlayers(1,client);
                System.out.println("Impostato numero giocatori a 1");
            }

        }
    }


    default void rispostatest ()throws RemoteException{
        System.out.println("bellaaa");
    }


    default void numberOfPlayers (int num,ClientCommunicationInterface client) throws RemoteException{
        boolean isValidNumberOfPlayers = controller.checkNumberOfPlayer(num);
        if(!isValidNumberOfPlayers){//non dovrebbe mai succedere perche abbiamo controllo pre chimamata metodo lato client
            try {
                client.handleMessageNew("InvalidNumberOfPlayers");
            } catch (RemoteException e) {
                System.err.println("Error while sending numOfPlayersNotOK to client.");
            }

            //se il numero di giocatori è valido:
        } else {
            controller.setPlayersNumber(num);//setta il numero di giocatori
            if (controller.gameIsFull()) {
                controller.newGame();
                startGame(client);
                //System.out.println("devo inviare le started card...");
            } else {
                System.out.println("giocatori settati nel controller: "+controller.getPlayersNumber());
                System.out.println("numero di giocatori: "+num);
                try {
                    client.handleMessageNew("LobbyNotFull");
                } catch (RemoteException e) {
                    System.err.println("Error while sending waitingRoom to client.");
                }
            }
        }
    }

    default void checkNicknameNew(ClientCommunicationInterface client, String nickname, int checkNicknameStatus) throws RemoteException {
        switch (checkNicknameStatus) {
            case 1 -> {

                if(!controller.isGameStarted()){//se gioco non iniziato ancora
                    if(controller.isFirst()){//controllo se è il primo
                        controller.addClientRMI(nickname, client);
                        controller.addPlayer(nickname);//aggiungo player
                        System.out.println(nickname + " logged in."); //per debug
                        try {//chiedo numeri giocatori e gestisco eccezione
                            client.handleMessageNew("ChooseNumOfPlayer");
                        } catch (RemoteException e) {
                            System.err.println("Error 3 while sending message to " + nickname);
                            throw new RemoteException();
                        }
                    }else{

                        if(controller.gameIsFull()){//partita piena
                            System.out.println("Game started.");
                        }else{
                            controller.addClientRMI(nickname, client);
                            controller.addPlayer(nickname);//aggiungo player
                            System.out.println(nickname + " logged in."); //per debug
                            if(controller.gameIsFull()){
                                startGame(client);
                            }
                            /*try {//gestisco eccezione Remote, setto nickname
                                client.handleMessageNew("Nickname");
                            } catch (RemoteException e) {
                                System.err.println("Error 1 while sending message to " + nickname);
                                throw new RemoteException();
                            }*/
                        }
                    }
                }else{//gioco gia iniziato
                    try {//gestisco eccezione Remote
                        client.handleMessageNew("GameAlreadyStarted");
                    } catch (RemoteException e) {
                        System.err.println("Error 2 while sending message to " + nickname);
                        throw new RemoteException();
                    }


                }
            }
        }
    }

    default void startGame(ClientCommunicationInterface client) throws RemoteException {
        //HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        List<ObjectiveCard> commonObjectiveCards = controller.getCommonObjectiveCards();

        for (String nickname : rmiClients.keySet()) {
            /*
            for (String username : rmiClients.keySet()) {
                int position = controller.getPositionByUsername(username);
                try {
                    rmiClients.get(username).callBackSendMessage(myGame);
                } catch (RemoteException e) {
                    // e.printStackTrace();
                    System.err.println("Error while sending game to " + username);
                }
            }*/
            Message starterCardMessage = new Message("StarterCard", controller.getStarterCard());

            try{
                sendMessage(starterCardMessage,rmiClients.get(nickname));
            }catch (RemoteException e){
                e.printStackTrace();
                System.err.println("Error while sending game to " + nickname);
            }

            Message commmonObjectiveCardsMessage = new Message("CommonObjective", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            try{
                sendMessage(commmonObjectiveCardsMessage,rmiClients.get(nickname));
            }catch (RemoteException e){
                e.printStackTrace();
                System.err.println("Error while sending game to " + nickname);
            }

            List<ObjectiveCard> secretObjectiveCardsOptions = controller.getSecretObjectiveCardsOptions();
            Message secretObjectiveCardsOptionsMessage = new Message("SecretObjectiveCardsOptions", secretObjectiveCardsOptions.get(0), secretObjectiveCardsOptions.get(1));
            try{
                sendMessage(secretObjectiveCardsOptionsMessage,rmiClients.get(nickname));
            }catch (RemoteException e){
                e.printStackTrace();
                System.err.println("Error while sending game to " + nickname);
            }
        }
    }

    default void sendMessage(Message message,ClientCommunicationInterface client) throws RemoteException{
        try {
            String jsonString = message.getJson().toString();
            System.out.println(jsonString);
            //System.out.println("entra in sendmessage");
            client.receiveCard(jsonString);
           //client.handleMessageNew(jsonString);
            // server.receiveMessageRMI(jsonString, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    default void sendStartCondition() throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

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
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            if(!nickname.equals(currentPlayerNickname)) {
                tcpClients.get(nickname).sendMessageToClient(message);
            }
        }

        // TODO: do the same for the RMI clients
    }
}