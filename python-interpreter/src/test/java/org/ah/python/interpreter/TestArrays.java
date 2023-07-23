package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.ah.python.interpreter.ThreadContext.Executable;
import org.junit.Test;

public class TestArrays extends BaseTestClass {

    @Test public void testArrayManual() {
        Block block = new Block();

        Reference aReference = new Reference(null, "a");
        PythonObject expression = new PythonListGenerator(Arrays.<Executable>asList(PythonString.valueOf("value")), PythonList.PYTHON_LIST_CLASS);

        Assign aAssignment = new Assign(aReference, expression, true);

        block.addStatement(aAssignment, 1, "test.py");


        Reference aReference2 = new Reference(null, "a");
        Reference refAdd = new Reference(aReference2, "__getitem__");
        Call callAccess = new Call(refAdd, PythonInteger.ZERO);

        Reference bReference = new Reference(null, "b");

        Assign bAssignment = new Assign(bReference, callAccess, true);
        bAssignment.setLastInstruction(true);

        block.addStatement(bAssignment, 2, "test.py");

        context.continuation(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals(context.currentScope.getAttribute("b").asString(), "value");

        contextIsEmpty();
    }

    @Test public void testArray() {
        executeLines(
            "a = [2, 3, 4]",
            "b1 = a[0]",
            "b2 = a[1]",
            "b3 = a[2]",
            "a[1] = 5",
            "c = a[1]"
        );

        PythonObject c = context.currentScope.getAttribute("c");

        assertEquals(5, c.asInteger());

        contextIsEmpty();
    }
}
