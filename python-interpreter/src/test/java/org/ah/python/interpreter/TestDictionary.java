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
        contextIsEmpty();
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
        contextIsEmpty();
    }


    @Test
    public void assignToDict() {
        executeLines(
            "p = {1: \"A\", 2: \"B\"}",
            "p[3] = 'C'",
            "print(p[1] + ',' + p[2] + ',' + p[3])"
        );

        assertEquals("A,B,C\n", result());
        contextIsEmpty();
    }


    @Test
    public void assignInPlaceToDict() {
        executeLines(
            "p = {'A': 1, 'B': 2}",
            "p['A'] += 3",
            "print(str(p['A']) + ',' + str(p['B']))"
        );

        assertEquals("4,2\n", result());
        contextIsEmpty();
    }
}
