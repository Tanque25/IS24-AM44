package org.example.myversion.server.serverController;

import org.example.myversion.messages.Message;
import org.example.myversion.server.model.exceptions.ExtraRoundException;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

public interface ServerInterface {

    GameController controller = new GameController();

    public default void start() {
    }

    ;

    public default void stop() {
    }

    ;

    public default void receiveMessageTCP(Message message) throws IllegalAccessException, InvalidNicknameException, InvalidMoveException, InvalidChoiceException, ExtraRoundException {
    }
}