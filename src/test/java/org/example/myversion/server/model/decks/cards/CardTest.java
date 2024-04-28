package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
/**
 * Test class for the {@link Card} class.
 */
public class CardTest {
    // Create a new PlayableCard, representing Resource Card 1
    private PlayableCard RC_001;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Resource Card 1 (RC_001)

        // This is the map of the Resource Card
        RC_001 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                0
        );
    }

    /**
     * Tests the {@link Card#getCorners()} method.
     */
    @Test
    void getCorners() {
        // Check that the corners are not null
        assertNotNull(RC_001.getCorners());
        // Check that there are 4 corners on the card
        assertEquals(4, RC_001.getCorners().size());
        // Check that the card contains corners in the specified positions
        assertTrue(RC_001.getCorners().containsKey(CornerPosition.UP_LEFT));
        assertTrue(RC_001.getCorners().containsKey(CornerPosition.UP_RIGHT));
        assertTrue(RC_001.getCorners().containsKey(CornerPosition.BOTTOM_LEFT));
        assertTrue(RC_001.getCorners().containsKey(CornerPosition.BOTTOM_RIGHT));
    }

    /**
     * Tests the {@link Card#isPlayedBack()} method.
     */
    @Test
    void isPlayedBack() {
        // Check that at the beginning of the game the variable isPlayedBack is false
        assertFalse(RC_001.isPlayedBack());
    }
}
