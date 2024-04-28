package org.example.myversion.messages;

import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

/**
 * The Message class represents a message body used in TCP protocol.
 */
public class Message {
    private String header;
    private String playerNickname;
    private int maxPlayerNumber;
    private StarterCard starterCard;
    private ObjectiveCard[] secretObjectiveOptions;
    private PlayableCard pickedCard;
    private Coordinates pickedPosition;
    private String senderNickname;
    private String receiverNickname;
    private String text;
    private NewView newView;

    /**
     * Gets the header of the message.
     *
     * @return The header of the message.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the message.
     *
     * @param header The header of the message.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Gets the nickname of the player associated with the message.
     *
     * @return The player's nickname.
     */
    public String getPlayerNickname() {
        return playerNickname;
    }

    /**
     * Sets the nickname of the player associated with the message.
     *
     * @param playerNickname The player's nickname.
     */
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    /**
     * Gets the maximum number of players.
     *
     * @return The maximum number of players.
     */
    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    /**
     * Sets the maximum number of players.
     *
     * @param maxPlayerNumber The maximum number of players.
     */
    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    /**
     * Gets the starter card.
     * The player gets to choose on which side to play it.
     *
     * @return The starter card.
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * Sets the starter card.
     * The player gets to choose on which side to play it.
     *
     * @param starterCard The starter card.
     */
    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    /**
     * Gets the options for secret objectives.
     * The player will get to choose one of these.
     *
     * @return The options for secret objectives.
     */
    public ObjectiveCard[] getSecretObjectiveOptions() {
        return secretObjectiveOptions;
    }

    /**
     * Sets the two options for secret objectives.
     * The player will get to choose one of these.
     *
     * @param secretObjectiveOptions The options for secret objectives.
     */
    public void setSecretObjectiveOptions(ObjectiveCard[] secretObjectiveOptions) {
        this.secretObjectiveOptions = secretObjectiveOptions;
    }

    /**
     * Gets the picked card to draw.
     *
     * @return The picked card.
     */
    public PlayableCard getPickedCard() {
        return pickedCard;
    }

    /**
     * Sets the picked card to draw.
     *
     * @param pickedCard The picked card.
     */
    public void setPickedCard(PlayableCard pickedCard) {
        this.pickedCard = pickedCard;
    }

    /**
     * Gets the picked position in the play area to place the card.
     *
     * @return The picked position.
     */
    public Coordinates getPickedPosition() {
        return pickedPosition;
    }

    /**
     * Sets the picked position in the play area to place the card.
     *
     * @param pickedPosition The picked position.
     */
    public void setPickedPosition(Coordinates pickedPosition) {
        this.pickedPosition = pickedPosition;
    }

    /**
     * Gives the nickname of the sender associated with this message.
     *
     * @return The sender's nickname.
     */
    public String getSenderNickname() {
        return senderNickname;
    }

    /**
     * Sets the nickname of the sender associated with this message.
     *
     * @param senderNickname The sender's nickname.
     */
    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    /**
     * Gives the nickname of the receiver associated with this message.
     *
     * @return The receiver's nickname.
     */
    public String getReceiverNickname() {
        return receiverNickname;
    }

    /**
     * Sets the nickname of the receiver associated with this message.
     *
     * @param receiverNickname The receiver's nickname.
     */
    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    /**
     * Gives the text of the message associated with this message.
     *
     * @return The text of the message.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the message associated with this message.
     *
     * @param text The text of the message.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gives the new view associated with this message.
     *
     * @return The new view.
     */
    public NewView getNewView() {
        return newView;
    }

    /**
     * Sets the new view associated with this message.
     *
     * @param newView The new view.
     */
    public void setNewView(NewView newView) {
        this.newView = newView;
    }
}