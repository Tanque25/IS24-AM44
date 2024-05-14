package org.example.myversion.client.view;

import org.example.myversion.client.Client;
import org.example.myversion.client.CodexNaturalis;
import org.example.myversion.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIView implements GameView {
    private Client client;

    public CLIView() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        System.out.print("Insert server IP: ");
//        try {
//            String ipAddress = reader.readLine();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }

        CodexNaturalis.setParameters("127.0.0.1", "tcp", this);

    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Shows a generic message.
     *
     * @param message the message to be shown
     */
    @Override
    public void showMessage(String message) {
        System.out.print(message);
    }

    public void startView() throws IOException {
        clientLogin();
    }

    public void clientLogin() throws IOException {
        showGameTitle();
        String nickname = showLogin();
        client.sendMessage(new Message("Login", nickname));
    }

    public void showGameTitle() {
        String gameTitle = "\n" +
                " _____           _           _   _       _                   _ _     \n" +
                "/  __ \\         | |         | \\ | |     | |                 | (_)    \n" +
                "| /  \\/ ___   __| | _____  _|  \\| | __ _| |_ _   _ _ __ __ _| |_ ___ \n" +
                "| |    / _ \\ / _` |/ _ \\ \\/ / . ` |/ _` | __| | | | '__/ _` | | / __|\n" +
                "| \\__/\\ (_) | (_| |  __/>  <| |\\  | (_| | |_| |_| | | | (_| | | \\__ \\\n" +
                " \\____/\\___/ \\__,_|\\___/_/\\_\\_| \\_/\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/\n" +
                "                                                                     \n" +
                "                                                                     \n";
        System.out.println("Welcome to...\n" + gameTitle);
    }

    public String showLogin() throws IOException {
        showMessage("Please, insert your nickname: ");
        return readNickname();
    }

    public String readNickname() throws IOException {
        String nickname = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            nickname = in.readLine();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the nickname.");
        }

        return nickname;
    }

    public void showGameAlreadyStartedMessage() {
        showMessage("Game already started! You have to wait for the next one :(\n");
        client.stop();
    }

    public void playersNumberChoice() throws IOException {
        int playersNumber = askForPlayersNumber();
        client.sendMessage(new Message("PlayersNumber", playersNumber));
    }

    public int askForPlayersNumber() {
        showMessage("Please enter the number of players you would like to play: ");
        int playersNumber = 0;

        while (playersNumber<2 || playersNumber>4) {
            System.err.println("Please enter a number between 2 and 4.");
            playersNumber = readNumber();
        }

        return playersNumber;
    }

    public int readNumber() {
        int number = 0;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            number = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the number.");
        }

        return number;
    }

    public void waitForOtherPlayers() {
        showMessage("Waiting for other players to join...\n");
    }

}
