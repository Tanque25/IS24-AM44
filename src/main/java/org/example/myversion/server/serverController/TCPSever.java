package org.example.myversion.server.serverController;

import org.example.myversion.server.serverController.ServerInterface;

import java.io.*;
import java.net.*;

public class TCPServer implements ServerInterface{

    private ServerSocket serverSocket;//server

    private boolean running; //booleano per sapere se sta runnando

    private Thread acceptThread;



    //costruttore, crea thread per gestire creazione server TCP
    public TCPServer(int port) {
        try {
            serverSocket = new ServerSocket(port);//port è la porta della connessione che li passeremo
            running = true;//

            // Avvio un thread per accettare connessioni in ingresso
            acceptThread = new Thread(() -> {
                while (running) {
                    try {
                        Socket clientSocket = serverSocket.accept();//accept continua ad accettare nuova connesioni
                        handleClientConnection(clientSocket);//per ogni connessione creata viene creato un nuovo thread per gestire connessione con clienti
                    } catch (IOException e) {
                        e.printStackTrace();//stampa errore poi metterlo a posto meglio nel caso
                    }
                }
            });
            acceptThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void start(){
        acceptThread.start();//starta il thread
    }

    @Override
    public void stop() {
        running = false;//non runna piu

        try {
            serverSocket.close();//chiude server
        } catch (IOException e) {
            e.printStackTrace();//da sostituire
        }
    }

    //è il metodo che gestisce le richieste dal client, ogni volta che creo un server, devo runnarlo cosicche ascolti richieste client
    private void handleClientConnection(Socket clientSocket) throws IOException {
        try {
            //messaggi che riceve in input e che manda
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}