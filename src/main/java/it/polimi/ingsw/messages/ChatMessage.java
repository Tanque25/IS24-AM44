package it.polimi.ingsw.messages;

import jakarta.json.Json;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.chatClient.ChatClient;

import java.time.LocalTime;
import java.io.Serializable;
import jakarta.json.*;

public class ChatMessage implements Serializable{

    private String text;
    private Client sender;
    private LocalTime time;
    private JsonObject json;
    /**
     * Constructor
     * @param text
     * @param sender
     */
    public ChatMessage(String text, String sender) {
        json = Json.createObjectBuilder()
                .add("text", text)
                .add("sender", sender)
                .add("time", String.valueOf(LocalTime.now()))
                .build();
    }

    public JsonObject getJson() {
        return json;
    }

    public Client getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public LocalTime getTime() {
        return time;
    }

    /**
     * Constructor
     */
    public ChatMessage() {
        this.time = null;
        this.text = null;
        this.sender = null;
    }



}
