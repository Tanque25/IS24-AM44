package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.exceptions.ExtraRoundException;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;
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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        if (acceptThread.getState() == Thread.State.NEW) {
            acceptThread.start();
        }
    }

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

    //ha senso gestire questi tipi di ecceezioni qui?
    @Override
    public void receiveMessageTCP(Message message) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, ExtraRoundException {
        String messageType = message.getMessageCode();

        switch (messageType){

            case "Ping" ->{
                System.out.println("Received ping from " /*+ client.getUsername()*/); //per CLI
                String nickname = message.getArgument(); //nel ping c'è anche nickname
                controller.pong(nickname);
                sendMessageToClient(new Message("Pong","Nickname is valid"));



            }
            case "DrawCard" -> {//ne faccio un caso diverso a seconda della carta che vuole prendere? (da che mazzo)
                //non ci vuole una checkDraw di sicurezza !!!! controllare
                PlayableCard chosenCard = message.getPlayableCard();
                String nickname = message.getArgument(); //get del nickname ? tolgo nickname da draw
                controller.drawCard(nickname,chosenCard); //la pesco

                sendMessageToClient(new Message("Card drew successfully"));//pescata

            }
            case "PlayCard" ->{
                String nickname = message.getArgument();
                PlayableCard chosenCard = message.getPlayableCard();
                if(controller.isValidMove()){//ho aggiunto isValidMove --> implementarla
                    System.out.println("la mossa è valida");//per debug
                    //la gestione dell'eccezioni l'ho messa nel controller (in PlayCard) ha senso?
                    controller.playCard(nickname,chosenCard,message.getCoordinates());
                    sendMessageToClient(new Message("Move executed successfully"));//posizionata
                }
                else{
                    //è da mettere qui ?
                    while(!controller.isValidMove()){
                        //ha senso ? ricontrollare
                        sendMessageToClient(new Message("Coordinates not valid"));
                    }
                }
            }
        }
    }

    public void sendMessageToClient(Message message) {
        //clientPrintStream.println(message.getJSONstring()); ?? come implementare, chiede a Edo
    }

}