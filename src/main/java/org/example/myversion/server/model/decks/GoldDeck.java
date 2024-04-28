package org.example.myversion.server.model.decks;

import org.example.myversion.server.model.decks.cards.Corner;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.StarterCard;
import org.example.myversion.server.model.enumerations.*;

import java.util.Collections;
import java.util.Map;
import java.util.Stack;

/**
 * Represents a deck of gold cards used in the game.
 * Gold cards are special cards that players can play depending on the number of resources in their play area and provide additional points.
 */
public class GoldDeck{
    private Stack<GoldCard> goldDeck;

    /**
     * Constructs a GoldDeck and initializes it with gold cards.
     */
    public GoldDeck() {
        this.goldDeck = new Stack<GoldCard>();
        initializeDeck();
    }

    /**
     * Initializes the gold deck with a set of predefined gold cards.
     * Each gold card is created with a specific resource, corners, cost, and points parameter.
     * After initialization, the deck is shuffled to randomize the order of cards.
     */
    private void initializeDeck(){
        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.QUILL)
                ),
                1,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.ANIMAL_KINGDOM},
                SpecialObject.QUILL
        )); // 41

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.INKWELL
        )); // 42

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                1,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.MANUSCRIPT
        )); // 43

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM,Resource.FUNGI_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.CORNER
        )); // 44

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                2,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.CORNER
        )); // 45

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.CORNER
        )); // 46

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.EMPTY
        )); // 47

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.EMPTY
        )); // 48

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                3,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.EMPTY
        )); // 49

        goldDeck.push(new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                5,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.EMPTY
        )); // 50

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                       CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                       CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                       CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                       CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.FULL)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        )); //51

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.FUNGI_KINGDOM},
                SpecialObject.MANUSCRIPT
        )); // 52

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.ANIMAL_KINGDOM},
                SpecialObject.INKWELL
        )); // 53

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.CORNER
        )); // 54

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                2,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.CORNER
        )); // 55

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.CORNER
        )); // 56

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM},
                SpecialObject.INKWELL
        )); // 57

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.EMPTY
        )); // 58

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                3,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.EMPTY
        )); // 59

        goldDeck.push(new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                5,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.EMPTY
        )); // 60

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                1,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.INKWELL
        )); // 61

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.MANUSCRIPT)
                ),
                1,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.PLANT_KINGDOM},
                SpecialObject.MANUSCRIPT
        )); // 62

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM},
                SpecialObject.QUILL
        )); // 63

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.CORNER
        )); // 64

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.CORNER
        )); // 65

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.CORNER
        )); // 66

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.EMPTY
        )); // 67

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.EMPTY
        )); // 68

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.QUILL)
                ),
                3,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.EMPTY
        )); // 69

        goldDeck.push(new GoldCard(
                Resource.ANIMAL_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                5,
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.EMPTY
        )); // 70

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.PLANT_KINGDOM},
                SpecialObject.QUILL
        )); // 71

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.ANIMAL_KINGDOM},
                SpecialObject.MANUSCRIPT
        )); // 72

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.INKWELL)
                ),
                1,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.FUNGI_KINGDOM},
                SpecialObject.INKWELL
        )); // 73

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.ANIMAL_KINGDOM},
                ParameterType.CORNER
        )); // 74

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                2,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.PLANT_KINGDOM},
                ParameterType.CORNER
        )); // 75

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                2,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.FUNGI_KINGDOM},
                ParameterType.CORNER
        )); // 76

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(SpecialObject.INKWELL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.EMPTY
        )); // 77

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                3,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.EMPTY
        )); // 78

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.QUILL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                3,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.EMPTY
        )); // 79

        goldDeck.push(new GoldCard(
                Resource.INSECT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                5,
                new Resource[]{Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM},
                ParameterType.EMPTY
        )); // 80

        Collections.shuffle(goldDeck);

    }

    /**
     * Removes the top card from the gold deck and returns it.
     *
     * @return the gold card on the top of the gold deck.
     */
    public GoldCard drawCard(){
        return goldDeck.pop();
    }


    /**
     * Retrieves the gold deck.
     *
     * @return the gold deck.
     */
    public Stack<GoldCard> getGoldDeck() {
        return goldDeck;
    }
}
