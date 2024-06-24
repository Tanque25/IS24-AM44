package org.example.myversion.server.serverController;

import jakarta.json.Json;
import org.example.myversion.client.ClientCommunicationInterface;
import org.example.myversion.client.view.HandView;
import org.example.myversion.messages.Message;

import java.io.StringReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.Card;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidGameStateException;
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
            case "NumberOfPlayers" ->{
                //controller.setPlayersNumber(message.getNumber());
                switch (message.getNumber()){
                    case 0 ->{
                        System.out.println("Number of players isn't inizialized");
                    }
                    case 2 ->{
                        numberOfPlayers(2,client);
                        System.out.println("Impostato numero giocatori a 2");
                    }
                    case 3 ->{
                        numberOfPlayers(3,client);
                        System.out.println("Impostato numero giocatori a 3");
                    }
                    case 4 ->{
                        numberOfPlayers(4,client);
                        System.out.println("Impostato numero giocatori a 4");
                    }
                    default -> System.out.println("Number o");
                }

            }
            case "StarterCard" ->{

                System.out.println("Starter card side from: "+client.getNickname());
                controller.playStarterCard(controller.getPlayerFromNickname(client.getNickname()), message.getStarterCard());
            }

            case "CardToPlayChoice"->{
                String nickname = client.getNickname();
                System.out.println("Playing card from: "+nickname);
                try {
                    // If it's a PlayableCard call getPlayableCard(), otherwise call getGoldCard()
                    if (message.getJson().containsKey("playableCard")) {
                        controller.playCard(nickname, message.getPlayableCard(), message.getCoordinates());
                        updateClientsPlayedCard(message.getPlayableCard(), message.getCoordinates());
                    } else {
                        controller.playCard(nickname, message.getGoldCard(), message.getCoordinates());
                        updateClientsPlayedCard(message.getGoldCard(), message.getCoordinates());
                    }

                    sendMessage(new Message("VisibleCards", controller.getVisibleResourceCards(), controller.getRsourceDeckPeek(), controller.getVisibleGoldCards(), controller.getGoldDeckPeek()),client);

                    client.handleMessageNew("DrawCard");
                } catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                    client.handleMessageNew("InvalidMove");
                } catch (InvalidNicknameException | InvalidGameStateException e) {
                    // The nickname will always be valid
                    // The game state will always be valid
                    // sendMessageToClient(new Message("InvalidMove"));
                }
            }
            case "ObjectiveCardChoice" -> {

                controller.chooseObjectiveCard(controller.getPlayerFromNickname( client.getNickname()), message.getObjectiveCard());
                // When the server receives the player's secret objective choice, the readyPlayersNumber is updated
                controller.updateReadyPlayersNumber();
                System.out.println("ReadyPlayersNumber: " +controller.getReadyPlayersNumber());
                System.out.println("getPlayersNumber: " +controller.getPlayersNumber());
                // When all the players are ready, the servers sends every player the other players' hands and play areas and starts the turns cycle
                if (controller.getReadyPlayersNumber() == controller.getPlayersNumber() ) {
                    sendStartCondition();
                }
            }
            case "CardToDrawChoice" -> {
                int cardToDrawChoice = message.getNumber();
                String nickname = client.getNickname();
                switch (cardToDrawChoice) {
                    case 0, 1 -> {
                        try {
                            PlayableCard chosenCard = controller.getVisibleResourceCards().get(cardToDrawChoice);
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException  | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2, 3 -> {
                        try {
                            GoldCard chosenCard = controller.getVisibleGoldCards().get(cardToDrawChoice-2);
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException  | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        try {
                            PlayableCard chosenCard = controller.getRsourceDeckPeek();
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException  | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        try {
                            GoldCard chosenCard = controller.getGoldDeckPeek();
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException  | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                }
                updateScores();
                // The current turn is finished, it passes to the next player.
                changeTurn();

            }


        }
    }

    default void updateClientsPlayedCard(PlayableCard playedCard, Coordinates coordinates) throws RemoteException {
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdatePlayedCard", nickname, playedCard, coordinates);

        sendMessageToAll( updateMessage);
    }

    default void updateClientsDrawnCard(PlayableCard drawnCard) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll( updateMessage);
    }

    default void updateClientsDrawnCard(GoldCard drawnCard) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll( updateMessage);
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
                startGame();
                //System.out.println("devo inviare le started card...");
            } else {
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
                    System.out.println(nickname + " logged in.");

                    controller.addPlayer(nickname);
                    controller.addClientRMI(nickname, client);

                    if(controller.isFirst()){//controllo se è il primo

                        try {//chiedo numeri giocatori e gestisco eccezione
                            client.handleMessageNew("ChooseNumOfPlayer");

                        } catch (RemoteException e) {
                            System.err.println("Error 3 while sending message to " + nickname);
                            throw new RemoteException();
                        }
                    }else{

                        if(controller.gameIsFull()){//partita piena
                            System.out.println("starting game");
                            startGame();
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

    default void startGame() throws RemoteException {
        controller.setGameState(GameState.IN_GAME);

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

        for (String nickname : rmiClients.keySet()) {

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

    default void startRestoredGame() throws RemoteException {
        System.out.println("Starting restored game.");

        controller.setGameState(GameState.IN_GAME);

        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        // Getting the drawable cards
        List<PlayableCard> visibleResourceCards = controller.getVisibleResourceCards();
        PlayableCard coveredResourceCard = controller.getRsourceDeckPeek();
        List<GoldCard> visibleGoldCards = controller.getVisibleGoldCards();
        GoldCard coveredGoldCard = controller.getGoldDeckPeek();

        // Getting the common objectives
        List<ObjectiveCard> commonObjectiveCards = controller.getCommonObjectiveCards();

        // Getting the scores
        Map<String, Integer> scores = controller.getScores();

        // Getting the hands map
        Map<String, List<PlayableCard>> handsMap = controller.getHandsMap();

        // Getting the play areas map
        Map<String, Card[][]> playAreasMap = controller.getPlayAreasMap();

        // Send restored game to all tcp clients
        for (String nickname : tcpClients.keySet()) {
            Message visibleCardsMessage = new Message("VisibleCards", visibleResourceCards, coveredResourceCard, visibleGoldCards, coveredGoldCard);
            tcpClients.get(nickname).sendMessageToClient(visibleCardsMessage);
            Message commmonObjectiveCardsMessage = new Message("CommonObjectiveCards", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            tcpClients.get(nickname).sendMessageToClient(commmonObjectiveCardsMessage);
            Message gameDataMessage = new Message("GameData", scores, handsMap, playAreasMap);
            tcpClients.get(nickname).sendMessageToClient(gameDataMessage);
        }

        // TODO: same logic for RMI clients

        startTurn();
    }

    default void sendMessage(Message message,ClientCommunicationInterface client) throws RemoteException{
        String jsonString = message.getJson().toString();
        System.out.println(jsonString);
        try {
            client.receiveCard(jsonString);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    default void sendStartCondition() throws RemoteException {
        System.out.println("Entra in start");
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            Message startConditionMessage = new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap());
            tcpClients.get(nickname).sendMessageToClient(startConditionMessage);
        }

        for (String nickname : rmiClients.keySet()) {
            System.out.println("nickname: " + nickname);
            HandView handView = new HandView();
            Set<String> nicknames = controller.getPlayersHandsMap().keySet();
            for (String nick : nicknames)
                handView.displayHand(controller.getPlayersHandsMap().get(nick));

            sendMessage(new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap()),rmiClients.get(nickname));
        }
        startTurn();
    }

    default void startTurn() throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message myTurn = new Message("MyTurn");

        if (controller.isTCP(nickname))
            controller.getTcpClients().get(nickname).sendMessageToClient(myTurn);

        if(controller.isRMI(nickname))
            controller.getRmiClients().get(nickname).handleMessageNew("MyTurn");

        Message otherTurn = new Message("OtherTurn", nickname);
        sendMessageToAllExcept(nickname, otherTurn);
    }

    default void changeTurn() throws RemoteException {
        // Update the current player
        controller.changeTurn();

        if(controller.getGameState() == GameState.IN_GAME) {
            controller.saveGame();
            startTurn();
        } else if (controller.getGameState() == GameState.LAST_ROUND) {
            controller.saveGame();
            Message lastRound = new Message("LastRound");
            sendMessageToAll(lastRound);
            startTurn();
        } else if (controller.getGameState() == GameState.END) {
            Message finalScores = new Message("FinalScores", controller.getFinalScores());
            sendMessageToAll(finalScores);
            Message endGame = new Message("EndGame", controller.findWinner());
            sendMessageToAll(endGame);
        }

    }

    default void sendMessageToAll (Message message) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            tcpClients.get(nickname).sendMessageToClient(message);
        }
        for (String nickname : rmiClients.keySet()) {
            sendMessage(message,rmiClients.get(nickname));
        }
    }

    default void sendMessageToAllExcept (String currentPlayerNickname, Message message) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        for (String nickname : tcpClients.keySet()) {
            if(!nickname.equals(currentPlayerNickname)) {
                tcpClients.get(nickname).sendMessageToClient(message);
            }
        }
        sendMessageToAllExceptRMI(currentPlayerNickname,"OtherTurn");
    }

    default void updateScores() throws RemoteException {
        Map<String, Integer> scores = controller.getScores();

        Message scoresMessage = new Message("Scores", scores);

        sendMessageToAll(scoresMessage);
    }


    default void sendMessageToAllExceptRMI (String currentPlayerNickname, String scelta) throws RemoteException {

        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : rmiClients.keySet()) {
            if(!nickname.equals(currentPlayerNickname)) {
                rmiClients.get(nickname).handleMessageNew(scelta);
            }
        }
    }
}