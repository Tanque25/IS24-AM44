package org.example.myversion.server.model.decks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link GoldDeck}.
 */
class GoldDeckTest {

    // Instance of the GoldDeck to be tested
    private GoldDeck goldDeck;

    // Initial size of the gold deck
    private int size;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Creates a new GoldDeck instance
        goldDeck = new GoldDeck();

        // Retrieves the initial size of the gold deck
        size = goldDeck.getGoldDeck().size();
    }

    /**
     * Tests the drawCard method of {@link GoldDeck}.
     */
    @Test
    void drawCard() {
        // Verifies that the initial deck is not empty
        assertFalse(goldDeck.getGoldDeck().isEmpty());

        // Draws a card from the deck
        goldDeck.drawCard();

        // Verifies that the deck has one card less after drawing
        assertEquals((size - 1), goldDeck.getGoldDeck().size());

        // Draws all remaining cards from the deck
        for (int i = (size - 1); i > 0; i--) {
            goldDeck.drawCard();
        }

        // Verifies that the deck is empty after drawing all cards
        assertTrue(goldDeck.getGoldDeck().isEmpty());
    }

    /**
     * Tests the {@link GoldDeck#getGoldDeck()} method of GoldDeck.
     */
    @Test
    void getGoldDeck() {
        // Verifies that the returned deck is not null
        assertNotNull(goldDeck.getGoldDeck());

        // Verifies that the returned deck is the same object as the internal one
        assertSame(goldDeck.getGoldDeck(), goldDeck.getGoldDeck());
    }
}
