package org.example.am24is44.model;

import java.util.List;

public class Player {
    private String nickname;
    private List<Card> hand;
    private Card[][] playArea;
    private ObjectiveCard secretObjective;

    //private int achievedObjective;


    // Constructor
    public Player(String nickname) {
        this.nickname = nickname;
        this.playArea = new Card[81][81];
        //setSecretObjective();
    }

    // Getter for the nickname
    public String getNickname() {
        return nickname;
    }

    // Method to initialize the player's hand
    public void initializeHand(List<Card> starterHand) {
        this.hand = starterHand;
    }

    // Method to initialize the player's play area
    private void initializePlayArea(StarterCard starterCard) {
        playArea[41][41] = starterCard;
    }

    // Method to set the player's secret objective
    private void setSecretObjective(ObjectiveCard card) {

        this.secretObjective = card;
    }

    // Method to play a card
    public void playCard(Card card, int x, int y) {
        playArea[x][y] = card;
        hand.remove(card);
    }

    // Method to draw a card
    public void drawCard(Card card) {
        hand.add(card);
    }

    // Method to calculate the final score
    public int finalScoreCalculator() {
        // Implement logic to calculate the final score
        return 0;
    }

}