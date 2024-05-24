package org.example.myversion.client;

import org.example.myversion.client.view.GameView;
import org.example.myversion.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCommunicationInterface extends Remote {

    void handleMessage(Message message) throws RemoteException;

    void handleMessageNew(String scelta) throws RemoteException;

    String getNickname() throws RemoteException;

    void setNickname(String nickname) throws RemoteException;

    void startGame() throws RemoteException;

    void receiveCard(String message) throws RemoteException;

}
