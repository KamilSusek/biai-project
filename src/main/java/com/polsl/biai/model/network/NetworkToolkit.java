package com.polsl.biai.model.network;

import com.polsl.biai.model.utils.DataConfig;
import com.polsl.biai.model.utils.DataSetUtils;
import com.polsl.biai.model.utils.FileUtils;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * Networks actions provider.
 * */
public class NetworkToolkit {

    private final FileUtils fileUtils = new FileUtils();
    private DataSetUtils dataSetUtils = new DataSetUtils();
    private NetworkConfig config = new NetworkConfig();
    private NetworkModel network;
    private DataConfig dataConfig = new DataConfig();

    public NetworkToolkit() {
        network = new NetworkModel(config.getConfig());
    }

    NetworkToolkit(NetworkConfig config) {
        this.config = config;
        network = new NetworkModel(config.getConfig());
    }

    /**
     * Trains model using files provided by dataPath.
     *
     * @param dataPath dataset location
     */
    public void train(String dataPath) throws IOException {
        logger("## ROZPOCZETO TRENING ##");
        File file = new File(dataPath);
        DataSetIterator iter = dataSetUtils.createDataSetIterator(file + "\\training");
        network.getModel().init();

        for (int i = 0; i < 15; i++) {
            network.getModel().fit(iter);
        }

        logger("## ZAKOŃCZONO TRENING ##");
    }

    /**
     * Serializes trained model in specified path.
     *
     * @param serializePath location where model is saved.
     */
    public void saveModel(String serializePath) throws IOException {
        logger("## ZAPISYWANIE MODELU ##");
        File locationToSave = new File(serializePath);
        boolean saveUpdater = false;
        network.getModel().save(locationToSave, saveUpdater);
        logger("## MODEL ZAPISANO W " + serializePath + " ##");
    }

    /**
     * Loads model from specified path.
     *
     * @param serializePath location of serialized model.
     */
    public void loadModel(String serializePath) throws IOException {
        logger("## WCZYTYWANIE MODELU ##");
        File locationToSave = new File(serializePath);
        network.setModel(MultiLayerNetwork.load(locationToSave, true));
        logger("## WCZYTANO MODEL ##");
    }

    /**
     * Provides evaluation of model based on test data set.
     *
     * @param dataPath dataset location.
     */
    public void evaluateOnTest(String dataPath) throws IOException {
        logger("## ROZPOCZĘTO TESTOWANIE ##");
        File file = new File(dataPath);
        DataSetIterator iter =
                dataSetUtils.createDataSetIterator(file + "\\testing");
        Evaluation eval = new Evaluation(36);

        while (iter.hasNext()) {
            DataSet next = iter.next();
            INDArray output = network.getModel().output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }
        logger("## ZAKOŃCZONO TESTOWANIE ##");
        logger(eval.stats());
    }

    /**
     * Restores model from specified location.
     *
     * @param serializePath location of serialized model.
     */
    public void restoreNetworkModel(String serializePath) throws IOException {
        File locationToSave = new File(serializePath);
        network.setModel(ModelSerializer.restoreMultiLayerNetwork(locationToSave));
    }

    /**
     * Evaluates model on specified file.
     *
     * @param chosenFile file location.
     */
    public String evaluateOnFile(String chosenFile) throws IOException {
        logger("## ROZPOCZĘTO ROZPOZNAWANIE ZNAKU ##");
        List<String> labelList = Arrays.asList(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
                "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "x", "y", "z"
        );
        String result;
        File file = new File(chosenFile);

        NativeImageLoader loader = new NativeImageLoader(dataConfig.HEIGHT, dataConfig.WIDTH, dataConfig.CHANNELS);
        INDArray image = loader.asMatrix(file);

        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        INDArray output = network.getModel().output(image);
        logger("## FILE:" + chosenFile);
        logger(output.toString());
        logger(labelList.toString());
        double array[] = output.toDoubleVector();
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        result = labelList.get(max);
        logger("## ROZPOCZĘTO ROZPOZNAWANIE ZNAKU ##");
        logger("## ZNALEZIONY ZNAK " + result + " ##");
        return result;
    }

    private String logger(String logData) {
        System.out.println(logData);
        return logData;
    }
}
