package org.array.wrapper.transforms;

import org.array.wrapper.Array2d;
import org.array.wrapper.Array2di;

public class Array2diTransformer extends Array2dTransformer<Integer> {

    public Array2diTransformer() {
        super();
    }

    @Override
    protected Array2d<Integer> instanceNewArray(int width, int height) {
        return new Array2di(0x00000000, width, height);
    }

    public void transform(Array2di in, Array2di out) {
        super.transform(in, out);
    }

}