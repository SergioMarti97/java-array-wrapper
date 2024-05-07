package org.array.wrapper.d1;

import org.array.wrapper.CellConsumer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Array1diTest {

    @Test
    void constructor() {
        Array1di array1di = new Array1di( 5);
        Random rnd = new Random();
        array1di.forEach((array1d, x, value) -> {
            array1d.set(x, rnd.nextInt(10));
            return CellConsumer.CONTINUE;
        });
        System.out.println(array1di);
        assertNotNull(array1di);
    }

}