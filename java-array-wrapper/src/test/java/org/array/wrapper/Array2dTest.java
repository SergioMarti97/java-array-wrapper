package org.array.wrapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Array2dTest {

    @Test
    void toX() {
        Array2di array2di = new Array2di(3, 3); // 3 x 3 grid
        assertEquals(0, array2di.toX(6));
    }

    @Test
    void toY() {
        Array2di array2di = new Array2di(3, 3); // 3 x 3 grid
        assertEquals(2, array2di.toY(6));
    }

}