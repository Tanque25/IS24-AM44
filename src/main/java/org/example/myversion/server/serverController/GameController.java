package org.example.myversion.server.serverController;

import org.example.myversion.server.Server;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Game;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.StarterDeck;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.example.myversion.server.model.exceptions.InvalidNicknameException;

import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 * Manages the game dynamics including the lobby, player interactions, and connections.
 */
public class GameController {
    private Server server;
    public Game game;

    private GameState gamePhase;
    public List<String> disconnectedPlayers;
    public HashMap<String, Integer> pongLost;
    public List<String> pongReceived;
    public int roundsPlayed;
    private boolean gameOver;
    private int maxPlayerNumber;
    private boolean isGameLoaded;
    private boolean gameIsStarted;
    private boolean isFirstPlayer;
    private Player firstPlayer;
    private HashMap <Player, Integer> playerRoundsPlayed;
    private boolean lastTurn;
    private boolean roundOver;
    private GameState gameState;

    private int numberOfPlayer=0;

    //private final HashMap<String, ClientCommunicationInterface> rmiClients;
    //private final HashMap<String, SocketClientHandler> tcpClients;
    //private final List<Thread> checkThreads;
    //public static final String BACKUP_FILE = "backUp.json";
    //private final List<String> players;
    //public HashMap<String, Integer> winners;
    //public HashMap<String, Integer> losers;



    public GameController() {
        this.game = new Game();
        this.disconnectedPlayers = new ArrayList<>();
        this.pongLost = new HashMap<>();
        this.pongReceived = new ArrayList<>();
        this.isGameLoaded = false;
        this.gameIsStarted = false;
        this.gameOver = false;
        this.isFirstPlayer = true;
        this.maxPlayerNumber = 4;
        this.lastTurn=false;
        this.roundOver=false;
        this.playerRoundsPlayed= new HashMap<>();
        this.gameState = GameState.INITIALIZATION;
    }

    /**
     * This method add a Player in the game,
     * if isFirstPlayer is true the player chooses maxPlayerNumber (then isFirstPlayer became false), otherwise, each player's nickname will be checked,
     * there will be an update of the player's list
     * @param nickname the name of the player who wants to enter the game
     */
    public void addPlayer(String nickname){
        gameState = GameState.LOGIN;
        int numCurrentPlayers = 0;
        if (isFirstPlayer){
            game.newPlayer(nickname);
            numCurrentPlayers++;
            isFirstPlayer = false;
            firstPlayer = game.getPlayers().getFirst();
        }
        else {
            if(checkNickname(nickname)==1 && numCurrentPlayers<=maxPlayerNumber){
                game.newPlayer(nickname);
                numCurrentPlayers++;
            }
        }
    }

    private void initializeRoundMap (){
        for (Player player : game.getPlayers())
            playerRoundsPlayed.put(player, 0);
    }

    /**
     * Checks if the nickname is already present in the list of players or among the disconnected players
     * and returns 0, -1 respectively. If the username is available it returns 1.
     * @param nickname the username chosen by the player
     * @return a number representing the availability of the username
     */
    public int checkNickname(String nickname){
            for (Player player : game.getPlayers()) {
                if (player.getNickname().equals(nickname)) {
                    if (disconnectedPlayers.contains(nickname)) {
                        disconnectedPlayers.remove(nickname);
                        return -1;
                    }
                    return 0;
                }
            }
            return 1;
    }



    /**
     * Notifies the server that a client is still connected.
     * This method is called periodically by the client to indicate its activity.
     * @param nickname the name of the client that sent the pong.
     */
    public void pong(String nickname){
        //dentro il pong chiamo checkNickaname ?
    }

    /**
     * Adds a client that has lost connection to the list of disconnected clients.
     * This method is called when a client disconnects from the server.
     * @param nickname the name of the disconnected client.
     *
     */
    public void addPongLost(String nickname){

    }

    /**
     * Checks if the specified player is the first player.
     * If the player is not the first player, the method sets isFirstPlayer to false.
     *
     * @param nickname The name of the player to check.
     * @return True if the player is the first player, false otherwise.
     */
    public boolean isFirstPlayer(String nickname) {
        return isFirstPlayer;
    }


    /**
     * It checks if the player's number chosen by the first player is correct
     * @return true if the player's number chosen by the first player is correct
     * @throws InvalidChoiceException if the number of players is not within the valid range
     */
    public boolean checkNumberOfPlayer(int numPlayer) throws InvalidChoiceException{
        if(numPlayer>=2 && numPlayer<=4) {
            return true;
        }
        else {
            throw new InvalidChoiceException("Invalid number of players. Please change the number.");
        }
    }

    /**
     * Checks if the game state is saved.
     * @return True if the game state is saved, false otherwise.
     */
    public boolean isGameSaved() {
        return isGameSaved();
    }

    /**
     * If isGameSaved is false this method starts a new game, resetting the game state.
     */
    public void newGame() {
        //game = new Game();
        game.getBoard().initializePlayerScores(game.getPlayers());
        initializeRoundMap();
        roundsPlayed = 0;

        for (Player player : game.getPlayers()) {
            List<ObjectiveCard> secretObjectives = game.drawSecretObjectives();
            //draw secret obj -> set secret obj
                game.setPlayerSecretObjective(player, secretObjectives.getFirst());
                game.setPlayerSecretObjective(player, secretObjectives.get(1));
                //choose starter card - > place starter card (initialize play area)
                StarterCard starterCard = game.drawStarterCard();
                game.placeStarterCard(player, starterCard);
        }
        gameIsStarted = true;
        gameState = GameState.IN_GAME;
    }

    /**
     * Loads the last saved game state.
     */
    public void loadLastGame() {
    }

    public boolean isValidMove(){
        //da togliere???
        return true;
    }

    /**
     * Checks if the game has started.
     *
     * @return True if the game has started, false otherwise.
     */
    public boolean gameIsStarted() {
        return gameIsStarted;
    }

    /**
     * Allows a player to play a card, it will call the game's playCard
     * This method will be called by the client to play a card.
     *
     * @param nickname the name of the player who is playing the card.
     * @param card the card to be played.
     * @param coordinates the coordinates on the board where the card will be placed.
     */
    public void playCard(String nickname, PlayableCard card, Coordinates coordinates) throws InvalidNicknameException, InvalidMoveException {
        Player actualPlayer = null;

        if (gameState == GameState.IN_GAME || gameState == GameState.LAST_ROUND) {
            for (Player player : game.getPlayers()) {
                if (player.getNickname().equals(nickname)) {
                    actualPlayer = player;
                    try {
                        game.playCard(actualPlayer, card, coordinates);
                        playerRoundsPlayed.put(actualPlayer, playerRoundsPlayed.get(player) + 1);
                        roundsPlayed++;
                        lastTurn = checkLastTurn();
                        gameOver = endGame();
                    } catch (InvalidMoveException e) {
                        throw new InvalidMoveException("Invalid move: " + e.getMessage());
                    }
                    return; // Esci dal metodo dopo aver giocato la carta, non so se ha senso
                }

            }

            // Se actualPlayer è ancora null, il nickname non è stato trovato
            if (actualPlayer == null) {
                throw new InvalidNicknameException("Invalid nickname");
            }

            if (actualPlayer == game.getPlayers().getFirst() && roundOver)
                roundOver = false;
        }

    }



    /**
     * Allows a player to draw a card from the available options.
     * This method will be called by the client to draw a card.
     *
     * @param nickname the name of the player who is drawing the card.
     * @param chosenCard The card chosen by the player.
     */
    public void drawCard(String nickname, PlayableCard chosenCard) throws InvalidNicknameException, InvalidChoiceException {
        Player actualPlayer = null;

        if(gameState == GameState.IN_GAME) {
            for (Player player : game.getPlayers()) {
                if (player.getNickname().equals(nickname)) {
                    actualPlayer = player;


                    game.drawCard(actualPlayer, chosenCard);
                    changeTurn();

                    if (actualPlayer == game.getPlayers().getLast() && !roundOver) {
                        roundOver = true;
                    }

                    // Check se è l'ultimo turno
                    lastTurn = checkLastTurn();
                }
            }
        }
    }

    /**
     * Changes the turn to the next player in the game.
     * This method will be called to switch the turn to the next player.
     */
    public void changeTurn() {

        if (gameState == GameState.IN_GAME || gameState == GameState.LAST_ROUND) {
            int currentIndex = game.getPlayers().indexOf(game.getCurrentPlayer());
            int nextIndex = (currentIndex + 1) % game.getPlayers().size();
            game.setCurrentPlayer(game.getPlayers().get(nextIndex));
        }
    }

    /**
     * Checks if the lobby is full and starts a new game if it is.
     *
     * @return {@code true} if the lobby is full and a new game is started; {@code false} otherwise.
     */
    public boolean checkLobby() {
        // Se il numero di giocatori nella lobby è uguale al massimo consentito
        // Se la lobby non è ancora piena
        return game.getPlayers().size() == numberOfPlayer;
    }


    /**
     * Checks if it is the last turn of the game.
     * @return true if it is the last turn, false otherwise.
     */
    public boolean isLastTurn() {
        return isLastTurn();
    }

    /**
     * Handles the disconnection of a player.
     * This method will be called when a player disconnects from the game.
     * It removes the disconnected player from the list of active players and adds them to the list of disconnected players.
     *
     * @param nickname the name of the disconnected player.
     */
    public void disconnected(String nickname) {

    }

    /**
     * Checks if the game is over.
     * This method will be called to determine if the game has ended.
     * @return True if the game is over, false otherwise.
     */
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Resets the saved game state.
     * This method will be called to reset the saved game state.
     */
    public void resetSavedGame() {
    }

    /**
     * Sets the winner of the game.
     * This method will be called to set the winner of the game.
     */
    public void setWinner() {
        if (gameState == GameState.LAST_ROUND) {
            game.winner();
        }
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    /**
     * Checks whether it is the last turn of the game.
     * The last turn is triggered when at least one player reaches (or exceeds) a certain score
     * or when both decks are empty. Players finish their current rounds and each plays an additional
     * turn, so they resolve the same number of turns by the end of the game.
     *
     * @return  true if it is the last turn,  false otherwise.
     */
    public boolean checkLastTurn(){
        lastTurn = game.checkLastTurn();
        return lastTurn;

    }

    /**
     * Checks whether an extra round should be played.
     * An extra round is played when it is the last turn of the game and all players have finished
     * their current rounds.
     *
     * @return true if an extra round should be played, false otherwise.
     */
    public boolean playExtraRound(){
        if (lastTurn && roundOver && gameState == GameState.IN_GAME) {
            gameState = GameState.LAST_ROUND;
        }
        return lastTurn && roundOver;
    }

    /**
     * Checks if the game is over.
     * This method will be called to determine if the game has ended.
     * @return True if the game is over, false otherwise.
     */
    public boolean endGame(){
        if(gameState == GameState.LAST_ROUND) {
            for (Player player : game.getPlayers())
                if (playerRoundsPlayed.get(player) == roundsPlayed) {
                    gameOver = true;
                    gameState = GameState.END;
                }
        }
        return gameOver;
    }

    public GameState GamePhase (){
        return gamePhase;
    }

}
