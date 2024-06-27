package it.polimi.ingsw.server.model.decks;

import it.polimi.ingsw.server.model.decks.cards.Corner;
import it.polimi.ingsw.server.model.decks.cards.StarterCard;
import it.polimi.ingsw.server.model.enumerations.CornerPosition;
import it.polimi.ingsw.server.model.enumerations.CornerVisibility;
import it.polimi.ingsw.server.model.enumerations.Resource;

import java.util.Collections;
import java.util.Stack;
import java.util.Map;

/**
 * Represents a deck of starter cards used in the game.
 * Each player gets a starter card in the beginning of the game.
 */
public class StarterDeck {
    private Stack<StarterCard> starterDeck;

    /**
     * Constructs a StarterDeck and initializes it with starter cards.
     */
    public StarterDeck() {
        this.starterDeck = new Stack<StarterCard>();
        initializeDeck();
    }

    /**
     * Initializes the starter deck with a set of predefined starter cards.
     * Each starter card is created with specific resources and corners.
     * After initialization, the deck is shuffled to randomize the order of cards.
     */
    private void initializeDeck(){
        starterDeck.push(new StarterCard(
                new Resource[]{Resource.INSECT_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.ANIMAL_KINGDOM)
                ),
                81
        ));

        starterDeck.push(new StarterCard(
                new Resource[]{Resource.FUNGI_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.INSECT_KINGDOM)
                ),
                82
        ));

        starterDeck.push(new StarterCard(
                new Resource[]{Resource.PLANT_KINGDOM, Resource.FUNGI_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.PLANT_KINGDOM)
                ),
                83
        ));

        starterDeck.push(new StarterCard(
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.INSECT_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                ),
                84
        ));

        starterDeck.push(new StarterCard(
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.INSECT_KINGDOM, Resource.PLANT_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.FULL)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.ANIMAL_KINGDOM)
                ),
                85
        ));

        starterDeck.push(new StarterCard(
                new Resource[]{Resource.PLANT_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.FULL)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.INSECT_KINGDOM)
                ),
                86
        ));

        Collections.shuffle(starterDeck);
    }

    /**
     * Removes the top card from the starter deck and returns it.
     *
     * @return the starter card on the top of the starter deck.
     */
    public StarterCard drawCard() {
        return starterDeck.pop();
    }

    /**
     * Retrieves the starter deck.
     *
     * @return the starter deck.
     */
    public Stack<StarterCard> getStarterDeck() {
        return starterDeck;
    }
}
