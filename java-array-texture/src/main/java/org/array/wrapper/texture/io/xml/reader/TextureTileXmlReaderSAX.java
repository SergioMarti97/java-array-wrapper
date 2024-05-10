package org.array.wrapper.texture.io.xml.reader;

import org.array.wrapper.texture.Texture;
import org.array.wrapper.texture.TextureTile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TextureTileXmlReaderSAX {

    public TextureTile read(final File file) {
        TextureTile tt = null;
        try (FileInputStream stream = new FileInputStream(file)) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            TextureTileXmlReaderSAXHandler handler = new TextureTileXmlReaderSAXHandler();
            parser.parse(stream, handler);
            tt = handler.getTextureTile();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return tt;
    }

    public Texture read(final File file, final int x, final int y) {
        TextureTile tt = read(file);
        return tt.getTile(x, y);
    }

    public TextureTile read(final String filename) {
        return read(new File(filename));
    }

    public Texture read(final String filename, final int x, final int y) {
        return read(new File(filename), x, y);
    }

}
