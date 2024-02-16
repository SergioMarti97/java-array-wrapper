package org.array.wrapper.texture;

import org.array.wrapper.Array2di;
import org.array.wrapper.operations.ShapeArray2diOperations;

import java.io.File;
import java.io.IOException;

public class TextureTile extends Texture {

    protected Array2dTexture grid;

    protected final int tileW;

    protected final int tileH;

    protected final int numTilesX;

    protected final int numTilesY;

    public TextureTile(int[] array, int width, int height, int tileW, int tileH) {
        super(array, width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(int initialValue, int width, int height, int tileW, int tileH) {
        super(initialValue, width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(int width, int height, int tileW, int tileH) {
        super(width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(int size, int tileW, int tileH) {
        super(size);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(Array2di array2di, int tileW, int tileH) {
        super(array2di);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(File file, int tileW, int tileH) throws IOException {
        super(file);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public Texture getTile(final int tileX, final int tileY) {
        return grid.get(tileX, tileY);
    }

    public Texture getTile(final int index) {
        return grid.get(index);
    }

    public void writeArray() {
        Array2di p = new Array2di(width, height);
        ShapeArray2diOperations op = new ShapeArray2diOperations(p);
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                op.writeArray2d(getTile(x, y), x * tileW, y * tileH);
            }
        }
        set(p);
    }

    // Getters

    public int getTileW() {
        return tileW;
    }

    public int getTileH() {
        return tileH;
    }

    public int getNumTilesX() {
        return numTilesX;
    }

    public int getNumTilesY() {
        return numTilesY;
    }

    public Array2dTexture getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return "TextureTile{" +
                "width=" + width +
                ", height=" + height +
                ", alpha=" + alpha +
                ", tileW=" + tileW +
                ", tileH=" + tileH +
                ", numTilesX=" + numTilesX +
                ", numTilesY=" + numTilesY +
                '}';
    }

}
