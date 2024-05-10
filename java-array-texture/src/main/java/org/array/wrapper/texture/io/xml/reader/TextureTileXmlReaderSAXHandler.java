package org.array.wrapper.texture.io.xml.reader;

import org.array.wrapper.texture.Texture;
import org.array.wrapper.texture.TextureTile;
import org.joml.Vector2i;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;

import static org.array.wrapper.texture.io.xml.TextureTileXmlUtils.*;

public class TextureTileXmlReaderSAXHandler extends DefaultHandler {

    private StringBuilder buffer;

    private String filename = null;

    private Vector2i dim = null;

    private Vector2i tileDim = null;

    private Vector2i numTiles = null;

    private Vector2i tilePos = null;

    private TextureTile tt = null;

    private boolean isFilenameRead = false;

    private boolean isTileWidthRead = false;

    private boolean isTileHeightRead = false;

    private boolean isTextureTileInstantiated = false;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (buffer == null) {
            buffer = new StringBuilder();
        } else {
            buffer.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case TAG_TEXTURE_TILE_DIMENSIONS:
                dim = new Vector2i();
                break;
            case TAG_TILE_DIMENSIONS:
                tileDim = new Vector2i();
                break;
            case TAG_NUM_TILES:
                numTiles = new Vector2i();
                break;
            case TAG_TEXTURE_POSITION:
                tilePos = new Vector2i();
                break;
            case TAG_TEXTURE_TILE_FILE:
            case TAG_TEXTURE_TILE_WIDTH:
            case TAG_TEXTURE_TILE_HEIGHT:
            case TAG_TILE_WIDTH:
            case TAG_TILE_HEIGHT:
            case TAG_NUM_TILES_X:
            case TAG_NUM_TILES_Y:
            case TAG_TEXTURE_ID:
            case TAG_TEXTURE_POSITION_X:
            case TAG_TEXTURE_POSITION_Y:
                buffer = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case TAG_TEXTURE_TILE_FILE:
                filename = buffer.toString();
                isFilenameRead = true;
                break;
            case TAG_TEXTURE_TILE_WIDTH:
                dim.x = Integer.parseInt(buffer.toString());
                break;
            case TAG_TEXTURE_TILE_HEIGHT:
                dim.y = Integer.parseInt(buffer.toString());
                break;
            case TAG_TILE_WIDTH:
                tileDim.x = Integer.parseInt(buffer.toString());
                isTileWidthRead = true;
                break;
            case TAG_TILE_HEIGHT:
                tileDim.y = Integer.parseInt(buffer.toString());
                isTileHeightRead = true;
                break;
            case TAG_NUM_TILES_X:
                numTiles.x = Integer.parseInt(buffer.toString());
                break;
            case TAG_NUM_TILES_Y:
                numTiles.y = Integer.parseInt(buffer.toString());
                break;
            case TAG_TEXTURE_ID:
                int id = Integer.parseInt(buffer.toString());
                break;
            case TAG_TEXTURE_POSITION_X:
                tilePos.x = Integer.parseInt(buffer.toString());
                break;
            case TAG_TEXTURE_POSITION_Y:
                tilePos.y = Integer.parseInt(buffer.toString());
                break;
        }

        if (isFilenameRead && isTileWidthRead && isTileHeightRead && !isTextureTileInstantiated) {
            try {
                tt = new TextureTile(new File(filename), tileDim.x, tileDim.y);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isTextureTileInstantiated = true;
        }
    }

    public TextureTile getTextureTile() {
        return tt;
    }

}
