package org.array.wrapper;

import org.array.wrapper.d1.Array1d;

public interface CellConsumer<T> {

    boolean BREAK = true;

    boolean CONTINUE = false;

    boolean consume(Array1d<T> array, int index, T value);

}
