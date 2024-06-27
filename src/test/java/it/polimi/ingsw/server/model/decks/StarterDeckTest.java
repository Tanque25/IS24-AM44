package it.polimi.ingsw.server.model.decks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link StarterDeck}.
 */
class StarterDeckTest {

    // Instance of the StarterDeck to be tested
    public StarterDeck starterDeck;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        starterDeck = new StarterDeck();
    }

    /**
     * Tests the drawCard method of {@link StarterDeck}.
     */
    @Test
    void drawCard() {
        // Verifies that the initial deck is not empty
        assertFalse(starterDeck.getStarterDeck().isEmpty());

        // Draws a card from the deck
        starterDeck.drawCard();

        // Verifies that the deck has one card less after drawing
        assertEquals(5, starterDeck.getStarterDeck().size());

        // Draws all remaining cards from the deck
        for (int i = 5; i > 0; i--) {
            starterDeck.drawCard();
        }

        // Verifies that the deck is empty after drawing all cards
        assertTrue(starterDeck.getStarterDeck().isEmpty());
    }

    /**
     * Tests the {@link StarterDeck#getStarterDeck()} method of StarterDeck.
     */
    @Test
    void getStarterDeck() {
        // Verifies that the returned deck is not null
        assertNotNull(starterDeck.getStarterDeck());

        // Verifies that the returned deck is the same object as the internal one
        assertSame(starterDeck.getStarterDeck(), starterDeck.getStarterDeck());

        // Verifies that the initial deck contains 6 cards
        assertEquals(6, starterDeck.getStarterDeck().size());
    }
}
