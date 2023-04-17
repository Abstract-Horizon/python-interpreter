package org.ah.python.interpreter;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestExpression extends BaseTestClass {

    @Test public void testSimpleIntegerAdd() {

        PythonObject two = PythonInteger.valueOf(2);
        PythonObject three = PythonInteger.valueOf(3);

        Reference refAdd = new Reference(three, "__add__");
        Call callAdd = new Call(refAdd, asList(two));

        context.pushPC(callAdd);

        for (int i = 0; i < 10000 && context.next(); i++) {}

        assertEquals(context.a.asInteger(), 5);
        contextIsEmpty();
    }

    @Test public void testSimpleIntegerAddSub() {

        PythonObject one = PythonInteger.valueOf(1);
        PythonObject two = PythonInteger.valueOf(2);
        PythonObject three = PythonInteger.valueOf(3);

        Reference refAdd = new Reference(three, "__add__");
        Call callAdd = new Call(refAdd, asList(two));

        Reference refSub = new Reference(callAdd, "__sub__");
        Call callSub = new Call(refSub, asList(one));

        context.pushPC(callSub);

        for (int i = 0; i < 10000 && context.next(); i++) {}

        assertEquals(context.a.asInteger(), 4);
        assertTrue(context.a.__eq__(PythonInteger.valueOf(4)).asBoolean());

        contextIsEmpty();
    }

    @Test public void testExpressionAddingStrings() {

        PythonObject one = PythonString.valueOf("123");
        PythonObject two = PythonString.valueOf("456");

        Reference refAdd = new Reference(one, "__add__");
        Call callAdd = new Call(refAdd, asList(two));

        context.pushPC(callAdd);

        for (int i = 0; i < 5 && context.next(); i++) { }

        assertEquals(context.a.asString(), "123456");

        contextIsEmpty();
    }

    @Test public void testExpressionFromParsedCode() {
        executeLines(
            "a = 5",
            "a = a.__add__(6)",
            "a = a + 1",
            "print(a)"
        );

        assertEquals(module.__getattr__("a").asInteger(), 12);
        assertEquals("12\n", result());
        contextIsEmpty();
    }

    @Test public void testComparison() {
        executeLines(
            "a = 5",
            "b = 6",
            "c = a == b",
            "d = a == a",
            "print(c)",
            "print(d)"
        );

        assertEquals(module.__getattr__("c").asBoolean(), false);
        assertEquals(module.__getattr__("d").asBoolean(), true);
        assertEquals("False\nTrue\n", result());
        contextIsEmpty();
    }

}
