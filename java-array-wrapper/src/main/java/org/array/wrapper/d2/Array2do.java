package org.array.wrapper.d2;

import org.array.wrapper.CellConsumer;
import org.array.wrapper.d1.Array1d;
import org.array.wrapper.d1.Array1do;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class Array2do<T> extends Array2d<T> implements Iterable<T> {

    protected final T[] array;

    public static <Type> Array2do<Type> newNotType(final int width, final int height) {
        return new Array2do<>(width, height) {
            @Override
            @SuppressWarnings("unchecked")
            protected Type[] instanceArray(final int size) {
                return (Type[]) new Object[size];
            }
        };
    }

    public static <Type> Array2do<Type> newNotType(Type[] array, final int width, final int height) {
        return new Array2do<>(array, width, height) {
            @Override
            @SuppressWarnings("unchecked")
            protected Type[] instanceArray(final int size) {
                return (Type[]) new Object[size];
            }
        };
    }

    // Constructors

    public Array2do(int width, int height) {
        super(width, height);
        this.array = instanceArray(width * height);
    }

    public Array2do(int size) {
        super(size);
        this.array = instanceArray(size);
    }

    public Array2do(final T[] array, final int width, final int height) {
        super(width, height);
        if (array.length != size()) {
            throw new IllegalArgumentException(
                    "Passed array is too small. Expected length: " + width * height + ", received: " + array.length);
        }
        this.array = array;
    }

    public Array2do(final T[] array, final int size) {
        this(array, size, size);
    }

    public Array2do(Array2do<T> array2do) {
        this(array2do.array, array2do.width, array2do.height);
    }

    // Abstract methods

    protected abstract T[] instanceArray(int size);

    // Iterative methods

    @Override
    protected void iterate(CellConsumer<T> cc, int fromIndex, int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (cc.consume(this, index, array[index])) {
                break;
            }
        }
    }

    // Methods

    public T get(final int x, final int y) {
        return array[toIndex(x, y)];
    }

    public T get(final int i) {
        return array[i];
    }

    @Override
    public void set(final int index, T value) {
        array[index] = value;
    }

    public T getOrNull(final int x, final int y) {
        return getOrElse(x, y, null);
    }

    public T getOrElse(final int x, final int y, final T alternative) {
        if (isIndexValid(x, y)) {
            return get(x, y);
        }
        return alternative;
    }

    public void swap(final int x1, final int y1, final int x2, final int y2) {
        final T first = get(x1, y1);
        set(x1, y1, get(x2, y2));
        set(x2, y2, first);
    }

    public void fill(final T value) {
        Arrays.fill(this.array, value);
    }

    public void set(final int width, final int height, final T[] array) {
        validateArray(width, height);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array2do<T> array) {
        set(array.width, array.height, array.array);
    }

    // Copy methods

    @Override
    public Array2do<T> copy() {
        final T[] copy = instanceArray(size());
        System.arraycopy(array, 0, copy, 0, copy.length);
        return Array2do.<T>newNotType(copy, width, height);
    }

    // Getters

    public T[] getArray() {
        return array;
    }

    public Object[] getObjectArray() {
        return array;
    }

    // Inherit methods: getRow and getColumn

    @Override
    public Array1d<T> getRow(int nRow) {
        return Array1do.newNotType(Arrays.copyOfRange(array, nRow, nRow + width), width);
    }

    @Override
    public Array1d<T> getColumn(int nCol) {
        @SuppressWarnings("unchecked")
        final T[] col = (T[]) new Object[height];
        for (int i = 0; i < height; i++) {
            col[i] = get(nCol, i);
        }
        return Array1do.newNotType(col, height);
    }

    // Inherit methods

    @Override
    public void set(final int x, final int y, final T value) {
        array[toIndex(x, y)] = value;
    }

    @Override
    public T getValue(int x, int y) {
        return get(x, y);
    }

    @Override
    public T getValue(int i) {
        return get(i);
    }

    @Override
    public T getSample(float x, float y) {
        return getValue((int) x, (int) y);
    }

    @Override
    public T getSample(float x) {
        return getSample(toX((int) x), toY((int) x));
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    protected class ArrayIterator implements Iterator<T> {

        private final int size = width * height;

        private int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            return array[index++];
        }

        @Override
        public void remove() {
            if (index == 0) {
                throw new IllegalStateException("#next() has to be called before using #remove() method.");
            }
            array[index - 1] = null;
        }

        public void reset() {
            index = 0;
        }

    }

}
