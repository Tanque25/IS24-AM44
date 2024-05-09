package org.example.myversion.client;

import org.example.myversion.client.view.GameView;
import org.example.myversion.messages.Message;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
     * Initiates a periodic check for server connectivity every 20 seconds using a scheduled task.
     * If no ping response is received, the client proceeds to a clean shutdown by calling {@link #stop}.
     */
    public void startConnectionCheck() {
        // Define the task to send a ping message and check for server response
        final Runnable connectionCheck = () -> {
            try {
                sendMessage(new Message("ping"));  // Send ping message to server
                synchronized (lock) {
                    if (!serverConnection) {  // Check if server responded to the last ping
                        System.err.println("Server is down. Exiting...");  // Log if server is down
                        stop();  // Stop the client
                    }
                    serverConnection = false;  // Reset server connection status
                }
            } catch (IOException e) {
                System.err.println("Failed to send ping: " + e.getMessage());  // Log errors if ping fails
            }
        };

        // Schedule the connection check task to run periodically every 20 seconds
        scheduler.scheduleAtFixedRate(connectionCheck, 0, 20, TimeUnit.SECONDS);
    }

    /**
     * Parses and handles messages received from the server.
     *
     * @param message the message received from the server.
     */
    public void handleMessage(Message message) {
        String messageCode = message.getMessageCode();

        switch (messageCode) {
            case "Ping" ->
                serverConnection = true;
            case "Nickname" ->
                setNickname(message.getArgument());
                // TODO might have to add a checkServerConnection method
            default -> throw new IllegalArgumentException("Invalid messageCode: " + messageCode);
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    public abstract void sendMessage(Message message) throws IOException;;

    /**
     * Shuts down the client application and stops all scheduled tasks.
     */
    public void stop() {
        // Stop the scheduled executor service to prevent further scheduled tasks from running
        scheduler.shutdownNow();

        // Exit the application
        System.exit(0);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}