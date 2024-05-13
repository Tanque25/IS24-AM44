package org.example.myversion.server.serverController;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements ServerInterface{

    private ServerSocket serverSocket;//server

    private boolean running; //booleano per sapere se sta runnando

    private Thread acceptThread;

    private List<HandleClientSocket> clients;

    public ExecutorService executor;

    /**
     * Constructor, it initializes a TCP server on the specified port.
     *
     * @param port The port number on which the server will listen for incoming connections.
     */
    public TCPServer(int port) {
        clients = new ArrayList<>();
        executor = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(port);//port è la porta della connessione che li passeremo
            running = true;//

            // Avvio un thread per accettare connessioni in ingresso
            acceptThread = new Thread(() -> {
                while (running) {
                    try {
                        Socket clientSocket = serverSocket.accept(); //accept continua ad accettare nuova connesioni

                        //per ogni connessione creata viene creato un nuovo thread per gestire connessione con clients
                        HandleClientSocket handleClientSocket = new HandleClientSocket(clientSocket, controller);

                        executor.submit(handleClientSocket);
                        clients.add(handleClientSocket);
                    } catch (IOException e) {
                        e.printStackTrace(); //stampa errore poi metterlo a posto meglio nel caso
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
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
        running = false;//non runna piu

        try {
            serverSocket.close();//chiude server
            acceptThread.interrupt();  // Ensure thread is interrupted if blocked on accept()
        } catch (IOException e) {
            e.printStackTrace();//da sostituire
        }
    }
}