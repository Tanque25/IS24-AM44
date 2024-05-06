package org.example.myversion.client;

import org.example.myversion.client.view.CLIView;
import org.example.myversion.client.view.GameView;

public class CodexNaturalis {

    static GameView gameView;

    public static void main(String[] args) {
        gameView = new CLIView();

    }
}
