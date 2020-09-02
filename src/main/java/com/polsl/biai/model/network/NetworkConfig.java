package com.polsl.biai.model.network;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NetworkConfig {

    // picture height
    private final int HEIGHT = 28;
    // picture width
    private final int WIDTH = 28;
    // RGB channels
    private final int CHANNELS = 1;
    // seed for random generator
    private final int SEED = 123;
    // number of output classes, 36 = digits + letters
    private final int OUTPUT_NUMBER = 36;
    // number of nodes
    private final int NODES = 500;
    // Neural net config
    private MultiLayerConfiguration config;

    NetworkConfig() {
        // models configuration
        config = new NeuralNetConfiguration.Builder()
                .seed(SEED)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nesterovs(0.006, 0.9))
                .l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(HEIGHT * WIDTH)
                        .nOut(NODES)
                        .activation(Activation.RELU )
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(NODES)
                        .nOut(NODES)
                        .activation(Activation.RELU )
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(NODES)
                        .nOut(OUTPUT_NUMBER)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .setInputType(InputType.convolutional(HEIGHT, WIDTH, CHANNELS))
                .build();
    }

    public MultiLayerConfiguration getConfig() {
        return config;
    }

    public void setConfig(MultiLayerConfiguration config) {
        this.config = config;
    }
}
