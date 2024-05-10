package org.array.wrapper.texture.io.xml.reader;

import org.array.wrapper.texture.TextureTile;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TextureTileXmlReaderSAXTest {

    @Test
    void read() {
        final String filename = Paths.get( "src", "test", "resources", "Joseph21_05_metainfo.xml").toAbsolutePath().toString();
        TextureTile tt = new TextureTileXmlReaderSAX().read(filename);
        assertNotNull(tt);
    }
}