package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCommunicationInterface extends Remote {

    void handleMessageNew(String scelta) throws RemoteException;

    String getNickname() throws RemoteException;

    void setNickname(String nickname) throws RemoteException;

    void startGame() throws RemoteException;

    void receiveCard(String message) throws RemoteException;

}
