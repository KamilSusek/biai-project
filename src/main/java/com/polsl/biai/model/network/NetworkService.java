package com.polsl.biai.model.network;

public class NetworkService {
    public static void main(String[] args) throws Exception {
        Network network = new Network();
        //network.train();
     //   network.evaluateOnFile();
    }
}

/*
import model.utils.FileUtils;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
*/
/*
    // Logger issues
    private static String myTempLogger(String logData) {
        System.out.println(logData);
        return logData;
    }



    public static void oldMain() throws IOException {

        int height = 28;
        int width = 28;
        int channels = 1;
        int seed = 123;
        Random randomNumber = new Random(seed);
        // Number of processed files
        int batchSize = 128;
        int outputNum = 10;
        int numOfEpochs = 15;
        private final int HEIGHT = 28;
        private final int WIDTH = 28;
        private final int CHANNELS = 1;
        private final int SEED = 123;
        private Random randomNumber = new Random(SEED);
        // Number of processed files
        private final int BATCH_SIZE = 128;
        private final int OUTPUT_NUMBER = 10;
        private final int NUMBER_OF_EPOCHS = 15;

        File trainData = new File(dataFilePath + "\\training");
        File testData = new File(dataFilePath + "\\testing");

        FileSplit train = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS,
                randomNumber);
        FileSplit test = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS,
                randomNumber);

        ParentPathLabelGenerator labelGenerator = new ParentPathLabelGenerator();

        ImageRecordReader recordReader = new ImageRecordReader(height, width, channels,
                labelGenerator);

        recordReader.initialize(train);
        recordReader.setListeners(new LogRecordListener());

        DataSetIterator dataSetIterator =
                new RecordReaderDataSetIterator(
                        recordReader,batchSize,1,outputNum
                );

        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(scaler);

        // Neural net model
        myTempLogger("**** Neural Net ****");

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nesterovs(0.006, 0.9))
                .l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(height * width)
                        .nOut(100)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .setInputType(InputType.convolutional(height, width, channels))
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener((10)));

        myTempLogger("**** TRAIN MODEL ****");
        for(int i=0;i < numOfEpochs; i++){
            model.fit(dataSetIterator);
        }
        myTempLogger("**** SAVE MODEL****");
        File locationToSave = new File(serializeModelPath);
        boolean saveUpdater = false;
        model.save(locationToSave,saveUpdater);


        File locationToSave = new File(serializeModelPath);
        if (locationToSave.exists()) {
            myTempLogger("Saved Model Found!");
        } else {
            myTempLogger("File not found!");
            myTempLogger("This example depends on running MnistImagePipelineExampleSave, run that example first");
            System.exit(0);
        }

        MultiLayerNetwork model = MultiLayerNetwork.load(locationToSave, true);
        myTempLogger("**** EVALUATE MODEL ****");

        recordReader.reset();
        recordReader.initialize(test);
        DataSetIterator testDataIterator =
                new RecordReaderDataSetIterator(recordReader,batchSize,1,outputNum);
        scaler.fit(testDataIterator);
        testDataIterator.setPreProcessor(scaler);

        Evaluation eval = new Evaluation(outputNum);

        while(testDataIterator.hasNext()){
            DataSet next = testDataIterator.next();
            INDArray output = model.output((next.getFeatures()));
            eval.eval(next.getLabels(),output);
        }
    }

    public static void evaluateFile() throws IOException {
        int height = 28;
        int width = 28;
        int channels = 1;
        List<Integer> labelList = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
       // String chosenFile = fileUtils.FileChooser();
        File locationToSave = new File(serializeModelPath);

        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);

        File file = new File(chosenFile);

        NativeImageLoader loader = new NativeImageLoader(height,width,channels);

        INDArray image = loader.asMatrix(file);

        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.transform(image);

        INDArray output = model.output(image);
        myTempLogger("## FILE CHOSEN:" + chosenFile);
        myTempLogger(output.toString());
        myTempLogger(labelList.toString());
    }*/