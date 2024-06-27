package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;
import it.polimi.ingsw.server.model.enumerations.SpecialObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SpecialObjectiveCardTest {

    private SpecialObjectiveCard OC_099; // ha 3 special object divesri
    private ObjectiveCard OC_101;

    private Map<CornerContent, Integer> stockCase1;
    private Map<CornerContent, Integer> stockCase2;
    private Map<CornerContent, Integer> stockCase3;
    private Map<CornerContent, Integer> stockCase4;
    private Map<CornerContent, Integer> stockCase5;

    @BeforeEach
    void setUp() {
        OC_099 = new SpecialObjectiveCard(3, new SpecialObject[]{
                SpecialObject.QUILL, SpecialObject.INKWELL, SpecialObject.MANUSCRIPT}, 99
        ); // 99

        OC_101 = new SpecialObjectiveCard(2, new SpecialObject[]{
                SpecialObject.INKWELL, SpecialObject.INKWELL}, 101
        ); // 101

        stockCase1 = new HashMap<>();
        stockCase1.put(SpecialObject.QUILL, 1);
        stockCase1.put(SpecialObject.INKWELL, 1);
        stockCase1.put(SpecialObject.MANUSCRIPT, 1);

        stockCase2 = new HashMap<>();
        stockCase2.put(SpecialObject.QUILL, 4);
        stockCase2.put(SpecialObject.INKWELL, 5);
        stockCase2.put(SpecialObject.MANUSCRIPT, 7);

        stockCase3 = new HashMap<>();
        stockCase3.put(SpecialObject.QUILL, 0);
        stockCase3.put(SpecialObject.INKWELL, 0);
        stockCase3.put(SpecialObject.MANUSCRIPT, 0);

        stockCase4 = new HashMap<>();
        stockCase4.put(SpecialObject.QUILL, 0);
        stockCase4.put(SpecialObject.INKWELL, 1);
        stockCase4.put(SpecialObject.MANUSCRIPT, 0);

        stockCase5 = new HashMap<>();
        stockCase5.put(SpecialObject.QUILL, 0);
        stockCase5.put(SpecialObject.INKWELL, 4);
        stockCase5.put(SpecialObject.MANUSCRIPT, 8);
    }

    @Test
    void getCardPoints() {

    }

    @Test
    void getCardKey() {
        // Test per OC_099
        SpecialObject[] expectedKey1 = {SpecialObject.QUILL, SpecialObject.INKWELL, SpecialObject.MANUSCRIPT};
        assertArrayEquals(expectedKey1, OC_099.getCardKey());

        // Test per OC_101
        SpecialObject[] expectedKey2 = {SpecialObject.INKWELL, SpecialObject.INKWELL};
        assertArrayEquals(expectedKey2, OC_101.getCardKey());

        // Test per OC_099
        CornerContent[] actualKey1 = OC_099.getCardKey();
        assertEquals(3, actualKey1.length); // Verifica la lunghezza dell'array
        assertEquals(SpecialObject.QUILL, actualKey1[0]); // Verifica il primo oggetto
        assertEquals(SpecialObject.INKWELL, actualKey1[1]); // Verifica il secondo oggetto
        assertEquals(SpecialObject.MANUSCRIPT, actualKey1[2]); // Verifica il terzo oggetto

        // Test per OC_101
        CornerContent[] actualKey2 = OC_101.getCardKey();
        assertEquals(2, actualKey2.length); // Verifica la lunghezza dell'array
        assertEquals(SpecialObject.INKWELL, actualKey2[0]); // Verifica il primo oggetto
        assertEquals(SpecialObject.INKWELL, actualKey2[1]); // Verifica il secondo oggetto

    }

    @Test
    void calculateObjectiveCardPoints1() {
        int points1 = OC_099.calculateObjectiveCardPoints(stockCase1, null, OC_099 );
        int points2 = OC_101.calculateObjectiveCardPoints(stockCase1, null, OC_101);

        assertEquals(3,points1);
        assertEquals(0,points2);
    }

    @Test
    void findOjectiveCard2(){
        int points1 = OC_099.calculateObjectiveCardPoints(stockCase2, null,OC_099 );
        int points2 = OC_101.calculateObjectiveCardPoints(stockCase2, null, OC_101);

        assertEquals(12,points1);
        assertEquals(4,points2);

    }

    @Test
    void findOjectiveCard3(){
        int points1 = OC_099.calculateObjectiveCardPoints(stockCase3, null,OC_099 );
        int points2 = OC_101.calculateObjectiveCardPoints(stockCase3, null, OC_101);

        assertEquals(0,points1);
        assertEquals(0,points2);

    }

    @Test
    void findOjectiveCard4(){
        int points1 = OC_099.calculateObjectiveCardPoints(stockCase4, null,OC_099 );
        int points2 = OC_101.calculateObjectiveCardPoints(stockCase4, null, OC_101);

        assertEquals(0,points1);
        assertEquals(0,points2);

    }

    @Test
    void findOjectiveCard5(){
        int points1 = OC_099.calculateObjectiveCardPoints(stockCase5, null,OC_099 );
        int points2 = OC_101.calculateObjectiveCardPoints(stockCase5, null, OC_101);

        assertEquals(0,points1);
        assertEquals(4,points2);

    }



}