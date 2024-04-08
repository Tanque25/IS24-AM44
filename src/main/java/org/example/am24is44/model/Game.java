package org.example.am24is44.model;
import static org.example.am24is44.model.CornerPosition.*;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CardPoints.*;

import java.util.HashMap;
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
    private Corner corner;
    private Map<CornerPosition, Corner> corners;

    // Constructor
    public Game() {
    }


    // Method to initialize the game
    private void initializeGame() {
        //inizializzazione StarterCard
        Card[] starterDeck = new Card[6];

        this.corners = new HashMap<>();

        //prima carta:
        //io sposterei creazione dei corner nella classe Card ??????? --> da capire
        corners.put(UP_LEFT,new Corner(true,VUOTO));
        corners.put(BOTTOM_LEFT,new Corner(true,INSECT_KINGDOM));
        corners.put(UP_RIGHT,new Corner(true,PLANT_KINGDOM));
        corners.put(BOTTOM_RIGHT,new Corner(true,VUOTO));

        resource[0]=INSECT_KINGDOM;
        starterDeck[0]=new Card(resource,ZERO,corners);

        //seconda carta
        corners.put(UP_LEFT,new Corner(true,ANIMAL_KINGDOM));
        corners.put(BOTTOM_LEFT,new Corner(true,VUOTO));
        corners.put(UP_RIGHT,new Corner(true,VUOTO));
        corners.put(BOTTOM_RIGHT,new Corner(true,FUNGI_KINGDOM));

        resource[0]=FUNGI_KINGDOM;
        starterDeck[0]=new Card(resource,ZERO,corners);

        //terza carta..

        resource[0]=PLANT_KINGDOM;
        resource[1]=FUNGI_KINGDOM;

        starterDeck[1]=new Card(resource,ZERO,corners);

        starterDeck[2]=new Card(resource,ZERO,corners);
        resource[0]=ANIMAL_KINGDOM;
        resource[1]=INSECT_KINGDOM;
        starterDeck[3]=new Card(resource,ZERO,corners);
        resource[2]=PLANT_KINGDOM;
        starterDeck[4]=new Card(resource,ZERO,corners);
        resource[0]=PLANT_KINGDOM;
        resource[1]=ANIMAL_KINGDOM;
        resource[1]=FUNGI_KINGDOM;
        starterDeck[5]=new Card(resource,ZERO,corners);
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
