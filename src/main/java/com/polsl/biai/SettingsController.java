package com.polsl.biai;

import com.polsl.biai.model.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@FxmlView("settings-stage.fxml")
public class SettingsController {

    private Stage stage;
    @FXML
    private VBox settingsDialog;
    private FileUtils fileUtils = new FileUtils();

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(settingsDialog));
    }

    public void show(VBox main) {
        stage.show();
    }

    public void setModelPath() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File chosenDir = directoryChooser.showDialog(new Stage());
            if (chosenDir != null) {
                fileUtils.setModelPath("config.xml", chosenDir.toString());
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void setMnistPath() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File chosenDir = directoryChooser.showDialog(new Stage());
            if (chosenDir != null) {
                fileUtils.setMnistPath("config.xml", chosenDir.toString());
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
