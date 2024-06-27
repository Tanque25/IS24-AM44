package it.polimi.ingsw.server.ChatServer;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.chatClient.ChatClient;
import it.polimi.ingsw.messages.ChatMessage;

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl {

    private List<ChatMessage> message = new ArrayList<>();

    public static void main(String args[]) throws Exception {
        ChatServerImpl server = new ChatServerImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        //registry.bind("ChatServer", server);
        System.out.println("Server bound and ready");
    }

    public ChatServerImpl() {
        message = new ArrayList<>();
    }

    /**
     * Constructor
     * @param msgs
     */
    public ChatServerImpl(List<ChatMessage> msgs) {
        this.message = msgs;
    }

    /**
     *
     * @return the list of messages
     */
    public List<ChatMessage> getMsgs() {
        return message;
    }

    /**
     * Adds a message
     * @param m message param
     */
    public void addMsg(ChatMessage m) {
        if (message.size() > 100)
            message.remove(0);
        message.add(m);
    }

    /**
     * Adds a message
     * @param sender message param
     * @param text message param
     */
    public void addMsg(String sender, String text) {
        ChatMessage temp = new ChatMessage(text, sender);
        if (message.size() > 100)
            message.remove(0);
        message.add(temp);
    }

    /**
     *
     * @return the last message in string form
     */
    public String getLast() {
        return message.get(message.size() - 1).toString();
    }

    /**
     * @return the last message in message form
     */
    public ChatMessage getLastMessage() {
        return message.get(message.size() - 1);
    }


}
