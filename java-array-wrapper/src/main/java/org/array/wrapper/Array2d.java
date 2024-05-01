package org.array.wrapper;

public abstract class Array2d<T> extends Array1d<T> {

    protected int width;

    protected int height;

    // Constructors

    public Array2d(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public Array2d(final int size) {
        this(size, size);
    }

    // Abstract method

    public abstract void set(final int x, final int y, final T v);

    public abstract void set(final int i, T v);

    public abstract T getValue(final int x, final int y);

    public abstract T getValue(final int i);

    // todo implement a class called "Array2dSampler" with all the methods to take a sample from an array
    public abstract T getSample(final float x, final float y);

    // Methods

    protected void validateArray(final int width, final int height) {
        if (width != this.width || height != this.height) {
            throw new IllegalStateException("Grid's sizes do not match. Unable to perform operation.");
        }
    }

    protected void validateArray(final Array2d<T> array) {
        validateArray(array.width, array.height);
    }

    public boolean isIndexValid(final int x, final int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int toIndex(final int x, final int y) {
        return y * width + x;
    }

    public int toX(final int index) {
        return index % width;
    }

    public int toY(final int index) {
        return index / width;
    }

    // Getters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int size() {
        return width * height;
    }

    @Override
    public String toString() {
        return "width = " + width + " height = " + height;
    }

}
