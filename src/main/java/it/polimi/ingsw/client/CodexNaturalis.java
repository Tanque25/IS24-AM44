package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.client.view.GameView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.*;

public class CodexNaturalis {
    public static Client client;
    public static String hostname;
    public static String communicationProtocol;
    public static GameView gameView;

    public static void main(String[] args) throws IOException {
        Option view = new Option("v", "view", true, "launch CLI or GUI (default: GUI)");
        Option host = new Option("host", "hostname", true, "set the hostname");
        Option protocol = new Option("p", "protocol", true, "set the communication protocol (TCP or RMI)");
        Option help = new Option("h", "help", false, "show this help message");

        Options options = new Options();
        options.addOption(view);
        options.addOption(host);
        options.addOption(protocol);
        options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine line = null;

        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("CodexNaturalis", options);
            System.exit(1);
        }

        if (line.hasOption("help")) {
            formatter.printHelp("CodexNaturalis", options);
            System.exit(0);
        }

        String modeType = line.getOptionValue("view", "cli");
        hostname = line.getOptionValue("hostname", "localhost"); // default to localhost if not provided
        communicationProtocol = line.getOptionValue("protocol", "rmi"); // default to TCP if not provided

        switch (modeType) {
            case "cli" -> gameView = new CLIView();
            case "gui" -> gameView = new GUI();
            default -> {
                System.err.println("Invalid view: " + modeType + ". Use 'cli' or 'gui'.");
                System.exit(1);
            }
        }

        gameView.startView();
    }

    /**
     * Used to set the parameters of the client.
     *
     * @param gameView     the view used to display the game.
     */
    public static void setParameters(GameView gameView) {

        CodexNaturalis.gameView = gameView;

        switch (CodexNaturalis.communicationProtocol) {
            case "rmi" -> {
                try {
                    client = new RMIClient(CodexNaturalis.hostname);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            case "tcp" -> {
                try {
                    client = new TCPClient(CodexNaturalis.hostname);
                } catch (IOException e) {
                    e.printStackTrace();
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
