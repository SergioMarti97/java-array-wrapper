package org.array.wrapper.d2;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d1.Array1d;

public abstract class Array2d<T> extends Array1d<T> {

    protected int height;

    // Constructors

    public Array2d(final int width, final int height) {
        super(width);
        this.height = height;
    }

    public Array2d(final int size) {
        this(size, size);
    }

    // Abstract method

    public abstract void set(final int x, final int y, final T v);

    public abstract T getValue(final int x, final int y);

    public T getSample(final float x, final float y) {
        int sampleX = Math.min((int)(x * (float)this.width), this.width > 0 ? this.width - 1 : this.width);
        int sampleY = Math.min((int)(y * (float)this.height), this.height > 0 ? this.height - 1 : this.height);

        try {
            return this.getValue(sampleX, sampleY);
        } catch (ArrayIndexOutOfBoundsException var8) {
            String errorMessage = "X: " + x + " Y: " + y + " outside of " + this.getWidth() + "x" + this.getHeight();
            System.out.println("Get sample Error: " + errorMessage + var8.getMessage());
        }

        return null;
    }

    // Abstract methods: getRow and getColumn

    public abstract Array1d<T> getRow(final int nRow);

    public abstract Array1d<T> getColumn(final int nCol);

    // Iterative methods

    public void forEach(final CellConsumer<T> cc, final int fromX, final int fromY, final int toX, final int toY) {
        iterate(cc, toIndex(fromX, fromY), toIndex(toX, toY));
    }

    // Methods

    protected void validateArray(final int width, final int height) {
        super.validateArray(width);
        if (height != this.height) {
            throw new IllegalStateException("Grid's sizes do not match. Unable to perform operation.");
        }
    }

    protected void validateArray(final Array2d<T> array) {
        validateArray(array.width, array.height);
    }

    public boolean isIndexValid(final int x, final int y) {
        return super.isIndexValid(x) && y >= 0 && y < height;
    }

    public int toIndex(final int x, final int y) {
        return y * width + x;
    }

    public int toY(final int index) {
        return index / width;
    }

    // Getters

    public int getHeight() {
        return height;
    }

    public int size() {
        return super.size() * height;
    }

    // toString methods

    @Override
    public String arrayValuesToString() {
        final StringBuilder out = new StringBuilder();
        forEach((array, index, value) -> {
            int x = toX(index);
            int y = toY(index);
            out.append('[').append(x).append(',').append(y).append('|').append(value).append(']');
            if (x == getWidth() - 1) {
                out.append('\n');
            } else {
                out.append(' ');
            }
            return CellConsumer.CONTINUE;
        });
        return out.toString();
    }

    @Override
    public String toString() {
        return "width=" + width + " height=" + height + '\n' + arrayValuesToString();
    }

}
