package com.polsl.biai.model.network;

import com.polsl.biai.model.DataConfig;
import com.polsl.biai.model.utils.DataSetUtils;
import com.polsl.biai.model.utils.FileUtils;
import javafx.stage.FileChooser;
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

public class Network {

    //private static final String DATA_FILE_PATH = "F:\\STUDIA\\BIAI PROJEKT\\mnist_png";
    //private static final String SERIALIZE_MODEL_PATH = "trained_mnist_model.zip";
    private final FileUtils fileUtils = new FileUtils();
    private DataSetUtils dataSetUtils = new DataSetUtils();
    NetConfig config = new NetConfig();
    Net network;
    DataConfig dataConfig = new DataConfig();

    public Network() {
        network = new Net(config.getConfig());
    }

    Network(NetConfig config) {
        this.config = config;
        network = new Net(config.getConfig());
    }

    /**
     * Trains model using files provided by dataPath.
     * @param dataPath dataset location
     * */
    public void train(String dataPath) throws IOException {
        myTempLogger("## ROZPOCZETO TRENING ##");
        DataSetIterator iter =
                dataSetUtils.createDataSetIterator(dataPath + "\\training");
        network.getModel().init();

        for (int i = 0; i < 15; i++) {
            network.getModel().fit(iter);
        }
        myTempLogger("## ZAKOŃCZONO TRENING ##");
    }

    /**
     * TODO
     * */
    public void configureModel() {}

    /**
     * Serializes trained model in specified path.
     * @param serializePath location where model is saved.
     * */
    public void saveModel(String serializePath) throws IOException {
        myTempLogger("## ZAPISYWANIE MODELU ##");
        File locationToSave = new File(serializePath);
        boolean saveUpdater = false;
        network.getModel().save(locationToSave, saveUpdater);
        myTempLogger("## MODEL ZAPISANO W " + serializePath + " ##");
    }

    /**
     * Loads model from specified path.
     * @param serializePath location of serialized model.
     * */
    public void loadModel(String serializePath) throws IOException {
        myTempLogger("## WCZYTYWANIE MODELU ##");
        File locationToSave = new File(serializePath);
        network.setModel(MultiLayerNetwork.load(locationToSave, true));
        myTempLogger("## WCZYTANO MODEL ##");
    }

    /**
     * Provides evaluation of model based on test data set.
     * @param dataPath dataset location.
     * */
    public void evaluateOnTest(String dataPath) throws IOException {
        myTempLogger("## ROZPOCZĘTO TESTOWANIE ##");
        DataSetIterator iter =
                dataSetUtils.createDataSetIterator(dataPath + "\\testing");
        Evaluation eval = new Evaluation(10);

        while (iter.hasNext()) {
            DataSet next = iter.next();
            INDArray output = network.getModel().output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }
        myTempLogger("## ZAKOŃCZONO TESTOWANIE ##");
        myTempLogger(eval.stats());
    }

    /**
     * Restores model from specified location.
     * @param serializePath location of serialized model.
     * */
    public void restoreNetworkModel(String serializePath) throws IOException {
        File locationToSave = new File(serializePath);
        network.setModel(ModelSerializer.restoreMultiLayerNetwork(locationToSave));
    }

    /**
     * Evaluates model on specified file.
     * @param chosenFile file location.
     * */
    public String evaluateOnFile(String chosenFile) throws IOException {
        myTempLogger("## ROZPOCZĘTO ROZPOZNAWANIE ZNAKU ##");
        List<String> labelList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        String result;
        File file = new File(chosenFile);

        NativeImageLoader loader = new NativeImageLoader(dataConfig.HEIGHT, dataConfig.WIDTH, dataConfig.CHANNELS);
        INDArray image = loader.asMatrix(file);

        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        INDArray output = network.getModel().output(image);
        myTempLogger("## FILE:" + chosenFile);
        myTempLogger(output.toString());
        myTempLogger(labelList.toString());
        double array[] = output.toDoubleVector();
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        result = labelList.get(max);
        myTempLogger("## ROZPOCZĘTO ROZPOZNAWANIE ZNAKU ##");
        myTempLogger("## ZNALEZIONY ZNAK " + result + " ##");
        return result;
    }

    private String myTempLogger(String logData) {
        System.out.println(logData);
        return logData;
    }
}
