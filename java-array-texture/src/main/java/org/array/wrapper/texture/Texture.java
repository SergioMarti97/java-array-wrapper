package org.array.wrapper.texture;

import org.array.wrapper.Array2di;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture extends Array2di {

    protected boolean alpha = false;

    // Constructors

    public Texture(int[] array, int width, int height) {
        super(array, width, height);
    }

    public Texture(int initialValue, int width, int height) {
        super(initialValue, width, height);
    }

    public Texture(int width, int height) {
        super(width, height);
    }

    public Texture(int size) {
        super(size);
    }

    public Texture(Array2di array2di) {
        super(array2di);
    }

    public Texture(BufferedImage image) {
        super(0);
        load(image);
    }

    public Texture(File file) throws IOException {
        super(0);
        load(file);
    }

    protected void load(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        array = new int[width * height];
        image.getRGB(0, 0, width, height, array, 0, width);
    }

    protected void load(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        load(image);
    }

    public int getSample(float x, float y) {
        int sampleX = Math.min((int)(x * (float)this.width), this.width > 0 ? this.width - 1 : this.width);
        int sampleY = Math.min((int)(y * (float)this.height), this.height > 0 ? this.height - 1 : this.height);

        int color;
        try {
            color = this.get(sampleX, sampleY);
        } catch (ArrayIndexOutOfBoundsException var8) {
            color = 0;
            String errorMessage = "X: " + x + " Y: " + y + " outside of " + this.getWidth() + "x" + this.getHeight();
            System.out.println("Get sample Error: " + errorMessage + var8.getMessage());
        }

        return color;
    }

    // Getters

    public boolean isAlpha() {
        return alpha;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "width=" + width +
                ", height=" + height +
                ", alpha=" + alpha +
                '}';
    }

}
