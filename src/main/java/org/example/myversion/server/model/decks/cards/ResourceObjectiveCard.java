package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Map;
/**
 * Represents a resource objective card.
 * It specifies which and how many resources the player needs to have in his play area.
 */
public class ResourceObjectiveCard extends ObjectiveCard{
    private final Resource[] objective;

    /**
     * Constructs a ResourceObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies which and how many resources the player needs to have in his play area.
     */
    public ResourceObjectiveCard(int cardPoints, Resource[] objective) {
        super(cardPoints);
        this.objective = objective;
    }

    /**
     * Retrieves the resource objective.
     *
     * @return The array specifying which and how many resources the player needs to have in their play area.
     */

    @Override
    public CornerContent[] getCardKey(){
        return objective;//devo fare un altro tipo che restituisca
    }


    public Resource[] getObjective() {
        return objective;
    }

    @Override
    public int findObjectiveCard(Map<CornerContent, Integer> stock, Card[][] playArea,ObjectiveCard commonObjective) {
        // Implementazione per ResourceObjectiveCard

        int count=0;

        //trovo il numero di elementi che corrispondono a quella risorsa
        count=stock.getOrDefault(commonObjective.getCardKey()[0],0);
        //System.out.println("Stampo numero di elementi di quella risorsa cercata nello stock: : "+count);
        //ritorna multiplo di 2 se il numero di risorse è > di 3 altrimenti 0
        if(count>=3){
            return ((count / 3) * 2);
        }
        return 0;
    }
}
