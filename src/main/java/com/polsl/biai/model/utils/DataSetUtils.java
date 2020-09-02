package com.polsl.biai.model.utils;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DataSetUtils {

    private DataConfig config = new DataConfig();

    public DataSetIterator createDataSetIterator(String dataFilePath) throws IOException {

        Random randomNumber = new Random(123);

        File data = new File(dataFilePath);
        FileSplit splitData = new FileSplit(data, NativeImageLoader.ALLOWED_FORMATS,
                randomNumber);

        ParentPathLabelGenerator labelGenerator = new ParentPathLabelGenerator();
        ImageRecordReader recordReader =
                new ImageRecordReader(
                        config.HEIGHT, config.WIDTH, config.CHANNELS,labelGenerator
                );
        recordReader.initialize(splitData);

        recordReader.setListeners(new LogRecordListener());
        DataSetIterator dataSetIterator =
                new RecordReaderDataSetIterator(
                        recordReader,config.BATCH_SIZE,1,config.OUTPUT_NUMBER
                );
        DataNormalization scaler = new ImagePreProcessingScaler(0,1);

        scaler.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(scaler);
        return  dataSetIterator;
    }
}
