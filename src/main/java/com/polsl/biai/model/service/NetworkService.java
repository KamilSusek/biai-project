package com.polsl.biai.model.service;

import com.polsl.biai.model.network.NetworkToolkit;
import com.polsl.biai.model.utils.FileUtils;
import com.polsl.biai.model.utils.XMLConfig;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * This class wraps networks action into methods that provide applications services.
 */

@Service
public class NetworkService {

    private NetworkToolkit networkToolkit = new NetworkToolkit();
    private FileUtils fileUtils = new FileUtils();
    private XMLConfig cfg;

    public NetworkService() throws ParserConfigurationException, SAXException, IOException {
        cfg = fileUtils.readConfigXML("config.xml");
        System.out.println(cfg.getMnistPath() + " " + cfg.getModelPath());
    }

    public void train() throws IOException {
        try {
            String dataPath = cfg.getMnistPath();
            networkToolkit.train(dataPath);
        } catch (IOException e) {
            throw new IOException("Directory contains invalid data or cannot be opened");
        }
    }

    public void test() throws IOException {
        String modelPath = cfg.getModelPath();
        networkToolkit.restoreNetworkModel(modelPath);
        String dataPath = cfg.getMnistPath();
        networkToolkit.evaluateOnTest(dataPath);
    }

    public void save() throws IOException {
        try {
            String modelPath = cfg.getModelPath();
            networkToolkit.saveModel(modelPath);
        } catch (IOException e) {
            throw new IOException("Trained model cannot be saved.");
        }
    }

    public void load() throws IOException {
        String modelPath = cfg.getModelPath();
        networkToolkit.loadModel(modelPath);
    }

    public String recognize(String file) throws IOException {
        String modelPath = cfg.getModelPath();
        networkToolkit.restoreNetworkModel(modelPath);
        String result = networkToolkit.evaluateOnFile(file);
        return result;
    }
}
