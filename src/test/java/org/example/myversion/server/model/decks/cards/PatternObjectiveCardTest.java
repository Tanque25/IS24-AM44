package org.example.myversion.server.model.decks.cards;

import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PatternObjectiveCard}.
 */
class PatternObjectiveCardTest {
    // Instance of PatternObjectiveCard (pattern of the objectve card 87) to be tested
    private PatternObjectiveCard OC_087;
    private PatternObjectiveCard OC_088;
    private PatternObjectiveCard OC_091;
    private PatternObjectiveCard OC_092;

    private PatternObjectiveCard OC_093;
    private PatternObjectiveCard OC_094;

    private Card [][] playArea1;

    private Card [][] playArea2;
    private Card [][] playArea3;

    private Card [][] playArea4;
    private PlayableCard RC_001;
    private PlayableCard RC_002;
    private PlayableCard RC_003;
    private PlayableCard GC_051;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        // Create a new PatternObjectiveCard instance with specified parameters
        OC_087 = new PatternObjectiveCard(2, new Resource[][]{
                {null, null, Resource.FUNGI_KINGDOM},
                {null, Resource.FUNGI_KINGDOM, null},
                {Resource.FUNGI_KINGDOM, null, null}
        }, 87);

        OC_088 = new PatternObjectiveCard(2, new Resource[][]{
                {Resource.PLANT_KINGDOM, null, null},
                {null, Resource.PLANT_KINGDOM, null},
                {null, null, Resource.PLANT_KINGDOM}
        }, 88);

        OC_091 = new PatternObjectiveCard(3, new Resource[][]{
                {null, Resource.FUNGI_KINGDOM, null},
                {null,null,null},
                {null, Resource.FUNGI_KINGDOM, null},
                { null, null, Resource.PLANT_KINGDOM}
        }, 91);

        OC_092 = new PatternObjectiveCard(3, new Resource[][]{
                {null, Resource.PLANT_KINGDOM, null},
                {null, Resource.PLANT_KINGDOM, null},
                {Resource.INSECT_KINGDOM, null, null}
        }, 92);

        OC_093 = new PatternObjectiveCard(3, new Resource[][]{
                { null, Resource.FUNGI_KINGDOM, null},
                {Resource.ANIMAL_KINGDOM, null, null},
                {Resource.ANIMAL_KINGDOM, null, null}
        }, 93);

        OC_094 = new PatternObjectiveCard(3, new Resource[][]{
                {Resource.ANIMAL_KINGDOM, null, null},
                {null, Resource.INSECT_KINGDOM, null},
                {null, Resource.ANIMAL_KINGDOM, null}
        }, 94);

        RC_001 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 001
        );

        RC_002 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 002
        );

        RC_003 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 003
        );

        GC_051 = new GoldCard(Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                0,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL, 051
        );

        playArea1 = new Card[][]{
                {null, null, RC_003},
                {null, RC_002, null},
                {RC_001, null, null}
        };

        playArea2 = new Card[][]{
                {null, null, null}
        };

        playArea3 = new Card[][]{
                {null, RC_003, null},
                {null, null, null},
                {null, RC_002, null},
                {RC_001, null, GC_051}
        };

        playArea4 = new Card[][]{
                {null, null, null,null},
                {null, RC_002, null,GC_051},
                {null, null, RC_001,null},
                {null, RC_002, null, GC_051},
                {null, null, GC_051, null},
                {null, null, null,null},
        };


    }

    /**
     * Tests the {@link PatternObjectiveCard#getObjective()} method of PatternObjectiveCard.
     * Verifies that the getObjective method returns the correct objective pattern.
     */
    @Test
    void getObjective() {
        // Define the expected objective pattern
        Resource[][] expected = {
                {null, null, Resource.FUNGI_KINGDOM},
                {null, Resource.FUNGI_KINGDOM, null},
                {Resource.FUNGI_KINGDOM, null, null}
        };
        // Check that the actual objective pattern matches the expected one
        assertArrayEquals(expected, OC_087.getObjective());

    }

    @Test
    void getCardKey() {
    }

    @Test
    void findObjectiveCard1() {
        int point = OC_087.findObjectiveCard(null, playArea1, OC_087);

        assertEquals(2, point);

    }

    @Test
    void findObjectiveCard2() {
        int point = OC_087.findObjectiveCard(null, playArea2, OC_087);

        assertEquals(0, point);

    }

    @Test
    void findObjectiveCard3() {
        int point = OC_091.findObjectiveCard(null, playArea3, OC_091);

        assertEquals(3, point);

    }

    //test in cui non solo quelle
    @Test
    void findObjectiveCard4() {
        int point = OC_091.findObjectiveCard(null, playArea4, OC_091);

        assertEquals(3, point);

    }

}
