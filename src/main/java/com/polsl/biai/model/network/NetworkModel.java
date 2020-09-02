package com.polsl.biai.model.network;


import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
/*
 * Network model representation.
 */

public class NetworkModel {
    private MultiLayerConfiguration config;
    private MultiLayerNetwork model;

    public NetworkModel(MultiLayerConfiguration config){
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
