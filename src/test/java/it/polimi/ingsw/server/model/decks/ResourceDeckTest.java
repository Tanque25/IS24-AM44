package it.polimi.ingsw.server.model.decks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ResourceDeck}.
 */
class ResourceDeckTest {

    // Instance of the ResourceDeck to be tested
    private ResourceDeck resourceDeck;

    // Initial size of the resource deck
    private int size;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {

        // Creates a new ResourceDeck instance
        resourceDeck = new ResourceDeck();

        // Retrieves the initial size of the resource deck
        size = resourceDeck.getResourceDeck().size();
    }

    /**
     * Tests the drawCard method of {@link ResourceDeck}.
     */
    @Test
    void drawCard() {
        // Verifies that the initial deck is not empty

        assertFalse(resourceDeck.getResourceDeck().isEmpty());

        // Draws a card from the deck
        resourceDeck.drawCard();

        // Verifies that the deck has one card less after drawing
        assertEquals((size - 1), resourceDeck.getResourceDeck().size());

        // Draws all remaining cards from the deck
        for (int i = (size - 1); i > 0; i--) {
            resourceDeck.drawCard();
        }

        // Verifies that the deck is empty after drawing all cards
        assertTrue(resourceDeck.getResourceDeck().isEmpty());
    }

    /**
     * Tests the {@link ResourceDeck#getResourceDeck()} method of ResourceDeck.
     */
    @Test
    void getResourceDeck() {
        // Verifies that the returned deck is not null
        assertNotNull(resourceDeck.getResourceDeck());


        // Verifies that the returned deck is the same object as the internal one
        assertSame(resourceDeck.getResourceDeck(), resourceDeck.getResourceDeck());
    }
}