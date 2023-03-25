package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestIf extends BaseTestClass {

    @Test
    public void canInterpretIfPositive() {
        PythonObject module = Interpreter.convert(
                "if (5 == 5):\n"
              + "    print(\"TRUE\")\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretIfNegative() {
        PythonObject module = Interpreter.convert(
                "if (5 == 6):\n"
              + "    print(\"FALSE\")\n"
              );
        
        module.__call__();
        
        assertEquals("", result());
    }

    @Test
    public void canInterpretElsePositive() {
        PythonObject module = Interpreter.convert(
                "if (5 == 6):\n"
              + "    print(\"FALSE\")\n"
              + "else:\n"
              + "    print(\"TRUE\")\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseNegative() {
        PythonObject module = Interpreter.convert(
                "if (5 == 5):\n"
              + "    print(\"TRUE\")\n"
              + "else:\n"
              + "    print(\"FALSE\")\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseIfPositive() {
        PythonObject module = Interpreter.convert(
                "if (5 == 6):\n"
              + "    print(\"FALSE\")\n"
              + "elif (5 == 5):\n"
              + "    print(\"TRUE\")\n"
              + "else:\n"
              + "    print(\"FALSE\")\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }

    @Test
    public void canInterpretElseIfNegative() {
        PythonObject module = Interpreter.convert(
                "if (5 == 6):\n"
              + "    print(\"FALSE\")\n"
              + "elif (5 == 7):\n"
              + "    print(\"FALSE\")\n"
              + "else:\n"
              + "    print(\"TRUE\")\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }
    
}
