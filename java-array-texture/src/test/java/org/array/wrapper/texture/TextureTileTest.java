package org.array.wrapper.texture;

import org.console.colors.ColorsASCII;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TextureTileTest {

    @Test
    void getTile() throws IOException {
        final String filename = Paths.get( "src", "test", "resources", "Joseph21_05.png").toAbsolutePath().toString();
        TextureTile tt = new TextureTile(new File(filename), 32, 32);
        // Texture t = tt.getTile(3);
        // assertNotNull(t);
        for (var t : tt.getGrid().getArray()) {
            ColorsASCII.renderTexture(t);
            assertNotNull(t);
        }
    }

}