package org.array.wrapper.operations;

import org.array.wrapper.Array2di;

public class ShapeArray2diOperations extends ShapeArrayOperations<Integer, Array2di> {

    //todo processing alpha on set method

    private boolean isProcessingAlpha = true;

    public ShapeArray2diOperations(Array2di array) {
        super(array);
    }

    public ShapeArray2diOperations(Array2di array, boolean isProcessingAlpha) {
        super(array);
        this.isProcessingAlpha = isProcessingAlpha;
    }

    @Override
    protected void set(int x, int y, Integer v) {
        if (isProcessingAlpha) {
            int a = ((v >> 24) & 0xff);
            if (!array.isIndexValid(x, y) || a == 0) { // v == 0xffff00ff
                return;
            }
            if (a == 255) {
                array.set(x, y, v);
            } else {
                int pc = array.get(x, y);
                int nr = ((pc >> 16) & 0xff) - (int)((((pc >> 16) & 0xff) - ((v >> 16) & 0xff)) * (a / 255.0f));
                int ng = ((pc >> 8) & 0xff) - (int)((((pc >> 8) & 0xff) - ((v >> 8) & 0xff)) * (a / 255.0f));
                int nb = (pc & 0xff) - (int)(((pc & 0xff) - (v & 0xff)) * (a / 255.0f));
                int nc = (nr << 16 | ng << 8 | nb); //(255 << 24 | nr << 16 | ng << 8 | nb)
                array.set(x, y, nc);
            }
        } else {
            super.set(x, y, v);
        }
    }

    public boolean isProcessingAlpha() {
        return isProcessingAlpha;
    }

    public void setProcessingAlpha(boolean processingAlpha) {
        isProcessingAlpha = processingAlpha;
    }

}
