package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a card in the game, which consists of four corners and two sides.
 * Players can play cards to achieve objectives and earn points.
 */
public abstract class Card {
    private final Map<CornerPosition, Corner> corners;

    private boolean playedBack;

    private final Integer id;

    /**
     * Constructs a card with the specified corners.
     *
     * @param corners    the map containing corners and their positions on the card.
     */
    public Card(Map<CornerPosition, Corner> corners, Integer id) {
        this.corners = corners;
        this.playedBack = false;
        this.id = id;
    }

    public void setPlayedBack(boolean playedBack) {
        this.playedBack = playedBack;
    }

    /**
     * Retrieves the map containing corners and their positions on the card.
     *
     * @return the map of corners and their positions.
     */
    public Map<CornerPosition, Corner> getCorners() {
        return corners;
    }

    /**
     * Checks if the card has been played on the back.
     *
     * @return true if the card has been played back, false otherwise.
     */
    public boolean isPlayedBack() {
        return playedBack;
    }
    public int getId() {
        return id;
    }


    public abstract Resource getPlayableResource();
}
