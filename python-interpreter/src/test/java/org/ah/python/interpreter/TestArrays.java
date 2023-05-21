package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TestArrays extends BaseTestClass {

    @Test public void testArrayManual() {
        Block block = new Block();

        Reference aReference = new Reference(null, "a");
        PythonObject expression = new PythonListGenerator(Arrays.<PythonObject>asList(PythonString.valueOf("value")), PythonList.PYTHON_LIST_CLASS);

        Assign aAssignment = new Assign(aReference, expression, true);

        block.getStatements().add(aAssignment);


        Reference aReference2 = new Reference(null, "a");
        Reference refAdd = new Reference(aReference2, "__getitem__");
        Call callAccess = new Call(refAdd, PythonInteger.ZERO);

        Reference bReference = new Reference(null, "b");

        Assign bAssignment = new Assign(bReference, callAccess, true);
        bAssignment.setLastInstruction(true);

        block.getStatements().add(bAssignment);

        context.continuation(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals(context.currentScope.getAttribute("b").asString(), "value");

        contextIsEmpty();
    }

    @Test public void testArray() {
        executeLines(
            "a = [2, 3, 4]",
            "b = a[1]",
            "a[1] = 5",
            "c = a[1]"
        );

        PythonObject b = context.currentScope.getAttribute("b");
        PythonObject c = context.currentScope.getAttribute("c");

        System.out.println("Got b=" + b);
        System.out.println("Got c=" + c);

        assertEquals(b.asInteger(), 3);
        assertEquals(c.asInteger(), 5);

        contextIsEmpty();
    }
}
