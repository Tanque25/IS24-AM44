package org.example.myversion.server.serverController;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.example.myversion.messages.*;

public class RMIInterface implements Remote {

    public void receiveMessage(Message message /* ci dovra essere anche l'inteffaccia client qui */) throws RemoteException {
        String messageType = message.getArgument(); //get del tipo del messaggio

        switch (messageType){
            case "PickCard" ->{
                //
            }
        }
    }

}
