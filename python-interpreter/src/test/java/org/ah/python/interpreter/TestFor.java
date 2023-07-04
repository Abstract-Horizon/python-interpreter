package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestFor extends BaseTestClass {

    @Test
    public void canFor() {
        executeLines(
            "print(\"start\")",
            "for i in range(3):",
            "    print(i)",
            "print(\"end\")"
        );

        assertEquals("start\n0\n1\n2\nend\n", result());
        contextIsEmpty();
    }

    @Test
    public void canForOverList() {
        executeLines(
            "l = [1, 2, 3, 4]",
            "for i in l:",
            "    print(i)",
            "print(\"End\")"
        );

        assertEquals("1\n2\n3\n4\nEnd\n", result());
        contextIsEmpty();
    }


    @Test
    public void testForBreak() {
        executeLines(
            "for i in range(5):",
            "    i += 1",
            "    if i >= 3:",
            "        break",
            "print(i)"
          );

        assertEquals("3\n", result());
        contextIsEmpty();
    }

    @Test
    public void testForBreakDeep() {
        executeLines(
            "for i in range(5):",
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
    public void testForContinue() {
        executeLines(
            "for i in range(5):",
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
    public void testForContinueDeep() {
        executeLines(
            "for i in range(5):",
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
