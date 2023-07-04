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

    @Test
    public void testWhileBreak() {
        executeLines(
            "i = 1",
            "while True:",
            "    i += 1",
            "    if i >= 3:",
            "        break",
            "print(i)"
          );

        assertEquals("3\n", result());
        contextIsEmpty();
    }

    @Test
    public void testWhileBreakDeep() {
        executeLines(
            "i = 1",
            "while True:",
            "    i += 1",
            "    if i % 2 == 1:",
            "        if i >= 3:",
            "            break",
            "print(i)"
          );

        assertEquals("3\n", result());
        contextIsEmpty();
    }

    @Test
    public void testWhileContinue() {
        executeLines(
            "i = 0",
            "while i < 5:",
            "    i += 1",
            "    if i % 2 == 0:",
            "        continue",
            "    print(i)",
            "print('i=' + str(i))"
          );

        assertEquals("1\n3\n5\ni=5\n", result());
        contextIsEmpty();
    }

    @Test
    public void testWhileContinueDeep() {
        executeLines(
            "i = 0",
            "while i < 5:",
            "    i += 1",
            "    if i > 0:",
            "        if i % 2 == 0:",
            "            continue",
            "    print(i)",
            "print('i=' + str(i))"
          );

        assertEquals("1\n3\n5\ni=5\n", result());
        contextIsEmpty();
    }
}
