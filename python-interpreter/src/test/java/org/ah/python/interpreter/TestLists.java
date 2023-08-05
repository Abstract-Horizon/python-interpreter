package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestLists extends BaseTestClass {

    @Test
    public void canReadAccessElements() {
        executeLines(
            "x = [5, 6]",
            "print(str(x[0]))",
            "print(str(x[1]))"
        );
        assertEquals("5\n6\n", result());
        contextIsEmpty();
    }

    @Test
    public void canWritedAccessElements() {
        executeLines(
            "x = [5, 6]",
            "x[1] = 7",
            "print(str(x[0]))",
            "print(str(x[1]))"
        );

        assertEquals("5\n7\n", result());
        contextIsEmpty();
    }

    @Test
    public void canReadAndWritedAccessElements() {
        executeLines(
            "x = [5, 6]",
            "x[1] = x[1] + 3",
            "print(str(x[0]))",
            "print(str(x[1]))"
        );

        assertEquals("5\n9\n", result());
        contextIsEmpty();
    }

//    @Test
//    public void testLstComprehension() {
//        executeLines(
//            "l = [i for i in (1, 2, 3)]",
//            "print(str(l))"
//        );
//        assertEquals("[1, 2, 3, 4]\n", result());
//        contextIsEmpty();
//    }
}
