package org.array.wrapper.d3;

import org.array.wrapper.CellConsumer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Array3diTest {

    @Test
    void constructor() {
        Array3di array3di = new Array3di(5, 2, 3);
        Random rnd = new Random();
        array3di.forEach((array, index, value) -> {
            array3di.set(index, rnd.nextInt(10));
            return CellConsumer.CONTINUE;
        });
        System.out.println(array3di);
        assertNotNull(array3di);
    }

}