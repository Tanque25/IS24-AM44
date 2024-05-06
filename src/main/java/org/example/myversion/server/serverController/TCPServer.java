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

    /**
     * Constructor, it initializes a TCP server on the specified port.
     *
     * @param port The port number on which the server will listen for incoming connections.
     */
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

    /**
     * Handles the connection with a client by setting up input and output streams for message exchange.
     *
     * @param clientSocket The socket representing the connection with the client.
     * @throws IOException If an I/O error occurs while setting up input/output streams.
     */
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

    /**
     * Receives a TCP message and processes it based on the message type.
     *
     * @param message The message received over TCP.
     * @throws IllegalAccessException If there's an illegal access attempt.
     * @throws InvalidNicknameException If the nickname provided is invalid.
     * @throws InvalidMoveException If the move performed is invalid.
     * @throws InvalidChoiceException If the choice made is invalid.
     * @throws ExtraRoundException If an extra round condition is encountered.
     */
    //ha senso gestire questi tipi di ecceezioni qui?
    @Override
    public void receiveMessageTCP(Message message) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, ExtraRoundException {
        String messageType = message.getMessageCode();

        switch (messageType){

            case "Ping" ->{
                System.out.println("Received ping from " /*+ client.getUsername()*/); //per CLI
                String nickname = message.getArgument(); //nel ping c'è anche nickname
                //controller.pong(nickname);
                if (controller.checkNickname(nickname)==1){
                    sendMessageToClient(new Message("Pong","Nickname is valid"));
                    controller.addPlayer(nickname);//aggiungo il player
                    //se la lobby ha il numero di giocatori = maxnumberOfplayer, se non
                    if (controller.checkLobby()){
                        controller.newGame();
                    }

                }else if(controller.checkNickname(nickname)==0){
                    sendMessageToClient(new Message("Pong","Nickname is  already in use"));
                }else{
                    sendMessageToClient(new Message("Pong","Nickname is  already in use by a disconnected player"));
                }

            }

            case "NumberOfPlayer" ->{
                int numberOfPlayer = message.getMaxPlayers();
                //controllo sia valido
                if (controller.checkNumberOfPlayer(numberOfPlayer)){
                    sendMessageToClient(new Message("Valid Number of Player"));
                    //setto numero giocatori del controller
                    controller.setNumberOfPlayer(numberOfPlayer);

                }
                else{
                    sendMessageToClient(new Message("Invalid number of Player"));
                }
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