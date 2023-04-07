package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestClass extends BaseTestClass {

    @Test
    public void canInterpretDef() {
        PythonObject module = Interpreter.convert(
                "class Cls:\n"
              + "    def x(self):\n"
              + "        print(\"TRUE\")\n"
              + "\n"
              + "c = Cls()\n"
              + "c.x()\n"
              );

        module.__call__();

        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretDefWithExtension() {
        PythonObject module = Interpreter.convert(
                "class ClsOne:\n"
              + "    def x(self):\n"
              + "        print(\"x\")\n"
              + "\n"
              + "class ClsTwo(ClsOne):\n"
              + "    def y(self):\n"
              + "        print(\"y\")\n"
              + "\n"
              + "c = ClsTwo()\n"
              + "c.x()\n"
              + "c.y()\n"
              );

        module.__call__();

        assertEquals("x\ny\n", result());
    }

    @Test
    public void canInvokeConstructor() {
        PythonObject module = Interpreter.convert(
                "class Cls:\n"
              + "    def __init__(self):\n"
              + "        print(\"Constructor invoked\")\n"
              + "\n"
              + "c = Cls()\n"
              );

        module.__call__();

        assertEquals("Constructor invoked\n", result());
    }
}
