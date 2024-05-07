package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.GoldDeck;
import org.example.myversion.server.model.decks.ResourceDeck;
import org.example.myversion.server.model.decks.StarterDeck;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;

public class CardView {

    public static void main(String[] args) {
        StarterDeck starterDeck = new StarterDeck();
        ResourceDeck resourceDeck = new ResourceDeck();
        GoldDeck goldDeck = new GoldDeck();

        for (StarterCard starterCard : starterDeck.getStarterDeck()) {
            displayCardFront(starterCard);
        }

        for(PlayableCard playableCard : resourceDeck.getResourceDeck()) {
            displayCardFront(playableCard);
        }

        for(GoldCard goldCard : goldDeck.getGoldDeck()) {
            displayCardFront(goldCard);
        }

    }

    public static void displayCardFront(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int resourceSize = starterCard.getResource().length;

        // Top line of the card
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        // Middle line of the card
        switch(resourceSize) {
            case 1: {
                stringBuilder.append("|   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("   |\n");
                break;
            }
            case 2: {
                stringBuilder.append("|   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  |\n");
                break;
            }
            case 3: {
                stringBuilder.append("|  ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  |\n");
                break;
            }
        }

        // Bottom line of the card
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }

    public static void displayCardFront(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("---");
        stringBuilder.append(playableCard.getCardPoints());
        stringBuilder.append("---");
        stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(playableCard.getResource().getShortName());
        stringBuilder.append("   |\n");

        // Bottom line of the card
        stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }

    public static void displayCardFront(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int costSize = goldCard.getCost().length;

        // Top line of the card
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("--");
        stringBuilder.append(goldCard.getCardPoints()).append("-").append(goldCard.getPointsParameter().getShortName());
        stringBuilder.append("--");
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(goldCard.getResource().getShortName());
        stringBuilder.append("   |\n");

        // Bottom line of the card
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        switch (costSize) {
            case 0: {
                stringBuilder.append("----");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("---");
                break;
            }
            case 1: {
                stringBuilder.append("---");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("---");
                break;
            }
            case 2: {
                stringBuilder.append("---");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("--");
                break;
            }
            case 3: {
                stringBuilder.append("--");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("--");
                break;
            }
            case 4: {
                stringBuilder.append("--");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("-");
                break;
            }
            case 5: {
                stringBuilder.append("-");
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("-");
                break;
            }
        }
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }
}
