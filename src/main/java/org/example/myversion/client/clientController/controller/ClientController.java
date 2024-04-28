package org.example.myversion.client.clientController.controller;

import java.util.Map;

/**
 * Interface representing the client controller for managing interactions between the client and the server.
 */
public interface ClientController {
    /**
     * Starts the user interface based on the specified UI type.
     *
     * @param uiType The type of user interface to start ("gui" for graphic user interface, "tui" for text user interface).
     */
    void startUserInterface(String uiType);

    /**
     * Sends the player's nickname to the server.
     *
     * @param nickname The player's nickname.
     */
    void sendNickname(String nickname);

    /**
     * Notifies the user that the entered nickname is invalid.
     */
    void invalidNickname();

    /**
     * Requests the number of players from the user interface.
     */
    void getPlayerNumber();

    /**
     * Sends the game parameters to the server.
     *
     * @param numPlayers The number of players.
     */
    void sendPlayerNumber(int numPlayers);

    /**
     * Notifies the user that the entered nickname is accepted.
     */
    void nicknameAccepted(int nPlayers, Map<String, Boolean> lobby);
}
