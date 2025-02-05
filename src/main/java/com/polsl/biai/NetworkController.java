package com.polsl.biai;


import com.polsl.biai.model.service.NetworkService;
import com.polsl.biai.model.service.SpecialFeatureService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@FxmlView("main-stage.fxml")
public class NetworkController {

    private final FxWeaver fxWeaver;

    @FXML
    VBox main;
    @FXML
    Label label;
    @FXML
    ImageView imageView;
    @FXML
    Button settings;

    private NetworkService networkService;

    @Autowired
    public NetworkController(FxWeaver fxWeaver, NetworkService networkService) {
        this.fxWeaver = fxWeaver;
        this.networkService = networkService;
    }


    public void train() {
        try {
            networkService.train();
            networkService.save();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void test() {
        try {
            networkService.test();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText("Unable to load model.");
            alert.showAndWait();
        }
    }

    public void recognize() {
        try {
            FileChooser fChooser = new FileChooser();
            fChooser.setTitle("Open Picture");
            fChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*png")
            );

            File fileName = fChooser.showOpenDialog(new Stage());

            if (fileName != null) {
                Image image = new Image(String.valueOf(fileName.toURI()));
                imageView.setImage(image);
                String result = networkService.recognize(fileName.toString());
                label.setText(result);
            }
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void changeSettings() {
        fxWeaver.loadController(SettingsController.class).show(main);
    }

    /**
     * Provides 'special feature' logic
     * */
    public void feature() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File chosenDir = directoryChooser.showDialog(new Stage());

            if (chosenDir != null) {
                SpecialFeatureService feature = new SpecialFeatureService();
                String result;
                result = feature.runFeature(chosenDir.toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Result");
                alert.setContentText(result);
                alert.showAndWait();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
