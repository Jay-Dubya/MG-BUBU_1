package com.sml.burberry.dao;


import com.sml.burberry.model.ExtractDetail;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michaelgoode on 10/08/2017.
 */
@Component
public class BurberryDAOImpl implements BurberryDAO {

    public BurberryDAOImpl() {
    }

    private ArrayList<String> readOrderNumbers(String filename) {
        BufferedReader br = null;
        ArrayList<String> list = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            list.add(line);
            while (line != null) {
                if (!line.isEmpty()) {
                    list.add(line);
                }
                line = br.readLine();
            }
            String everything = sb.toString();
            return list;
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                br.close();
            } catch (Exception ex) {
            }
        }
    }

    public List<ExtractDetail> search(String inputDir, String outputDir, String field, String value, String newValue, String orderFile) {

        orderFile = "C:\\Burberry XML\\Modify\\Orders.txt";
        ArrayList<String> list = readOrderNumbers(orderFile);
        List<File> files = getFilesForSearch(inputDir);
        ArrayList<ExtractDetail> results = new ArrayList<ExtractDetail>();
        field="EBELN";
        Iterator iter = list.iterator();

        for (File xmlFile : files) {
            while (iter.hasNext()) {
                value = (String) iter.next();
                ArrayList<Node> foundNodes = searchFile(xmlFile, field, value, newValue);
                ExtractDetail extractDetail = new ExtractDetail(xmlFile.getName(), field, value, foundNodes);
                results.add(extractDetail);
                writeToFile(outputDir, extractDetail);
            }
        }
        return results;
    }

    public void writeToFile(String outputDir, ExtractDetail extractDetail) {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("NEWDATASET");
            doc.appendChild(mainRootElement);
            // append child elements to root element
            for (int i = 0; i < extractDetail.getNodes().size(); i++) {
                Node aNode = doc.importNode(extractDetail.getNodes().get(i), true);
                mainRootElement.appendChild(aNode);
            }
            // output DOM XML to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", new Integer(2));
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            String filename = extractDetail.getFilename();
            filename = outputDir + filename.substring(0, filename.indexOf(".")) + "E.XML";
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(new File(filename)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<File> getFilesForSearch(String inputDir) {
        File dir = new File(inputDir);
        File[] files = dir.listFiles();
        return Arrays.asList(files);
    }

    public ArrayList<Node> searchFile(File fXmlFile, String field, String value, String newValue) {
        try {
            ArrayList<Node> results = new ArrayList<Node>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("NEWDATASET");
            Node datasetNode = nList.item(0);
            NodeList childList = datasetNode.getChildNodes();
            boolean found = false;
            for (int i = 0; i < childList.getLength(); i++) {
                Node topNode = childList.item(i);
                if (topNode.hasChildNodes()) {
                    for (int j = 0; j < topNode.getChildNodes().getLength(); j++) {
                        Node jNode = topNode.getChildNodes().item(j);
                        if (jNode.hasChildNodes()) {
                            if (!editNode(jNode, field, value, newValue)) {
                                for (int k = 0; k < jNode.getChildNodes().getLength(); k++) {
                                    Node kNode = jNode.getChildNodes().item(k);
                                    if (!editNode(kNode, field, value, newValue)) {
                                        if (kNode.hasChildNodes()) {
                                            for (int l = 0; l < kNode.getChildNodes().getLength(); l++) {
                                                Node lNode = kNode.getChildNodes().item(l);
                                                if (editNode(lNode, field, value, newValue)) {
                                                    results.add(topNode);
                                                }
                                            }
                                        }
                                    } else {
                                        results.add(topNode);
                                    }
                                }
                            } else {
                                results.add(topNode);
                            }
                        }
                    }
                }
            }
            return results;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean editNode(Node node, String field, String value, String newValue) {
        try {
            if (node != null) {
                System.out.println(node.getNodeName());
                if (node.getNodeName().equalsIgnoreCase(field.trim())) {
                    Node child = node.getFirstChild();
                    if (newValue.trim().isEmpty()) { // if no new value then dont change it, just output as is
                        if (child.getNodeValue().equalsIgnoreCase(value)) {
                            child.setNodeValue(newValue);
                            System.out.println("name:" + node.getNodeName() + " value:" + node.getNodeValue() + " " + newValue);
                            return true;
                        } else return false;
                    } else return true;
                } else return false;
            } else return false;
        } catch (Exception ex) {
            System.out.println("name:" + node.getNodeName() + " value:" + node.getNodeValue() + " " + ex.getMessage());
            return false;
        }
    }
}
