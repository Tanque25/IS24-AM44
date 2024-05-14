package org.example.myversion.server;

import org.example.myversion.server.serverController.RMIServer;
import org.example.myversion.server.serverController.TCPServer;

/**
 * Manages the server component of the game application.
 * Handles client connections, communication and updates clients with the latest game state.
 */
public class Server {

    private TCPServer servertcp;

    private RMIServer serverrmi;

    private static final int RMI_PORT = 70;
    private static final int SOCKET_PORT = 8080;

    /**
     * Constructor: It creates both new server.
     * In particular, it initializes both the RMI and the socket servers.
     */
    public Server(){
        // Inizializzo il server TCP
        servertcp = new TCPServer(SOCKET_PORT);

        // Inizializzo il server RMI
        // serverrmi = new RMIServer(RMI_PORT);

        // Avvia entrambi i server
        servertcp.start();
        // serverrmi.start();

        System.out.println("Server initialized with TCP port " + SOCKET_PORT + " and RMI port " + RMI_PORT);
    }

    public static void main(String[] args) {
        Server server = new Server();
        addShutdownHook(server);
    }

    private static void addShutdownHook(Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down servers...");
            server.stop();
        }));
    }

    public void stop() {
        if (servertcp != null) {
            servertcp.stop();
        }
        // if (serverrmi != null) {
        //     serverrmi.stop();
        // }
    }

}