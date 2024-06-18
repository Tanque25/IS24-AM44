package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.myversion.server.model.enumerations.SpecialObject.*;

/**
 * Represents a special object objective card.
 * It specifies which and how many special objects the player needs to have in his play area.
 */
public class SpecialObjectiveCard extends ObjectiveCard {
    private final SpecialObject[] objective;

    /**
     * Constructs a SpecialObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective  specifies which and how many special objects the player needs to have in his play area.
     */
    public SpecialObjectiveCard(int cardPoints, SpecialObject[] objective, int id) {
        super(cardPoints, id);
        this.objective = objective;
    }

    /**
     * Retrieves the special object objective.
     *
     * @return The array specifying which and how many special objects the player needs to have in their play area.
     */

    public SpecialObject[] getObjective() {
        return objective;
    }

    //Restituisce objective della carta con override
    @Override
    public CornerContent[] getCardKey() {
        return objective;//devo fare un altro tipo che restituisca tutto
    }

    @Override
    public int calculateObjectiveCardPoints(Map<CornerContent, Integer> stock, Card[][] playArea, ObjectiveCard objective) {
        // Create a map with the count of each special object in the objective card array
        Map<CornerContent, Integer> specialObjectCounts = Arrays.stream(objective.getCardKey())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));

        // Retrieve the points given each time the objective is achieved
        int objectiveCardPoints = objective.getCardPoints();

        // Initialize the minimum count with a large number
        int minCount = Integer.MAX_VALUE;

        // Iterate over each entry in the special object counts map
        for (Map.Entry<CornerContent, Integer> entry : specialObjectCounts.entrySet()) {
            CornerContent specialObject = entry.getKey();
            int requiredCount = entry.getValue();

            // Get the count of the current special object in the stock
            int stockCount = stock.getOrDefault(specialObject, 0);

            // Calculate how many times we can fulfill the requirement for the current special object
            int objectCount = stockCount / requiredCount;

            // Update the minimum count
            if (objectCount < minCount) {
                minCount = objectCount;
            }
        }

        // Calculate the total points by multiplying the minimum count by the objective card points
        return minCount * objectiveCardPoints;
    }
}
