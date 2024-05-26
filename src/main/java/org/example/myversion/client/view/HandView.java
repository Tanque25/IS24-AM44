package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.cards.Corner;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.CornerVisibility;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandView {

//    public static void main(String[] args) {
//        List<PlayableCard> hand = new ArrayList<>();
//
//        hand.add(new PlayableCard(
//                Resource.FUNGI_KINGDOM,
//                Map.of(
//                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
//                ),
//                0
//        ));
//
//        hand.add(new PlayableCard(
//                Resource.FUNGI_KINGDOM,
//                Map.of(
//                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
//                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
//                ),
//                0
//        ));
//
//        hand.add(new GoldCard(
//                Resource.FUNGI_KINGDOM,
//                Map.of(
//                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
//                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.QUILL)
//                ),
//                1,
//                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.ANIMAL_KINGDOM},
//                SpecialObject.QUILL
//        ));
//
//        hand.add(new GoldCard(
//                Resource.FUNGI_KINGDOM,
//                Map.of(
//                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.UP_RIGHT,new Corner(SpecialObject.INKWELL),
//                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
//                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
//                ),
//                1,
//                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.INSECT_KINGDOM},
//                SpecialObject.INKWELL
//        ));
//
//        displayHand(hand);
//    }

    public void displayHand(List<PlayableCard> hand) {
        // Print top line of the cards in the player's hand
        for(PlayableCard card : hand) {
            if (card instanceof GoldCard)
                CardView.displayCardFrontTopLine((GoldCard) card);
            else
                CardView.displayCardFrontTopLine(card);
            System.out.print("  ");
        }

        System.out.println(); // Move to the next line

        // Print middle line of the cards in the player's hand
        for(PlayableCard card : hand) {
            if (card instanceof GoldCard)
                CardView.displayCardFrontMiddleLine((GoldCard) card);
            else
                CardView.displayCardFrontMiddleLine(card);
            System.out.print("  ");
        }

        System.out.println(); // Move to the next line

        // Print bottom line of the cards in the player's hand
        for(PlayableCard card : hand) {
            if (card instanceof GoldCard)
                CardView.displayCardFrontBottomLine((GoldCard) card);
            else
                CardView.displayCardFrontBottomLine(card);
            System.out.print("  ");
        }

        System.out.println(); // Move to the next line
    }
}
