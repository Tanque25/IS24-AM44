package org.example.myversion.client;

import org.example.myversion.messages.ChatMessage;
import org.example.myversion.messages.Message;

import jakarta.json.*;
import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class TCPClient extends Client implements ClientCommunicationInterface {

    // Used to send messages to the server.
    private DataOutputStream dataOutputStream;

    // Used to read messages from the server.
    private BufferedReader serverBufferedReader;

    // The socket used to communicate with the server.
    private Socket socket;

    // The thread that listens for messages from the server.
    private Thread listenThread;

    public TCPClient() throws IOException {
        super();
        connect();
    }

    /**
     * Establishes a connection with the server and initializes necessary streams.
     * Also starts the listener thread to handle incoming messages.
     *
     * @throws IOException if the connection or stream creation fails.
     */
    @Override
    public void connect() throws IOException {
        socket = new Socket("127.0.0.1", 8080); // Connect to the server at localhost on port 8080
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
                    break; // Break the loop on IOException to avoid spinning
                }
            }
        }, "Server Listener Thread");

        listenThread.start();
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if there is an error sending the message.
     */
    @Override
    public void sendMessage(Message message) throws IOException {
        String jsonString = message.getJson().toString(); // Convert message to JSON string
        dataOutputStream.writeBytes(jsonString + "\n"); // Send the JSON string over the network
        dataOutputStream.flush(); // Flush to ensure the message is sent immediately
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
    public void startGame() throws RemoteException {

    }

    //@Override
    //public void receiveCard(String message) throws RemoteException {

    //}
}
