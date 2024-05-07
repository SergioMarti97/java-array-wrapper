package org.array.wrapper.d2;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d1.Array1d;
import org.array.wrapper.d1.Array1di;

import java.util.Arrays;

public class Array2di extends Array2d<Integer> {

    protected int[] array;

    // Constructors

    public Array2di(final int[] array, final int width, final int height) {
        super(width, height);
        this.array = array;
        if (array.length != size()) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a grid with " + width + " columns and " + height + " rows.");
        }
    }

    public Array2di(int width, int height) {
        this(new int[width * height], width, height);
    }

    public Array2di(final int initialValue, final int width, final int height) {
        this(width, height);
        set(initialValue);
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

    // Fill methods

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

    @Override
    protected void iterate(final CellConsumer<Integer> cc, final int fromIndex, final int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, index, array[index])) { // toX(index), toY(index)
                break;
            }
        }
    }

    // Copy methods

    @Override
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
    public Integer getSample(float x, float y) {
        Integer color = super.getSample(x, y);
        return color == null ? 0 : color;
    }

    @Override
    public Integer getSample(float x) {
        return getSample(toX((int) x), toY((int) x));
    }

    // Abstract methods: getRow and getColumn

    @Override
    public Array1di getRow(int nRow) {
        return new Array1di(Arrays.copyOfRange(array, nRow, nRow + width), width);
    }

    @Override
    public Array1di getColumn(int nCol) {
        int[] col = new int[height];
        for (int i = 0; i < height; i++) {
            col[i] = get(nCol, i);
        }
        return new Array1di(col, height);
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

}
