package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;

import java.util.List;

public class HandView {

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
