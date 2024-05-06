package org.example.myversion.client;

import org.example.myversion.client.view.CLIView;
import org.example.myversion.client.view.GameView;

import java.io.IOException;

public class CodexNaturalis {

    static GameView gameView;

    public static void main(String[] args) throws IOException {
        gameView = new CLIView();
        gameView.startView();
    }
}
