package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestClass extends BaseTestClass {

    @Test
    public void canInterpretDef() {
        executeLines(
            "class Cls:",
            "    def x(self):",
            "        print(\"TRUE\")",
            "",
            "c = Cls()",
            "c.x()"
        );

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretDefWithExtension() {
        executeLines(
            "class ClsOne:",
            "    def x(self):",
            "        print(\"x\")",
            "",
            "class ClsTwo(ClsOne):",
            "    def y(self):",
            "        print(\"y\")",
            "",
            "c = ClsTwo()",
            "c.x()",
            "c.y()"
        );

        assertEquals("x\ny\n", result());
    }

    @Test
    public void canInvokeConstructor() {
        executeLines(
            "class Cls:",
            "    def __init__(self):",
            "        print(\"Constructor invoked\")",
            "",
            "c = Cls()"
        );

        assertEquals("Constructor invoked\n", result());
    }
}
