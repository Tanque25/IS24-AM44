package it.polimi.ingsw.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import it.polimi.ingsw.client.GUI.GUIController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.model.decks.cards.Card;
import it.polimi.ingsw.server.model.decks.cards.PlayableCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class OtherPlayAreaController extends GUIController {
    @FXML
    GridPane playArea;
    @FXML
    Label title;
    @FXML
    Button update;
    private Stage secondaryStage;

    public Stage getSecondaryStage() {
        return secondaryStage;
    }

    public void showPlayArea(String nickname){
        try {
            URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/OtherPlayArea.fxml")).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setController(this);
            Parent root = loader.load();

            setGridPaneImages(nickname);
            if(secondaryStage==null){
                secondaryStage = new Stage();
            }
            secondaryStage.setOnCloseRequest(event-> secondaryStage = null);
            update.setOnMouseClicked(event -> update(nickname));

            Platform.runLater(() -> {
                secondaryStage.setTitle(nickname + "'s playArea");
                secondaryStage.setScene(new Scene(root));
                secondaryStage.show();
            });
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit method if FXML loading fails
        }
    }

    public void setGridPaneImages(String nickname) {
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
        for (int row = 0; row < gui.getPlayAreasMap().get(nickname).length; row++) {
            for (int col = 0; col < gui.getPlayAreasMap().get(nickname).length; col++) {
                Card card = gui.getPlayAreasMap().get(nickname)[row][col];
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

    public void update(String nickname){
        Platform.runLater(()->{
            setGridPaneImages(nickname);
        });
    }
}
