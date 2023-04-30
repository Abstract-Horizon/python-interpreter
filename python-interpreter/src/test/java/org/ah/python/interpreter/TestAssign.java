package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestAssign extends BaseTestClass {

    @Test public void testAssign() {
        Block block = new Block();

        Reference reference = new Reference(null, "a");
        PythonObject expression = PythonString.valueOf("value");

        Assign assignment = new Assign(reference, expression);

        block.getStatements().add(assignment);

        context.pushPC(block);

        for (int i = 0; i < 10000 && context.next(); i++) { }

        assertEquals(context.currentScope.__getattr__(context, "a").asString(context), "value");

        contextIsEmpty();
    }

    @Test public void testAssignParsed() {
        executeLines(
            "a = 5",
            "b = \"value\"",
            "c = d = 7",
            "print(c)",
            "e = 5 + 10",
            "f = e",
            "g = e + 1"
        );

        assertEquals(context.currentScope.__getattr__(context, "a").asInteger(context), 5);
        assertEquals(context.currentScope.__getattr__(context, "b").asString(context), "value");
        assertEquals(context.currentScope.__getattr__(context, "c").asInteger(context), 7);
        assertEquals(context.currentScope.__getattr__(context, "d").asInteger(context), 7);
        assertEquals(context.currentScope.__getattr__(context, "e").asInteger(context), 15);
        assertEquals(context.currentScope.__getattr__(context, "f").asInteger(context), 15);
        assertEquals(context.currentScope.__getattr__(context, "g").asInteger(context), 16);

        assertEquals("7\n", result());

        contextIsEmpty();
    }

    @Test public void testAssignParsed2() {
        executeLines(
            "a = 5",
            "a = a + 1",
            "print(a)"
        );

        assertEquals(context.currentScope.__getattr__(context, "a").asInteger(context), 6);
        assertEquals("6\n", result());
        contextIsEmpty();
    }
}
