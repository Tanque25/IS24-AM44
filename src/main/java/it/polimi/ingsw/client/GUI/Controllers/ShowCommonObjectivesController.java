package it.polimi.ingsw.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import it.polimi.ingsw.client.GUI.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for displaying the common objectives in the GUI.
 */
public class ShowCommonObjectivesController extends GUIController {

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private Button ok;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets the action executed when you press the ok button
     */
    public void initialize() {
        ok.setOnMouseClicked(event -> {
            try {
                gui.setPlayerObjectiveSeen(true);
                gui.secretObjectiveCardChoice(gui.getObjectiveCards());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Displays the common objectives by loading the corresponding FXML and setting the images.
     */
    public void showObjectives() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/ShowCommonObjectives.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                Image img1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(0).getId() + ".png"));
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
