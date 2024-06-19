package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.*;

import java.util.Map;

public class PlayAreaView {

    public static void main(String[] args) {
        Card[][] playArea = new Card[21][21];

        playArea[10][10] = new StarterCard(
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
                ), 1
        );

        playArea[9][9] = new GoldCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT,new Corner(SpecialObject.QUILL)
                ),
                1,
                new Resource[]{Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.ANIMAL_KINGDOM},
                SpecialObject.QUILL, 1
        );

        playArea[9][11] = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 1
        );

        displayMyPlayArea(playArea);

    }

    public static void displayMyPlayArea(Card[][] playArea) {
        int[] boundaries = findBoundaries(playArea);

        int minRow = boundaries[0];
        int maxRow = boundaries[1];
        int minCol = boundaries[2];
        int maxCol = boundaries[3];

        for(int row = minRow-1; row <= maxRow+1; row++) {
            // Print top line of the cards in the current row
            for(int col = minCol-1; col <= maxCol+1; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((PlayableCard) playArea[row][col]);
                } else if (row % 2 == 0 && col % 2 == 0 || (row % 2 == 1 && col % 2 == 1)) {
                    // Check if the adjacent cards have full corners
                    if (playArea[row-1][col-1] != null && playArea[row-1][col-1].getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row-1][col+1] != null && playArea[row-1][col+1].getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col-1] != null && playArea[row+1][col-1].getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col+1] != null && playArea[row+1][col+1].getCorners().get(CornerPosition.UP_LEFT).getCornerContent().equals(CornerVisibility.FULL))
                        CardView.displayEmptySlotLine();
                    else CardView.displayOptionSlotTopBottomLine();
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print middle line of the cards in the current row
            for(int col = minCol-1; col <= maxCol+1; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((PlayableCard) playArea[row][col]);
                } else if (row % 2 == 0 && col % 2 == 0 || (row % 2 == 1 && col % 2 == 1)) {
                    // Check if the adjacent cards have full corners
                    if (playArea[row-1][col-1] != null && playArea[row-1][col-1].getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row-1][col+1] != null && playArea[row-1][col+1].getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col-1] != null && playArea[row+1][col-1].getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col+1] != null && playArea[row+1][col+1].getCorners().get(CornerPosition.UP_LEFT).getCornerContent().equals(CornerVisibility.FULL))
                        CardView.displayEmptySlotLine();
                    else CardView.displayOptionSlotMiddleLine(row, col);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print bottom line of the cards in the current row
            for(int col = minCol-1; col <= maxCol+1; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((PlayableCard) playArea[row][col]);
                } else if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                    // Check if the adjacent cards have full corners
                    if (playArea[row-1][col-1] != null && playArea[row-1][col-1].getCorners().get(CornerPosition.BOTTOM_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row-1][col+1] != null && playArea[row-1][col+1].getCorners().get(CornerPosition.BOTTOM_LEFT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col-1] != null && playArea[row+1][col-1].getCorners().get(CornerPosition.UP_RIGHT).getCornerContent().equals(CornerVisibility.FULL) ||
                            playArea[row+1][col+1] != null && playArea[row+1][col+1].getCorners().get(CornerPosition.UP_LEFT).getCornerContent().equals(CornerVisibility.FULL))
                        CardView.displayEmptySlotLine();
                    else CardView.displayOptionSlotTopBottomLine();
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line
        }
    }

    public static void displayOtherPlayArea(Card[][] playArea) {
        int[] boundaries = findBoundaries(playArea);

        int minRow = boundaries[0];
        int maxRow = boundaries[1];
        int minCol = boundaries[2];
        int maxCol = boundaries[3];

        for(int row = minRow; row <= maxRow; row++) {
            // Print top line of the cards in the current row
            for(int col = minCol; col <= maxCol; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackTopLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontTopLine((PlayableCard) playArea[row][col]);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print middle line of the cards in the current row
            for(int col = minCol; col <= maxCol; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackMiddleLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontMiddleLine((PlayableCard) playArea[row][col]);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print bottom line of the cards in the current row
            for(int col = minCol; col <= maxCol; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((GoldCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    if(playArea[row][col].isPlayedBack())
                        CardView.displayCardBackBottomLine((PlayableCard) playArea[row][col]);
                    else CardView.displayCardFrontBottomLine((PlayableCard) playArea[row][col]);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line
        }
    }

    public static int[] findBoundaries(Card[][] playArea) {
        int minRow = playArea.length, maxRow = 0;
        int minCol = playArea[0].length, maxCol = 0;

        for (int i = 0; i < playArea.length; i++) {
            for (int j = 0; j < playArea[i].length; j++) {
                if (playArea[i][j] != null) {
                    if (i < minRow) minRow = i;
                    if (i > maxRow) maxRow = i;
                    if (j < minCol) minCol = j;
                    if (j > maxCol) maxCol = j;
                }
            }
        }

        // Ensure the boundaries are within the array dimensions
        return new int[]{minRow, Math.min(playArea.length - 1, maxRow),
                minCol, Math.min(playArea[0].length - 1, maxCol)};
    }

}
