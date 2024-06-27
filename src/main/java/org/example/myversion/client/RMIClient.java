package org.example.myversion.client;

import org.example.myversion.messages.ChatMessage;
import org.example.myversion.messages.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.example.myversion.server.serverController.CommunicationInterface;

public class RMIClient extends Client implements ClientCommunicationInterface {

    private Registry registry;
    private CommunicationInterface server;

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
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            String jsonString = message.getJson().toString();
            server.receiveMessageRMI(jsonString, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendChatMessage(ChatMessage message) {
        try {
            String jsonString = message.getJson().toString();
            server.receiveMessageRMI(jsonString, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startGame() throws RemoteException {

    }

}
