package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestAssign extends BaseTestClass {

    @Test  public void testAssign() {
        Block block = new Block();

        Reference reference = new Reference(null, "a");
        PythonObject expression = PythonString.valueOf("value");

        Assign assignment = new Assign(reference, expression);

        block.getStatements().add(assignment);

        context.pushPC(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals(context.currentScope.__getattr__("a").asString(), "value");

        contextIsEmpty();
    }
}
