package com.polsl.biai.model.service;

import com.polsl.biai.model.network.NetworkToolkit;
import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfig;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SpecialFeatureService {

    private NetworkToolkit networkToolkit = new NetworkToolkit();
    private FileUtils fileUtils = new FileUtils();

    public String runFeature(String directory) throws ParserConfigurationException, SAXException, IOException {
        File dir = new File(directory);

        int imgIterator = 0;

        XMLConfig xmlcfg = fileUtils.readConfigXML("config.xml");
        networkToolkit.loadModel(xmlcfg.getModelPath());
        String result = "";

        for (final File f : dir.listFiles()) {
            if (!f.isDirectory() && (imgIterator + ".png").equals(f.getName())) {
                result += networkToolkit.evaluateOnFile(directory + "/" + f.getName());
                imgIterator++;
            }
        }
        System.out.println(result);

        return result;
    }
}
