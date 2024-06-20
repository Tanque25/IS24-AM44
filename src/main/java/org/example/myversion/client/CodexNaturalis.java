package org.example.myversion.client;

import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.view.CLIView;
import org.example.myversion.client.view.GameView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //System.out.println("bella\n");
        CodexNaturalis.hostname = hostname;
        CodexNaturalis.communicationProtocol = communicationProtocol;
        CodexNaturalis.gameView = gameView;

        switch (communicationProtocol) {
            case "rmi" -> {
                try {
                    //System.out.println("bella2\n");
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
        //System.out.println("qui passa2");
        gameView.setClient(client);
        //System.out.println("qui passa3");
    }

    /**
     * Checks if the given IP address is valid.
     *
     * @param ip the IP address to check.
     * @return true if the IP address is valid, false otherwise.
     */
    public static boolean isIpValid(String ip) {
        if (ip.equals("localhost"))
            return true;
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
