package org.example.am24is44.model;public class Game {

    private Player[] players;
    private Player startingPlayer,currentPlayer;
    private GameStatus status;
    private int numberOfPlayer=0;

    public Game(Player[] players,Player startingPlayer,Player currentPlayer,GameStatus status){
        this.players=players;
        this.startingPlayer=startingPlayer;
        this.currentPlayer=currentPlayer;
        this.status=status;
    }

    public Player getStartingPlayer(){return startingPlayer;}
    public GameStatus getStatus(){return status;}

    public void createNewPlayer(String nickName,Pion pion, DrawableCard[] hand, DrawableCard[][] playArea,ObjectiveCard secretObjective,int score){
        Player newPlayer = new Player(nickName, pion, hand, playArea, secretObjective, score);
        players[numberOfPlayer] = newPlayer;
        numberOfPlayer++;
    }


}
