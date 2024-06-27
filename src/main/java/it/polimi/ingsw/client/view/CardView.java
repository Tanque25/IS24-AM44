package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.decks.cards.*;
import it.polimi.ingsw.server.model.enumerations.CornerPosition;
import it.polimi.ingsw.server.model.enumerations.ParameterType;
import it.polimi.ingsw.server.model.enumerations.Resource;

public class CardView {

    private static final String RESET_COLOR = "\u001B[0m";
    private static final String YELLOW_COLOR = "\u001B[93m";

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
        if(starterCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append("―――――――");
        if(starterCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int resourceSize = starterCard.getResource().length;

        // Middle line of the card
        switch(resourceSize) {
            case 1: {
                stringBuilder.append("│   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("   │");
                break;
            }
            case 2: {
                stringBuilder.append("│   ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  │");
                break;
            }
            case 3: {
                stringBuilder.append("│  ");
                for(Resource resource : starterCard.getResource()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append("  │");
                break;
            }
        }

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(starterCard.getCorners().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        stringBuilder.append("―――――――");
        if(starterCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////STARTER CARD BACK////////////////////////////////////////////////////////

    public static void displayCardBackTopLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(starterCard.getBackCorner().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());

        stringBuilder.append("―――――――");

        if(starterCard.getBackCorner().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("X");
        else
            stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardBackMiddleLine(StarterCard starterCard) {
        // Middle line of the card
        System.out.print("│       │");
    }

    public static void displayCardBackBottomLine(StarterCard starterCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(starterCard.getBackCorner().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());

        stringBuilder.append("―――――――");

        if(starterCard.getBackCorner().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(starterCard.getBackCorner().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////PLAYABLE CARD////////////////////////////////////////////////////////

    public static void displayCardFrontTopLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());

        stringBuilder.append("―――");
        if (playableCard.getCardPoints() != 0)
            stringBuilder.append(playableCard.getCardPoints());
        else stringBuilder.append("―");
        stringBuilder.append("―――");

        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(playableCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(PlayableCard playableCard) {
        // Middle line of the card
        String stringBuilder = "│   " +
                playableCard.getResource().getShortName() +
                "   │";

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("✖");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());

        stringBuilder.append("―――――――");
        if(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("✖");
        else
            stringBuilder.append(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////PLAYABLE CARD BACK////////////////////////////////////////////////////////

    public static void displayCardBackTopLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(playableCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append("▫");
        stringBuilder.append("―――――――");
        if(playableCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append("▫");

        System.out.print(stringBuilder);
    }

    public static void displayCardBackMiddleLine(PlayableCard playableCard) {
        // Middle line of the card
        String stringBuilder = "│   " +
                playableCard.getResource().getShortName() +
                "   │";

        System.out.print(stringBuilder);
    }

    public static void displayCardBackBottomLine(PlayableCard playableCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bottom line of the card
        if(playableCard.getCorners().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append("▫");
        stringBuilder.append("―――――――");
        if(playableCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append("▫");

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////GOLD CARD////////////////////////////////////////////////////////

    public static void displayCardFrontTopLine(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();

        // Top line of the card
        if(goldCard.getCorners().get(CornerPosition.UP_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_LEFT).getCornerContent().getShortName());
        stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
        if (goldCard.getPointsParameter().equals(ParameterType.EMPTY))
            stringBuilder.append(YELLOW_COLOR + "―" + RESET_COLOR).append(goldCard.getCardPoints()).append(YELLOW_COLOR + "―" + RESET_COLOR);
        else stringBuilder.append(goldCard.getCardPoints()).append(YELLOW_COLOR + "―" + RESET_COLOR).append(goldCard.getPointsParameter().getShortName());
        stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
        if(goldCard.getCorners().get(CornerPosition.UP_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(goldCard.getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontMiddleLine(GoldCard goldCard) {
        // Middle line of the card
        String stringBuilder = YELLOW_COLOR + "│   " + RESET_COLOR +
                goldCard.getResource().getShortName() +
                YELLOW_COLOR + "   │" + RESET_COLOR;

        System.out.print(stringBuilder);
    }

    public static void displayCardFrontBottomLine(GoldCard goldCard) {
        StringBuilder stringBuilder = new StringBuilder();
        int costSize = goldCard.getCost().length;

        // Bottom line of the card
        if(goldCard.getCorners().get(CornerPosition.BOTTOM_LEFT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(goldCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().getShortName());
        switch (costSize) {
            case 0: {
                stringBuilder.append(YELLOW_COLOR + "――――" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "―――" + RESET_COLOR);
                break;
            }
            case 1: {
                stringBuilder.append(YELLOW_COLOR + "―――" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "―――" + RESET_COLOR);
                break;
            }
            case 2: {
                stringBuilder.append(YELLOW_COLOR + "―――" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
                break;
            }
            case 3: {
                stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
                break;
            }
            case 4: {
                stringBuilder.append(YELLOW_COLOR + "――" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "―" + RESET_COLOR);
                break;
            }
            case 5: {
                stringBuilder.append(YELLOW_COLOR + "―" + RESET_COLOR);
                for(Resource resource : goldCard.getCost()) {
                    stringBuilder.append(resource.getShortName());
                }
                stringBuilder.append(YELLOW_COLOR + "―" + RESET_COLOR);
                break;
            }
        }
        if(goldCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).isCovered())
            stringBuilder.append("✖");
        else stringBuilder.append(goldCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().getShortName());

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////GOLD CARD BACK////////////////////////////////////////////////////////

    public static void displayCardBackTopLine(GoldCard goldCard) {
        // Top line of the card
        String stringBuilder = "▫" +
                YELLOW_COLOR + "―――――――" + RESET_COLOR +
                "▫";

        System.out.print(stringBuilder);
    }

    public static void displayCardBackMiddleLine(GoldCard goldCard) {
        // Middle line of the card
        String stringBuilder = YELLOW_COLOR + "│   " + RESET_COLOR +
                goldCard.getResource().getShortName() +
                YELLOW_COLOR + "   │" + RESET_COLOR;

        System.out.print(stringBuilder);
    }

    public static void displayCardBackBottomLine(GoldCard goldCard) {
        // Bottom line of the card
        String stringBuilder = "▫" +
                YELLOW_COLOR + "―――――――" + RESET_COLOR +
                "▫";

        System.out.print(stringBuilder);
    }

    ///////////////////////////////////////////////////////EMPTY SLOT AND PLACE OPTION////////////////////////////////////////////////////////

    public static void displayEmptySlotLine() {
        // Line of the empty slot
        System.out.print("         ");
    }

    public static void displayOptionSlotTopBottomLine() {
        // Top and bottom line of the option slot
        System.out.print(" ――――――― ");
    }

    public static void displayOptionSlotMiddleLine(int row, int col) {
        if (row<10 && col<10)
            System.out.print("│(" + row + ", " + col + " )│");
        else if (row<10 || col<10)
            System.out.print("│(" + row + ", " + col + ")│");
        else
            System.out.print("│(" + row + "," + col + ")│");
    }
}
