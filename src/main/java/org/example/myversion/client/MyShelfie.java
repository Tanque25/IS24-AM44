package org.example.myversion.client;

import org.example.myversion.client.view.CLIView;
import org.example.myversion.client.GUI.GUI;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the entry point of the client. It parses the command line arguments
 * and starts the client in the selected mode.
 */
public class MyShelfie {

    public static Client client; // It's static so that it can be accessed from static methods

    /**
     * The hostname of the server. It's set when the game is launched.
     * It's shared between RMI and TCP.
     */
    public static String HOSTNAME;
    /**
     * The protocol used to communicate with the server.
     */
    public static String protocolType;

    /**
     * The view used to display the game.
     */
    /*public static GameView gameView;

    /*public static void main(String[] args) {
        SettingLoader.loadBookshelfSettings();
        Option view = new Option("v", "view", true, "launch CLI or GUI (default: GUI)");
        Option help = new Option("h", "help", false, "show this help message");

        Options options = new Options();
        options.addOption(view);
        options.addOption(help);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine line = null;

        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (line.hasOption("help")) {
            formatter.printHelp("java -jar <project-root>/deliverables/JARs/AM13_Client.jar", options);
            System.exit(0);
        }

        String modeType = line.getOptionValue("view", "gui");
        switch (modeType) {
            case "cli" -> gameView = new GameCliView();
            case "gui" -> gameView = new GuiView();
            default -> {
                System.err.println("Invalid view: " + modeType + ". Use 'cli' or 'gui'.");
                System.exit(1);
            }
        }
        gameView.startView();
    }

    public static void setParameters(String hostname, String protocolType, GameView gameView) {
        MyShelfie.HOSTNAME = hostname;
        MyShelfie.protocolType = protocolType;
        MyShelfie.gameView = gameView;
        switch (protocolType) {
            case "rmi" -> {
                try {
                    client = new ClientRmi();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            case "tcp" -> {
                try {
                    client = new ClientTcp();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                System.err.println("Invalid protocol: " + protocolType + ". Use 'rmi' or 'tcp'.");
                System.exit(1);
            }
        }
        client.setView(gameView);
        gameView.setClient(client);
    }


    public static boolean isIpValid(String ip) {
        if (ip.equals("localhost"))
            return true;
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public void setClient(Client client) {
        MyShelfie.client = client;
    }
    */
}
