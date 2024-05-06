package org.example.myversion.server.model.exceptions;

/**
 * Custom exception class for representing invalid moves in the game.
 */
public class InvalidMoveException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
