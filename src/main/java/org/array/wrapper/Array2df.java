package org.array.wrapper;

import java.util.Arrays;

public class Array2df extends Array2d<Float> {

    private final float[] array;

    // Constructor

    public Array2df(final float[] array, final int width, final int height) {
        super(width, height);
        this.array = array;
        if (array.length != width * height) {
            throw new IllegalArgumentException("Array with length: " + array.length
                    + " is too small or too big to store a grid with " + width + " columns and " + height + " rows.");
        }
    }

    public Array2df(final float initialValue, final int width, final int height) {
        this(new float[width * height], width, height);
        set(initialValue);
    }

    public Array2df(int width, int height) {
        this(new float[width * height], width, height);
    }

    public Array2df(int size) {
        this(size, size);
    }

    public Array2df(Array2df copy) {
        this(copy.array, copy.width, copy.height);
    }

    // Methods

    public float get(final int x, final int y) {
        return array[toIndex(x, y)];
    }

    public float get(final int i) {
        return array[i];
    }

    public float set(final int x, final int y, final float value) {
        return array[toIndex(x, y)] = value;
    }

    public float set(final int index, final float value) {
        return array[index] = value;
    }

    public Array2df set(final float value) {
        Arrays.fill(array, value);
        return this;
    }

    public Array2df fill(final float value) {
        return set(value);
    }

    public Array2df fillColumn(final int x, final float value) {
        for (int y = 0; y < height; y++) {
            array[toIndex(x, y)] = value;
        }
        return this;
    }

    public Array2df fillRow(final int y, final float value) {
        for (int x = 0; x < width; x++) {
            array[toIndex(x, y)] = value;
        }
        return this;
    }

    public float add(final int x, final int y, final float value) {
        return array[toIndex(x, y)] += value;
    }

    public float sub(final int x, final int y, final float value) {
        return array[toIndex(x, y)] -= value;
    }

    public float mul(final int x, final int y, final float value) {
        return array[toIndex(x, y)] *= value;
    }

    public float div(final int x, final int y, final float value) {
        return array[toIndex(x, y)] /= value;
    }

    public float mod(final int x, final int y, final float mod) {
        return array[toIndex(x, y)] %= mod;
    }

    // Iterative methods

    public void forEach(final CellConsumer<Float> cc) {
        iterate(cc, 0, array.length);
    }

    public void forEach(final CellConsumer<Float> cc, final int fromX, final int fromY) {
        iterate(cc, toIndex(fromX, fromY), array.length);
    }

    public void forEach(final CellConsumer<Float> cc, final int fromX, final int fromY, final int toX, final int toY) {
        iterate(cc, toIndex(fromX, fromY), toIndex(toX, toY));
    }

    protected void iterate(final CellConsumer<Float> cc, final int fromIndex, final int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, toX(index), toY(index), array[index])) {
                break;
            }
        }
    }

    protected void validateGrid(final Array2df array2df) {
        validateArray(array2df.width, array2df.height);
    }

    public void set(final int width, final int height, final float[] array) {
        validateArray(width, height);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array2df array) {
        set(array.width, array.height, array.array);
    }

    public void add(final Array2df array2df) {
        validateGrid(array2df);
        for (int index = 0, length = this.array.length; index < length; index++) {
            this.array[index] += array2df.array[index];
        }
    }

    public void sub(final Array2df array2df) {
        validateGrid(array2df);
        for (int index = 0, length = this.array.length; index < length; index++) {
            this.array[index] -= array2df.array[index];
        }
    }

    public void mul(final Array2df array2df) {
        validateGrid(array2df);
        for (int index = 0, length = this.array.length; index < length; index++) {
            this.array[index] *= array2df.array[index];
        }
    }

    public void div(final Array2df array2df) {
        validateGrid(array2df);
        for (int index = 0, length = this.array.length; index < length; index++) {
            this.array[index] /= array2df.array[index];
        }
    }

    public Array2df add(final float value) {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] += value;
        }
        return this;
    }

    public Array2df sub(final float value) {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] -= value;
        }
        return this;
    }

    public Array2df mul(final float value) {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] *= value;
        }
        return this;
    }

    public Array2df div(final float value) {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] /= value;
        }
        return this;
    }

    public Array2df mod(final float modulo) {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] %= modulo;
        }
        return this;
    }

    public Array2df negate() {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index] = -array[index];
        }
        return this;
    }

    public Array2df clamp(final float min, final float max) {
        for (int index = 0, length = array.length; index < length; index++) {
            final float value = array[index];
            array[index] = value > max ? max : Math.max(value, min);
        }
        return this;
    }

    public Array2df replace(final float value, final float withValue) {
        for (int index = 0, length = array.length; index < length; index++) {
            if (Float.compare(array[index], value) == 0) {
                array[index] = withValue;
            }
        }
        return this;
    }

    public Array2df increment() {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index]++;
        }
        return this;
    }

    public Array2df decrement() {
        for (int index = 0, length = array.length; index < length; index++) {
            array[index]--;
        }
        return this;
    }

    public Array2df copy() {
        final float[] copy = new float[array.length];
        System.arraycopy(array, 0, copy, 0, copy.length);
        return new Array2df(copy, width, height);
    }

    // Getter

    public float[] getArray() {
        return array;
    }

    // Inherit methods

    @Override
    public void set(int x, int y, Float v) {
        set(x, y, v.floatValue());
    }

    @Override
    public void set(int i, Float v) {
        set(i, v.floatValue());
    }

    @Override
    public Float getValue(int x, int y) {
        return get(x, y);
    }

    @Override
    public Float getValue(int i) {
        return get(i);
    }

    @Override
    public boolean equals(final Object object) {
        return object == this || object instanceof Array2df && ((Array2df) object).width == width // If width is equal
                && Arrays.equals(((Array2df) object).array, array); // and arrays are the same size, height is equal.
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
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
