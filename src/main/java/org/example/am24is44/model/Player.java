package org.example.am24is44.model;

public class Player {
    //attribute
    private String nickName;
    private Pion pion;
    private DrawableCard[] hand;
    private DrawableCard[][] playArea;
    private ObjectiveCard secretObjective;
    private int score;

    //constructor
    public Player(String nickName, Pion pion, DrawableCard[] hand, DrawableCard[][] playArea, ObjectiveCard secretObjective, int score) {
        this.nickName = nickName;
        this.pion = pion;
        this.hand = hand;
        this.playArea = playArea;
        this.secretObjective = secretObjective;
        this.score=score;
    }

    //get e set
    public void setNickName(String nome){nickName=nome;}
    public void setPion (Pion p){pion=p;}
    public String getNickName(){return nickName;}
    public Pion getPion(){return pion;}
    public DrawableCard[] getHand(){return hand;}
    public DrawableCard[][] getPlayArea(){return playArea;}
    public ObjectiveCard getSecretObjective() {return secretObjective;}
    public int getScore() {return score;}

    //scegli la carta obbiettvo
    public void ChooseObjectiveCard(){

    }

}
