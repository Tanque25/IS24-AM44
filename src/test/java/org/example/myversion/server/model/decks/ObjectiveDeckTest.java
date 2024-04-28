package org.example.myversion.server.model.decks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ObjectiveDeck}.
 */
class ObjectiveDeckTest {

    // Instance of the ObjectiveDeck to be tested
    private ObjectiveDeck objectiveDeck;

    // Initial size of the objective deck
    private int size;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Creates a new ObjectiveDeck instance
        objectiveDeck = new ObjectiveDeck();

        // Retrieves the initial size of the objective deck
        size = objectiveDeck.getObjectiveDeck().size();
    }

    /**
     * Tests the drawCard method of {@link ObjectiveDeck}.
     */
    @Test
    void drawCard() {
        // Verifies that the initial deck is not empty
        assertFalse(objectiveDeck.getObjectiveDeck().isEmpty());

        // Draws a card from the deck
        objectiveDeck.drawCard();

        // Verifies that the deck has one card less after drawing
        assertEquals((size - 1), objectiveDeck.getObjectiveDeck().size());

        // Draws all remaining cards from the deck
        for (int i = (size - 1); i > 0; i--) {
            objectiveDeck.drawCard();
        }

        // Verifies that the deck is empty after drawing all cards
        assertTrue(objectiveDeck.getObjectiveDeck().isEmpty());
    }

    /**
     * Tests the {@link ObjectiveDeck#getObjectiveDeck()} method of ObjectiveDeck.
     */
    @Test
    void getObjectiveDeck() {
        // Verifies that the returned deck is not null
        assertNotNull(objectiveDeck.getObjectiveDeck());

        // Verifies that the returned deck is the same object as the internal one
        assertSame(objectiveDeck.getObjectiveDeck(), objectiveDeck.getObjectiveDeck());
    }
}
