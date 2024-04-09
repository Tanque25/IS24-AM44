package org.example.am24is44.model;

public class Player {
    private String nickname = "";
    private Card[] hand = new Card[3];
    private Card[][] playArea = new Card[20][20];
    private ObjectiveCard secretObjective;

    //private int achievedObjective;


    // Constructor
    public Player(String nickname) {
        this.nickname = nickname;
        initializeHand();
        initializePlayArea();
        //setSecretObjective();
    }

    // Getter for the nickname
    public String getNickname() {
        return nickname;
    }

    // Method to initialize the player's hand
    private void initializeHand() {
        // Implement initialization logic for the hand
    }

    // Method to initialize the player's play area
    private void initializePlayArea() {
        // Implement initialization logic for the play area
    }

    // Method to set the player's secret objective
    private void setSecretObjective(ObjectiveCard card) {

        this.secretObjective = card;
    }

    // Method to play a card
    public void playCard(int cardIndex, int x, int y) {
            this.playArea[x][y]= this.hand[cardIndex];
            this.hand[cardIndex] = null;
    }




    // Method to draw a card
    public void setCard(Card card) {
        for(int i = 0; i < this.hand.length; i++){
            if(this.hand[i] == null){
                this.hand[i] = card;
                return;
            }
        }
    }

    // Method to calculate the final score
    public int finalScoreCalculator() {
        // Implement logic to calculate the final score
        return 0;
    }

}
