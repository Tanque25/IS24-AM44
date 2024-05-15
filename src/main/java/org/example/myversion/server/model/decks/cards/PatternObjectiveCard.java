package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.Map;
/**
 * Represents a pattern objective card.
 * It specifies the pattern that the player has to recreate in his play area to get the points.
 */
public class PatternObjectiveCard extends ObjectiveCard{
    private final Resource[][] objectives;

    /**
     * Constructs a PatternObjectiveCard with specified points and objective.
     *
     * @param cardPoints specifies the points awarded to the player upon accomplishing its objective.
     * @param objective specifies the pattern that the player has to recreate in his play area.
     */
    public PatternObjectiveCard(int cardPoints, Resource[][] objective) {
        super(cardPoints);
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

    //solo di prova
    @Override
    public CornerContent[] getCardKey(){
        return objectives[0];//devo fare un altro tipo che restituisca
    }

    /**
     * Retrieves the number of point
     *
     * @param stock is the map that count the Resource or SpecialObject in the play area
     * @param playArea is the matrix of card of the player
     * @param objective specifies the pattern that the player has to recreate in his play area.
     */
    @Override
    public int findObjectiveCard(Map<CornerContent, Integer> stock, Card[][] playArea,ObjectiveCard objective) {

        int count=0;//contatore di volte in cui trovi la sotto matrice nella matrice
        int rows = playArea.length;
        int cols = playArea[0].length;
        int subRows = objectives.length;
        int subCols = objectives.length;


        for (int i = 0; i <= rows - subRows; i++) {
            for (int j = 0; j <= cols - subCols; j++) {
                //per ogni elemento della matrice:
                System.out.println("riga: "+i);
                System.out.println("colonna: "+j);
                if (isSubMatrix(playArea, objectives, i, j)) {
                    //contatore aumenta ogni volta che trova la sotto-matrice nella play area
                    count++;
                }
            }
        }

        // nel caso sia una PatternObjectiveCard da 2 punti (quelle in diagonale):
        if(objectives[0][2]!=null && objectives[1][1]!=null && objectives[2][0]!=null){
            return count*2;
        }
        //altrimenti in tutti gli altri casi restituisco count*3
        return count*3;//tanto nel caso in cui sia 0 nessuno trovato e quindi 0*3 =0
    }

    //metodo privato che controlla se è sotto matrice, ritorna un booleano: si o no
    private static boolean isSubMatrix(Card[][] playArea, Resource[][] subMatrix, int row, int col) {
        //scannerizza tutte le posizioni
        for (int i = 0; i < subMatrix.length; i++) {
            System.out.println("è entrata-riga: "+i);
            for (int j = 0; j < subMatrix[0].length; j++) {
                //se la casella della play area è diversa da null allora:
                System.out.println("colonna: "+j);
                if (playArea[row + i][col + j]!=null && subMatrix[i][j]!=null) {
                    System.out.println("colonna non è null: ");
                    //se la submatrice
                    if (playArea[row + i][col + j].getPlayableResource() != subMatrix[i][j]) {
                        System.out.println("è diversa");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
