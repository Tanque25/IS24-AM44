package org.example.myversion.server;

import org.example.myversion.server.serverController.CommunicationInterface;
import org.example.myversion.server.serverController.RMIServer;
import org.example.myversion.server.serverController.ServerInterface;
import org.example.myversion.server.serverController.TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manages the server component of the game application.
 * Handles client connections, communication and updates clients with the latest game state.
 */
public class Server implements ServerInterface, CommunicationInterface {

    private TCPServer servertcp;
    private RMIServer serverrmi;

    /**
     * Constructor: It creates both new server.
     * In particular, it initializes both the RMI and the socket servers.
     */
    public Server(){
        // Initialize the TCP server
        servertcp = new TCPServer();

        // Initialize the RMI server
        serverrmi = new RMIServer();
    }

    /**
     * Main method to start the server.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();

        // Start both TCP and RMI servers
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Unable to start the server.");
        }

        addShutdownHook(server);
    }

    /**
     * Adds a shutdown hook to ensure the server stops gracefully.
     *
     * @param server the server instance
     */
    private static void addShutdownHook(Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down servers...");
            server.stop();
        }));
    }

    /**
     * Starts both the TCP and RMI servers.
     */
    @Override
    public void start(){
        // Start the TCP server
        servertcp.start();
        // Start the RMI server
        serverrmi.start();
        System.out.println("Server started.");

        if (controller.isGameSaved()) {
            System.out.println("A saved game has been found. Do you want to load it?");
            System.out.print("Enter '0' to start a new game, '1' to load the saved game: ");

            int load = 0;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                load = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                System.err.println("An error occurred while reading the number.");
            }

            if(load == 1) {
                try {
                    controller.loadLastGame();
                } catch (IOException e) {
                    System.err.println("Unable to load the game.");
                }
            } else System.out.println("Starting a new game.");

        }
    }

    /**
     * Stops both the TCP and RMI servers.
     */
    @Override
    public void stop() {
        // Stop the TCP server
        if (servertcp != null) {
            servertcp.stop();
        }

        // Stop the RMI server
        if (serverrmi != null) {
            serverrmi.stop();
        }
    }

}