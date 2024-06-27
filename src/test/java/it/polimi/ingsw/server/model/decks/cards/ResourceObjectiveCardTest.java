package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;
import it.polimi.ingsw.server.model.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResourceObjectiveCardTest {
    private ResourceObjectiveCard OC_095;
    private Map<CornerContent, Integer> stockCase1;
    private Map<CornerContent, Integer> stockCase2;
    private Map<CornerContent, Integer> stockCase3;
    private Map<CornerContent, Integer> stockCase4;
    private Map<CornerContent, Integer> stockCase5;

    @BeforeEach
    void setUp() {
        OC_095 = new ResourceObjectiveCard(2, new Resource[]{
                Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM}, 95
        ); // 95

        stockCase1  = new HashMap<>();
        stockCase1.put(Resource.FUNGI_KINGDOM, 3);
        stockCase1.put(Resource.ANIMAL_KINGDOM, 1);

        stockCase2  = new HashMap<>();
        stockCase2.put(Resource.FUNGI_KINGDOM, 5);
        stockCase2.put(Resource.ANIMAL_KINGDOM, 1);

        stockCase3  = new HashMap<>();
        stockCase3.put(Resource.FUNGI_KINGDOM, 9);
        stockCase3.put(Resource.ANIMAL_KINGDOM, 1);

        stockCase4  = new HashMap<>();
        stockCase4.put(Resource.FUNGI_KINGDOM, 1);
        stockCase4.put(Resource.ANIMAL_KINGDOM, 1);

        stockCase5  = new HashMap<>();
        stockCase5.put(Resource.FUNGI_KINGDOM, 0);
        stockCase5.put(Resource.ANIMAL_KINGDOM, 1);
    }

    @Test
    void getCardPoints() {
    }

    @Test
    void getCardKey() {
    }

    @Test
    void calculateObjectiveCardPoints1() {
        int points = OC_095.calculateObjectiveCardPoints(stockCase1, null, OC_095);

        assertEquals(2, points);

    }

    @Test
    void calculateObjectiveCardPoints2() {
        int points = OC_095.calculateObjectiveCardPoints(stockCase2, null, OC_095);

        assertEquals(2, points);

    }

    @Test
    void calculateObjectiveCardPoints3() {
        int points = OC_095.calculateObjectiveCardPoints(stockCase3, null, OC_095);

        assertEquals(6, points);

    }

    @Test
    void calculateObjectiveCardPoints4() {
        int points = OC_095.calculateObjectiveCardPoints(stockCase4, null, OC_095);

        assertEquals(0, points);

    }

    @Test
    void calculateObjectiveCardPoints5() {
        int points = OC_095.calculateObjectiveCardPoints(stockCase5, null, OC_095);

        assertEquals(0, points);

    }
}