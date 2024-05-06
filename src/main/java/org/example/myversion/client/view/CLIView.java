package org.example.myversion.client.view;

import org.example.myversion.client.Client;
import org.example.myversion.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIView implements GameView {
    private Client client;

    public CLIView() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Insert server IP: ");
        try {
            String ipAddress = reader.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    public void startView() throws IOException {
        clientLogin();
    }

    public void clientLogin() throws IOException {
        client.sendMessage(new Message("Ping", "edoode"));
    }
}
