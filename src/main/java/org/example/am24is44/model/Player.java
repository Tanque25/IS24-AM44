package org.example.am24is44.model;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class Player {
    private String nickname;
    private List<Card> hand;
    private Card[][] playArea;
    private ObjectiveCard secretObjective;
    private Map<MergeEnumInterface, Integer> summaryScore;

    private int score;
    //private int achievedObjective;

    /**
     * player's constructor
     * @param nickname
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
    private void initializePlayArea(StarterCard starterCard) {
        playArea[41][41] = starterCard;
    }

    /**
     * Method to set the player's secret objective
     * @param card
     */
    private void setSecretObjective(ObjectiveCard card) {

        this.secretObjective = card;
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
     * @param handChoice
     * @param x
     * @param y
     */
    public void playCard(int handChoice, int x, int y) {
        Card card = hand.get(handChoice);
        playArea[x][y] = card;
        hand.remove(card);
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Method to draw a card
     * @param card
     */
    public void drawCard(Card card) {
        hand.add(card);
    }

    /**
     *Method to calculate the final score
     * @return 0
     */
    public int finalScoreCalculator(List<ObjectiveCard> commonObjectives) {

        int ObjectiveScore=0;
        for (ObjectiveCard obbiettivo : commonObjectives) {
            //qui fare un flag per ogni tipo di obbiettivo controllo se rispettato:
            //per esempio nella se flag=0 --> è un pattern objective e quindi controllo nella play area la disposizione
            //se è un recource Objective --> controllo le risorse
            //se è un specialObjective -->controllo gli special object
            //è un' idea che ho buttato li in 3 min, probabilmente ce ne sono di migliori, magari utilizzando
            //il polimorfismo?
        }
        return ObjectiveScore;
    }

    /**
     * method to check the possible position of the card the player wants to play
     * @param x
     * @param y
     * @return boolean
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
     * @param cardChoice
     * @param side
     * @return boolean
     */
    public boolean checkCard (int cardChoice, boolean side){
        Card card= hand.get(cardChoice);
        int nAnimal=0, nFungi=0, nPlant=0, nInsect=0;
        if (side && (card instanceof GoldCard)) {
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
     * @param x
     * @param y
     * @param h
     * @param k
     * @return boolean
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