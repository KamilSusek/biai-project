package com.polsl.biai.model.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    private Document openXMLFile(String fileName) throws IOException, SAXException, ParserConfigurationException {
        File file = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder =
                documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        return document;
    }

    public XMLConfig readConfigXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
        Document document = openXMLFile(fileName);
        String mnistPath = document.getElementsByTagName("mnist-path")
                .item(0)
                .getTextContent();
        String modelPath = document.getElementsByTagName("model-name")
                .item(0)
                .getTextContent();
        return new XMLConfig(mnistPath, modelPath);
    }

    public void setMnistPath(String fileName, String mnistPath) throws IOException, SAXException, ParserConfigurationException {
        Document document = openXMLFile(fileName);
        System.out.println(mnistPath);
        document.getElementsByTagName("mnist-path")
                .item(0)
                .setTextContent(mnistPath);
        // TODO FIX
    }

    public void setModelPath(String fileName, String modelPath) throws IOException, SAXException, ParserConfigurationException {
        Document document = openXMLFile(fileName);
        Node element = document.getElementsByTagName("config").item(0);
        element.getChildNodes().item(1).setTextContent(modelPath);
        System.out.println(modelPath);
        // TODO FIX
    }

}
