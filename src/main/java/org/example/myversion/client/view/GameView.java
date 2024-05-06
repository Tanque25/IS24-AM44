package org.example.myversion.client.view;

import java.io.IOException;

/**
 * This is the interface used for the view (cli or gui)
 */
public interface GameView {

    void startView() throws IOException;

    void clientLogin() throws IOException;

}
