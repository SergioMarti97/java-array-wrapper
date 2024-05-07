package org.array.wrapper.d1;

import org.array.wrapper.CellConsumer;

public abstract class Array1d<T> {

    protected int width;

    // Constructor

    public Array1d(final int width) {
        this.width = width;
    }

    // Abstract methods

    public abstract void set(final int i, T v);

    public abstract T getValue(final int i);

    public T getSample(final float x) {
        int sampleX = Math.min((int)(x * (float)this.width), this.width > 0 ? this.width - 1 : this.width);
        try {
            return this.getValue(sampleX);
        } catch (ArrayIndexOutOfBoundsException var8) {
            String errorMessage = "X: " + x + " outside of " + this.getWidth() + "x";
            System.out.println("Get sample Error: " + errorMessage + var8.getMessage());
        }
        return null;
    }

    // Abstract iterative methods

    protected abstract void iterate(final CellConsumer<T> cc, final int fromIndex, final int toIndex);

    public void forEach(final CellConsumer<T> cc) {
        iterate(cc, 0, size());
    }

    public void forEach(final CellConsumer<T> cc, final int from, final int to) {
        iterate(cc, from, to);
    }

    // Abstract copy methods

    public abstract Array1d<T> copy();

    // Methods

    protected void validateArray(final int width) {
        if (width != this.width) {
            throw new IllegalArgumentException("Array's size do not match. Unable to perform operation.");
        }
    }

    protected void validateArray(final Array1d<T> array) {
        validateArray(array.width);
    }

    public boolean isIndexValid(final int x) {
        return x >= 0 && x < width;
    }

    public int toX(final int index) {
        return index % width;
    }

    // Getters

    public int getWidth() {
        return width;
    }

    public int size() {
        return width;
    }

    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder();
        out.append("width = ").append(width).append(" [");
        forEach((array, x, value) -> {
            out.append(x).append('=').append(value);
            if (x != array.getWidth() - 1) {
                out.append(", ");
            }
            return CellConsumer.CONTINUE;
        });
        out.append(']');
        return out.toString();
    }

}
