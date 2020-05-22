package com.polsl.biai.model.network;


import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class Net {
    MultiLayerConfiguration config;
    MultiLayerNetwork model;

    public Net(MultiLayerConfiguration config){
       this.config = config;
       model = new MultiLayerNetwork(config);
    }

    public MultiLayerConfiguration getConfig(){
        return  config;
    }

    public MultiLayerNetwork getModel(){
        return  model;
    }

    public void setConfig(MultiLayerConfiguration config){
        this.config = config;
        this.model = new MultiLayerNetwork(config);
    }

    public void setModel(MultiLayerNetwork model){
        this.model = model;
    }

}
