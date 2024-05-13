package org.example.myversion.server.serverController;

import org.example.myversion.client.Client;
import org.example.myversion.client.RMIClient;
import org.example.myversion.messages.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.StarterCard;
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

    public default void receiveMessageTCP(Message message,HandleClientSocket client) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, RemoteException {
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

    /**
     * It is going to start the game so that each player gets to choose his objective card
     */
    default void startGame() {
        HashMap<String, HandleClientSocket> tcpClients = controller.getTcpClients();
        HashMap<String, Client> rmiClients = controller.getRmiClients();

        List<ObjectiveCard> commonObjectiveCards = controller.getCommonObjectiveCards();

        for (HandleClientSocket client : tcpClients.values()) {
            Message commmonObjectiveCardsMessage = new Message("CommonObjectiveCards", commonObjectiveCards.get(0), commonObjectiveCards.get(1));


            List<ObjectiveCard> secretObjectiveCardsOptions = controller.getSecretObjectiveCardsOptions();
            Message secretObjectiveCardsOptionsMessage = new Message("SecretObjectiveCardsOptions", secretObjectiveCardsOptions.get(0), secretObjectiveCardsOptions.get(1));
        }


    }


}