package it.polimi.ingsw.server.ChatServer;

import it.polimi.ingsw.server.serverController.CommunicationInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.client.chatClient.*;

public interface ServerChat extends Remote{



    public void join(ChatClient client) throws RemoteException;
    public void leave(ChatClient client) throws RemoteException;
    //public void sendMsg(Message msg) throws RemoteException;

}
