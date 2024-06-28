package it.polimi.ingsw.client.GUI.Controllers;

import it.polimi.ingsw.server.model.decks.cards.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GUIController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.model.Coordinates;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GamePhaseController extends GUIController {
    @FXML
    GridPane playArea;
    @FXML
    Button drawCard;
    @FXML
    GridPane yourHand;
    @FXML
    GridPane cardToDraw;
    @FXML
    ImageView secretObjective;
    @FXML
    ImageView commonObjective1;
    @FXML
    ImageView commonObjective2;
    @FXML
    ImageView handCard1;
    @FXML
    ImageView handCard2;
    @FXML
    ImageView handCard3;
    @FXML
    ImageView handCard4;
    @FXML
    ImageView handCard5;
    @FXML
    ImageView handCard6;
    @FXML
    ImageView drawableCard1;
    @FXML
    ImageView drawableCard2;
    @FXML
    ImageView drawableCard3;
    @FXML
    ImageView drawableCard4;
    @FXML
    ImageView drawableCard5;
    @FXML
    ImageView drawableCard6;
    @FXML
    Button drawingButton;
    @FXML
    VBox playerScores;
    @FXML
    VBox otherPlayAreas;
    @FXML
    Button buttonPlayArea1;
    @FXML
    Button buttonPlayArea2;
    @FXML
    Button buttonPlayArea3;
    private PlayableCard cardPlayed;
    private int cardDrawn;
    private boolean yourTurn = false;
    private boolean yourDraw = false;
    private OtherPlayAreaController otherPlayAreaController1;
    private OtherPlayAreaController otherPlayAreaController2;
    private OtherPlayAreaController otherPlayAreaController3;


    /**
     * Called when it's the player turn. Shows an alert to notify the player it's his
     * turn to play a card and sets the yourTurn param to true
     */
    public void activateTurn(){
        Platform.runLater(()->{
            showAlert("It's your turn", "Please make a move");
        });
        yourTurn = true;
    }
    /**
     * Called when it's the player turn. Shows an alert to notify the player it's his
     * turn to draw a card and sets the yourDraw param to true
     */
    public void activateDraw(){
        Platform.runLater(()->{
            showAlert("It's your turn", "Please draw a card");
        });
        yourDraw = true;
        cardDrawn = 7;
        drawingButton.setDisable(false);
    }
    /**
     * Sets the correct images and loads up the game scene.
     */
    public void chargeScene(){
        try {
            URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/GamePhase.fxml")).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setController(this);
            Parent root = loader.load();

            setPLayAreasButtons();
            setScores();
            setObjectiveImages();
            setGridPaneImages();
            updateHandImages();
            setDrawableCardsImages();


            Platform.runLater(() -> {
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            });
        } catch (IOException e) {

        }
    }
    /**
     * Called when it's the player time to play a card, Allows him to effectively play it
     */
    public void gamePhase(){
        Platform.runLater(() -> {
            setHandImages();
        });

    }
    /**
     * Refresh the scene by setting it up with the updated parameters
     */
    public void updateScene(){
        Platform.runLater(()->{
            updateHandImages();
            updateDrawableCardsImages();
            setScores();
        });
    }
    /**
     * Called when it's the player time to play a card, Allows him to effectively draw it
     * and sets the updated playArea on the scene
     */
    public void drawPhase(){
        drawingButton.setOnMouseClicked(event -> handleDrawButton());
        Platform.runLater(() -> {
            updateHandImages();
            setGridPaneImages();
        });
    }
    /**
     * Configures the images in the GridPane representing the play area.
     */
    public void setGridPaneImages() {
        for (int i = 0; i < playArea.getColumnCount(); i++) {
            playArea.getColumnConstraints().get(i).setPrefWidth(100);
            playArea.getColumnConstraints().get(i).setMinWidth(100);
            playArea.getColumnConstraints().get(i).setMaxWidth(100);
        }
        for (int i = 0; i < playArea.getRowCount(); i++) {
            playArea.getRowConstraints().get(i).setPrefHeight(50);
            playArea.getRowConstraints().get(i).setMinHeight(50);
            playArea.getRowConstraints().get(i).setMaxHeight(50);
        }
        for (int row = 0; row < gui.getPlayAreasMap().get(gui.getClient().getNickname()).length; row++) {
            for (int col = 0; col < gui.getPlayAreasMap().get(gui.getClient().getNickname()).length; col++) {
                Card card = gui.getPlayAreasMap().get(gui.getClient().getNickname())[row][col];
                if (card != null) {
                    if(card.isPlayedBack()){
                        Image imgFront = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + card.getId() + ".png"));
                        ImageView imageView = new ImageView(imgFront);
                        double cellWidth = playArea.getColumnConstraints().get(col).getPrefWidth();
                        double cellHeight = playArea.getRowConstraints().get(row).getPrefHeight();

                        imageView.setFitWidth(cellWidth);
                        imageView.setFitHeight(cellHeight);
                        imageView.setPreserveRatio(true);
                        playArea.add(imageView, col, row);

                    }else{
                        Image imgFront = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + card.getId() + ".png"));
                        ImageView imageView = new ImageView(imgFront);
                        double cellWidth = playArea.getColumnConstraints().get(col).getPrefWidth();
                        double cellHeight = playArea.getRowConstraints().get(row).getPrefHeight();

                        imageView.setFitWidth(cellWidth);
                        imageView.setFitHeight(cellHeight);
                        imageView.setPreserveRatio(true);
                        playArea.add(imageView, col, row);
                    }

                }
            }
        }
    }
    /**
     * Sets the correct images for the drawable cards and sets the event
     * that allows you to draw a card when you click on when it's your time to draw
     */
    public void setDrawableCardsImages(){
        drawableCard1.setImage(null);
        drawableCard2.setImage(null);
        drawableCard3.setImage(null);
        drawableCard4.setImage(null);
        drawableCard5.setImage(null);
        drawableCard6.setImage(null);
        Image img1 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getCoveredResourceCard().getId() + ".png"));
        drawableCard1.setImage(img1);

        Image img2 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(0).getId() + ".png"));
        drawableCard2.setImage(img2);
        Image img3 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(1).getId() + ".png"));
        drawableCard3.setImage(img3);
        Image img4 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getCoveredGoldCard().getId() + ".png"));
        drawableCard4.setImage(img4);
        Image img5 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(0).getId() + ".png"));
        drawableCard5.setImage(img5);
        Image img6 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(1).getId() + ".png"));
        drawableCard6.setImage(img6);
        drawableCard1.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 4;
            }
        });
        drawableCard2.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 0;
            }
        });
        drawableCard3.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 1;
            }
        });
        drawableCard4.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 5;
            }
        });
        drawableCard5.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 2;
            }
        });
        drawableCard6.setOnMouseClicked(event -> {
            if(yourDraw){
                cardDrawn = 3;
            }
        });
    }
    /**
     * Sets the correct images for the drawable cards
     */
    public void updateDrawableCardsImages(){
        drawableCard1.setImage(null);
        drawableCard2.setImage(null);
        drawableCard3.setImage(null);
        drawableCard4.setImage(null);
        drawableCard5.setImage(null);
        drawableCard6.setImage(null);
        Image img1 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getCoveredResourceCard().getId() + ".png"));
        drawableCard1.setImage(img1);
        Image img2 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(0).getId() + ".png"));
        drawableCard2.setImage(img2);
        Image img3 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(1).getId() + ".png"));
        drawableCard3.setImage(img3);
        Image img4 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getCoveredGoldCard().getId() + ".png"));
        drawableCard4.setImage(img4);
        Image img5 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(0).getId() + ".png"));
        drawableCard5.setImage(img5);
        Image img6 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(1).getId() + ".png"));
        drawableCard6.setImage(img6);
    }
    /**
     * Sets the correct images for the cards in your hand
     */
    public void updateHandImages(){
        handCard1.setImage(null);
        handCard2.setImage(null);
        handCard3.setImage(null);
        handCard4.setImage(null);
        handCard5.setImage(null);
        handCard6.setImage(null);
        Image img1 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).getFirst().getId() + ".png"));
        handCard1.setImage(img1);
        Image img2 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).get(1).getId() + ".png"));
        handCard2.setImage(img2);
        Image img4 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).getFirst().getId() + ".png"));
        handCard4.setImage(img4);
        Image img5 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).get(1).getId() + ".png"));
        handCard5.setImage(img5);
        if(gui.getHandsMap().get(gui.getClient().getNickname()).size()==3){
            Image img6 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).get(2).getId() + ".png"));
            handCard6.setImage(img6);
            Image img3 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).get(2).getId() + ".png"));
            handCard3.setImage(img3);
        }

    }

    /**
     * Sets the correct images for the hand of the player and sets the event
     * that allows you to play a card when you click on it during your turn
     */
    public void setHandImages(){
        System.out.println("Your hand:");
        System.out.println(gui.getHandsMap().get(gui.getClient().getNickname()).get(1).getId());
        System.out.println(gui.getHandsMap().get(gui.getClient().getNickname()).get(0).getId());
        if(gui.getHandsMap().get(gui.getClient().getNickname()).size() == 3){
            System.out.println(gui.getHandsMap().get(gui.getClient().getNickname()).get(2).getId());
        }

        handCard1.setImage(null);
        handCard2.setImage(null);
        handCard3.setImage(null);
        handCard4.setImage(null);
        handCard5.setImage(null);
        handCard6.setImage(null);
        Image img1 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).getFirst().getId() + ".png"));
        handCard1.setImage(img1);
        Image img2 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).get(1).getId() + ".png"));
        handCard2.setImage(img2);
        Image img4 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).getFirst().getId() + ".png"));
        handCard4.setImage(img4);
        Image img5 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).get(1).getId() + ".png"));
        handCard5.setImage(img5);
        if(gui.getHandsMap().get(gui.getClient().getNickname()).size() == 3){
            Image img6 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getHandsMap().get(gui.getClient().getNickname()).get(2).getId() + ".png"));
            handCard6.setImage(img6);
            Image img3 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_back/back" + gui.getHandsMap().get(gui.getClient().getNickname()).get(2).getId() + ".png"));
            handCard3.setImage(img3);
            handCard3.setOnMouseClicked(event -> {
                if(yourTurn){
                    cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).get(2);
                    cardPlayed.setPlayedBack(true);
                    populateAdjacentCellsWithButtons();
                }
            });
            handCard6.setOnMouseClicked(event -> {
                if(yourTurn){
                    cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).get(2);
                    cardPlayed.setPlayedBack(false);
                    populateAdjacentCellsWithButtons();
                }
            });
        }
        handCard1.setOnMouseClicked(event -> {
            if(yourTurn){
                cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).getFirst();
                cardPlayed.setPlayedBack(true);
                populateAdjacentCellsWithButtons();
            }
        });
        handCard2.setOnMouseClicked(event -> {
            if(yourTurn){
                cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).get(1);
                cardPlayed.setPlayedBack(true);
                populateAdjacentCellsWithButtons();
            }
        });
        handCard4.setOnMouseClicked(event -> {
            if(yourTurn){
                cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).getFirst();
                cardPlayed.setPlayedBack(false);
                populateAdjacentCellsWithButtons();
            }
        });
        handCard5.setOnMouseClicked(event -> {
            if(yourTurn){
                cardPlayed = gui.getHandsMap().get(gui.getClient().getNickname()).get(1);
                cardPlayed.setPlayedBack(false);
                populateAdjacentCellsWithButtons();
            }
        });
    }

    /**
     * Method to populate the cells with button in the correct position
     */
    public void populateAdjacentCellsWithButtons() {
        List<Pair<Integer, Integer>> positionsToAddButtons = new ArrayList<>();

        for (Node node : playArea.getChildren()) {
            if (node instanceof ImageView) {
                Integer col = GridPane.getColumnIndex(node);
                Integer row = GridPane.getRowIndex(node);
                if (col != null && row != null) {
                    collectCornerPositions(row, col, positionsToAddButtons);
                }
            }
        }

        for (Pair<Integer, Integer> position : positionsToAddButtons) {
            int newRow = position.getKey();
            int newCol = position.getValue();

            Button button = new Button("Click Me");
            button.setOnAction(event -> handleButtonClick(newRow, newCol));
            playArea.add(button, newCol, newRow);
        }
    }
    /**
     * Method to calculate the correct positions where you will add the button in the playArea gridPane
     */
    private void collectCornerPositions(int row, int col, List<Pair<Integer, Integer>> positions) {
        int[] rowOffsets = {-1, -1, 1, 1};
        int[] colOffsets = {-1, 1, -1, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (isValidCell(newRow, newCol) && !isCellOccupied(newRow, newCol)) {
                positions.add(new Pair<>(newRow, newCol));
            }
        }
    }

    /**
     * Adds buttons to the cells around a specific ImageView located at (row, col) in the GridPane.
     * @param row The row index of the ImageView.
     * @param col The column index of the ImageView.
     */
    private void addButtonsAroundImageView(int row, int col) {
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (isValidCell(newRow, newCol) && !isCellOccupied(newRow, newCol)) {
                Button button = new Button("Click Me");
                button.setOnAction(event -> handleButtonClick(newRow, newCol));
                playArea.add(button, newCol, newRow);
            }
        }
    }

    /**
     * Checks if the specified cell (row, col) is within the bounds of the GridPane.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return True if the cell is within bounds, false otherwise.
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < 81 && col >= 0 && col < 81;
    }

    /**
     * Checks if the specified cell (row, col) is already occupied by an ImageView.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return True if the cell is occupied, false otherwise.
     */
    private boolean isCellOccupied(int row, int col) {
        for (Node node : playArea.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if (nodeRow != null && nodeRow == row && nodeCol != null && nodeCol == col && node instanceof ImageView) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the action when a button is clicked.
     * @param row The row index of the button.
     * @param col The column index of the button.
     */
    private void handleButtonClick(int row, int col) {
        try{
            yourTurn = false;
            disableAllButtons();
            if (cardPlayed instanceof GoldCard)
                gui.getClient().sendMessage(new Message("CardToPlayChoice", (GoldCard) cardPlayed, new Coordinates(row, col)));
            else
                gui.getClient().sendMessage(new Message("CardToPlayChoice", cardPlayed, new Coordinates(row, col)));
        }catch(IOException e){

        }
    }
    /**
     * Disable all the buttons present in the gridPane
     */
    public void disableAllButtons() {
        for (Node node : playArea.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        }
    }
    /**
     * This method is called when the server notifies you that you tried to play an invalid move.
     * It asks you to play a new move and sets the scene to allow you to do it
     */
    public void invalidMove(){
        Platform.runLater(()->{
            showAlert("Invalid move", "Please make a different move");
        });
        yourTurn = true;
    }
    /**
     * Called when you click on the drawing button. Check that the drawnCard is valid, send it to the server
     * and set yourDraw to true
     */
    public void handleDrawButton(){
        if(yourDraw){
            if (cardDrawn==7) {
                Platform.runLater(()-> showAlert("Invalid drawing", "Please selct a card before drawing"));
            }else{
                try {
                    drawingButton.setDisable(true);
                    gui.getClient().sendMessage(new Message("CardToDrawChoice", cardDrawn));
                    yourDraw = false;
                } catch (IOException e) {

                }
            }
        }
    }
    /**
     * Sets the images for the objective cards.
     */
    private void setObjectiveImages(){
        Image img1 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(0).getId() + ".png"));
        commonObjective1.setImage(img1);
        Image img2 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(1).getId() + ".png"));
        commonObjective2.setImage(img2);
        Image img3 = new Image(getClass().getResourceAsStream("/it/polimi/ingsw/Images/cards_gold_front/front" + gui.getSecretObjectiveCard().getId() + ".png"));
        secretObjective.setImage(img3);
    }
    /**
     * Sets up the player scores in the VBox.
     */
    private void setScores(){
        playerScores.getChildren().clear();
        Label scoreTitle = new Label();
        scoreTitle.setPrefSize(100, 20);
        scoreTitle.setText("Scores: ");
        playerScores.getChildren().add(scoreTitle);
        if(gui.getScores()!=null){
            for (String nickname : gui.getScores().keySet()) {
                Label scoreLabel = new Label();

                scoreLabel.setPrefSize(100, 20);
                scoreLabel.setText(nickname + ": " + gui.getScores().get(nickname));

                playerScores.getChildren().add(scoreLabel);
            }
        }
    }
    /**
     * Sets up the buttons for switching between play areas.
     */
    private void setPLayAreasButtons(){
        System.out.println(gui.getHandsMap().keySet().size());
        List<String> nicknames = new ArrayList<>(gui.getHandsMap().keySet());
        nicknames.remove(gui.getClient().getNickname());
        switch (gui.getHandsMap().keySet().size()) {
            case 2 -> {
                buttonPlayArea1.setText(nicknames.getFirst());
                buttonPlayArea1.setOnAction(event -> handlePlayerButtonClick1(nicknames.getFirst()));
                otherPlayAreas.getChildren().removeAll(buttonPlayArea2, buttonPlayArea3);
            }
            case 3 -> {
                buttonPlayArea1.setText(nicknames.getFirst());
                buttonPlayArea1.setOnAction(event -> handlePlayerButtonClick1(nicknames.getFirst()));
                buttonPlayArea2.setText(nicknames.get(1));
                buttonPlayArea2.setOnAction(event -> handlePlayerButtonClick1(nicknames.get(1)));
                otherPlayAreas.getChildren().remove(buttonPlayArea3);
            }
            case 4 -> {
                buttonPlayArea1.setText(nicknames.getFirst());
                buttonPlayArea1.setOnAction(event -> handlePlayerButtonClick1(nicknames.getFirst()));
                buttonPlayArea2.setText(nicknames.get(1));
                buttonPlayArea2.setOnAction(event -> handlePlayerButtonClick1(nicknames.get(1)));
                buttonPlayArea2.setText(nicknames.get(2));
                buttonPlayArea2.setOnAction(event -> handlePlayerButtonClick1(nicknames.get(2)));
            }
        }
    }
    /**
     * Charges the new stage with the playArea of the player identified by the nickname
     */
    private void handlePlayerButtonClick1(String nickname){
        if(otherPlayAreaController1 == null){
            otherPlayAreaController1 = new OtherPlayAreaController();
            otherPlayAreaController1.setGui(getGui());
        }
        if(otherPlayAreaController1.getSecondaryStage()==null){
            otherPlayAreaController1.showPlayArea(nickname);
        }else{
            otherPlayAreaController1.update(nickname);
        }

    }
    /**
     * Charges the new stage with the playArea of the player identified by the nickname
     */
    private void handlePlayerButtonClick2(String nickname){
        if(otherPlayAreaController2 == null){
            otherPlayAreaController2 = new OtherPlayAreaController();
            otherPlayAreaController2.setGui(getGui());
        }
        if(otherPlayAreaController2.getSecondaryStage()==null){
            otherPlayAreaController2.showPlayArea(nickname);
        }else{
            otherPlayAreaController2.update(nickname);
        }
    }
    /**
     * Charges the new stage with the playArea of the player identified by the nickname
     */
    private void handlePlayerButtonClick3(String nickname){
        if(otherPlayAreaController3 == null){
            otherPlayAreaController3 = new OtherPlayAreaController();
            otherPlayAreaController3.setGui(getGui());
        }
        if(otherPlayAreaController3.getSecondaryStage()==null){
            otherPlayAreaController3.showPlayArea(nickname);
        }else{
            otherPlayAreaController3.update(nickname);
        }

    }
}

