package org.array.wrapper.texture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.operations.ShapeArray2diOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TextureTile extends Texture {

    protected Array2dTexture grid;

    /**
     * The width which has the tiles of the bigger image
     */
    protected final int tileW;

    /**
     * The height which has the tiles of the bigger image
     */
    protected final int tileH;

    /**
     * The number of tiles what have the image sheet in x axis
     */
    protected final int numTilesX;

    /**
     * The number of tiles what have the image sheet in y axis
     */
    protected final int numTilesY;

    // Array constructors

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

    // Image constructors

    public TextureTile(BufferedImage image, int tileW, int tileH) {
        super(image);
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

    // Copy constructors

    public TextureTile(Array2di array2di, int tileW, int tileH) {
        super(array2di);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(Texture texture, int tileW, int tileH) {
        super(texture);
        this.tileW = tileW;
        this.tileH = tileH;
        numTilesX = width / tileW;
        numTilesY = height / tileH;
        grid = new Array2dTexture(this);
    }

    public TextureTile(TextureTile copy) {
        super(copy);
        this.tileW = copy.tileW;
        this.tileH = copy.tileH;
        numTilesX = copy.numTilesX;
        numTilesY = copy.numTilesY;
        grid = new Array2dTexture(copy.grid); // todo check this
    }

    // Methods

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

    public Texture[] getTexturesArray() {
        return grid.getArray();
    }

    public List<Texture> getTexturesList() {
        return Arrays.asList(getTexturesArray());
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("^Texture", "TextureTile") +
                ", tileW=" + tileW +
                ", tileH=" + tileH +
                ", numTilesX=" + numTilesX +
                ", numTilesY=" + numTilesY;
    }

}
