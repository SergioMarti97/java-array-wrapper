package org.array.wrapper.gltexture;

import org.array.wrapper.Array2di;
import org.array.wrapper.Array2do;

import java.util.Spliterator;
import java.util.function.Consumer;

public class Array2dGLTexture extends Array2do<GLTexture> {

    public Array2dGLTexture(Array2do<GLTexture> array2do) {
        super(array2do);
    }

    public Array2dGLTexture(GLTextureTile tt) {
        super(tt.numTilesX, tt.numTilesY);
        for (int x = 0; x < tt.numTilesX; x++) {
            for (int y = 0; y < tt.numTilesY; y++) {
                this.set(x, y, getTileImage(tt, x, y));
            }
        }
    }

    private GLTexture getTileImage(GLTextureTile it, int tileX, int tileY) {
        Array2di p = new Array2di(it.tileW, it.tileH);
        for (int y = 0; y < it.tileH; y++) {
            for (int x = 0; x < it.tileW; x++) {
                p.set(x, y, it.get(tileX * it.tileW + x, tileY * it.tileH + y));
            }
        }
        return new GLTexture(p);
    }

    @Override
    protected GLTexture[] instanceArray(int size) {
        return new GLTexture[0];
    }

}
