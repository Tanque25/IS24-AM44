package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link StarterCard}.
 */
class StarterCardTest {

    // Instance of StarterCard (started card 2) to be tested
    StarterCard SC_002;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Create a new StarterCard instance with specified parameters
       /* SC_002 = new StarterCard(
                // Resources of the card
                new Resource[]{Resource.FUNGI_KINGDOM},
                // Corners of the card
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(null),
                        CornerPosition.BOTTOM_LEFT, new Corner(null),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                ),
                // Back corners of the card
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.INSECT_KINGDOM)
                )
        );*/
    }

    /**
     * Tests the {@link StarterCard#getCorners()} method of StarterCard.
     * Verifies that the getCorners method returns the correct map of corners.
     */
    @Test
    void getCorners() {
        // Verify that the getCorners method does not return null
        assertNotNull(SC_002.getCorners());
        // Verify that the number of corners is correct
        assertEquals(4, SC_002.getCorners().size());
        // Verify that all required corner positions are present
        assertTrue(SC_002.getCorners().containsKey(CornerPosition.UP_LEFT));
        assertTrue(SC_002.getCorners().containsKey(CornerPosition.UP_RIGHT));
        assertTrue(SC_002.getCorners().containsKey(CornerPosition.BOTTOM_LEFT));
        assertTrue(SC_002.getCorners().containsKey(CornerPosition.BOTTOM_RIGHT));
    }

    /**
     * Tests the {@link StarterCard#isPlayedBack()} method of StarterCard.
     * Verifies that the isPlayedBack method correctly returns false.
     */
    @Test
    void isPlayedBack() {
        // Verify that the isPlayedBack method returns false
        assertFalse(SC_002.isPlayedBack());
    }

    /**
     * Tests the {@link StarterCard#getResource()} method of StarterCard.
     * Verifies that the getResource method returns the correct array of resources.
     */
    @Test
    void getResource() {
        // Verify that the getResource method does not return null
        assertNotNull(SC_002.getResource());
        // Verify that the length of the resource array is correct
        assertEquals(1, SC_002.getResource().length);
        // Verify that the resource type is correct
        assertEquals(Resource.FUNGI_KINGDOM, SC_002.getResource()[0]);
    }

    /**
     * Tests the {@link StarterCard#getBackCorner()} method of StarterCard.
     * Verifies that the getBackCorner method returns the correct map of back corners.
     */
    @Test
    void getBackCorner() {
        // Verify that the getBackCorner method does not return null
        assertNotNull(SC_002.getBackCorner());
        // Verify that the number of back corners is correct
        assertEquals(4, SC_002.getBackCorner().size());
        // Verify that all required corner positions are present
        assertTrue(SC_002.getBackCorner().containsKey(CornerPosition.UP_LEFT));
        assertTrue(SC_002.getBackCorner().containsKey(CornerPosition.UP_RIGHT));
        assertTrue(SC_002.getBackCorner().containsKey(CornerPosition.BOTTOM_LEFT));
        assertTrue(SC_002.getBackCorner().containsKey(CornerPosition.BOTTOM_RIGHT));
    }
}
