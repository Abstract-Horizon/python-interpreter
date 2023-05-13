package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestIf extends BaseTestClass {

    @Test
    public void canInterpretIfPositive() {
        executeLines(
            "if (5 == 5):",
            "    print(\"TRUE\")"
          );

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretIfNegative() {
        executeLines(
            "if (5 == 6):",
            "    print(\"FALSE\")"
        );

        assertEquals("", result());
    }

    @Test
    public void canInterpretElsePositive() {
        executeLines(
            "if (5 == 6):",
            "    print(\"FALSE\")",
            "else:",
            "    print(\"TRUE\")"
        );

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseNegative() {
        executeLines(
            "if (5 == 5):",
            "    print(\"TRUE\")",
            "else:",
            "    print(\"FALSE\")"
        );

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseIfPositive() {
        executeLines(
            "if (5 == 6):",
            "    print(\"1\")",
            "elif (5 == 5):",
            "    print(\"2\")",
            "else:",
            "    print(\"3\")"
        );

        assertEquals("2\n", result());
    }

    @Test
    public void canInterpretElseIfNegative() {
        executeLines(
            "if (5 == 6):",
            "    print(\"1\")",
            "elif (5 == 7):",
            "    print(\"2\")",
            "else:",
            "    print(\"3\")"
        );

        assertEquals("3\n", result());
    }

}
