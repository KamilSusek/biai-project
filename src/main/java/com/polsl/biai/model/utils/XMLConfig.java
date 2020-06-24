package com.polsl.biai.model.utils;

public class XMLConfig {
    private String mnistPath;
    private String modelPath;
    public XMLConfig(){}
    public XMLConfig(String mnistPath, String modelPath){
        this.mnistPath = mnistPath;
        this.modelPath = modelPath;
    }

    public String getMnistPath() {
        return mnistPath;
    }

    public void setMnistPath(String mnistPath) {
        this.mnistPath = mnistPath;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
