package org.array.wrapper.texture.io.xml.writer;

import org.array.wrapper.texture.TextureTile;
import org.joml.Vector2i;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static org.array.wrapper.texture.io.xml.XmlUtils.getDocumentBuilder;
import static org.array.wrapper.texture.io.xml.TextureTileXmlUtils.*;

public class TextureTileXmlWriter {

    private final String filename;

    // Constructor
    
    public TextureTileXmlWriter(String filename) {
        this.filename = filename;
    }

    // Methods

    protected Element createElement(Document doc, String tagName) {
        return doc.createElement(tagName);
    }

    protected void writeXml(Document doc, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    protected void writeXml(Document doc, File file) {
        try (FileOutputStream output = new FileOutputStream(file)) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    protected void writeXml(Document doc, String path) {
        writeXml(doc, new File(path));
    }

    public void save(TextureTile root, File file) throws ParserConfigurationException {
        DocumentBuilder db = getDocumentBuilder();
        Document doc = db.newDocument();
        doc.appendChild(createRootElement(doc, root));
        writeXml(doc, file);
    }

    public Element createRootElement(Document doc, TextureTile tt) {
        Element root = createElement(doc, TAG_TEXTURE_TILE);

        Element file = createElement(doc, TAG_TEXTURE_TILE_FILE);
        file.setTextContent(filename);
        root.appendChild(file);

        Element dimensions = createElement(doc, TAG_TEXTURE_TILE_DIMENSIONS);
        Element textureTileWidth = createElement(doc, TAG_TEXTURE_TILE_WIDTH);
        Element textureTileHeight = createElement(doc, TAG_TEXTURE_TILE_HEIGHT);
        textureTileWidth.setTextContent(String.format("%d", tt.getWidth()));
        textureTileHeight.setTextContent(String.format("%d", tt.getHeight()));
        dimensions.appendChild(textureTileWidth);
        dimensions.appendChild(textureTileHeight);
        root.appendChild(dimensions);

        Element tileDimensions = createElement(doc, TAG_TILE_DIMENSIONS);
        Element tileWidth = createElement(doc, TAG_TILE_WIDTH);
        Element tileHeight = createElement(doc, TAG_TILE_HEIGHT);
        tileWidth.setTextContent(String.format("%d", tt.getTileW()));
        tileHeight.setTextContent(String.format("%d", tt.getTileH()));
        tileDimensions.appendChild(tileWidth);
        tileDimensions.appendChild(tileHeight);
        root.appendChild(tileDimensions);

        Element numTiles = createElement(doc, TAG_NUM_TILES);
        Element numTilesX = createElement(doc, TAG_NUM_TILES_X);
        Element numTilesY = createElement(doc, TAG_NUM_TILES_Y);
        numTilesX.setTextContent(String.format("%d", tt.getNumTilesX()));
        numTilesY.setTextContent(String.format("%d", tt.getNumTilesY()));
        numTiles.appendChild(numTilesX);
        numTiles.appendChild(numTilesY);
        root.appendChild(numTiles);

        Element textures = createElement(doc, TAG_TEXTURES);
        for (int y = 0; y < tt.getGrid().getHeight(); y++) {
            for (int x = 0; x < tt.getGrid().getWidth(); x++) {
                var t = tt.getGrid().get(x, y);
                Element texture = createElement(doc, TAG_TEXTURE);

                int id = tt.getGrid().toIndex(x, y);
                Element textureId = createElement(doc, TAG_TEXTURE_ID);
                textureId.setTextContent(String.format("%d", id));
                texture.appendChild(textureId);

                Element texturePosition = createElement(doc, TAG_TEXTURE_POSITION);
                Element texturePositionX = createElement(doc, TAG_TEXTURE_POSITION_X);
                Element texturePositionY = createElement(doc, TAG_TEXTURE_POSITION_Y);
                texturePositionX.setTextContent(String.format("%d", x));
                texturePositionY.setTextContent(String.format("%d", y));
                texturePosition.appendChild(texturePositionX);
                texturePosition.appendChild(texturePositionY);
                texture.appendChild(texturePosition);

                textures.appendChild(texture);
            }
        }
        root.appendChild(textures);

        return root;
    }

    public Element createRootElement(Document doc, TextureTile tt, HashMap<Vector2i, Integer> listTexturesIds) {
        Element root = createElement(doc, TAG_TEXTURE_TILE);

        Element file = createElement(doc, TAG_TEXTURE_TILE_FILE);
        file.setTextContent(filename);
        root.appendChild(file);

        Element dimensions = createElement(doc, TAG_TEXTURE_TILE_DIMENSIONS);
        Element textureTileWidth = createElement(doc, TAG_TEXTURE_TILE_WIDTH);
        Element textureTileHeight = createElement(doc, TAG_TEXTURE_TILE_HEIGHT);
        textureTileWidth.setTextContent(String.format("%d", tt.getWidth()));
        textureTileHeight.setTextContent(String.format("%d", tt.getHeight()));
        dimensions.appendChild(textureTileWidth);
        dimensions.appendChild(textureTileHeight);
        root.appendChild(dimensions);

        Element tileDimensions = createElement(doc, TAG_TILE_DIMENSIONS);
        Element tileWidth = createElement(doc, TAG_TILE_WIDTH);
        Element tileHeight = createElement(doc, TAG_TILE_HEIGHT);
        tileWidth.setTextContent(String.format("%d", tt.getTileW()));
        tileHeight.setTextContent(String.format("%d", tt.getTileH()));
        tileDimensions.appendChild(tileWidth);
        tileDimensions.appendChild(tileHeight);
        root.appendChild(tileDimensions);

        Element numTiles = createElement(doc, TAG_NUM_TILES);
        Element numTilesX = createElement(doc, TAG_NUM_TILES_X);
        Element numTilesY = createElement(doc, TAG_NUM_TILES_Y);
        numTilesX.setTextContent(String.format("%d", tt.getNumTilesX()));
        numTilesY.setTextContent(String.format("%d", tt.getNumTilesY()));
        numTiles.appendChild(numTilesX);
        numTiles.appendChild(numTilesY);
        root.appendChild(numTiles);

        Element textures = createElement(doc, TAG_TEXTURES);
        for (int y = 0; y < tt.getGrid().getHeight(); y++) {
            for (int x = 0; x < tt.getGrid().getWidth(); x++) {
                var t = tt.getGrid().get(x, y);
                Element texture = createElement(doc, TAG_TEXTURE);

                int id = tt.getGrid().toIndex(x, y);
                Element textureId = createElement(doc, TAG_TEXTURE_ID);
                textureId.setTextContent(String.format("%d", listTexturesIds.get(new Vector2i(x, y))));
                texture.appendChild(textureId);

                Element texturePosition = createElement(doc, TAG_TEXTURE_POSITION);
                Element texturePositionX = createElement(doc, TAG_TEXTURE_POSITION_X);
                Element texturePositionY = createElement(doc, TAG_TEXTURE_POSITION_Y);
                texturePositionX.setTextContent(String.format("%d", x));
                texturePositionY.setTextContent(String.format("%d", y));
                texturePosition.appendChild(texturePositionX);
                texturePosition.appendChild(texturePositionY);
                texture.appendChild(texturePosition);

                textures.appendChild(texture);
            }
        }
        root.appendChild(textures);

        return root;
    }

    public void save(TextureTile root, File file, HashMap<Vector2i, Integer> listTexturesIds) throws ParserConfigurationException {
        DocumentBuilder db = getDocumentBuilder();
        Document doc = db.newDocument();
        var e = createRootElement(doc, root, listTexturesIds);
        doc.appendChild(e);
        writeXml(doc, file);
    }

}
