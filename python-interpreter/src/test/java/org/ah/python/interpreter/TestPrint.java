package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPrint extends BaseTestClass {

    @Test public void testPrint1() {
        executeLines(
            "print(1)"
        );

        assertEquals("1\n", result());
        contextIsEmpty();
    }

    @Test public void testPrint2() {
        executeLines(
            "a = 1",
            "print(a)"
        );

        assertEquals("1\n", result());
        contextIsEmpty();
    }

//    @Test public void testPrint() {
//        Block block = new Block();
//        block.addStatement(
//            new Call(BuiltInFunctions.getFunction("print"), PythonString.valueOf("Result.")),
//            1
//        );
//
//        context.continuation(block);
//
//
//        for (int i = 0; i < 10000 && context.next(); i++) { }
//
//        assertEquals("Result.\n", result());
//        contextIsEmpty();
//    }
}
