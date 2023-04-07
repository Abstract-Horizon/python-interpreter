package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPrint extends BaseTestClass {

    @Test
    public void testPrint() {
        PythonObject module = Interpreter.convertLines(
                "print()"
              );

        module.__call__();

        assertEquals("\n", result());
    }
}
