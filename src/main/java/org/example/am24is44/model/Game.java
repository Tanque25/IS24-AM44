package org.example.am24is44.model;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CardPoints.*;

import java.util.Map;
import java.util.Stack;

public class Game {
    private Player[] players;
    private Player startingPlayer;
    private Player currentPlayer;
    private GameStatus status;
    private ObjectiveCard[] commonObjectives;
    private Card[] resourcePile;
    private GoldCard[] goldPile;
    private Card[] visibleCards;
    private Card[] starterDeck;
    private Resource[] resource;

    // Constructor
    public Game() {
    }


    // Method to initialize the game
    private void initializeGame() {
        Card[] starterDeck = new Card[6];

        resource[0]=INSECT_KINGDOM;
        starterDeck[0]=new Card(resource,ZERO);
        resource[0]=FUNGI_KINGDOM;
        starterDeck[1]=new Card(resource,ZERO);
        resource[0]=PLANT_KINGDOM;
        resource[1]=FUNGI_KINGDOM;
        starterDeck[2]=new Card(resource,ZERO);
        resource[0]=ANIMAL_KINGDOM;
        resource[1]=INSECT_KINGDOM;
        starterDeck[3]=new Card(resource,ZERO);
        resource[2]=PLANT_KINGDOM;
        starterDeck[4]=new Card(resource,ZERO);
        resource[0]=PLANT_KINGDOM;
        resource[1]=ANIMAL_KINGDOM;
        resource[1]=FUNGI_KINGDOM;
        starterDeck[5]=new Card(resource,ZERO);
    }

    // Method to add a player to the game
    public void addPlayer(String nickname) {
        // Implement logic to add a player to the game
        // Conflict test
    }

    // Method to get the current player
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Method to choose secret objectives
    public void chooseSecretObjective() {
        // Implement logic to choose secret objectives
    }

    // Method to choose starting side
    public void chooseStartingSide() {
        // Implement logic to choose starting side
    }

    // Getter for common objectives
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;

    }

    // Method to play a card
    public void playCard() {
        // Implement logic to play a card
    }

    // Method to draw a card
    public void drawCard() {
        // Implement logic to draw a card
    }

    // Getter for visible cards
    public Card[] getVisibleCards() {
        return visibleCards;
    }

    // Method to determine the winner
    public Player Winner() {
        // Implement logic to determine the winner
        return null; // Placeholder, replace with actual winner
    }

}
