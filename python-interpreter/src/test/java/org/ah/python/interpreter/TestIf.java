package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestIf extends BaseTestClass {

    @Test
    public void canInterpretIfPositive() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 5):",
                "    print(\"TRUE\")"
              );

        module.__call__(context);

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretIfNegative() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 6):",
                "    print(\"FALSE\")"
              );

        module.__call__(context);

        assertEquals("", result());
    }

    @Test
    public void canInterpretElsePositive() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 6):",
                "    print(\"FALSE\")",
                "else:",
                "    print(\"TRUE\")"
              );

        module.__call__(context);

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseNegative() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 5):",
                "    print(\"TRUE\")",
                "else:",
                "    print(\"FALSE\")"
              );

        module.__call__(context);

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseIfPositive() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 6):",
                "    print(\"FALSE\")",
                "elif (5 == 5):",
                "    print(\"TRUE\")",
                "else:",
                "    print(\"FALSE\")"
              );

        module.__call__(context);

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseIfNegative() {
        PythonObject module = Interpreter.convertLines(
                "if (5 == 6):",
                "    print(\"FALSE\")",
                "elif (5 == 7):",
                "    print(\"FALSE\")",
                "else:",
                "    print(\"TRUE\")"
              );

        module.__call__(context);

        assertEquals("TRUE\n", result());
    }

}
