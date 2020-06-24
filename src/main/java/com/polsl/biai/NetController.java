package com.polsl.biai;


import javafx.fxml.FXML;
import javafx.scene.control.*;
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
public class NetController {

    private final FxWeaver fxWeaver;

    @FXML
    VBox main;
    @FXML
    Label label;
    @FXML
    ImageView imageView;
    @FXML
    Button settings;

    private NetService netService;

    @Autowired
    public NetController(FxWeaver fxWeaver, NetService netService) {
        this.fxWeaver = fxWeaver;
        this.netService = netService;
    }



    public void train() {
        try {
            netService.train();
            netService.save();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void test() {
        try {
            netService.test();
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
                String result = netService.recognize(fileName.toString());
                label.setText(result);
            }
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void changeSettings(){
        fxWeaver.loadController(SettingsController.class).show(main);
        //main.setDisable(true);
    }

    public void feature() {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File chosenDir = directoryChooser.showDialog(new Stage());

            if (chosenDir != null) {
                //feature actions here
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
