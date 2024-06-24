package org.example.myversion.messages;
import jakarta.json.Json;
import org.example.myversion.client.Client;
import org.example.myversion.client.chatClient.ChatClient;

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

    /**
     * Constructor
     */
    public ChatMessage() {
        this.time = null;
        this.text = null;
        this.sender = null;
    }



}
