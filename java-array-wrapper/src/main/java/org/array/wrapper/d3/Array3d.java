package org.array.wrapper.d3;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d2.Array2d;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Array3d<T> extends Array2d<T> {

    protected int length;

    protected int defaultLayer = 0;

    // Constructors

    public Array3d(final int width, final int height, final int length) {
        super(width, height);
        this.length = length;
    }

    public Array3d(final int size) {
        this(size, size, size);
    }

    // Abstract method

    public abstract void set(final int x, final int y, final int z, final T v);

    public abstract T getValue(final int x, final int y, final int z);

    public T getSample(final float x, final float y, final float z) {
        int sampleX = Math.min((int)(x * (float)this.width), this.width > 0 ? this.width - 1 : this.width);
        int sampleY = Math.min((int)(y * (float)this.height), this.height > 0 ? this.height - 1 : this.height);
        int sampleZ = Math.min((int)(z * (float)this.length), this.length >0 ? this.length - 1 : this.length);

        try {
            return this.getValue(sampleX, sampleY, sampleZ);
        } catch (ArrayIndexOutOfBoundsException var8) {
            String errorMessage = "X: " + x + " Y: " + y + " Z: " + z +
                    " outside of " + this.getWidth() + "x " + this.getHeight() + "y " + this.getLength() + "z ";
            System.out.println("Get sample Error: " + errorMessage + var8.getMessage());
        }

        return null;
    }

    // Iterative methods

    public void forEach(
            final CellConsumer<T> cc,
            final int fromX, final int fromY, final int fromZ,
            final int toX, final int toY, final int toZ) {
        iterate(cc, toIndex(fromX, fromY, fromZ), toIndex(toX, toY, toZ));
    }

    // Methods

    protected void validateArray(final int width, final int height, final int length) {
        super.validateArray(width, height);
        if (length != this.length) {
            throw new IllegalStateException("Grid's sizes do not match. Unable to perform operation.");
        }
    }

    protected void validateArray(final Array3d<T> array) {
        validateArray(array.width, array.height, array.length);
    }

    public boolean isIndexValid(final int x, final int y, final int z) {
        return super.isIndexValid(x, y) && z >= 0 && z < length;
    }

    public int toIndex(final int x, final int y, final int z) {
        return z * width * height + y * width + x;
    }

    @Override
    public int toY(int index) {
        return super.toY(index) - height * toZ(index);
    }

    public int toZ(final int index) {
        return index / (width * height);
    }

    // Getters

    public int getLength() {
        return length;
    }

    public int getDefaultLayer() {
        return defaultLayer;
    }

    public void setDefaultLayer(int defaultLayer) {
        this.defaultLayer = defaultLayer;
    }

    @Override
    public int size() {
        return super.size() * length;
    }

    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder();
        out.append("width = ").append(width);
        out.append(" height = ").append(height);
        out.append(" length = ").append(length).append('\n');
        AtomicInteger numRows = new AtomicInteger();
        forEach(((array, index, value) -> {
            int x = toX(index);
            int y = toY(index);
            int z = toZ(index);
            out.append('[').append(x).append(',').append(y).append(',').append(z).append('|').append(value).append(']');
            if (x != getWidth() - 1) {
                out.append(' ');
            } else {
                out.append('\n');
                numRows.getAndIncrement();
                if (numRows.get() == getHeight()) {
                    out.append('\n');
                    numRows.set(0);
                }
            }
            return CellConsumer.CONTINUE;
        }));
        out.setLength(out.length() - 2);
        return out.toString();
    }

}
