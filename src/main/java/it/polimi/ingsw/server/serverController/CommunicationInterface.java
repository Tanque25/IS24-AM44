package it.polimi.ingsw.server.serverController;

import jakarta.json.Json;
import it.polimi.ingsw.client.ClientCommunicationInterface;
import it.polimi.ingsw.messages.Message;

import java.io.StringReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.server.model.Coordinates;
import it.polimi.ingsw.server.model.decks.cards.Card;
import it.polimi.ingsw.server.model.decks.cards.GoldCard;
import it.polimi.ingsw.server.model.decks.cards.ObjectiveCard;
import it.polimi.ingsw.server.model.decks.cards.PlayableCard;
import it.polimi.ingsw.server.model.exceptions.InvalidChoiceException;
import it.polimi.ingsw.server.model.exceptions.InvalidGameStateException;
import it.polimi.ingsw.server.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.server.model.exceptions.InvalidNicknameException;

public interface CommunicationInterface extends Remote {

    GameController controller = new GameController();

    int TCP_PORT = 54321;
    int RMI_PORT = 59666;


    default void receiveMessageTCP(Message message, HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
    }

    /**
     * Handles incoming messages from a remote client via RMI (Remote Method Invocation).
     * The method decodes the JSON string to a {@code Message} object and performs actions based on the message type.
     *
     * @param messageString The JSON string representing the message from the client.
     * @param client        The {@code ClientCommunicationInterface} instance representing the remote client.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void receiveMessageRMI(String messageString, ClientCommunicationInterface client) throws RemoteException {
        Message message = new Message(Json.createReader(new StringReader(messageString)).readObject());
        String messageType = message.getMessageCode();

        switch (messageType) {
            case "Login" -> {
                String nickname = message.getArgument();
                int checkNicknameStatus = controller.checkNickname(nickname);
                checkNickname(client, nickname, checkNicknameStatus);
            }
            case "NumberOfPlayers" -> {
                switch (message.getNumber()) {
                    case 2 -> numberOfPlayers(2, client);
                    case 3 -> numberOfPlayers(3, client);
                    case 4 -> numberOfPlayers(4, client);
                    default -> System.err.println("Invalid number of players");
                }
            }
            case "StarterCard" ->
                    controller.playStarterCard(controller.getPlayerFromNickname(client.getNickname()), message.getStarterCard());
            case "CardToPlayChoice" -> {
                String nickname = client.getNickname();
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
                        try {
                            sendMessage(new Message("VisibleCards", controller.getVisibleResourceCards(), controller.getRsourceDeckPeek(), controller.getVisibleGoldCards(), controller.getGoldDeckPeek()), client);
                            client.handleMessageNew("DrawCard");
                        }catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            try {
                                sendMessageToAll(new Message("ClientDisconnected"));
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

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
                controller.chooseObjectiveCard(controller.getPlayerFromNickname(client.getNickname()), message.getObjectiveCard());

                // When the server receives the player's secret objective choice, the readyPlayersNumber is updated
                controller.updateReadyPlayersNumber();

                // When all the players are ready, the servers sends every player the other players' hands and play areas and starts the turns cycle
                if (controller.getReadyPlayersNumber() == controller.getPlayersNumber()) {
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
                        } catch (InvalidGameStateException | InvalidChoiceException | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2, 3 -> {
                        try {
                            GoldCard chosenCard = controller.getVisibleGoldCards().get(cardToDrawChoice - 2);
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        try {
                            PlayableCard chosenCard = controller.getRsourceDeckPeek();
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException | InvalidNicknameException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        try {
                            GoldCard chosenCard = controller.getGoldDeckPeek();
                            controller.drawCard(nickname, chosenCard);
                            updateClientsDrawnCard(chosenCard);
                        } catch (InvalidGameStateException | InvalidChoiceException | InvalidNicknameException e) {
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

    /**
     * Notifies all clients about a played {@code PlayableCard} with its coordinates.
     *
     * @param playedCard  The {@code PlayableCard} that was played.
     * @param coordinates The {@code Coordinates} where the card was played.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void updateClientsPlayedCard(PlayableCard playedCard, Coordinates coordinates) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdatePlayedCard", nickname, playedCard, coordinates);

        sendMessageToAll(updateMessage);
    }

    /**
     * Notifies all clients about a played {@code GoldCard} with its coordinates.
     *
     * @param playedCard  The {@code GoldCard} that was played.
     * @param coordinates The {@code Coordinates} where the card was played.
     * @throws RemoteException If an RMI communication-related error arrives.
     */
    default void updateClientsPlayedCard(GoldCard playedCard, Coordinates coordinates) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdatePlayedCard", nickname, playedCard, coordinates);

        sendMessageToAll(updateMessage);
    }

    /**
     * Notifies all clients about a drawn {@code PlayableCard}.
     *
     * @param drawnCard The {@code PlayableCard} that was drawn.
     * @throws RemoteException If an RMI communication error arrives.
     */
    default void updateClientsDrawnCard(PlayableCard drawnCard) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll(updateMessage);
    }

    /**
     * Notifies all clients about a drawn {@code GoldCard}.
     *
     * @param drawnCard The {@code GoldCard} that was drawn.
     * @throws RemoteException If an RMI communication error occurs.
     */
    default void updateClientsDrawnCard(GoldCard drawnCard) throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message updateMessage = new Message("UpdateDrawnCard", nickname, drawnCard, null);

        sendMessageToAll(updateMessage);
    }

    /**
     * Handles the number of players for the game.
     * Validates the provided number of players and starts the game if the number is correct and the game is full.
     *
     * @param num    The number of players for the game.
     * @param client The {@code ClientCommunicationInterface} instance representing the remote client.
     * @throws RemoteException If an RMI communication error arrives.
     */
    default void numberOfPlayers(int num, ClientCommunicationInterface client) throws RemoteException {
        boolean isValidNumberOfPlayers = controller.checkNumberOfPlayer(num);
        if (!isValidNumberOfPlayers) {//non dovrebbe mai succedere perche abbiamo controllo pre chimamata metodo lato client
            try {
                client.handleMessageNew("InvalidNumberOfPlayers");
            } catch (RemoteException e) {
                System.err.println("Error while sending numOfPlayersNotOK to client.");
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                stop();
            }

            //se il numero di giocatori è valido:
        } else {
            controller.setPlayersNumber(num);//setta il numero di giocatori
            if (controller.gameIsFull()) {
                controller.newGame();
                startGame();
            }
        }
    }

    /**
     * Handles the validation and processing of a client's nickname during login.
     * Based on the status of the nickname check, it performs appropriate actions including handling game states,
     * notifying clients, adding players, and starting or restoring the game.
     *
     *
     * @param client              The {@code ClientCommunicationInterface} instance representing the remote client.
     * @param nickname            The nickname of the player attempting to log in.
     * @param checkNicknameStatus The status code indicating the result of the nickname check.
     *
     * @throws RemoteException If an RMI communication error arrives
     */
    default void checkNickname(ClientCommunicationInterface client, String nickname, int checkNicknameStatus) throws RemoteException {
        switch (checkNicknameStatus) {
            case 1 -> {
                if (!controller.getGameState().equals(GameState.LOGIN) && !controller.getGameState().equals(GameState.INITIALIZATION)) {
                    try {//gestisco eccezione Remote
                        client.handleMessageNew("GameAlreadyStarted");
                    } catch (RemoteException e) {
                        System.err.println("Error while sending message to " + nickname);
                        try {
                            sendMessageToAll(new Message("ClientDisconnected"));
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    controller.addPlayer(nickname);
                    System.out.println(nickname + " logged in.");

                    controller.addClientRMI(nickname, client);

                    if (controller.isFirst()) {
                        try {
                            client.handleMessageNew("ChooseNumOfPlayer");
                        } catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            try {
                                sendMessageToAll(new Message("ClientDisconnected"));
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                        try {//chiedo numeri giocatori e gestisco eccezione
                            client.handleMessageNew("WaitForOtherPlayers");

                        } catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            try {
                                sendMessageToAll(new Message("ClientDisconnected"));
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        try {//chiedo numeri giocatori e gestisco eccezione
                            client.handleMessageNew("WaitForOtherPlayers");

                        } catch (RemoteException e) {
                            System.err.println("Error while sending message to " + nickname);
                            try {
                                sendMessageToAll(new Message("ClientDisconnected"));
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                        // If this is the last player to reach the max player number, the game starts
                        if (controller.getPlayersNumber() != 0 && controller.gameIsFull()) {
                            System.out.println("Game is full.");
                            startGame();
                            System.out.println("Game started.");
                        }
                    }
                }
            }case 0 -> {
                // The username has already been taken, retry
                checkNicknameStatus = controller.checkNickname(nickname);
                if(checkNicknameStatus == 0) {
                    System.out.println(nickname + " requested login, but the username is already taken.");
                    sendMessage(new Message("TakenUsername"),client);
                }
            }
            case -1 -> {
                // The player is trying to reconnect to a saved game
                System.out.println(nickname + " reconnected.");
                //setNickname(nickname);
                controller.addClientRMI(nickname, client);

                try {
                    client.handleMessageNew("WaitForOtherPlayers");

                } catch (RemoteException e) {
                    System.err.println("Error while sending message to " + nickname);
                    try {
                        sendMessageToAll(new Message("ClientDisconnected"));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // If this is the last player has reconnected, the game starts
                if (controller.getDisconnectedPlayers().isEmpty()) {
                    System.out.println("Game is full.");
                    startRestoredGame();
                    System.out.println("Game restored.");

                }
            }
        }
    }

    /**
     * Initializes and starts the game by setting the game state to {@code IN_GAME} and sending initial game data to all clients.
     * This includes visible resource cards, starter cards, and common and secret objective cards.
     *
     * @throws RemoteException If an RMI communication-related error occurs.
     */
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

            try {
                sendMessage(starterCardMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Message commmonObjectiveCardsMessage = new Message("CommonObjective", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            try {
                sendMessage(commmonObjectiveCardsMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            List<ObjectiveCard> secretObjectiveCardsOptions = controller.getSecretObjectiveCardsOptions();
            Message secretObjectiveCardsOptionsMessage = new Message("SecretObjectiveCardsOptions", secretObjectiveCardsOptions.get(0), secretObjectiveCardsOptions.get(1));
            try {
                sendMessage(secretObjectiveCardsOptionsMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    /**
     * Restores and starts a saved game by setting the game state to {@code IN_GAME} and sending the restored game data to all clients.
     * This includes visible cards, common objectives, scores, hands, and play areas.
     *
     * @throws RemoteException If an RMI communication-related error arrives.
     */
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

        // Send restored game to all rmi clients
        for (String nickname : rmiClients.keySet()) {
            Message visibleCardsMessage = new Message("VisibleCards", visibleResourceCards, coveredResourceCard, visibleGoldCards, coveredGoldCard);
            try {
                sendMessage(visibleCardsMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
            Message commmonObjectiveCardsMessage = new Message("CommonObjectiveCards", commonObjectiveCards.get(0), commonObjectiveCards.get(1));
            try {
                sendMessage(commmonObjectiveCardsMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
            Message gameDataMessage = new Message("GameData", scores, handsMap, playAreasMap);
            try {
                sendMessage(gameDataMessage, rmiClients.get(nickname));
            } catch (RemoteException e) {
                System.err.println("Error while sending message to " + nickname);
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        startTurn();
    }


    /**
     * Sends a message to a client using the Remote Method Invocation (RMI) mechanism.
     * The message is converted to a JSON string and sent via the client's `receiveCard` method.

     * @param message The {@code Message} object to be sent. This contains the data that needs to be transmitted to the client.
     * @param client The {@code ClientCommunicationInterface} through which the message is to be sent.
     * @throws RemoteException If an RMI communication-related error is presented
     */
    default void sendMessage(Message message, ClientCommunicationInterface client) throws RemoteException {
        String jsonString = message.getJson().toString();
        try {
            client.receiveCard(jsonString);
        } catch (RemoteException e) {
            System.err.println("Error while sending message, client disconnected");
        }
    }

    /**
     * Sends the start condition of the game to all connected clients via both TCP and RMI.
     * The start condition includes each player's starter cards and hands.
     *
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void sendStartCondition() throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            Message startConditionMessage = new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap());
            tcpClients.get(nickname).sendMessageToClient(startConditionMessage);
        }

        for (String nickname : rmiClients.keySet()) {
            try{
                sendMessage(new Message("StartCondition", controller.getStarterCardsMap(), controller.getPlayersHandsMap()), rmiClients.get(nickname));
            }catch (RemoteException e){
                System.err.println("Error while sending message, client disconnected");
                try {
                    sendMessageToAll(new Message("ClientDisconnected"));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        startTurn();
    }

    default void startTurn() throws RemoteException {
        String nickname = controller.getCurrentPlayer().getNickname();

        Message myTurn = new Message("MyTurn");

        if (controller.isTCP(nickname))
            controller.getTcpClients().get(nickname).sendMessageToClient(myTurn);

        if (controller.isRMI(nickname))
            controller.getRmiClients().get(nickname).handleMessageNew("MyTurn");

        Message otherTurn = new Message("OtherTurn", nickname);
        sendMessageToAllExcept(nickname, otherTurn);
    }

    /**
     * Changes the turn in the game and handles game state transitions accordingly.
     * Updates the current player and determines the next actions based on the game state:
     * - Saves the game state and starts a new turn if the game is ongoing.
     * - Sends a "LastRound" message to all clients if it is the last round.
     * - Sends final scores and announces the game winner if the game has ended.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void changeTurn() throws RemoteException {
        // Update the current player
        controller.changeTurn();

        if (controller.getGameState() == GameState.IN_GAME) {
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

    /**
     * Sends a message to all connected clients, both TCP and RMI.
     *
     * @param message The message to be sent to all clients.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void sendMessageToAll(Message message) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : tcpClients.keySet()) {
            tcpClients.get(nickname).sendMessageToClient(message);
        }
        for (String nickname : rmiClients.keySet()) {
            sendMessage(message, rmiClients.get(nickname));

        }
    }


    /**
     * Sends a message to all connected TCP clients except the specified player's client.
     * Also sends a specific message to all RMI clients except the specified player.
     *
     * @param currentPlayerNickname The nickname of the current player whose client should not receive the message.
     * @param message The message to be sent to all TCP clients except the specified player.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void sendMessageToAllExcept(String currentPlayerNickname, Message message) throws RemoteException {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();

        for (String nickname : tcpClients.keySet()) {
            if (!nickname.equals(currentPlayerNickname)) {
                tcpClients.get(nickname).sendMessageToClient(message);
            }
        }
        sendMessageToAllExceptRMI(currentPlayerNickname, "OtherTurn");
    }


    /**
     * Updates and broadcasts the scores to all connected clients.
     *
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void updateScores() throws RemoteException {
        Map<String, Integer> scores = controller.getScores();

        Message scoresMessage = new Message("Scores", scores);

        sendMessageToAll(scoresMessage);
    }

    /**
     * Sends a message to all connected RMI clients except the current player.
     * This method is typically used to broadcast a message or notification to all clients,
     * excluding the client whose nickname matches the current player's nickname.
     *
     * @param currentPlayerNickname The nickname of the current player whose client should not receive the message.
     * @param scelta The choice or action represented by the message to be sent.
     * @throws RemoteException If an RMI communication-related error occurs.
     */
    default void sendMessageToAllExceptRMI(String currentPlayerNickname, String scelta) throws RemoteException {

        HashMap<String, ClientCommunicationInterface> rmiClients = controller.getRmiClients();

        for (String nickname : rmiClients.keySet()) {
            if (!nickname.equals(currentPlayerNickname)) {
                try {
                    rmiClients.get(nickname).handleMessageNew(scelta);
                }catch (RemoteException e){
                    System.err.println("Error while sending message, client disconnected");
                    try {
                        sendMessageToAll(new Message("ClientDisconnected"));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    default void stop() throws RemoteException{

        System.out.println("Server Closed.");
        // Exit the application
        System.exit(0);
    }
}