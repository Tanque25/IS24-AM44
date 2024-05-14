package org.example.myversion.server.serverController;

import org.example.myversion.client.Client;
import org.example.myversion.server.Server;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Game;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;
import org.example.myversion.server.model.exceptions.*;

import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 * Manages the game dynamics including the lobby, player interactions, and connections.
 */
public class GameController {
    private Server server;

    public static Game game;
    public List<String> disconnectedPlayers;
    public HashMap<String, Integer> pongLost;
    public List<String> pongReceived;
    private HashMap<String, HandleClientSocket> tcpClients;
    private HashMap<String, Client> rmiClients;
    private GameState gameState;
    public int roundsPlayed;
    private int maxPlayerNumber;
    private boolean isGameLoaded;
    private Player firstPlayer;
    private Player lastPlayer;
    private HashMap <Player, Integer> playerRoundsPlayed;
    private int numberOfPlayer;

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
        this.tcpClients = new HashMap<>();
        this.rmiClients = new HashMap<>();
        this.maxPlayerNumber = 0;
        this.isGameLoaded = false;
        this.playerRoundsPlayed= new HashMap<>();
        this.gameState = GameState.LOGIN;
        this.firstPlayer = null;
        this.lastPlayer = null;
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
                    // return -1 if the selected nickname corresponds to a disconnected player
                    return -1;
                }
                // return 0 if nickname is already present
                return 0;
            }
        }
        // return 1 if nickname is not present yet
        return 1;
    }

    public void addPlayer(String nickname){
        if (gameIsEmpty()) {
            game.newPlayer(nickname);
            firstPlayer = game.getPlayers().getFirst();
        } else {
            if (!gameIsFull()) {
                game.newPlayer(nickname);
                if(game.getPlayers().size()==maxPlayerNumber){
                    lastPlayer = game.getPlayers().get(maxPlayerNumber-1);
                    newGame();
                }
            }
        }
    }

    /**
     * Method to add a client to the game controller
     *
     * @param nickname the nickname of the player
     * @param client   the client associated to the player
     */
    public void addClient(String nickname, HandleClientSocket client) {
        tcpClients.put(nickname, client);
    }

    public void addClient(String nickname, Client client) {
        rmiClients.put(nickname, client);
    }

    public HashMap<String, Client> getRmiClients() {
        return rmiClients;
    }

    public HashMap<String, HandleClientSocket> getTcpClients() {
        return tcpClients;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Game getGame() {
        return game;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setMaxPlayerNumber(int maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public HashMap<Player, Integer> getPlayerRoundsPlayed() {
        return playerRoundsPlayed;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void newGame() {
        //game = new Game();
        gameState = GameState.INITIALIZATION;
        game.initializeGame();
        initializeRoundMap();
        roundsPlayed = 0;
    }

    private void initializeRoundMap (){
        for (Player player : game.getPlayers())
            playerRoundsPlayed.put(player, 0);
    }

    /**
     * Draws two secret objective cards form the objective deck to pass them to all the clients.
     *
     * @return the list of common objectives cards for the current game.
     */
    public List<ObjectiveCard> getCommonObjectiveCards() {
        return game.getCommonObjectives();
    }

    /**
     * Draws two objective cards from the objective deck. The server will pass these two cards to the client who will get to choose one of them.
     *
     * @return the two possible secret objective cards.
     */
    public List<ObjectiveCard> getSecretObjectiveCardsOptions() {
        return game.drawSecretObjectives();
    }

    /**
     * Draws a starter card from the starter deck in the game.
     *
     * @return a starter card.
     */
    public StarterCard getStarterCard() {
        return game.drawStarterCard();
    }

    /**
     * Sets the secret objective card for the selected player.
     *
     * @param player who chose the objective card.
     * @param card chosen objective card.
     */
    public void chooseObjectiveCard(Player player, ObjectiveCard card){
        game.setPlayerSecretObjective(player, card);
    }

    public void playStarterCard(Player player, StarterCard card, int side) throws InvalidGameStateException {
        if(gameState == GameState.INITIALIZATION) {
            if (side == 0) {
                card.setPlayedBack(true);
            }
            game.placeStarterCard(player, card);
            if (player.equals(lastPlayer)) {
                gameState = GameState.IN_GAME;
            }
        } else {
            throw new InvalidGameStateException("Invalid game state");
        }
    }

    /**
     * Allows a player to play a card, it will call the game's playCard
     * This method will be called by the client to play a card.
     *
     * @param nickname the name of the player who is playing the card.
     * @param card the card to be played.
     * @param coordinates the coordinates on the board where the card will be placed.
     */
    public void playCard(String nickname, PlayableCard card, Coordinates coordinates) throws InvalidNicknameException, InvalidMoveException, InvalidGameStateException {

        if (gameState == GameState.IN_GAME || gameState == GameState.LAST_ROUND) {
            if(game.getCurrentPlayer().getNickname().equals(nickname)){
                try {
                    game.playCard(game.getCurrentPlayer(), card, coordinates);
                    // gameOver = endGame(); cos'è gameOver?
                    if(isLastTurn() && game.getCurrentPlayer().equals(lastPlayer)){
                        roundsPlayed++;
                        gameState = GameState.END;
                        // Da capire cosa farsene del winner, se mettere un attributo o se pensa a tutto il server
                        Player winner = game.winner();
                    }
                } catch (InvalidMoveException e) {
                    throw new InvalidMoveException("Invalid move: " + e.getMessage());
                }
            }
            else {
                throw new InvalidNicknameException("Invalid nickname");
            }
        } else throw new InvalidGameStateException("Invalid game state");

    }

    /**
     * Allows a player to draw a card from the available options.
     * This method will be called by the client to draw a card.
     *
     * @param nickname the name of the player who is drawing the card.
     * @param chosenCard The card chosen by the player.
     */
    public void drawCard(String nickname, PlayableCard chosenCard) throws InvalidNicknameException, InvalidChoiceException, InvalidGameStateException {

        if(gameState == GameState.IN_GAME) {
            if(game.getCurrentPlayer().getNickname().equals(nickname)){
                game.drawCard(game.getCurrentPlayer(), chosenCard);
                playerRoundsPlayed.put(game.getCurrentPlayer(), playerRoundsPlayed.get(game.getCurrentPlayer()) + 1);
                if (game.getCurrentPlayer().equals(lastPlayer)) {
                    roundsPlayed++;
                    if(checkScores()){
                        gameState = GameState.LAST_ROUND;
                    }
                }
                changeTurn();
            }else{
                throw new InvalidNicknameException("Invalid nickname");
            }
        } else {
            throw new InvalidGameStateException("Invalid game state");
        }
    }

    public boolean gameIsFull(){
        if(game.getPlayers().size() == maxPlayerNumber){
            return true;
        }
        return false;
    }

    public boolean gameIsEmpty(){
        if(game.getPlayers().isEmpty()){
            return true;
        }
        return false;
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


    /**
     * It checks if the player's number chosen by the first player is correct
     * @return true if the player's number chosen by the first player is correct
     */
    public boolean checkNumberOfPlayer(int numPlayer){
        return numPlayer >= 2 && numPlayer <= 4;//ritorna true se compreso
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
    public boolean isGameStarted(){
        if(gameState.equals(GameState.IN_GAME) || gameState.equals(GameState.LAST_ROUND)){
            return true;
        }
        return false;
    }

    /**
     * Changes the turn to the next player in the game.
     * This method will be called to switch the turn to the next player.
     */
    public void changeTurn() {

        if (isGameStarted() || gameState == GameState.INITIALIZATION){
            game.updateCurrentPlayer();
        }
    }

    public boolean checkScores() {
        for(Player player : game.getPlayers()){
            if(game.getBoard().getScore(player)>=20){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the deck is empty.
     * This method will be called to check if the deck is empty.
     * @return True if the deck is empty, false otherwise.
     */
    public boolean checkEmptyDeck() {
        return game.checkLastTurn();
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
    public boolean isGameOver() {
        if(gameState.equals(GameState.END)){
            return true;
        }
        return false;
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
        if (gameState == GameState.END) {
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
    public boolean isLastTurn() {
        if(gameState.equals(GameState.LAST_ROUND)){
            return true;
        }
        return false;
    }
}
