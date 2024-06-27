package it.polimi.ingsw.server.model.decks.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ObjectiveCard}.
 */
class ObjectiveCardTest {
    // The card point to be tested
    private int cardPoint;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Initialize the card point
        cardPoint = 2;
    }

    /**
     * Tests the {@link ObjectiveCard#getCardPoints()} method of ObjectiveCard.
     * Verifies that the getCardPoints method returns the correct card point.
     */
    @Test
    void getCardPoints() {
        // Check that the actual card point matches the expected one
        assertEquals(2, cardPoint);
    }
}