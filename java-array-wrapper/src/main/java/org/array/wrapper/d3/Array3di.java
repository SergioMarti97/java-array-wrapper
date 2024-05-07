package org.array.wrapper.d3;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d1.Array1d;
import org.array.wrapper.d1.Array1df;
import org.array.wrapper.d1.Array1di;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Array3di extends Array3d<Integer> {

    protected int[] array;

    // Constructors

    public Array3di(final int[] array, final int width, final int height, final int length) {
        super(width, height, length);
        this.array = array;
        if (array.length != size()) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a grid with " + width + " columns and " + height + " rows.");
        }
    }

    public Array3di(final int width, final int height, final int length) {
        this(new int[width * height * length], width, height, length);
    }

    public Array3di(final int initialValue, final int width, final int height, final int length) {
        this(width, height, length);
        set(initialValue);
    }

    public Array3di(final int size) {
        this(size, size, size);
    }

    public Array3di(final Array3di array3di) {
        this(array3di.array, array3di.width, array3di.height, array3di.length);
    }

    // Methods

    public int get(final int x, final int y, final int z) {
        return array[toIndex(x, y, z)];
    }

    public int get(final int i) {
        return array[i];
    }

    public int set(final int x, final int y, final int z, final int value) {
        return array[toIndex(x, y, z)] = value;
    }

    public int set(final int index, final int value) {
        return array[index] = value;
    }

    public Array3di set(final int value) {
        Arrays.fill(array, value);
        return this;
    }

    // Fill methods

    public void fill(final int value) {
        set(value);
    }

    public Array3di fillColumn(final int x, final int layer, final int value) {
        for (int y = 0; y < height; y++) {
            array[toIndex(x, y, layer)] = value;
        }
        return this;
    }

    public Array3di fillRow(final int y, final int layer, final int value) {
        for (int x = 0; x < width; x++) {
            array[toIndex(x, y, layer)] = value;
        }
        return this;
    }

    public Array3di fillLayer(final int layer, final int value) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                array[toIndex(x, y, layer)] = value;
            }
        }
        return this;
    }

    public Array3di fillHole(final int x, final int y, final int value) {
        for (int z = 0; z < length; z++) {
            array[toIndex(x, y, z)] = value;
        }
        return this;
    }

    public void set(final int width, final int height, final int length, final int[] array) {
        validateArray(width, height, length);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array3di array) {
        set(array.width, array.height, array.length, array.array);
    }

    // Iterative methods

    @Override
    protected void iterate(CellConsumer<Integer> cc, int fromIndex, int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, index, array[index])) { // toX(index), toY(index)
                break;
            }
        }
    }

    // Copy methods

    @Override
    public Array3di copy() {
        final int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new Array3di(copy, width, height, length);
    }

    // Getters

    public int[] getArray() {
        return array;
    }

    // Inherit methods

    @Override
    public void set(int i, Integer v) {

    }

    @Override
    public Integer getValue(int i) {
        return null;
    }

    @Override
    public Integer getSample(float x) {
        return null;
    }

    @Override
    public void set(int x, int y, Integer v) {

    }

    @Override
    public Integer getValue(int x, int y) {
        return null;
    }

    @Override
    public Integer getSample(float x, float y) {
        return null;
    }

    @Override
    public void set(int x, int y, int z, Integer v) {

    }

    @Override
    public Integer getValue(int x, int y, int z) {
        return null;
    }

    @Override
    public Integer getSample(float x, float y, float z) {
        Integer v = super.getSample(x, y, z);
        return v == null ? 0 : v;
    }

    // Abstract methods: getRow and getColumn

    @Override
    public Array1di getRow(int nRow) {
        int start = toIndex(0, nRow, defaultLayer);
        return new Array1di(Arrays.copyOfRange(array, start, start + width), width);
    }

    @Override
    public Array1di getColumn(int nCol) {
        int[] col = new int[height];
        for (int i = 0; i < height; i++) {
            col[i] = get(nCol, i, defaultLayer);
        }
        return new Array1di(col, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Array3di)) return false;
        Array3di array3di = (Array3di) o;
        return Arrays.equals(getArray(), array3di.getArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getArray());
    }

}
