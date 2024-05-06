package org.example.myversion.server.model.exceptions;

/**
 * Exception thrown when attempting to perform an operation on an empty deck.
 */
public class EmptyDeckException extends Exception{
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EmptyDeckException(String message) {
        super(message);
    }
}
