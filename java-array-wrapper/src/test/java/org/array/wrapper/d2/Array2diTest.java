package org.array.wrapper.d2;

import org.array.wrapper.CellConsumer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Array2diTest {

    Random rnd = new Random();

    Array2di instance(final int width, final int height) {
        var array2di = new Array2di( width, height);
        array2di.forEach((array2d, x, value) -> {
            array2d.set(x, rnd.nextInt(10));
            return CellConsumer.CONTINUE;
        });
        return array2di;
    }

    @Test
    void constructor() {
        var array2di = instance(5, 2);
        System.out.println(array2di);
        assertNotNull(array2di);
    }

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

    @Test
    void getRow() {
        var array2di = instance(4, 5);
        final int row = rnd.nextInt(array2di.getHeight() - 1);
        var array1di = array2di.getRow(row);
        System.out.println("Array2di\n" + array2di + "row " + row + "\n" + array1di + '\n');
        assertNotNull(array1di);
    }

    @Test
    void getColumn() {
        var array2di = instance(5, 2);
        final int col = rnd.nextInt(array2di.getWidth() - 1);
        var array1di = array2di.getColumn(col);
        System.out.println("Array2di\n" + array2di + "column " + col + "\n" + array1di + '\n');
        assertNotNull(array1di);
    }

}