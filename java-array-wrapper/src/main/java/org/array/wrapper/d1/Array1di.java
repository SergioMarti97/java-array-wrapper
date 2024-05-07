package org.array.wrapper.d1;

import org.array.wrapper.CellConsumer;

import java.util.Arrays;

// todo revisar
public class Array1di extends Array1d<Integer> {

    protected int[] array;

    public Array1di(final int[] array, final int width) {
        super(width);
        this.array = array;
        if (array.length != width) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a array with size " + width);
        }
    }

    public Array1di(final int initialValue, final int width) {
        this(new int[width], width);
        set(initialValue);
    }

    public Array1di(final int width) {
        this(new int[width], width);
    }

    public Array1di(Array1di array1di) {
        this(array1di.array, array1di.width);
    }

    // Methods

    public int get(final int x) {
        return array[x];
    }

    public int set(final int x, final int value) {
        return array[x] = value;
    }

    public Array1di set(final int value) {
        Arrays.fill(array, value);
        return this;
    }

    public void fill(final int value) {
        set(value);
    }

    public void set(final int width, final int[] array) {
        validateArray(width);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array1di array1di) {
        set(array1di.width, array1di.array);
    }

    // Iterative methods

    @Override
    protected void iterate(final CellConsumer<Integer> cc, final int fromIndex, final int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, index, array[index])) {
                break;
            }
        }
    }

    // Copy methods

    @Override
    public Array1di copy() {
        final int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new Array1di(copy, width);
    }

    // Getters & Setters

    public int[] getArray() {
        return array;
    }

    @Override
    public void set(int i, Integer v) {
        this.set(i, v.intValue());
    }

    @Override
    public Integer getValue(int i) {
        return get(i);
    }

    @Override
    public Integer getSample(float x) {
        Integer sample = super.getSample(x);
        return sample == null ? 0 : sample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Array1di)) return false;
        Array1di array1di = (Array1di) o;
        return Arrays.equals(getArray(), array1di.getArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getArray());
    }

}
