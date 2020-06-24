package com.polsl.biai.model;

import com.polsl.biai.model.network.Network;
import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfig;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SpecialFeature {

    private Network network = new Network();
    private FileUtils fileUtils = new FileUtils();

    public String runFeature(String directory) throws ParserConfigurationException, SAXException, IOException {
        File dir = new File(directory);

        int imgIterator = 0;

        XMLConfig xmlcfg = fileUtils.readConfigXML("config.xml");
        network.loadModel(xmlcfg.getModelPath());
        String result = "";

        for (final File f : dir.listFiles()) {
            if (!f.isDirectory() && (imgIterator + ".png").equals(f.getName())) {
                result += network.evaluateOnFile(directory + "/" + f.getName());
                imgIterator++;
            }
        }
        System.out.println(result);

        return result;
    }
}
