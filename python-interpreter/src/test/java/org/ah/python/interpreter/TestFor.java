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
}
