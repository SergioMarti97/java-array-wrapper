package org.array.wrapper.gltexture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.Array2do;

@Deprecated
public class Array2dGLTexture extends Array2do<GLTexture> {

    public Array2dGLTexture(Array2do<GLTexture> array2do) {
        super(array2do);
    }

    public Array2dGLTexture(GLTextureTile tt) {
        super(tt.getNumTilesX(), tt.getNumTilesY());
        for (int y = 0; y < tt.getNumTilesY(); y++) {
            for (int x = 0; x < tt.getNumTilesX(); x++) {
                this.set(x, y, getTile(tt, x, y));
            }
        }
    }

    private GLTexture getTile(GLTextureTile tt, int tileX, int tileY) {
        Array2di p = new Array2di(tt.getTileW(), tt.getTileH());
        for (int y = 0; y < tt.getTileH(); y++) {
            for (int x = 0; x < tt.getTileW(); x++) {
                p.set(x, y, tt.get(tileX * tt.getTileW() + x, tileY * tt.getTileH() + y));
            }
        }
        return new GLTexture(p);
    }

    @Override
    protected GLTexture[] instanceArray(int size) {
        return new GLTexture[size];
    }

}
