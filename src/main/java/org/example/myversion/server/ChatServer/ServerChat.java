package org.example.myversion.server.ChatServer;

import org.example.myversion.server.serverController.CommunicationInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.example.myversion.client.chatClient.*;

public interface ServerChat extends Remote{



    public void join(ChatClient client) throws RemoteException;
    public void leave(ChatClient client) throws RemoteException;
    //public void sendMsg(Message msg) throws RemoteException;

}
