package org.example.myversion.server.model;

import org.example.myversion.server.model.decks.cards.ObjectiveCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * Retrieves the map of player scores.
     *
     * @return The map containing players and their respective scores.
     */
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    /**
     * Initializes the scores for each player to 0.
     *
     * @param players The list of players to initialize scores for.
     */
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
    public void updateScore(Player player, Integer points) {
        int currentScore = 0;
        if (scores != null)
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
    public Integer getScore(Player player) {
        return scores.get(player);
    }

    /**
     * Calculate the final scores for each player.
     *
     * @param commonObjectives common objectives of the current game
     * @return game winner
     */
    public Player findWinner(List<ObjectiveCard> commonObjectives) {
        Player winner = null;
        int highestScore = 0;

        // Get the set of keys (players)
        Set<Player> players = scores.keySet();

        // Iterate through the set of keys
        for (Player player : players) {
            int finalScore = scores.get(player);

            if (finalScore > highestScore) {
                highestScore = finalScore;
                winner = player;
            }
        }

        return winner;
    }

    /**
     * Calculates the final scores for each player based on their current scores and the points from objectives.
     *
     * @param commonObjectives The list of common objectives of the current game.
     */
    public void calculateFinalScores(List<ObjectiveCard> commonObjectives) {
        for (Player player : scores.keySet()) {
            int currentScore = scores.get(player);

            int objectiveScore = player.objectiveScoreCalculator(commonObjectives, player.getSecretObjective());

            int finalScore = currentScore + objectiveScore;

            scores.put(player, finalScore);
        }
    }
}
