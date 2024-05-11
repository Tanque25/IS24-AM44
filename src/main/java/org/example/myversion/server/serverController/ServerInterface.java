package org.example.myversion.server.serverController;

import org.example.myversion.client.RMIClient;
import org.example.myversion.messages.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.example.myversion.server.model.exceptions.ExtraRoundException;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

public interface ServerInterface extends Remote{

    GameController controller = new GameController();

    public default void start() {
    }

    ;

    public default void stop() {
    }

    ;

    public default void receiveMessageTCP(Message message,HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, ExtraRoundException, RemoteException {
    }

    default void receiveMessage(Message message, ServerInterface client) throws RemoteException {

    }

    default void startPingThread(RMIClient client) throws RemoteException {
        String username = null;
        try {
            username = client.getNickname();
        } catch (RemoteException e) {
            System.err.println("Error while getting username from client.");
        }
        String finalUsername = username;

        Thread checkThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    if (controller.game == null) {
                        break;
                    }
                    if (!controller.pongReceived.contains(finalUsername) && !controller.disconnectedPlayers.contains(finalUsername)) {
                        System.err.println("Ping not received from " + finalUsername + ". Disconnecting.");
                        Thread.sleep(10000);
                        if (!controller.pongReceived.contains(finalUsername)) {
                            //disconnect(finalUsername);
                            break;
                        }
                    } else {
                        controller.pongReceived.remove(finalUsername);
                    }
                } catch (InterruptedException ignored) {

                }
            }
        });
        //controller.addCheckThread(checkThread);
        checkThread.start();
    }


}