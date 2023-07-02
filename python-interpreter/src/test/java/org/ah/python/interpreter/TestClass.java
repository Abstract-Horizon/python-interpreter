package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
        contextIsEmpty();
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
        contextIsEmpty();
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
        contextIsEmpty();
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
        contextIsEmpty();
    }

    @Test
    public void canInvokeConstructorWithParameters() {
        executeLines(
            "class Cls():",
            "    def __init__(self, x):",
            "        print(\"Constructor invoked, x=\" + str(x))",
            "        self._x = x",
            "    def x(self):",
            "        print(\"x=\" + str(self._x))",
            "",
            "c = Cls(5)",
            "c.x()"
        );

        assertEquals("Constructor invoked, x=5\nx=5\n", result());
        contextIsEmpty();
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
        contextIsEmpty();
    }


    @Test
    public void canAssignNewAttribute() {
        executeLines(
            "class Cls():",
            "    pass",
            "",
            "c = Cls()",
            "c.x = 5",
            "print(\"x=\" + str(c.x))"
        );

        assertEquals("x=5\n", result());
        contextIsEmpty();
    }}
