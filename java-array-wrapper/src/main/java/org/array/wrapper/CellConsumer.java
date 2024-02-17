package org.array.wrapper;

public interface CellConsumer<T> {

    boolean BREAK = true;

    boolean CONTINUE = false;

    boolean consume(Array2d<T> array, int x, int y, T value);

}
