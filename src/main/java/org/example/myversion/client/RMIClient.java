package org.example.myversion.client;

import org.example.myversion.messages.Message;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.example.myversion.server.serverController.CommunicationInterface;

public class RMIClient extends Client {

    private String nickname;
    private Registry registry;
    private CommunicationInterface server;
    public RMIClient() throws RemoteException {
        super();
        try {
            connect();
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
    @Override
    public void connect() throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry("127.0.0.1", CommunicationInterface.RMI_PORT);

        server = (CommunicationInterface) registry.lookup("CommunicationInterface");

    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    @Override
    public void sendMessage(Message message) throws IOException {
        try {
            server.receiveMessageRMI(message, this);
        } catch (RemoteException e) {
            // Don't do anything: if the server is down, the client will
            // notice itself and will exit.
        }
    }

    public String getNickname() {
        return nickname;
    }

}
