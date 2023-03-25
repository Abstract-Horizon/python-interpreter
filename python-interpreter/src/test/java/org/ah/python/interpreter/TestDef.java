package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestDef extends BaseTestClass {
    
    @Test
    public void canInterpretDef() {
        PythonObject module = Interpreter.convert(
                "def x():\n"
              + "    print(\"TRUE\")\n"
              + "\n"
              + "x()\n"
              );
        
        module.__call__();
        
        assertEquals("TRUE\n", result());
    }

}
