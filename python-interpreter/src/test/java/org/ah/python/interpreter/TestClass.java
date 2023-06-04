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
            "class ClsOne():",
            "    def x(self):",
            "        print(\"x\")",
            "",
            "class ClsTwo(ClsOne):",
            "    def y(self):",
            "        print(\"y\")",
            "",
            "    def z(self):",
            "        self.x()",
            "        print(\"z\")",
            "",
            "c = ClsTwo()",
            "c.x()",
            "c.y()",
            "c.z()"
        );

        assertEquals("x\ny\nx\nz\n", result());
    }

    @Test
    public void canInterpretDefWithExtension2() {
        executeLines(
            "class ClsOne():",
            "    def x(self):",
            "        self.z()",
            "",
            "class ClsTwo(ClsOne):",
            "    def y(self):",
            "        self.x()",
            "",
            "    def z(self):",
            "        print(\"z\")",
            "",
            "c = ClsTwo()",
            "c.y()"
        );

        assertEquals("z\n", result());
    }

    @Test
    public void canInvokeConstructor() {
        executeLines(
            "class Cls():",
            "    def __init__(self):",
            "        print(\"Constructor invoked\")",
            "    def x():",
            "        print(\"x\")",
            "",
            "c = Cls()",
            "c.x()"
        );

        assertEquals("Constructor invoked\nx\n", result());
    }

    @Test
    public void canReturningSelf() {
        executeLines(
            "class Cls():",
            "    def x(self):",
            "        print(\"x\")",
            "        return self",
            "    def y():",
            "        print(\"y\")",
            "",
            "c = Cls()",
            "c.x().y()"
        );

        assertEquals("x\ny\n", result());
    }
}
