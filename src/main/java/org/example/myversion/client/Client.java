package org.example.myversion.client;

import org.example.myversion.client.view.GameView;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Game;
import org.example.myversion.server.model.decks.ObjectiveDeck;
import org.example.myversion.server.model.decks.cards.Card;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This class represents the client.
 * It is abstract because each client will either be an RMI client or a Socket client.
 */
public abstract class Client extends UnicastRemoteObject implements Serializable {
    private String nickname;
    private GameView gameView;
    private boolean serverConnection;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final Object lock;

    public Client() throws RemoteException {
        super();

        // TODO

        lock = new Object();
    }

    /**
     * Connects to the server.
     *
     * @throws IOException       if the connection fails.
     * @throws NotBoundException if the server is not bound.
     */
    public abstract void connect() throws IOException, NotBoundException;

    /**
     * Parses and handles messages received from the server.
     *
     * @param message the message received from the server.
     */
    public void handleMessage(Message message)throws RemoteException {
        String messageCode = message.getMessageCode();

//        if(!messageCode.equals("Pong")) {
//            System.out.println("Received TCP message: with messageCode " + messageCode);
//        }

        switch (messageCode) {
            case "Pong" -> {
                serverConnection = true;
            }
            case "Nickname" -> {
                setNickname(message.getArgument());
                // TODO: Implement the connection check on a different channel on the server side
                // checkServerConnection();
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

                gameView.showOthersHandsAndPlayAreas();
                gameView.showMyHand();
                gameView.showMyPlayArea();
            }
            case "MyTurn" -> {
                System.out.println("My turn");
            }
            case "OtherTurn" -> {
                gameView.showMessage("\nIt's " + message.getArgument() + "'s turn.\n");
            }
            default -> throw new IllegalArgumentException("Invalid messageCode: " + messageCode);
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    public abstract void sendMessage(Message message) throws IOException;

    public void myTurn() {
        gameView.showMessage("\nIt's your turn.\n");

        // TODO: implement myTurn thread launch
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

    /**
     * Checks if the server is still connected.
     * If it's not, exits the game.
     */
    public void checkServerConnection() {
        System.out.println("Starting server connection check");
        new Thread(() -> {
            while (true) {
                try {
                    sendMessage(new Message("Ping"));
                    synchronized (lock) {
                        Thread.sleep(20000);
                        if (!serverConnection) {
                            System.err.println("Server is down. Exiting...");
                            System.exit(0);
                        }
                        serverConnection = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
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