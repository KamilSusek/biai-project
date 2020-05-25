package com.polsl.biai;

import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfigInstance;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@FxmlView("main-stage.fxml")
public class NetController {

    private NetService netService;
    private FileUtils fileUtils = new FileUtils();
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
        FileChooser fChooser = new FileChooser();
        File fileName = fChooser.showOpenDialog(new Stage());
        /*try {
            netService.recognize(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
}
