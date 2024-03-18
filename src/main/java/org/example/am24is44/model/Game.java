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

    public void createNewPlayer(String nickName,Pion pion, DrawableCard[] hand, DrawableCard[][] playArea,ObjectiveCard secretObjective,int score,int position){
        Player newPlayer = new Player(nickName, pion, hand, playArea, secretObjective, score,position);
        players[numberOfPlayer] = newPlayer;
        numberOfPlayer++;
    }

    public Player getCurrentPlayer(){return currentPlayer;}
    public Player nextPlayer(Player currentPlayer) {
        int nextPlayerIndex = (currentPlayer.getPosition() + 1) % players.length;
        return players[nextPlayerIndex];
    }


}
