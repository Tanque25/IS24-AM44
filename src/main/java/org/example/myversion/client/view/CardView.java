package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.GoldDeck;
import org.example.myversion.server.model.decks.ResourceDeck;
import org.example.myversion.server.model.decks.StarterDeck;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;

public class CardView {

//    public static void main(String[] args) {
//        StarterDeck starterDeck = new StarterDeck();
//        ResourceDeck resourceDeck = new ResourceDeck();
//        GoldDeck goldDeck = new GoldDeck();
//
//        for (StarterCard starterCard : starterDeck.getStarterDeck()) {
//            displayCardFrontTopLine(starterCard);
//            displayCardFrontMiddleLine(starterCard);
//            displayCardFrontBottomLine(starterCard);
//            displayCardBackTopLine(starterCard);
//            displayCardBackMiddleLine(starterCard);
//            displayCardBackBottomLine(starterCard);
//        }
//
//        for(PlayableCard playableCard : resourceDeck.getResourceDeck()) {
//            displayCardFrontTopLine(playableCard);
//            displayCardFrontMiddleLine(playableCard);
//            displayCardFrontBottomLine(playableCard);
//            displayCardBackTopLine(playableCard);
//            displayCardBackMiddleLine(playableCard);
//            displayCardBackBottomLine(playableCard);
//        }
//
//        for(GoldCard goldCard : goldDeck.getGoldDeck()) {
//            displayCardFrontTopLine(goldCard);
//            displayCardFrontMiddleLine(goldCard);
//            displayCardFrontBottomLine(goldCard);
//            displayCardBackTopLine(goldCard);
//            displayCardBackMiddleLine(goldCard);
//            displayCardBackBottomLine(goldCard);
//        }
//
//    }

    ///////////////////////////////////////////////////////STARTER CARD FRONT////////////////////////////////////////////////////////

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

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontTopLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int resourceSize = starterCard.getResource().length;

        // Middle line of the card
        switch(resourceSize) {
            case 1: {
                stringBuilder.append("|   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("   |");
                break;
            }
            case 2: {
                stringBuilder.append("|   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  |");
                break;
            }
            case 3: {
                stringBuilder.append("|  ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  |");
                break;
            }
        }

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////STARTER CARD BACK////////////////////////////////////////////////////////

    public static void displayCardBack(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        // Middle line of the card
        stringBuilder.append("|       |\n");

        // Bottom line of the card
        stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        stringBuilder.append("-------");
        stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());
        stringBuilder.append("\n");

        System.out.print(stringBuilder);
    }

    public static void displayCardBackTopLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(starterCard.getBackCorner().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());

        stringBuilder.append("-------");

        if(starterCard.getBackCorner().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardBackMiddleLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Middle line of the card
        stringBuilder.append("|       |");

        System.out.print(stringBuilder);
    }

    public static void displayCardBackBottomLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(starterCard.getBackCorner().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());

        stringBuilder.append("-------");

        if(starterCard.getBackCorner().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////PLAYABLE CARD////////////////////////////////////////////////////////

    public static void displayCardFront(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());

        stringBuilder.append("---");
        stringBuilder.append(playableCard.getCardPoints());
        stringBuilder.append("---");

        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        stringBuilder.append("\n");

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(playableCard.getResource().getShortName());
        stringBuilder.append("   |\n");

        // Bottom line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());

        stringBuilder.append("-------");
        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }

    public static void displayCardFrontTopLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());

        stringBuilder.append("---");
        stringBuilder.append(playableCard.getCardPoints());
        stringBuilder.append("---");

        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(playableCard.getResource().getShortName());
        stringBuilder.append("   |");

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());

        stringBuilder.append("-------");
        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////PLAYABLE CARD BACK////////////////////////////////////////////////////////

    public static void displayCardBack(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append("E");
        stringBuilder.append("-------");
        stringBuilder.append("E");
        stringBuilder.append("\n");

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(playableCard.getResource().getShortName());
        stringBuilder.append("   |\n");

        // Bottom line of the card
        stringBuilder.append("E");
        stringBuilder.append("-------");
        stringBuilder.append("E");
        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }

    public static void displayCardBackTopLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append("E");
        stringBuilder.append("-------");
        stringBuilder.append("E");

        System.out.print(stringBuilder);
    }

    public static void displayCardBackMiddleLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(playableCard.getResource().getShortName());
        stringBuilder.append("   |");

        System.out.print(stringBuilder);
    }

    public static void displayCardBackBottomLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        stringBuilder.append("E");
        stringBuilder.append("-------");
        stringBuilder.append("E");

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////GOLD CARD////////////////////////////////////////////////////////

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

    public static void displayCardFrontTopLine(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("--");
        stringBuilder.append(goldCard.getCardPoints()).append("-").append(goldCard.getPointsParameter().getShortName());
        stringBuilder.append("--");
        stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Middle line of the card
        stringBuilder.append("|   ");
        stringBuilder.append(goldCard.getResource().getShortName());
        stringBuilder.append("   |");

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int costSize = goldCard.getCost().length;

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

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////EMPTY SLOT AND PLACE OPTION////////////////////////////////////////////////////////

    public static void displayEmptySlotLine() {
        // Line of the empty slot
        System.out.print("         ");
    }

    public static void displayOptionSlotTopBottomLine() {
        // Top and bottom line of the option slot
        System.out.print(" ------- ");
    }

    public static void displayOptionSlotMiddleLine(int row, int col) {
        if (row<10 && col<10)
            System.out.print("|(" + row + ", " + col + " )|");
        else if (row<10 || col<10)
            System.out.print("|(" + row + ", " + col + ")|");
        else
            System.out.print("|(" + row + "," + col + ")|");
    }
}
