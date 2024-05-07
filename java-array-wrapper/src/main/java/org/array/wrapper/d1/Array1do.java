package org.array.wrapper.d1;

import org.array.wrapper.CellConsumer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

// todo revisar
public abstract class Array1do<T> extends Array1d<T> implements Iterable<T> {

    protected final T[] array;

    public static <Type> Array1do<Type> newNotType(Type[] array, final int width) {
        return new Array1do<>(array, width) {
            @Override
            @SuppressWarnings("unchecked")
            protected Type[] instanceArray(int size) {
                return (Type[]) new Object[size];
            }
        };
    }

    // Constructors

    public Array1do(int width) {
        super(width);
        this.array = instanceArray(width);
    }

    public Array1do(final T[] array, final int width) {
        super(width);
        if (array.length != size()) {
            throw new IllegalArgumentException(
                    "Passed array is too small. Expected length: " + width + ", received: " + array.length);
        }
        this.array = array;
    }

    public Array1do(Array1do<T> array1do) {
        this(array1do.array, array1do.width);
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

    public T get(final int i) {
        return array[toX(i)];
    }

    public T getOrNull(final int x) {
        return getOrElse(x,null);
    }

    public T getOrElse(final int x, final T alternative) {
        if (isIndexValid(x)) {
            return get(x);
        }
        return alternative;
    }

    public void swap(final int x1, final int x2) {
        final T first = get(x1);
        set(x1, get(x2));
        set(x2, first);
    }

    public void fill(final T value) {
        Arrays.fill(this.array, value);
    }

    public void set(final int width, final T[] array) {
        validateArray(width);
        System.arraycopy(array, 0, this.array, 0, this.array.length);
    }

    public void set(final Array1do<T> array1do) {
        set(array1do.width, array1do.array);
    }

    // Copy methods

    @Override
    public Array1do<T> copy() {
        final T[] copy = instanceArray(size());
        System.arraycopy(array, 0, copy, 0, copy.length);
        return Array1do.<T>newNotType(copy, width);
    }

    // Getters

    public T[] getArray() {
        return array;
    }

    public Object[] getObjectArray() {
        return array;
    }

    // Inherit methods

    @Override
    public void set(final int x, final T value) {
        array[toX(x)] = value;
    }

    @Override
    public T getValue(int x) {
        return get(x);
    }

    @Override
    public Iterator<T> iterator() {
        return new Array1do.ArrayIterator();
    }

    protected class ArrayIterator implements Iterator<T> {

        private final int size = width;

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
