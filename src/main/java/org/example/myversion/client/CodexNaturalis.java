package org.example.myversion.client;

import org.example.myversion.client.view.CLIView;
import org.example.myversion.client.view.GameView;

import java.io.IOException;
import java.rmi.RemoteException;

public class CodexNaturalis {
    public static Client client;
    public static String hostname;
    public static String communicationProtocol;
    public static GameView gameView;

    public static void main(String[] args) throws IOException {
        gameView = new CLIView();
        gameView.startView();
    }

    public static void setParameters(String hostname, String communicationProtocol, GameView gameView) {
        CodexNaturalis.hostname = hostname;
        CodexNaturalis.communicationProtocol = communicationProtocol;
        CodexNaturalis.gameView = gameView;

        switch (communicationProtocol) {
            case "rmi" -> {
                try {
                    client = new RMIClient();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            case "tcp" -> {
                try {
                    client = new TCPClient();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                System.err.println("Invalid protocol: " + communicationProtocol + ". Use 'rmi' or 'tcp'.");
                System.exit(1);
            }
        }

        client.setGameView(gameView);
        gameView.setClient(client);
    }
}
