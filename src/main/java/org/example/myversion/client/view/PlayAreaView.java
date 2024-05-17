package org.example.myversion.client.view;

import org.example.myversion.server.model.decks.cards.*;

public class PlayAreaView {

//    public static void main(String[] args) {
//        Card[][] playArea = new Card[21][21];
//
//        playArea[10][10] = new StarterCard(
//                new Resource[]{Resource.INSECT_KINGDOM},
//                Map.of(
//                        CornerPosition.UP_LEFT, new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.UP_RIGHT, new Corner(Resource.PLANT_KINGDOM),
//                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.INSECT_KINGDOM),
//                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
//                ),
//                Map.of(
//                        CornerPosition.UP_LEFT, new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.UP_RIGHT, new Corner(Resource.PLANT_KINGDOM),
//                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.INSECT_KINGDOM),
//                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.ANIMAL_KINGDOM)
//                )
//        );
//
//        playArea[9][9] = new GoldCard(
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
//        );
//
//        playArea[9][11] = new PlayableCard(
//                Resource.FUNGI_KINGDOM,
//                Map.of(
//                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
//                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
//                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
//                ),
//                0
//        );
//
//        displayMyPlayArea(playArea);
//
//    }

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
                    CardView.displayCardFrontTopLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontTopLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontTopLine((PlayableCard) playArea[row][col]);
                } else if (row % 2 == 0 && col % 2 == 0 || (row % 2 == 1 && col % 2 == 1)) {
                    CardView.displayOptionSlotTopBottomLine();
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print middle line of the cards in the current row
            for(int col = minCol-1; col <= maxCol+1; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    CardView.displayCardFrontMiddleLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontMiddleLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontMiddleLine((PlayableCard) playArea[row][col]);
                } else if (row % 2 == 0 && col % 2 == 0 || (row % 2 == 1 && col % 2 == 1)) {
                    CardView.displayOptionSlotMiddleLine(row, col);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print bottom line of the cards in the current row
            for(int col = minCol-1; col <= maxCol+1; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    CardView.displayCardFrontBottomLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontBottomLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontBottomLine((PlayableCard) playArea[row][col]);
                } else if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                    // TODO: display option slot only if the player can place the card in that slot
                    CardView.displayOptionSlotTopBottomLine();
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
                    if(!playArea[row][col].isPlayedBack())
                        CardView.displayCardFrontTopLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardBackTopLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontTopLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontTopLine((PlayableCard) playArea[row][col]);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print middle line of the cards in the current row
            for(int col = minCol; col <= maxCol; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(!playArea[row][col].isPlayedBack())
                        CardView.displayCardFrontMiddleLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardBackMiddleLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontMiddleLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontMiddleLine((PlayableCard) playArea[row][col]);
                } else {
                    CardView.displayEmptySlotLine();
                }
            }

            System.out.println(); // Move to the next line

            // Print bottom line of the cards in the current row
            for(int col = minCol; col <= maxCol; col++) {
                if(playArea[row][col] instanceof StarterCard) {
                    if(!playArea[row][col].isPlayedBack())
                        CardView.displayCardFrontBottomLine((StarterCard) playArea[row][col]);
                    else CardView.displayCardBackBottomLine((StarterCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof GoldCard) {
                    CardView.displayCardFrontBottomLine((GoldCard) playArea[row][col]);
                } else if (playArea[row][col] instanceof PlayableCard) {
                    CardView.displayCardFrontBottomLine((PlayableCard) playArea[row][col]);
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
