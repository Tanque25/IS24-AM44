package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;
import it.polimi.ingsw.server.model.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for the {@link Corner} class.
 */
public class CornerTest {

    private Corner corner;

    /**
     * Sets up a test instance with a Corner object containing Fungi Kingdom Resource as its content.
     */
    @BeforeEach
    void setUp() {
        CornerContent cornerContent = Resource.FUNGI_KINGDOM;
        corner = new Corner(cornerContent);
    }

    /**
     * Tests the {@link Corner#getCornerContent()} method.
     * Checks if the Corner object returns the correct corner content.
     */
    @Test
    void getCornerContent() {
        assertEquals(Resource.FUNGI_KINGDOM, corner.getCornerContent());
    }

    /**
     * Tests the {@link Corner#isCovered()} method.
     * Checks if the Corner object is initially uncovered.
     */
    @Test
    void isCovered() {
        assertFalse(corner.isCovered());
    }
}