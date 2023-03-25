package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestLists extends BaseTestClass {

    @Test
    public void canReadAccessElements() {
        PythonObject module = Interpreter.convert(
                "x=[5,6]\n"
              + "print(str(x[0]))\n"
              + "print(str(x[1]))\n"
              );
        
        module.__call__();
        
        assertEquals("5\n6\n", result());
    }

    @Test
    public void canWritedAccessElements() {
        PythonObject module = Interpreter.convert(
                "x = [5, 6]\n"
              + "x[1] = 7\n"
              + "print(str(x[0]))\n"
              + "print(str(x[1]))\n"
              );
        
        module.__call__();
        
        assertEquals("5\n7\n", result());
    }

    @Test
    public void canReadAndWritedAccessElements() {
        PythonObject module = Interpreter.convert(
                "x = [5, 6]\n"
              + "x[1] = x[1] + 3\n"
              + "print(str(x[0]))\n"
              + "print(str(x[1]))\n"
              );
        
        module.__call__();
        
        assertEquals("5\n9\n", result());
    }

    @Test
    public void complexExample() {
        PythonObject module = Interpreter.convert(
                "p = {\n"
              + "'list': [5, 6]\n"
              + "}\n"
              + "def x(p):\n"
              + "  p['list'][0] = 0\n"
              + "  p['list'][1] = p['list'][1] + 1.5\n"
              + "  print(str(p['list'][0]))\n"
              + "  print(str(p['list'][1]))\n"
              + "x(p)\n"
              );
        
        module.__call__();
        
        assertEquals("0\n7.5\n", result());
    }
    
}
