package com.polsl.biai.model.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
// https://www.kaggle.com/tomasramos21/emnist-jpeg/data?
public class FileUtils {

   public  XMLConfigInstance readConfigXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
       File file = new File("config.xml");
       DocumentBuilderFactory documentBuilderFactory =
               DocumentBuilderFactory.newInstance();
       DocumentBuilder documentBuilder =
               documentBuilderFactory.newDocumentBuilder();
       Document document = documentBuilder.parse(file);
       String mnistPath = document.getElementsByTagName("mnist-path")
               .item(0)
               .getTextContent();
       String modelPath = document.getElementsByTagName("model-name")
               .item(0)
               .getTextContent();
       return new XMLConfigInstance(mnistPath,modelPath);
   }
   /*
    public static void extractTarGz(String inputPath, String outputPath) throws IOException {
        if (inputPath == null || outputPath == null)
            return;
        final int bufferSize = 4096;
        if (!outputPath.endsWith("" + File.separatorChar))
            outputPath = outputPath + File.separatorChar;
        try (TarArchiveInputStream tais = new TarArchiveInputStream(
                new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(inputPath))))) {
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) tais.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(outputPath + entry.getName()).mkdirs();
                } else {
                    int count;
                    byte data[] = new byte[bufferSize];
                    FileOutputStream fos = new FileOutputStream(outputPath + entry.getName());
                    BufferedOutputStream dest = new BufferedOutputStream(fos, bufferSize);
                    while ((count = tais.read(data, 0, bufferSize)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.close();
                }
            }
        }
    }

    public static void main(String [] args) throws IOException {
        extractTarGz("F:\\STUDIA\\BIAI PROJEKT\\gzip\\emnist-balanced-train-images-idx3-ubyte.gz","F:\\STUDIA\\BIAI-GZIP\\train");
    }*/
}
