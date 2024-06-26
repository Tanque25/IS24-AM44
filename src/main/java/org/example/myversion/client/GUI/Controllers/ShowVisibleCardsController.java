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

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for displaying the visible cards in the GUI.
 */
public class ShowVisibleCardsController extends GUIController {

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private Button ok;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets the action executed when you press the ok button
     */
    public void initialize() {
        ok.setOnMouseClicked(event -> {
            try {
                gui.setPlayerVisibleseen(true);
                gui.starterCardSideChoice(gui.getStarterCard());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Displays the drwable cards by loading the corresponding FXML and setting the images.
     */
    public void displayCards() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/ShowVisibleCards.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                Image img1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredResourceCard().getId() + ".png"));
                imageView1.setImage(img1);
                Image img2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(0).getId() + ".png"));
                imageView2.setImage(img2);
                Image img3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getVisibleResourceCards().get(1).getId() + ".png"));
                imageView3.setImage(img3);

                Image img4 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredGoldCard().getId() + ".png"));
                imageView4.setImage(img4);
                Image img5 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(0).getId() + ".png"));
                imageView5.setImage(img5);
                Image img6 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getVisibleGoldCards().get(1).getId() + ".png"));
                imageView6.setImage(img6);

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
