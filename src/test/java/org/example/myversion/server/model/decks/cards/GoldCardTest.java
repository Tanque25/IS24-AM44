package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Test case for the {@link GoldCard} class.
 */
public class GoldCardTest {
    private GoldCard GC_041;

    /**
     * Sets up a test instance with GoldCard 41 initialized with specific attributes.
     */
    @BeforeEach
    void setUp() {
        /*GC_041 = new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.QUILL)
                ),
                0,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.ANIMAL_KINGDOM},
                SpecialObject.QUILL);*/
    }

    /**
     * Tests the {@link GoldCard#getCost()} method.
     * Checks if the cost array is not null, its length, and each element.
     */
    @Test
    void getCost() {
        // Check if the cost array is not null
        assertNotNull(GC_041.getCost());
        // Check the length of the cost array
        assertEquals(3, GC_041.getCost().length);
        // Test each element of the cost array
        assertEquals(Resource.FUNGI_KINGDOM, GC_041.getCost()[0]);
        assertEquals(Resource.FUNGI_KINGDOM, GC_041.getCost()[1]);
        assertEquals(Resource.ANIMAL_KINGDOM, GC_041.getCost()[2]);
    }

    /**
     * Tests the {@link GoldCard#getPointsParameter()} method.
     * Checks if the points parameter is correct.
     */
    @Test
    void getPointsParameter() {
        assertEquals(SpecialObject.QUILL, GC_041.getPointsParameter());
    }
}
