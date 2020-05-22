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

    private static final String DATA_FILE_PATH = "F:\\STUDIA\\BIAI PROJEKT\\mnist_png";
    private static final String SERIALIZE_MODEL_PATH = "trained_mnist_model.zip";
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

    public void train() throws IOException {
        myTempLogger("## ROZPOCZETO TRENING ##");
        DataSetIterator iter =
                dataSetUtils.createDataSetIterator(DATA_FILE_PATH + "\\training");
        network.getModel().init();

        for (int i = 0; i < 15; i++) {
            network.getModel().fit(iter);
        }
        myTempLogger("## ZAKOŃCZONO TRENING ##");
    }

    public void configureModel() {

    }

    public void saveModel() throws IOException {
        myTempLogger("## ZAPISYWANIE MODELU ##");
        File locationToSave = new File(SERIALIZE_MODEL_PATH);
        boolean saveUpdater = false;
        network.getModel().save(locationToSave, saveUpdater);
        myTempLogger("## MODEL ZAPISANO W " + SERIALIZE_MODEL_PATH + " ##");
    }

    public void loadModel() throws IOException {
        myTempLogger("## WCZYTYWANIE MODELU ##");
        File locationToSave = new File(SERIALIZE_MODEL_PATH);
        network.setModel(MultiLayerNetwork.load(locationToSave, true));
        myTempLogger("## WCZYTANO MODEL ##");
    }

    public void evaluateOnTest() throws IOException {
        myTempLogger("## ROZPOCZĘTO TESTOWANIE ##");
        DataSetIterator iter =
                dataSetUtils.createDataSetIterator(DATA_FILE_PATH + "\\testing");
        Evaluation eval = new Evaluation(10);

        while (iter.hasNext()) {
            DataSet next = iter.next();
            INDArray output = network.getModel().output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }
        myTempLogger("## ZAKOŃCZONO TESTOWANIE ##");
        myTempLogger(eval.stats());
    }

    public void restoreNetworkModel() throws IOException {
        File locationToSave = new File(SERIALIZE_MODEL_PATH);
        network.setModel(ModelSerializer.restoreMultiLayerNetwork(locationToSave));
    }

    public String evaluateOnFile(String chosenFile) throws IOException {
        myTempLogger("## ROZPOCZĘTO ROZPOZNAWANIE ZNAKU ##");
        List<String> labelList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        String result;
        FileChooser fileChooser = new FileChooser();
        //String chosenFile = fileUtils.FileChooser();
        File locationToSave = new File(SERIALIZE_MODEL_PATH);
        File file = new File(chosenFile);

        NativeImageLoader loader = new NativeImageLoader(dataConfig.HEIGHT, dataConfig.WIDTH, dataConfig.CHANNELS);
        INDArray image = loader.asMatrix(file);

        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        INDArray output = network.getModel().output(image);
        myTempLogger("## FILE CHOSEN:" + chosenFile);
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
