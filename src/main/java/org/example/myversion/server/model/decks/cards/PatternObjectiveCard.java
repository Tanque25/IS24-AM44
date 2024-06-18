package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;

import java.util.Map;

/**
 * Represents a pattern objective card.
 * It specifies the pattern that the player has to recreate in his play area to get the points.
 */
public class PatternObjectiveCard extends ObjectiveCard {
    private final Resource[][] objectives;

    /**
     * Constructs a PatternObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective  specifies the pattern that the player has to recreate in his play area.
     */
    public PatternObjectiveCard(int cardPoints, Resource[][] objective, int id) {
        super(cardPoints, id);
        this.objectives = objective;
    }

    /**
     * Retrieves the pattern objective.
     *
     * @return The pattern that the player has to recreate in their play area.
     */
    public Resource[][] getObjective() {
        return objectives;
    }

    /**
     * Retrieves the key resources of the card.
     *
     * @return An array of CornerContent representing the key resources.
     */
    @Override
    public CornerContent[] getCardKey() {
        return objectives[0];
    }

    /**
     * Calculates the number of points based on how many times the pattern in the objective card is matched in the play area.
     *
     * @param stock     is the map that counts the Resource or SpecialObject in the play area.
     * @param playArea  is the matrix of cards of the player.
     * @param objective specifies the pattern that the player has to recreate in his play area.
     * @return The total points awarded based on the number of times the pattern is matched.
     */
    @Override
    public int calculateObjectiveCardPoints(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard objective) {
        PatternObjectiveCard objectiveCard = (PatternObjectiveCard) objective;

        // Retrieve the 3x3 resource pattern from the objective
        Resource[][] pattern = objectiveCard.getObjective();
        int patternRows = pattern.length;
        int patternCols = pattern[0].length;

        // Retrieve the points given each time the objective is achieved
        int objectiveCardPoints = objectiveCard.getCardPoints();

        int matches = 0;

        // Iterate through the playArea
        for (int i = 0; i <= playArea.length - patternRows; i++) {
            for (int j = 0; j <= playArea[i].length - patternCols; j++) {
                if (matchesPattern(playArea, pattern, i, j)) {
                    matches++;
                }
            }
        }

        // Calculate the total points by multiplying the matches count by the objective card points
        return matches * objectiveCardPoints;
    }

    /**
     * Checks if the pattern matches the submatrix in the play area starting from the specified coordinates.
     *
     * @param playArea the player's play area matrix.
     * @param pattern  the 3x3 resource pattern to match.
     * @param startX   the starting X coordinate in the play area.
     * @param startY   the starting Y coordinate in the play area.
     * @return true if the pattern matches the submatrix, false otherwise.
     */
    private boolean matchesPattern(Card[][] playArea, Resource[][] pattern, int startX, int startY) {
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length; j++) {
                if (pattern[i][j] != null) {
                    Card card = playArea[startX + i][startY + j];
                    if (card == null || !pattern[i][j].equals(card.getPlayableResource())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
