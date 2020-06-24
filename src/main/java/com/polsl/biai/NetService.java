package com.polsl.biai;

import com.polsl.biai.model.network.Network;
import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfig;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class NetService {

    private Network network = new Network();
    private FileUtils fileUtils = new FileUtils();
    private XMLConfig cfg;

    public NetService() throws ParserConfigurationException, SAXException, IOException {
        cfg = fileUtils.readConfigXML("config.xml");
        System.out.println(cfg.getMnistPath() + " " + cfg.getModelPath());
    }


    public void train() throws IOException {
        try {
            String dataPath = cfg.getMnistPath();
            network.train(dataPath);
        } catch (IOException e) {
            throw new IOException("Directory contains invalid data or cannot be opened");
        }
    }

    public void test() throws IOException {
        String modelPath = cfg.getModelPath();
        network.restoreNetworkModel(modelPath);
        String dataPath = cfg.getMnistPath();
        network.evaluateOnTest(dataPath);
    }

    public void save() throws IOException {
        try {
            String modelPath = cfg.getModelPath();
            network.saveModel(modelPath);
        } catch (IOException e) {
            throw new IOException("Trained model cannot be saved.");
        }
    }

    public void load() throws IOException {
        String modelPath = cfg.getModelPath();
        network.loadModel(modelPath);
    }

    public String recognize(String file) throws IOException {
        String modelPath = cfg.getModelPath();
        network.restoreNetworkModel(modelPath);
        String result = network.evaluateOnFile(file);
        return result;
    }
}
