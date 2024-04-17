package org.example.am24is44.model;

import java.util.Map;
import java.util.List;
import java.util.EnumMap;
import java.util.EnumSet;
import java.lang.Enum;
import java.util.HashMap;

public class Player {
    private String nickname;
    private List<Card> hand;
    private Card[][] playArea;
    private ObjectiveCard secretObjective;
    private Map<MergeEnumInterface, Integer> summaryScore;
    //private int achievedObjective;




    /**
     * Player's constructor
     * @param nickname the nickname of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.playArea = new Card[81][81];
        //setSecretObjective();

        summaryScore = new HashMap<>(); //new map with values set to 0
        for (MergeEnumInterface value : SpecialObject.values()) {
            summaryScore.put(value, 0);
        }
        for (MergeEnumInterface value : Resource.values()) {
            summaryScore.put(value, 0);
        }

    }

    /**
     * Getter for the nickname
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Method to initialize the player's hand
     * @param starterHand
     */
    public void initializeHand(List<Card> starterHand) {
        this.hand = starterHand;
    }


    /**
     * Method to initialize the player's play area
     * @param starterCard
     */
    public void initializePlayArea(StarterCard starterCard, int i, int j) {
        playArea[i][j] = starterCard;
    }

    /*private void initializePlayArea(StarterCard starterCard) {
        playArea[41][41] = starterCard;
    }*/

    /**
     * Method to set the player's secret objective
     * @param card is an objectiveCard
     */
    public void setSecretObjective(ObjectiveCard card) {

        this.secretObjective = card;
    }

    /**
     * Method to get the playArea
     */
    public Card[][] getPlayArea() {
        return playArea;
    }


    /**
     * Getter for the Map
     * @param object
     * @return summaryScore.get(object)
     */
    public int getObjectValue(MergeEnumInterface object) {
        return summaryScore.get(object);
    }

    /**
     * Method to play a card
     * @param handChoice returns the position of the card in the player's hand (player's choice)
     * @param x x coordinate in the play area
     * @param y y coordinate in the play area
     */
    public void playCard(int handChoice, int x, int y) {
        Card card = hand.get(handChoice);
        playArea[x][y] = card;
        hand.remove(card);
    }

    /**
     * Method to draw a card
     * @param card the player has drawn
     */
    public void drawCard(Card card) {
        hand.add(card);
    }

    /**
     *Method to calculate the final score
     * @return 0
     */
    public int finalScoreCalculator() {
        // Implement logic to calculate the final score
        return 0;
    }

    /**
     * method to check the possible position of the card the player wants to play
     * @param x possible x coordinate (in the play area) where the player wants to put the card
     * @param y possible y coordinate (in the play area) where the player wants to put the card
     * @return boolean true if the player can play the card in the position he chose
     */
    public boolean checkXY (int x, int y){

        if (this.playArea[x][y]==null && (x>=0 && x<playArea.length) && (y>=0 && y< playArea[0].length)){
            if(playArea[x-1][y]==null && playArea[x][y+1]==null && playArea[x+1][y]==null && playArea[x][y-1]==null){
                //check x-1, y-1, x+1, y+1 all'interno della matrice
                if (playArea[x-1][y+1]!=null && checkCorner(x,y,x-1,y+1))
                    return true;
                if (playArea[x+1][y+1]!=null && checkCorner(x,y,x+1,y+1))
                    return true;
                if (playArea[x+1][y-1]!=null && checkCorner(x,y,x+1,y-1))
                    return true;
                if (playArea[x-1][y-1]!=null && checkCorner(x,y,x-1,y-1))
                    return true;
            }

        }
        return false;
    }

    /**
     * method to check if the card the player wants to play is playable
     * @param cardChoice returns the position of the card in the player's hand (player's choice)
     * @param side returns false if the card is played
     * @return boolean true if the player can play the card he chose
     */
    public boolean checkCard (int cardChoice, boolean side){
        Card card= hand.get(cardChoice);
        int nAnimal=0, nFungi=0, nPlant=0, nInsect=0;
        if (side && (card.isGoldCard())) {
            GoldCard goldCard = (GoldCard) card;
            for(int i=0; i< goldCard.getCost().length; i++){
                if (goldCard.getCost()[i] == Resource.ANIMAL_KINGDOM ){
                    nAnimal++;
                }
                if (goldCard.getCost()[i] == Resource.FUNGI_KINGDOM ) {
                    nFungi++;
                }
                if (goldCard.getCost()[i] == Resource.PLANT_KINGDOM ) {
                    nPlant++;
                }
                if (goldCard.getCost()[i] == Resource.INSECT_KINGDOM ) {
                    nInsect++;
                }
            }

            if (nAnimal==summaryScore.get(Resource.ANIMAL_KINGDOM) && nFungi==summaryScore.get(Resource.FUNGI_KINGDOM) &&
            nPlant==summaryScore.get(Resource.PLANT_KINGDOM) && nInsect==summaryScore.get(Resource.INSECT_KINGDOM)) {
                return true;
                // check #cardResource==#playerResource
            }
        }
        return  false;
    }

    /**
     * method to check if the found card has visible corner
     * @param x possible x coordinate (of the play area) of the card the player wants to play
     * @param y possible y coordinate (of the play area) of the card the player wants to play
     * @param h first coordinate of the found card
     * @param k second coordinate of the found card
     * @return boolean true if the found card has only a visible corner, false otherwise
     */
    public boolean checkCorner (int x, int y, int h, int k){
        Card alreadyPlayedCard = playArea[h][k];
        if (h<x && k<y){ //case: c'è carta sopra a sx
            if (alreadyPlayedCard.getCorner(CornerPosition.BOTTOM_RIGHT).getVisibility()) {
                return true;
            }
        }
        else if (h<x && k>y) { //case: c'è carta sopra a dx
            if (alreadyPlayedCard.getCorner(CornerPosition.BOTTOM_LEFT).getVisibility()) {
                return true;
            }
        }
        else if (h>x && k<y) { //case: c'è carta sotto a sx
            if (alreadyPlayedCard.getCorner(CornerPosition.UP_RIGHT).getVisibility()) {
               return true;
            }
        }
        else if (h>x && k>y) { //case: c'è carta sotto a dx
            if (alreadyPlayedCard.getCorner(CornerPosition.UP_LEFT).getVisibility()) {
                return true;
            }
        }
        return false;
    }

}