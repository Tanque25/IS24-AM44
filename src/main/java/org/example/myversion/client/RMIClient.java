package org.example.myversion.client;

import org.example.myversion.messages.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient extends Client {

    private String nickname;
    public RMIClient() throws RemoteException {
    }

    /**
     * Connects to the server.
     *
     * @throws IOException       if the connection fails.
     * @throws NotBoundException if the server is not bound.
     */
    @Override
    public void connect() throws IOException, NotBoundException {

    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send.
     * @throws IOException if the message send fails.
     */
    @Override
    public void sendMessage(Message message) throws IOException {

    }

    public String getNickname() throws RemoteException {
        return nickname;
    }
}
