package org.array.wrapper.transforms;

import org.array.wrapper.Array2d;
import org.array.wrapper.Array2df;

public class Array2dfTransformer extends Array2dTransformer<Float> {

    public Array2dfTransformer() {
        super();
    }

    @Override
    protected Array2d<Float> instanceNewArray(int width, int height) {
        return new Array2df(width, height);
    }

    public void transform(Array2df in, Array2df out) {
        super.transform(in, out);
    }

}