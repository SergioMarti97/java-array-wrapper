package org.array.wrapper.texture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.Array2do;

public class Array2dTexture extends Array2do<Texture> {

    public Array2dTexture(Array2do<Texture> array2do) {
        super(array2do);
    }

    public Array2dTexture(TextureTile tt) {
        super(tt.numTilesX, tt.numTilesY);
        for (int x = 0; x < tt.numTilesX; x++) {
            for (int y = 0; y < tt.numTilesY; y++) {
                this.set(x, y, getTileImage(tt, x, y));
            }
        }
    }

    private Texture getTileImage(TextureTile it, int tileX, int tileY) {
        Array2di p = new Array2di(it.tileW, it.tileH);
        for (int y = 0; y < it.tileH; y++) {
            for (int x = 0; x < it.tileW; x++) {
                p.set(x, y, it.get(tileX * it.tileW + x, tileY * it.tileH + y));
            }
        }
        return new Texture(p);
    }

    @Override
    protected Texture[] instanceArray(int size) {
        return new Texture[size];
    }

}
