package org.example.myversion.server.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the board for tracking players' scores in the game.
 * This class maintains a mapping between players and their respective scores.
 */
public class Board {
    private HashMap<Player, Integer> scores;


    /**
     * Constructs a new Board object with an empty score mapping.
     */
    public Board() {
        this.scores = new HashMap<Player, Integer>();
    }

    public void initializePlayerScores(List<Player> players) {
        for (Player player : players) {
            scores.put(player, 0);
        }
    }
    /**
     * Update the score for the given player.
     *
     * @param player The player whose score needs to be updated.
     * @param points The points to add to the player's score.
     */
    public void updateScore(Player player, Integer points){
        int currentScore=0;
        if(scores!=null)
            currentScore = scores.get(player);

        int newScore = currentScore + points;

        scores.put(player, newScore);
    }

    /**
     * Get the score of the specified player.
     *
     * @param player The player whose score is to be retrieved.
     * @return The score of the specified player.
     */
    public Integer getScore(Player player){
        return scores.get(player);
    }

    /**
     * Finds the player with the highest score on the board.
     *
     * @return The player with the highest score. Returns null if there are no players or if the scores map is empty.
     */
    public Player findWinner() {
        Player winner = null;
        int highestScore = 0;

        // Iterate through the entries in the scores map
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();

            // Update playerWithHighestScore if current player's score is higher
            if (score > highestScore) {
                highestScore = score;
                winner = player;
            }
        }

        return winner;
    }

    public void addPlayer(Player player){
        this.scores.put(player, 0);

    }
}
