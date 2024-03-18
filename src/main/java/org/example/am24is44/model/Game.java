package org.example.am24is44.model;public class Game {

    private Player[] players;
    private Player startingPlayer,currentPlayer;
    private GameStatus status;

    public Game(Player[] players,Player startingPlayer,Player currentPlayer,GameStatus status){
        this.players=players;
        this.startingPlayer=startingPlayer;
        this.currentPlayer=currentPlayer;
        this.status=status;
    }


}
