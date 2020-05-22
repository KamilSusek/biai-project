package com.polsl.biai;

import com.polsl.biai.model.network.Network;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NetService {

    Network network = new Network();


    public void train() throws IOException {
        network.train();
    }

    public void test() throws IOException {
        network.evaluateOnTest();
    }

    public void save() throws IOException {
        network.saveModel();
    }

    public void load() throws IOException {
        network.loadModel();
    }

    public void recognize(String file) throws IOException {
        network.restoreNetworkModel();
        network.evaluateOnFile(file);
    }
}
