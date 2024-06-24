package org.example.myversion.server.serverController;

import org.example.myversion.client.ClientCommunicationInterface;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Board;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Game;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.GoldDeck;
import org.example.myversion.server.model.decks.ResourceDeck;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 * Manages the game dynamics including the lobby, player interactions, and connections.
 */
public class GameController {
    public static Game game;
    public HashMap<String, Integer> pongLost;
    public List<String> pongReceived;
    private HashMap<String, HandleClientSocket> tcpClients;
    private HashMap<String, ClientCommunicationInterface> rmiClients;
    private GameState gameState;
    public int roundsPlayed;
    public int playersNumber;
    private static int readyPlayersNumber = 0;
    private Player lastPlayer;
    private HashMap <Player, Integer> playerRoundsPlayed;

    // Game backup attributes
    public List<String> disconnectedPlayers;
    public static final String BACKUP_FILE = "backUp.json";
    private Map<String, List<PlayableCard>> handsMap;
    private Map<String, Card[][]> playAreasMap;

    public GameController() {
        this.game = new Game();
        this.pongLost = new HashMap<>();
        this.pongReceived = new ArrayList<>();
        this.tcpClients = new HashMap<>();
        this.rmiClients = new HashMap<>();
        this.playersNumber = 0;
        this.playerRoundsPlayed = new HashMap<>();
        this.gameState = GameState.LOGIN;
        this.lastPlayer = null;

        this.disconnectedPlayers = new ArrayList<>();
        this.handsMap = new HashMap<>();
        this.playAreasMap = new HashMap<>();
    }

    /**
     * Checks if the nickname is already present in the list of players or among the disconnected players
     * and returns 0, -1 respectively. If the username is available it returns 1.
     *
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
        } else {
            if (!gameIsFull()) {
                game.newPlayer(nickname);
                if(game.getPlayers().size()== playersNumber){
                    lastPlayer = game.getPlayers().get(playersNumber -1);
                    newGame();
                }
            }
        }
    }

    /**
     * @return true if the player is the first player, false otherwise
     */
    public boolean isFirst() {
        return tcpClients.size() + rmiClients.size() == 1;
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
     * Method to add a TCP client to the game controller
     *
     * @param nickname the nickname of the player
     * @param client   the client associated to the player
     */
    public void addClientTCP(String nickname, HandleClientSocket client) {
        tcpClients.put(nickname, client);
    }

    public boolean isTCP(String nickname) {
        return tcpClients.containsKey(nickname);
    }

    /**
     * Method to add an RMI client to the game controller
     *
     * @param nickname the nickname of the player
     * @param client   the client associated to the player
     */
    public void addClientRMI(String nickname, ClientCommunicationInterface client) {
        rmiClients.put(nickname, client);
    }

    public boolean isRMI(String nickname) {
        return rmiClients.containsKey(nickname);
    }

    /**
     * Sets the secret objective card for the selected player.
     *
     * @param player who chose the objective card.
     * @param objectiveCard chosen objective card.
     */
    public void chooseObjectiveCard(Player player, ObjectiveCard objectiveCard){
        game.setPlayerSecretObjective(player, objectiveCard);
    }

    public void playStarterCard(Player player, StarterCard starterCard) {
        game.placeStarterCard(player, starterCard);
    }

    public Player getPlayerFromNickname(String nickname) {
        Player player = null;

        for(Player p : game.getPlayers()) {
            if (p.getNickname().equals(nickname))
                player = p;
        }

        return player;
    }

    public void updateReadyPlayersNumber() {
        readyPlayersNumber = readyPlayersNumber + 1;
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    ///////////////////////////////////////////////////////GETTERS AND SETTERS////////////////////////////////////////////////////////

    public HashMap<String, ClientCommunicationInterface> getRmiClients() {
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

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
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

    public PlayableCard getRsourceDeckPeek() {
        return game.getResourceDeckPeek();
    }

    public GoldCard getGoldDeckPeek() {
        return game.getGoldDeckPeek();
    }

    public List<PlayableCard> getVisibleResourceCards() {
        return game.getVisibleResourceCards();
    }

    public List<GoldCard> getVisibleGoldCards() {
        return game.getVisibleGoldCards();
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

    public int getReadyPlayersNumber() {
        return readyPlayersNumber;
    }

    public Map<String, StarterCard> getStarterCardsMap() {
        Map<String, StarterCard> starterCardsMap = new HashMap<>();

        for(Player player : game.getPlayers()) {
            Card[][] playArea = player.getPlayArea();
            starterCardsMap.put(player.getNickname(), (StarterCard) playArea[41][41]);
        }

        return starterCardsMap;
    }

    public Map<String, List<PlayableCard>> getPlayersHandsMap() {
        Map<String, List<PlayableCard>> playersHandsMap = new HashMap<>();

        for(Player player : game.getPlayers()) {
            playersHandsMap.put(player.getNickname(), player.getHand());
        }

        return playersHandsMap;
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

                    if(isLastRound() && game.getCurrentPlayer().equals(lastPlayer)){
                        roundsPlayed++;
                        gameState = GameState.END;
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

                if (game.getCurrentPlayer().equals(lastPlayer)) {
                    roundsPlayed++;
                    if(checkScores())
                        gameState = GameState.LAST_ROUND;
                }

            } else {
                throw new InvalidNicknameException("Invalid nickname");
            }
        } else {
            throw new InvalidGameStateException("Drawing a card in an invalid game state");
        }
    }

    public boolean gameIsFull(){
        System.out.println("numero giocatori: "+game.getPlayers().size());
        System.out.println("numero giocatori settato: "+playersNumber);
        if(game.getPlayers().size() == playersNumber){
            return true;
        }
        return false;
    }

    public boolean gameIsEmpty(){
        return game.getPlayers().isEmpty();
    }

    /**
     * Notifies the server that a client is still connected.
     * This method is called periodically by the client to indicate its activity.
     * @param nickname the name of the client that sent the pong.
     */
    public void pong(String nickname){
        if(!pongReceived.contains(nickname)) {
            pongReceived.add(nickname);
        }
        pongLost.remove(nickname);
        pongLost.put(nickname, 0);
    }

    /**
     * Adds a client that has lost connection to the list of disconnected clients.
     * This method is called when a client disconnects from the server.
     * @param nickname the name of the disconnected client.
     *
     */
    public void addPongLost(String nickname){
        int currentPongLost = pongLost.get(nickname);
        pongLost.remove(nickname);
        pongLost.put(nickname, currentPongLost + 1);
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
     * Checks if the game has started.
     *
     * @return True if the game has started, false otherwise.
     */
    public boolean isGameStarted(){
        return gameState.equals(GameState.IN_GAME) || gameState.equals(GameState.LAST_ROUND);
    }

    /**
     * Changes the turn to the next player in the game.
     * This method will be called to switch the turn to the next player.
     */
    public void changeTurn() {
        game.updateCurrentPlayer();
    }

    public Map<String, Integer> getScores() {
        HashMap<Player, Integer> scores = game.getBoard().getScores();
        Map<String, Integer> scoresByNickname = new HashMap<>();

        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            Player player = entry.getKey();
            Integer score = entry.getValue();
            scoresByNickname.put(player.getNickname(), score);
        }

        // Returns a map of the scores by nickname
        return scoresByNickname;
    }

    /**
     * Calculate and get the final scores
     *
     * @return map with each player's score
     */
    public Map<String, Integer> getFinalScores() {
        game.getBoard().calculateFinalScores(game.getCommonObjectives());

        return getScores();
    }

    public boolean checkScores() {
        for(Player player : game.getPlayers()){
            if(game.getBoard().getScore(player)>=2){
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
        return gameState.equals(GameState.END);
    }

    /**
     * Retrieves the nickname of the winner.
     */
    public String findWinner() {
        return game.winner().getNickname();
    }

    /**
     * Checks whether it is the last turn of the game.
     * The last turn is triggered when at least one player reaches (or exceeds) a certain score
     * or when both decks are empty. Players finish their current rounds and each plays an additional
     * turn, so they resolve the same number of turns by the end of the game.
     *
     * @return  true if it is the last turn,  false otherwise.
     */
    public boolean isLastRound() {
        return gameState.equals(GameState.LAST_ROUND) || gameState.equals(GameState.END);
    }

    /**
     * Saves the current game in the json backup file
     */
    public void saveGame() {
        new Message("Backup", getScores(),
                game.getCommonObjectives().get(0), game.getCommonObjectives().get(1),
                getSecretObjectives(),
                game.getResourceDeck(), game.getGoldDeck(),
                game.getVisibleResourceCards(), game.getResourceDeckPeek(),
                game.getVisibleGoldCards(), game.getGoldDeckPeek(),
                getPlayersHandsMap(), getPlayAreas(),
                game.getCurrentPlayer().getNickname(), lastPlayer.getNickname(), gameState
        );
        System.out.println("Game saved.");
    }

    /**
     * Checks whether the game has been saved or not. This is done by
     * checking if the JSON that contains the state of the game exists.
     *
     * @return true if the game has been saved, false otherwise
     */
    public boolean isGameSaved() {
        File jsonGame = new File(BACKUP_FILE);
        return jsonGame.exists();
    }

    /**
     * Loads the game saved in the JSON file.
     *
     * @throws IOException if the file does not exist
     */
    public void loadLastGame() throws IOException {
        File jsonGame = new File(BACKUP_FILE);

        // Parse the JsonObject into a Message
        Message lastGame = new Message(jsonGame);

        // Restore the game resource and gold deck
        game.setResourceDeck(new ResourceDeck(lastGame.getResourceDeck()));
        game.setGoldDeck(new GoldDeck(lastGame.getGoldDeck()));

        // Restore the common objectives
        game.setCommonObjectives(lastGame.getObjectiveCards());

        // Restore the visible cards
        game.setVisibleResourceCards(lastGame.getResourceCards());
        game.setVisibleGoldCards(lastGame.getGoldCards());

        // Initialize the game players and their scores
        Map<String, Integer> scores = lastGame.getScores();
        Map<String, ObjectiveCard> secretObjectiveCards = lastGame.getSecretObjectiveCards();
        Board board = game.getBoard();

        for (String playerNickname : scores.keySet()) {
            game.newPlayer(playerNickname);
            Player player = getPlayerFromNickname(playerNickname);
            if (player != null) {
                board.getScores().put(player, scores.get(playerNickname));
                disconnectedPlayers.add(playerNickname); // Add the game players to the disconnected players so that they can rejoin
            }
        }

        // Set the game players number
        playersNumber = game.getPlayers().size();

        // Restore the secret objectives
        Map<String, ObjectiveCard> secretObjectives = lastGame.getSecretObjectiveCards();
        for (Player player : game.getPlayers()) {
            if (secretObjectives.containsKey(player.getNickname())) {
                game.setPlayerSecretObjective(player, secretObjectives.get(player.getNickname()));
            }
        }

        // Restore the players' hands
        game.restorePlayersHands(lastGame.getPlayersHandsMap());
        this.handsMap = lastGame.getPlayersHandsMap();

        // Restore the players' play areas
        game.restorePlayersPlayAreas(lastGame.getPlayAreasMap());
        this.playAreasMap = lastGame.getPlayAreasMap();

        // Restore the current player
        String currentPlayerNickname = lastGame.getJson().getString("currentPlayer");
        if (currentPlayerNickname != null) {
            game.setCurrentPlayer(getPlayerFromNickname(currentPlayerNickname));
        }

        // Restore the last player
        String lastPlayerNickname = lastGame.getJson().getString("lastPlayer");
        if (lastPlayerNickname != null) {
            lastPlayer = getPlayerFromNickname(lastPlayerNickname);
        }

        // Restore the last turn status
        String lastRoundState = lastGame.getJson().getString("lastRound");
        if (lastRoundState != null) {
            gameState = GameState.valueOf(lastRoundState);
        }

        System.out.println("Game loaded successfully.");

    }

    private Map<String, ObjectiveCard> getSecretObjectives() {
        Map<String, ObjectiveCard> secretObjectives = new HashMap<>();

        for (Player player : game.getPlayers()) {
            secretObjectives.put(player.getNickname(), player.getSecretObjective());
        }

        return secretObjectives;
    }

    private Map<String, Card[][]> getPlayAreas() {
        Map<String, Card[][]> playAreas = new HashMap<>();

        for (Player player : game.getPlayers()) {
            playAreas.put(player.getNickname(), player.getPlayArea());
        }

        return playAreas;
    }

    public List<String> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    public Map<String, List<PlayableCard>> getHandsMap() {
        return handsMap;
    }

    public Map<String, Card[][]> getPlayAreasMap() {
        return playAreasMap;
    }
}
