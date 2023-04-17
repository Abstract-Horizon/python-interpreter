package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestAssign extends BaseTestClass {

    @Test
    public void testAssign() {
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

        assertEquals(context.currentScope.__getattr__("a").asInteger(), 5);
        assertEquals(context.currentScope.__getattr__("b").asString(), "value");
        assertEquals(context.currentScope.__getattr__("c").asInteger(), 7);
        assertEquals(context.currentScope.__getattr__("d").asInteger(), 7);
        assertEquals(context.currentScope.__getattr__("e").asInteger(), 15);
        assertEquals(context.currentScope.__getattr__("f").asInteger(), 15);
        assertEquals(context.currentScope.__getattr__("g").asInteger(), 16);

        assertEquals("7\n", result());

        contextIsEmpty();
    }

    @Test public void testAssignParsed2() {
        executeLines(
            "a = 5",
            "a = a + 1",
            "print(a)"
        );

        assertEquals(context.currentScope.__getattr__("a").asInteger(), 6);
        assertEquals("6\n", result());
        contextIsEmpty();
    }
}
