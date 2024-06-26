package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PlayableCard}.
 */
class PlayableCardTest {
    // Instance of PlayableCard (resource card 3) to be tested
    private PlayableCard RC_003;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Create a new playable card instance with specified parameters
        RC_003 = new PlayableCard(
                // Resource of the card
                Resource.FUNGI_KINGDOM,
                // Corners of the card
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                ),
                // Points of the card
                0,3);
    }

    /**
     * Tests the {@link PlayableCard#getCorners()} method of PlayableCard.
     * Verifies that the getCorners method returns the correct map of corners.
     */
    @Test
    void getCorners() {
        // Check that the corners are not null
        assertNotNull(RC_003.getCorners());
        // Check that there are 4 corners on the card
        assertEquals(4, RC_003.getCorners().size());
        // Check that the card contains corners in the specified positions
        assertTrue(RC_003.getCorners().containsKey(CornerPosition.UP_LEFT));
        assertTrue(RC_003.getCorners().containsKey(CornerPosition.UP_RIGHT));
        assertTrue(RC_003.getCorners().containsKey(CornerPosition.BOTTOM_LEFT));
        assertTrue(RC_003.getCorners().containsKey(CornerPosition.BOTTOM_RIGHT));
    }

    /**
     * Tests the {@link PlayableCard#isPlayedBack()} method of PlayableCard.
     * Verifies that the isPlayedBack method correctly returns false.
     */
    @Test
    void isPlayedBack() {
        assertFalse(RC_003.isPlayedBack());
    }

    /**
     * Tests the {@link PlayableCard#getResource()} method of PlayableCard.
     * Verifies that the getResource method returns the correct resource type.
     */
    @Test
    void getResource() {
        assertEquals(Resource.FUNGI_KINGDOM, RC_003.getResource());
    }

    /**
     * Tests the {@link PlayableCard#getCardPoints()} method of PlayableCard.
     * Verifies that the getCardPoints method returns the correct number of points.
     */
    @Test
    void getCardPoints() {
        assertEquals(0, RC_003.getCardPoints());
    }
}
