package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;
import java.util.Map;
import java.lang.Math;
import static org.example.myversion.server.model.enumerations.SpecialObject.*;

/**
 * Represents a special object objective card.
 * It specifies which and how many special objects the player needs to have in his play area.
 */
public class SpecialObjectiveCard extends ObjectiveCard{
    private final SpecialObject[] objective;

    /**
     * Constructs a SpecialObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies which and how many special objects the player needs to have in his play area.
     */
    public SpecialObjectiveCard(int cardPoints, SpecialObject[] objective) {
        super(cardPoints);
        this.objective = objective;
    }

    /**
     * Retrieves the special object objective.
     *
     * @return The array specifying which and how many special objects the player needs to have in their play area.
     */

    @Override
    public Resource[][] getObjective() {
        return null;
    }

    //Restituisce objective della carta con override
    @Override
    public CornerContent[] getCardKey(){
        return objective;//devo fare un altro tipo che restituisca tutto
    }

    @Override
    public int findObjectiveCard(Map<CornerContent, Integer> stock, Card[][] playArea,ObjectiveCard commonObjective) {

        //count è punteggio assegnato che ritorni
        int count;
        int countINKWELL=0,countMANU=0;
        // special Object che danno 2 punti
        if(commonObjective.getCardKey().length>=2){
            if (commonObjective.getCardKey()[0]==commonObjective.getCardKey()[1]){
                //se ci sono piu di 2 MANUSCRIPT/QUILL/INKWELL
                count = stock.getOrDefault(commonObjective.getCardKey()[0], 0);
                if (count >= 2) {
                    return count;
                }

            }
            // lo special Object che da 3 punti
            else if(commonObjective.getCardKey()[0]==QUILL && commonObjective.getCardKey()[1]==INKWELL && commonObjective.getCardKey()[2]==MANUSCRIPT){

                count=stock.getOrDefault(commonObjective.getCardKey()[0], 0);
                //se c'è una QUILL nello stock
                if (count>=1){
                    countINKWELL=stock.getOrDefault(commonObjective.getCardKey()[1], 0);
                    //se c'è una INKWELL nello stock
                    if (countINKWELL>=1){
                        countMANU=stock.getOrDefault(commonObjective.getCardKey()[2], 0);
                        //se c'è un MANUSCRIPT nello stock
                        if (countMANU>=1){
                            //se ci sono piu di uno di tutti a questo punto ritorno il minimo
                            int min=Math.min(count, Math.min(countINKWELL, countMANU));
                            return (min*3);
                        }
                    }
                }
            }
        }
        //altrimenti non ci sono Special
        return 0;
    }
}
