package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ShowCommonObjectivesController extends GUIController {
    @FXML
    ImageView imageView1;
    @FXML
    ImageView imageView2;
    @FXML
    Button ok;

    public void initialize(){
        ok.setOnMouseClicked(event ->{
            try{
                gui.setPlayerObjectiveSeen(true);
                gui.secretObjectiveCardChoice(gui.getObjectiveCards());
            }catch(IOException e){

            }
        });
    }

    public void showObjectives(){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/ShowCommonObjectives.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                Image img1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCommonObjectiveCards().get(0).getId() + ".png"));
                imageView1.setImage(img1);
                Image img2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(1).getId() + ".png"));
                imageView2.setImage(img2);

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
                return; // Exit method if FXML loading fails
            }
        });
    }
}
