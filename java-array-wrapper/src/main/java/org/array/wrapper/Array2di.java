package org.array.wrapper;

import java.util.Arrays;

public class Array2di extends Array2d<Integer> {

    protected int[] array;

    // Constructors

    public Array2di(final int[] array, final int width, final int height) {
        super(width, height);
        this.array = array;
        if (array.length != width * height) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a grid with " + width + " columns and " + height + " rows.");
        }
    }

    public Array2di(final int initialValue, final int width, final int height) {
        this(new int[width * height], width, height);
        set(initialValue);
    }

    public Array2di(int width, int height) {
        this(new int[width * height], width, height);
    }

    public Array2di(int size) {
        this(size, size);
    }

    public Array2di(Array2di array2di) {
        this(array2di.array, array2di.width, array2di.height);
    }

    // Methods

    public int get(final int x, final int y) {
        return array[toIndex(x, y)];
    }

    public int get(final int i) {
        return array[i];
    }

    public int set(final int x, final int y, final int value) {
        return array[toIndex(x, y)] = value;
    }

    public int set(final int index, final int value) {
        return array[index] = value;
    }

    public Array2di set(final int value) {
        Arrays.fill(array, value);
        return this;
    }

    public void fill(final int value) {
        set(value);
    }

    public Array2di fillColumn(final int x, final int value) {
        for (int y = 0; y < height; y++) {
            array[toIndex(x, y)] = value;
        }
        return this;
    }

    public Array2di fillRow(final int y, final int value) {
        for (int x = 0; x < width; x++) {
            array[toIndex(x, y)] = value;
        }
        return this;
    }

    public void set(final int width, final int height, final int[] array) {
        validateArray(width, height);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array2di array) {
        set(array.width, array.height, array.array);
    }

    // Iterative methods

    public void forEach(final CellConsumer<Integer> cc) {
        iterate(cc, 0, array.length);
    }

    public void forEach(final CellConsumer<Integer> cc, final int fromX, final int fromY) {
        iterate(cc, toIndex(fromX, fromY), array.length);
    }

    public void forEach(final CellConsumer<Integer> cc, final int fromX, final int fromY, final int toX, final int toY) {
        iterate(cc, toIndex(fromX, fromY), toIndex(toX, toY));
    }

    protected void iterate(final CellConsumer<Integer> cc, final int fromIndex, final int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, toX(index), toY(index), array[index])) {
                break;
            }
        }
    }

    public Array2di copy() {
        final int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new Array2di(copy, width, height);
    }

    // Getters

    public int[] getArray() {
        return array;
    }

    // Inherit methods

    @Override
    public void set(final int x, final int y, Integer v) {
        set(x, y, v.intValue());
    }

    @Override
    public void set(int i, Integer v) {
        set(i, v.intValue());
    }

    @Override
    public Integer getValue(int x, int y) {
        return get(x, y);
    }

    @Override
    public Integer getValue(int i) {
        return get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Array2di)) return false;
        Array2di array2di = (Array2di) o;
        return Arrays.equals(getArray(), array2di.getArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getArray());
    }

    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder();
        forEach((array, x, y, value) -> {
            out.append('[').append(x).append(',').append(y).append('|').append(value).append(']');
            if (x == array.width - 1) {
                out.append('\n');
            } else {
                out.append(' ');
            }
            return CellConsumer.CONTINUE;
        });
        return out.toString();
    }

}
