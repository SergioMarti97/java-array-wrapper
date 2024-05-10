package org.array.wrapper.texture;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TextureTest {

    @Test
    void constructor() throws IOException {
        final String filename = Paths.get( "src", "test", "resources", "Joseph21_05.png").toAbsolutePath().toString();
        Texture t = new Texture(new File(filename));
        assertNotNull(t);
    }

}