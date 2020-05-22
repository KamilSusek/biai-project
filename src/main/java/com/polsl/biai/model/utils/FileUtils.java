package com.polsl.biai.model.utils;

import javax.swing.*;
import java.io.File;

public class FileUtils {

    public  String FileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            return filename;
        }else{
            System.out.println("Unable to open fileChooser");
            return null;
        }
    }
}
