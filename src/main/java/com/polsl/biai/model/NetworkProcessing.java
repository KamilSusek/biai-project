package com.polsl.biai.model;

import java.io.IOException;

public interface NetworkProcessing {

    void train() throws IOException;

    void configureModel();

    void saveModel();

    void loadModel();

    void evaluateOnTest();

    void evaluateOnFile();
}
