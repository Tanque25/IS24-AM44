package org.example.myversion.client.view;

import org.example.myversion.client.Client;

import java.io.IOException;

/**
 * This is the interface used for the view (cli or gui)
 */
public interface GameView {

    void setClient(Client client);

    void showMessage(String message);

    void startView() throws IOException;

    void clientLogin() throws IOException;

    void playersNumberChoice() throws IOException;

    void waitForOtherPlayers();
}
