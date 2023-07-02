package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestWhile extends BaseTestClass {

    @Test
    public void canWhile() {
        executeLines(
            "i = 1",
            "print(\"start\")",
            "while (i != 3):",
            "    print(i)",
            "    i = i + 1",
            "print(i)"
          );

        assertEquals("start\n1\n2\n3\n", result());
        contextIsEmpty();
    }

    @Test
    public void canWhileElse() {
        executeLines(
            "i = 1",
            "print(\"start\")",
            "while (i != 3):",
            "    print(i)",
            "    i = i + 1",
            "else:",
            "    print(\"else\")",
            "    print(i)",
            "print(i)"
          );

        assertEquals("start\n1\n2\nelse\n3\n3\n", result());
        contextIsEmpty();
    }

    @Test
    public void canNestedWhile() {
        executeLines(
            "i = 1",
            "run = True",
            "while run:",
            "    while i % 4 != 0:",
            "        i = i + 1",
            "    i = i + 1",
            "    if i >= 9:",
            "        run = False",
            "print(i)"
          );

        assertEquals("9\n", result());
        contextIsEmpty();
    }
}
