package org.array.wrapper;

public abstract class Array1d<T> {

    protected int width;

    // Abstract methods

    public abstract void set(final int i, T v);

    public abstract T getValue(final int i);

    public abstract T getSample(final float x);

    // Methods

    protected void validateArray(final int width) {
        if (width != this.width) {
            throw new IllegalArgumentException("Array's size do not match. Unable to perform operation.");
        }
    }

    protected void validateArray(final Array1d<T> array) {
        validateArray(array.width);
    }

    public int size() {
        return width;
    }

    @Override
    public String toString() {
        return "size = " + width;
    }

}
