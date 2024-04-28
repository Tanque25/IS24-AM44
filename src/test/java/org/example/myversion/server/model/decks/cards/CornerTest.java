package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
