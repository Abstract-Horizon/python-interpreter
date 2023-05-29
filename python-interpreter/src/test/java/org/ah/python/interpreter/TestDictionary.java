package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDictionary extends BaseTestClass {


    @Test
    public void simpleExample() {
        executeLines(
            "p = {1: \"A\", 2: \"B\"}",
            "",
            "print(p[2])"
        );

        assertEquals("B\n", result());
    }


    @Test
    public void complexExample() {
        executeLines(
            "p = {",
            "    'list': [5, 6]",
            "}",
            "",
            "def x(p):",
            "  p['list'][0] = 0",
            "  p['list'][1] = p['list'][1] + 1.5",
            "  print(str(p['list'][0]))",
            "  print(str(p['list'][1]))",
            "",
            "x(p)"
        );

        assertEquals("0\n7.5\n", result());
    }

}
