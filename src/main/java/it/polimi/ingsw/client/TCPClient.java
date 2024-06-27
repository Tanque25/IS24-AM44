package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.ChatMessage;
import it.polimi.ingsw.messages.Message;

import jakarta.json.*;
import it.polimi.ingsw.server.model.Coordinates;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.ingsw.server.serverController.CommunicationInterface.TCP_PORT;

public class TCPClient extends Client implements ClientCommunicationInterface {

    // Used to send messages to the server.
    private DataOutputStream dataOutputStream;

    // Used to read messages from the server.
    private BufferedReader serverBufferedReader;

    // The socket used to communicate with the server.
    private Socket socket;

    // The thread that listens for messages from the server.
    private Thread listenThread;

    public TCPClient(String hostname) throws IOException {
        super();
        connect(hostname);
    }

    /**
     * Establishes a connection with the server and initializes necessary streams.
     * Also starts the listener thread to handle incoming messages.
     *
     * @throws IOException if the connection or stream creation fails.
     */
    @Override
    public void connect(String hostname) throws IOException {
        socket = new Socket(hostname, TCP_PORT); // Connect to the server at localhost on port 8080
        dataOutputStream = new DataOutputStream(socket.getOutputStream()); // Set up stream for sending data to the server
        serverBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Set up stream for receiving data from the server
        startListenerThread(); // Start the listener thread to handle incoming messages
    }

    /**
     * Starts a thread named "Server Listener Thread" to continuously listen for messages from the server.
     * The method employs a loop that:
     * - Retrieves messages via {@code receiveMessageRMI}.
     * - Exits if {@code receiveMessageRMI} returns null (server closure).
     * - Passes any received messages to {@code handleMessage} for processing.
     */
    private void startListenerThread() {
        listenThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message receivedMessage = receiveMessage();
                    if (receivedMessage == null) {
                        System.err.println("Server connection closed.");
                        break; // Exit the loop if the server has closed the connection
                    }
                    handleMessage(receivedMessage);
                } catch (IOException e) {
                    System.err.println("IOException in listener thread: " + e.getMessage());
                    stop(); // Handle disconnection
                    break; // Break the loop on IOException to avoid spinning
                }
            }
        }, "Server Listener Thread");

        listenThread.start();
    }

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

                //gameView.setPlayers(message.);
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
            case "ClientDisconnected" -> {
                gameView.showMessage("\nClient disconnected, ending game.\n");
                stop();
            }
            default -> throw new IllegalArgumentException("Invalid messageCode: " + messageCode);
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if there is an error sending the message.
     */
    @Override
    public void sendMessage(Message message) throws IOException {
        try {
            String jsonString = message.getJson().toString(); // Convert message to JSON string
            dataOutputStream.writeBytes(jsonString + "\n"); // Send the JSON string over the network
            dataOutputStream.flush(); // Flush to ensure the message is sent immediately
        } catch (SocketException e) {
            System.err.println("Failed to send message: Broken pipe");
            stop(); // Handle disconnection
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
            stop(); // Handle disconnection
            throw e; // Re-throw the exception if you need to notify higher layers
        }
    }

    @Override
    public void sendChatMessage(ChatMessage message) throws IOException {
        String jsonString = message.getJson().toString(); // Convert message to JSON string
        dataOutputStream.writeBytes(jsonString + "\n"); // Send the JSON string over the network
        dataOutputStream.flush(); // Flush to ensure the message is sent immediately
    }


    /**
     * Receives and parses a JSON message from the server as a {@link Message} object.
     * Returns null if the connection is closed and no more messages are available.
     * If JSON parsing fails, the message is returned as raw text encapsulated in a Message object.
     *
     * @return A {@link Message} object with the server's response, or null if the stream has ended.
     * @throws IOException if an error occurs while reading from the server.
     */
    public Message receiveMessage() throws IOException {
        String serverMessageString = serverBufferedReader.readLine();
        if (serverMessageString == null) {
            stop();
            return null; // Stream has ended, possibly because the server has closed the connection
        }

        try (JsonReader jsonReader = Json.createReader(new StringReader(serverMessageString))) {
            JsonObject jsonObject = jsonReader.readObject();
            return new Message(jsonObject);
        } catch (Exception e) { // Catch any JSON parsing or unexpected exceptions
            System.err.println("Error parsing JSON, treating as raw message: " + e.getMessage());
            System.out.println(serverMessageString);
            return new Message(serverMessageString); // Return message as raw text
        }
    }

    @Override
    public void stop() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (serverBufferedReader != null) {
                serverBufferedReader.close();
            }
            if (listenThread != null && !listenThread.isInterrupted()) {
                listenThread.interrupt();
            }
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }

        // Notify the user
        System.err.println("Disconnected from the server. Closing the client.");

        scheduler.shutdownNow();
        System.out.println("Client stopped.");
        System.exit(0); // Exit the application
    }

    @Override
    public void startGame() throws RemoteException {

    }
}
