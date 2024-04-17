package org.example.am24is44.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Player, Integer> scores;

    /**
     * Board's constructor
     */

    public Board(Map<Player, Integer> scores) {
        // Constructor implementation
        this.scores=scores;
    }

    //crea le chiavi e le impone =0
    public  void createKey (Player player){

        scores.put(player,0);
    }

    /**
     * Method to update the score for a player
     * @param player
     * @param newScore
     */
    public void updateScore(Player player, int newScore) {

        if (player != null) {
            scores.put(player, newScore); // Aggiorna l'elemento nella mappa
        }//else throws exception, nell'if dovrei controllare altre cose, che player ci sia nella mappa...
    }

    /**
     * Method to get the score of a player
     * @param player
     * @return scores.get(player)
     */
    public int getScore(Player player) {
        // Method implementation
        return scores.get(player);
        //exception?
    }
    //pion gestione
}
