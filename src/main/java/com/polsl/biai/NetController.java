package com.polsl.biai;

import com.sun.jndi.toolkit.url.Uri;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;

@Component
@FxmlView("main-stage.fxml")
public class NetController {

    @FXML
    Label label;
    @FXML
    ImageView imageView;

    private NetService netService;

    @Autowired
    public NetController(NetService netService) {
        this.netService = netService;
    }

    public void train() {
        try {
            netService.train();
            netService.save();
        } catch (Exception e) {
            System.out.println("Wyjątek " + e.getMessage());
        }
    }

    public void test() {
        try {
            netService.test();
        } catch (Exception e) {
            System.out.println("Wyjątek " + e.getMessage());
        }
    }

    public void recognize() {
        try {
            FileChooser fChooser = new FileChooser();
            File fileName = fChooser.showOpenDialog(new Stage());
            Image image = new Image(String.valueOf(fileName.toURI()));
            imageView.setImage(image);
            String result = netService.recognize(fileName.toString());
            label.setText(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
