package org.array.wrapper.gltexture;

import org.array.wrapper.Array2di;
import org.array.wrapper.operations.ShapeArray2diOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GLTextureTile extends GLTexture {
    
    protected Array2dGLTexture grid;

    protected final int tileW;

    protected final int tileH;

    protected final int numTilesX;

    protected final int numTilesY;

    public GLTextureTile(int[] array, int width, int height, int tileW, int tileH, int numTilesX, int numTilesY) {
        super(array, width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
    }

    public GLTextureTile(int initialValue, int width, int height, int tileW, int tileH) {
        super(initialValue, width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTextureTile(int width, int height, int tileW, int tileH) {
        super(width, height);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTextureTile(int size, int tileW, int tileH) {
        super(size);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTextureTile(Array2di array2di, int tileW, int tileH) {
        super(array2di);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTextureTile(BufferedImage image, int tileW, int tileH) {
        super(image);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTextureTile(File file, int tileW, int tileH) throws IOException {
        super(file);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dGLTexture(this);
    }

    public GLTexture getTile(final int tileX, final int tileY) {
        return grid.get(tileX, tileY);
    }

    public GLTexture getTile(final int index) {
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

    public Array2dGLTexture getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return "GLTextureTile{" +
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
