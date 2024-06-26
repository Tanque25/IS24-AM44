package org.example.myversion.client;

import jakarta.json.Json;
import org.example.myversion.client.view.GameView;
import org.example.myversion.messages.ChatMessage;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Coordinates;

import java.io.IOException;
import java.util.Scanner;
import java.io.Serializable;
import java.io.StringReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This class represents the client.
 * It is abstract because each client will either be an RMI client or a Socket client.
 */
public abstract class Client extends UnicastRemoteObject implements Serializable, ClientCommunicationInterface {
    private String nickname;
    private GameView gameView;
    private boolean serverConnection;
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Client() throws RemoteException {
        super();
    }

    /**
     * Connects to the server.
     *
     * @throws IOException       if the connection fails.
     * @throws NotBoundException if the server is not bound.
     */
    public abstract void connect(String hostname) throws IOException, NotBoundException;

    /**
     * Parses and handles messages received from the server.
     *
     * @param message the message received from the server.
     */
    public void handleMessage(Message message)throws RemoteException {
        String messageCode = message.getMessageCode();

        switch (messageCode) {
            case "TakenUsername" -> {
                try {
                    gameView.showTakenUsername();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "GameAlreadyStarted" ->
                    gameView.showGameAlreadyStartedMessage();
            case "PlayersNumber" ->{
                try{
                    gameView.playersNumberChoice();
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "InvalidNumberOfPlayers" ->{
                try{
                    gameView.invalidPlayersNumberChoice();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "WaitForOtherPlayers" ->
                    gameView.waitForOtherPlayers();
            case "VisibleCards" ->{
                gameView.setVisibleResourceCards(message.getResourceCards());
                gameView.setCoveredResourceCard(message.getPlayableCard());
                gameView.setVisibleGoldCards(message.getGoldCards());
                gameView.setCoveredGoldCard(message.getGoldCard());

                gameView.showVisibleCards();
            }
            case "StarterCard" -> {
                try {
                    gameView.showStarterCard(message.getStarterCard());
                    gameView.starterCardSideChoice(message.getStarterCard());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "CommonObjectiveCards" ->
                    gameView.showCommonObjectives(message.getObjectiveCards());
            case "SecretObjectiveCardsOptions" -> {
                try {
                    gameView.showSecretObjectives(message.getObjectiveCards());
                    gameView.secretObjectiveCardChoice(message.getObjectiveCards());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "StartCondition" -> {
                gameView.setHandsMap(message.getPlayersHandsMap());

                gameView.initializePlayAreas(message.getStarterCardsMap());

                gameView.showOthersHandsAndPlayAreas(); // Questi tre metodi verranno compattati in GUI, nel senso
                gameView.showMyHand();                  // che uno solo farà tutte e tre le cose. Si potrebbe mettere in CLI
                gameView.showMyPlayArea();              // un metodo che le chiama tutte e 3 per avere interfaccia comune
            }
            case "GameData" -> {
                gameView.setHandsMap(message.getPlayersHandsMap());
                gameView.setPlayAreasMap(message.getPlayAreasMap());

                gameView.showOthersHandsAndPlayAreas(); // Questi tre metodi verranno compattati in GUI, nel senso
                gameView.showMyHand();                  // che uno solo farà tutte e tre le cose. Si potrebbe mettere in CLI
                gameView.showMyPlayArea();              // un metodo che le chiama tutte e 3 per avere interfaccia comune
            }
            case "MyTurn" -> {
                gameView.showMessage("\nIt's your turn.\n");
                myTurn();
            }
            case "OtherTurn" -> {
                gameView.showMessage("\nIt's " + message.getArgument() + "'s turn.\n");
            }
            case "InvalidMove" -> {
                try {
                    gameView.invalidMove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "UpdatePlayedCard" -> {
                String nickname = message.getArgument();
                Coordinates coordinates = message.getCoordinates();

                if (message.getJson().containsKey("playableCard")) {
                    gameView.playCard(nickname, message.getPlayableCard(), coordinates);
                } else {
                    gameView.playCard(nickname, message.getGoldCard(), coordinates);
                }

                gameView.showUpdatedPlayArea(nickname, gameView.getPlayAreasMap().get(nickname));
            }
            case "DrawCard" -> {
                try {
                    gameView.chooseCardToDraw();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "UpdateDrawnCard" -> {
                String nickname = message.getArgument();

                if (message.getJson().containsKey("playableCard")) {
                    gameView.drawCard(nickname, message.getPlayableCard());
                } else {
                    gameView.drawCard(nickname, message.getGoldCard());
                }
                gameView.showUpdatedHand(nickname);
            }
            case "UpdateChatSend" ->{
                try {
                    Scanner scanner = new Scanner(System.in);
                    //PER CLI e debug
                    System.out.print("Inserisci messaggio da inviare: "); //Richiesta di inserimento della stringa
                    String inputString = scanner.nextLine(); // Lettura della stringa inserita dall'utente
                    scanner.close();// Chiusura dello Scanner

                    sendChatMessage(new ChatMessage(inputString,this.getNickname()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "UpdateChatReceive" -> {

            }
            case "Scores" -> {
                gameView.showMessage("\nCurrent scores:\n");
                gameView.showScores(message.getScores());
            }
            case "LastRound" -> {
                gameView.showMessage("\nLast round\n");
            }
            case "FinalScores" -> {
                gameView.showMessage("\nFinal scores:\n");
                gameView.showScores(message.getScores());
            }
            case "EndGame" -> {
                gameView.showEndGame(message.getArgument());
            }
            default -> throw new IllegalArgumentException("Invalid messageCode: " + messageCode);
        }
    }

    public void handleMessageNew(String scelta)throws RemoteException {

        switch (scelta) {
            case "GameAlreadyStarted" ->
                    gameView.showGameAlreadyStartedMessage();
            case "ChooseNumOfPlayer" ->{
                try{
                    gameView.playersNumberChoice();
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "InvalidNumberOfPlayers" ->{
                try{
                    gameView.invalidPlayersNumberChoice();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "LobbyNotFull" ->{
                System.out.println("In attesa di altri giocatori...");
            }

            case "WaitForOtherPlayers" ->
                    gameView.waitForOtherPlayers();
            case "MyTurn" -> {
                gameView.showMessage("\nIt's your turn.\n");
                myTurn();
            }
            case "OtherTurn" -> {
                gameView.showMessage("\nIt's others turn.\n");
            }
            case "DrawCard" -> {
                System.out.println("Choose the card to draw.");
                try {
                    gameView.chooseCardToDraw();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "InvalidMove" -> {
                try {
                    gameView.invalidMove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> throw new IllegalArgumentException("Invalid messageCode: ");
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    public abstract void sendMessage(Message message) throws IOException;

    public abstract void sendChatMessage(ChatMessage message) throws IOException;

    public void myTurn() {
        try {
            gameView.chooseCardToPlay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveCard(String messageString) throws RemoteException{
        System.out.println("entra in receive card");
        Message message = new Message(Json.createReader(new StringReader(messageString)).readObject());
        String messageType = message.getMessageCode();

        switch (messageType){
            case "StarterCard"->{
                try {
                    gameView.showStarterCard(message.getStarterCard());
                    gameView.starterCardSideChoice(message.getStarterCard());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "CommonObjective" ->{
                gameView.showCommonObjectives(message.getObjectiveCards());
            }
            case "SecretObjectiveCardsOptions" -> {
                try {
                    gameView.showSecretObjectives(message.getObjectiveCards());
                    gameView.secretObjectiveCardChoice(message.getObjectiveCards());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            case "StartCondition" ->{
                System.out.println("é entrato in start condition");
                gameView.setHandsMap(message.getPlayersHandsMap());

                gameView.initializePlayAreas(message.getStarterCardsMap());

                gameView.showOthersHandsAndPlayAreas();
                gameView.showMyHand();
                gameView.showMyPlayArea();
            }
            case "UpdatePlayedCard" ->{
                String nickname = message.getArgument();
                Coordinates coordinates = message.getCoordinates();

                if (message.getJson().containsKey("playableCard")) {
                    gameView.playCard(nickname, message.getPlayableCard(), coordinates);
                } else {
                    gameView.playCard(nickname, message.getGoldCard(), coordinates);
                }

                gameView.showUpdatedPlayArea(nickname, gameView.getPlayAreasMap().get(nickname));
            }
            case "UpdateDrawnCard" -> {
                String nickname = message.getArgument();

                if (message.getJson().containsKey("playableCard")) {
                    gameView.drawCard(nickname, message.getPlayableCard());
                } else {
                    gameView.drawCard(nickname, message.getGoldCard());
                }
            }
            case "VisibleCards" ->{
                gameView.setVisibleResourceCards(message.getResourceCards());
                gameView.setCoveredResourceCard(message.getPlayableCard());
                gameView.setVisibleGoldCards(message.getGoldCards());
                gameView.setCoveredGoldCard(message.getGoldCard());

                gameView.showVisibleCards();
            }
            case "Scores" -> {
                gameView.showScores(message.getScores());
            }
            case "LastRound" -> {
                gameView.showMessage("\nLast round\n");
            }
            case "FinalScores" -> {
                gameView.showMessage("\nFinal scores:\n");
                gameView.showScores(message.getScores());
            }
            case "EndGame" -> {
                gameView.showEndGame(message.getArgument());
            }

        }
    }

    /**
     * Shuts down the client application and stops all scheduled tasks.
     */
    public void stop() {
        // Stop the scheduled executor service to prevent further scheduled tasks from running
        scheduler.shutdownNow();

        // Exit the application
        System.exit(0);
    }

    ///////////////////////////////////////////////////////GETTERS AND SETTERS////////////////////////////////////////////////////////

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}