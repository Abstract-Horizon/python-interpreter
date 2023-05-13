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
    }

    @Test
    public void complexExample() {
        executeLines(
            "p = {",
            "'list': [5, 6]",
            "}",
            "def x(p):",
            "  p['list'][0] = 0",
            "  p['list'][1] = p['list'][1] + 1.5",
            "  print(str(p['list'][0]))",
            "  print(str(p['list'][1]))",
            "x(p)"
        );

        assertEquals("0\n7.5\n", result());
    }

}
