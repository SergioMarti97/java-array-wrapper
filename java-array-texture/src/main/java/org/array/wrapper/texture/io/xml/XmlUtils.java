package org.array.wrapper.texture.io.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlUtils {

    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder();
    }

    public static Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder db = getDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static Document getDocument(String filename) throws ParserConfigurationException, IOException, SAXException {
        return getDocument(new File(filename));
    }

}
