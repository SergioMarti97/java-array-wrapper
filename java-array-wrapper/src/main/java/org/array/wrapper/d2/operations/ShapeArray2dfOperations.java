package org.array.wrapper.d2.operations;

import org.array.wrapper.d2.Array2df;

public class ShapeArray2dfOperations extends ShapeArray2dOperations<Float, Array2df> {

    public ShapeArray2dfOperations(Array2df array) {
        super(array);
    }

    public ShapeArray2dfOperations(final int width, final int height) {
        this(new Array2df(width, height));
    }

}
