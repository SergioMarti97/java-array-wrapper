package org.array.wrapper.texture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.Array2do;

public class Array2dTexture extends Array2do<Texture> {

    public Array2dTexture(Array2do<Texture> array2do) {
        super(array2do);
    }

    public Array2dTexture(TextureTile tt) {
        super(tt.numTilesX, tt.numTilesY);
        for (int y = 0; y < tt.numTilesY; y++) {
            for (int x = 0; x < tt.numTilesX; x++) {
                this.set(x, y, getTile(tt, x, y));
            }
        }
    }

    private Texture getTile(TextureTile tt, int tilePosX, int tilePosY) {
        Array2di p = new Array2di(tt.tileW, tt.tileH);
        for (int y = 0; y < tt.tileH; y++) {
            for (int x = 0; x < tt.tileW; x++) {
                p.set(x, y, tt.get(tilePosX * tt.tileW + x, tilePosY * tt.tileH + y));
            }
        }
        return new Texture(p);
    }

    @Override
    protected Texture[] instanceArray(int size) {
        return new Texture[size];
    }

}
