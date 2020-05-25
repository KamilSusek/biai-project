package com.polsl.biai;

import com.polsl.biai.model.network.Network;
import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfigInstance;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class NetService {

    Network network = new Network();
    private FileUtils fileUtils = new FileUtils();
    XMLConfigInstance cfg;
    public NetService() throws ParserConfigurationException, SAXException, IOException {
        cfg = fileUtils.readConfigXML("config.xml");
        System.out.println(cfg.getMnistPath() + " " + cfg.getModelPath());
    }


    public void train() throws IOException {
        String dataPath = cfg.getMnistPath();
        network.train(dataPath);
    }

    public void test() throws IOException {
        String dataPath = cfg.getMnistPath();
        network.evaluateOnTest(dataPath);
    }

    public void save() throws IOException {
        String modelPath = cfg.getModelPath();
        network.saveModel(modelPath);
    }

    public void load() throws IOException {
        String modelPath = cfg.getModelPath();
        network.loadModel(modelPath);
    }

    public void recognize(String file) throws IOException {
        String modelPath = cfg.getModelPath();
        network.restoreNetworkModel(modelPath);
        network.evaluateOnFile(file);
    }
}
