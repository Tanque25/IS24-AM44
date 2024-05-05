package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;

public interface ServerInterface {

    public default void start() {
    }

    ;

    public default void stop() {
    }

    ;

    public default void receiveMessageTCP(Message message) throws IllegalAccessException {
    }

    //public default void receiveMessageRMI(Message message) throws IllegalAccessException {
    //}
}
