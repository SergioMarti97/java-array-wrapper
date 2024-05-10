package org.array.wrapper.texture;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d1.Array1d;
import org.array.wrapper.d2.Array2di;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture extends Array2di {

    protected boolean alpha = false;

    // Array constructors

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

    // Image constructors

    public Texture(BufferedImage image) {
        super(0);
        load(image);
    }

    public Texture(File file) throws IOException {
        super(0);
        load(file);
    }

    // Copy constructors

    public Texture(Array2di array2di) {
        super(array2di);
    }

    public Texture(Texture copy) {
        super(copy.array, copy.width, copy.height);
        alpha = copy.alpha;
    }

    // Methods

    public void load(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        array = new int[width * height];
        alpha = image.getTransparency() == Transparency.TRANSLUCENT;
        image.getRGB(0, 0, width, height, array, 0, width);
    }

    public void load(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        load(image);
    }

    public void save(final File file, final String typeImage) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, array, 0, width);
        ImageIO.write(bufferedImage, typeImage, file);
    }

    public void save(final String filename, final String typeImage) throws IOException {
        save(new File(filename), typeImage);
    }

    // Getters

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return "Texture " + "width=" + width + " height=" + height + " alpha=" + alpha;
    }

}
