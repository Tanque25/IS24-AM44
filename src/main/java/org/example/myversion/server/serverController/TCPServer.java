package org.example.myversion.server.serverController;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The TCPServer class implements a TCP server for handling client connections.
 * It uses a ServerSocket to accept client connections and manages them using a thread pool.
 */
public class TCPServer implements ServerInterface, CommunicationInterface {

    private final ServerSocket serverSocket; // Server socket to accept connections
    private boolean running; // Flag to indicate if the server is running
    public Thread acceptThread; // Thread to accept incoming connections
    private List<HandleClientSocket> clients; // List of clients
    public ExecutorService executor; // Executor service to manage client handler threads

    /**
     * Constructor, it initializes a TCP server on the specified port.
     */
    public TCPServer() {
        clients = new ArrayList<>();
        executor = Executors.newCachedThreadPool();

        try {
            // The TCP Server starts running
            serverSocket = new ServerSocket(TCP_PORT);
            running = true;
            System.out.println("TCP server started on port " + serverSocket.getLocalPort() + ".");

            // Starting a thread that accepts incoming connections
            acceptThread = new Thread(() -> {
                while (running) {
                    try {
                        // Continuously accept new connections
                        Socket clientSocket = serverSocket.accept();

                        // For each new connection, create a new thread to handle communication with the client
                        HandleClientSocket handleClientSocket = new HandleClientSocket(clientSocket, controller);

                        executor.submit(handleClientSocket);
                        clients.add(handleClientSocket);
                    } catch (IOException e) {
                        // The server socket has been closed, this thread can be interrupted
                        break;
                    }
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Starts the server by starting the acceptThread if it is in the NEW state.
     * This method is called to begin accepting client connections.
     */
    @Override
    public void start() {
        if (acceptThread.getState() == Thread.State.NEW) {
            acceptThread.start();
        }
    }

    /**
     * Stops the server by setting the running flag to false and closing the server socket.
     * Also interrupts the acceptThread to ensure it is not blocked on accept().
     */
    @Override
    public void stop() {
        running = false;

        try {
            // Close the server socket
            serverSocket.close();

            // Ensure the thread is interrupted if blocked on accept()
            acceptThread.interrupt();

            // Stop all client handlers
            for (HandleClientSocket client : clients) {
                client.stop();
            }

            // Shutdown the executor service
            executor.shutdownNow();
            System.out.println("TCP server stopped.");
        } catch (IOException e) {
            System.out.println("Socket already closed: " + e.getMessage());
        }
    }
}