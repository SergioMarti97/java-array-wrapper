package org.array.wrapper.d1;

public interface CellConsumer1d<T> {

    boolean BREAK = true;

    boolean CONTINUE = false;

    boolean consume(Array1d<T> array1d, int x, T value);

}
