package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.ah.python.modules.BuiltInFunctions;
import org.junit.Test;


public class TestExecution extends BaseTestClass {

    public void internallyTestBlock(int j) {
        Block block = new Block();

        StringBuilder b = new StringBuilder();
        for (int k = 0; k < j; k++) {
            block.addStatement(new Call(BuiltInFunctions.getFunction("print"), PythonString.valueOf("" + k)), k + 1);
            b.append(k + "\n");
        }

        context.continuation(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

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
