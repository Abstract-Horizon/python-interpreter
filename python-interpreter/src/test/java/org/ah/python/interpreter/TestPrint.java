package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.ah.python.modules.BuiltInFunctions;
import org.junit.Test;

public class TestPrint extends BaseTestClass {

//    @Test public void testPrint() {
//        PythonObject module = Interpreter.convertLines(
//                "print()"
//              );
//
//        module.__call__();
//
//        assertEquals("\n", result());
//    }

    @Test public void testPrint() {
        Block block = new Block();
        block.getStatements().add(
                new Call(BuiltInFunctions.getFunction("print"), Arrays.<PythonObject>asList(PythonString.valueOf("Result.")))
        );

        context.pushPC(block);


        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals("Result.\n", result());
        contextIsEmpty();
    }
}
