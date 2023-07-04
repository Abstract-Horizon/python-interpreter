package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestExpression extends BaseTestClass {

//    @Test public void testSimpleIntegerAdd() {
//
//        PythonObject two = PythonInteger.valueOf(2);
//        PythonObject three = PythonInteger.valueOf(3);
//
//        Reference refAdd = new Reference(three, "__add__");
//        Call callAdd = new Call(refAdd, two);
//
//        context.continuation(callAdd);
//
//        for (int i = 0; i < 10000 && context.next(); i++) {}
//
//        assertEquals(((PythonNumber)context.a).asInteger(), 5);
//        contextIsEmpty();
//    }

//    @Test public void testSimpleIntegerAddSub() {
//
//        PythonObject one = PythonInteger.valueOf(1);
//        PythonObject two = PythonInteger.valueOf(2);
//        PythonObject three = PythonInteger.valueOf(3);
//
//        Reference refAdd = new Reference(three, "__add__");
//        Call callAdd = new Call(refAdd, two);
//
//        Reference refSub = new Reference(callAdd, "__sub__");
//        Call callSub = new Call(refSub, one);
//
//        context.continuation(callSub);
//
//        for (int i = 0; i < 10000 && context.next(); i++) {}
//
//        assertEquals(((PythonNumber)context.a).asInteger(), 4);
////        assertTrue(context.a.__eq__(context, PythonInteger.valueOf(4)).asBoolean(context));
//
//        contextIsEmpty();
//    }

//    @Test public void testExpressionAddingStrings() {
//
//        PythonObject one = PythonString.valueOf("123");
//        PythonObject two = PythonString.valueOf("456");
//
//        Reference refAdd = new Reference(one, "__add__");
//        Call callAdd = new Call(refAdd, two);
//
//        context.continuation(callAdd);
//
//        for (int i = 0; i < 5 && context.next(); i++) { }
//
//        assertEquals(context.a.asString(), "123456");
//
//        contextIsEmpty();
//    }

    @Test public void testExpressionFromParsedCode() {
        executeLines(
            "a = 5",
            "a = a.__add__(6)",
            "a = a + 1",
            "print(a)"
        );

        assertEquals(((PythonNumber)module.getattr(context, "a")).asInteger(), 12);
        assertEquals("12\n", result());
        contextIsEmpty();
    }

    @Test public void testComparison() {
        executeLines(
            "d = 1 == 2",
            "print(d)"
        );

        //assertEquals(((PythonBoolean)module.getattr(context, "d")).asBoolean(), true);
        assertEquals("False\n", result());
        contextIsEmpty();
    }

    @Test public void testComparison2() {
        executeLines(
            "a = 5",
            "b = 6",
            "c = a == b",
            "d = a == a",
            "print(c)",
            "print(d)"
        );

        assertEquals(((PythonBoolean)module.getattr(context, "c")).asBoolean(), false);
        assertEquals(((PythonBoolean)module.getattr(context, "d")).asBoolean(), true);
        assertEquals("False\nTrue\n", result());
        contextIsEmpty();
    }

    @Test public void testAnd() {
        executeLines(
            "print(False and False)",
            "print(True and False)",
            "print(False and True)",
            "print(True and True)",
            "print(\"\")"
        );

        assertEquals("False\nFalse\nFalse\nTrue\n\n", result());
        contextIsEmpty();
    }

    @Test public void testOr() {
        executeLines(
            "print(False or False)",
            "print(True or False)",
            "print(False or True)",
            "print(True or True)",
            "print(\"\")"
        );

        assertEquals("False\nTrue\nTrue\nTrue\n\n", result());
        contextIsEmpty();
    }

    @Test public void testNot() {
        executeLines(
            "print(not False)",
            "print(not True)",
            "print(not (False or True))",
            "print(not (True and False))",
            "print(\"\")"
        );

        assertEquals("True\nFalse\nFalse\nTrue\n\n", result());
        contextIsEmpty();
    }

    @Test public void testAddingExpression() {
        executeLines(
            "print(1 + 2 + 3)"
        );

        assertEquals("6\n", result());
        contextIsEmpty();
    }

    @Test public void testDivExpression() {
        executeLines(
            "print(1 / 3)"
        );

        assertEquals("0.3333333333333333\n", result());
        contextIsEmpty();
    }

    @Test public void testAddingStrings() {
        executeLines(
            "print('one, ' + str(2))"
        );

        assertEquals("one, 2\n", result());
        contextIsEmpty();
    }

    @Test public void testAddingMoreStrings() {
        executeLines(
            "a = 'four'",
            "print('one, ' + str(2) + ', three, ' + a)"
        );

        assertEquals("one, 2, three, four\n", result());
        contextIsEmpty();
    }

    @Test public void testAddingMoreStringsThroughVar() {
        executeLines(
            "a = 'four'",
            "r = 'one, ' + str(2) + ', three, ' + a",
            "print(r)"
        );

        assertEquals("one, 2, three, four\n", result());
        contextIsEmpty();
    }

    @Test public void testInPlaceAdd() {
        executeLines(
            "a = 1",
            "a += 2",
            "print(a)"
        );

        assertEquals("3\n", result());
        contextIsEmpty();
    }

}
