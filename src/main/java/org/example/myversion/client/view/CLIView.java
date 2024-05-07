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

    public void startView() throws IOException {
        clientLogin();
    }

    public void clientLogin() throws IOException {
        showGameTitle();
        client.sendMessage(new Message("Ping", "edoode"));
    }

    public void showGameTitle() {
        String gameTitle = "\n" +
                "_________            .___             \n" +
                "\\_   ___ \\  ____   __| _/____ ___  ___\n" +
                "/    \\  \\/ /  _ \\ / __ |/ __ \\\\  \\/  /\n" +
                "\\     \\___(  <_> ) /_/ \\  ___/ >    < \n" +
                " \\______  /\\____/\\____ |\\___  >__/\\_ \\\n" +
                "        \\/            \\/    \\/      \\/\n";

        System.out.println("Welcome to...\n" + gameTitle);
    }
}
