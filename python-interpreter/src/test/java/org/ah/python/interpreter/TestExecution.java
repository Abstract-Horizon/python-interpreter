package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.ah.python.modules.BuiltInFunctions;
import org.junit.Test;


public class TestExecution extends BaseTestClass {

    public void internallyTestBlock(int j) {
        Block block = new Block();

        StringBuilder b = new StringBuilder();
        for (int k = 0; k < j; k++) {
            block.getStatements().add(new Call(BuiltInFunctions.getFunction("print"), Arrays.<PythonObject>asList(PythonString.valueOf("" + k))));
            b.append(k + "\n");
        }

        context.pushPC(block);


        for (int i = 0; i < 10000 && context.next(); i++) {

        }

        // System.out.println("Testing for j=" + j + "; string.len=" + b.length());
        assertEquals(b.toString(), result());

        contextIsEmpty();
    }

    @Test public void testBlockOne() { internallyTestBlock(1); }
    @Test public void testBlockTwo() { internallyTestBlock(2); }
    @Test public void testBlockThree() { internallyTestBlock(3); }
    @Test public void testBlockFour() { internallyTestBlock(4); }
    @Test public void testBlockFive() { internallyTestBlock(5); }
    @Test public void testBlockSix() { internallyTestBlock(5); }

}
