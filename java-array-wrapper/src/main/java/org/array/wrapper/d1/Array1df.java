package org.array.wrapper.d1;

import org.array.wrapper.CellConsumer;

import java.util.Arrays;

// todo revisar
public class Array1df extends Array1d<Float> {

    protected float[] array;

    public Array1df(final float[] array, final int width) {
        super(width);
        this.array = array;
        if (array.length != width) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a array with size " + width);
        }
    }

    public Array1df(final int initialValue, final int width) {
        this(new float[width], width);
        set(initialValue);
    }

    public Array1df(final int width) {
        this(new float[width], width);
    }

    public Array1df(Array1df array1df) {
        this(array1df.array, array1df.width);
    }

    // Methods

    public float get(final int x) {
        return array[x];
    }

    public float set(final int x, final int value) {
        return array[x] = value;
    }

    public Array1df set(final int value) {
        Arrays.fill(array, value);
        return this;
    }

    public void fill(final int value) {
        set(value);
    }

    public void set(final int width, final float[] array) {
        validateArray(width);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array1df array1df) {
        set(array1df.width, array1df.array);
    }

    // Iterative methods

    @Override
    protected void iterate(final CellConsumer<Float> cc, final int fromIndex, final int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, index, array[index])) {
                break;
            }
        }
    }

    // Copy methods

    @Override
    public Array1df copy() {
        final float[] copy = new float[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new Array1df(copy, width);
    }

    // Getters & Setters

    public float[] getArray() {
        return array;
    }

    @Override
    public void set(int i, Float v) {
        this.set(i, v.intValue());
    }

    @Override
    public Float getValue(int i) {
        return get(i);
    }

    @Override
    public Float getSample(float x) {
        Float sample = super.getSample(x);
        return sample == null ? 0 : sample;
    }

}
