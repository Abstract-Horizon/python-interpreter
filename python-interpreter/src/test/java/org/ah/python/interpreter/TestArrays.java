package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TestArrays extends BaseTestClass {

    @Test public void testArrayManual() {
        Block block = new Block();

        Reference aReference = new Reference(null, "a");
        PythonObject expression = new PythonListGenerator(Arrays.<PythonObject>asList(PythonString.valueOf("value")), PythonList.PYTHON_LIST_CLASS);

        Assign aAssignment = new Assign(aReference, expression);

        block.getStatements().add(aAssignment);


        Reference aReference2 = new Reference(null, "a");
        Reference refAdd = new Reference(aReference2, "__getitem__");
        Call callAccess = new Call(refAdd, Arrays.<PythonObject>asList(PythonInteger.ZERO));

        Reference bReference = new Reference(null, "b");

        Assign bAssignment = new Assign(bReference, callAccess);

        block.getStatements().add(bAssignment);

        context.pushPC(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals(context.currentScope.__getattr__(context, "b").asString(context), "value");

        contextIsEmpty();
    }

    @Test public void testArray() {
        executeLines(
            "a = [2, 3, 4]",
            "b = a[1]",
            "a[1] = 5",
            "c = a[1]"
        );

        PythonObject b = context.currentScope.__getattr__(context, "b");
        PythonObject c = context.currentScope.__getattr__(context, "c");

        System.out.println("Got b=" + b);
        System.out.println("Got c=" + c);

        assertEquals(b.asInteger(context), 3);
        assertEquals(c.asInteger(context), 5);

        contextIsEmpty();
    }
}