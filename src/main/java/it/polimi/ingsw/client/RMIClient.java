package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.ChatMessage;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.server.serverController.CommunicationInterface;

public class RMIClient extends Client implements ClientCommunicationInterface {

    private Registry registry;
    private CommunicationInterface server;

    /**
     * Constructor for the RMIClient.
     * @param hostname
     * @throws RemoteException RMI Exception
     */
    public RMIClient(String hostname) throws RemoteException {
        super();

        try {
            connect(hostname);
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Connects to the server.
     *
     * @throws IOException       if the connection fails.
     * @throws NotBoundException if the server is not bound.
     */
    public void connect(String hostname) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(hostname, CommunicationInterface.RMI_PORT);
        server = (CommunicationInterface) registry.lookup("CommunicationInterface");
    }

   /**
     * Sends a message to the server.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            String jsonString = message.getJson().toString();
            server.receiveMessageRMI(jsonString, this);
        } catch (RemoteException e) {
            System.err.println("Error while reading from Server");
            System.out.println("Closing the client...");
            stop();

        }
    }

    /**
     * Sends a chat message to the server.
     * @param message The message to send.
     */
    @Override
    public void sendChatMessage(ChatMessage message) {
        try {
            String jsonString = message.getJson().toString();
            server.receiveMessageRMI(jsonString, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game.
     */
    @Override
    public void startGame() throws RemoteException {

    }

}
