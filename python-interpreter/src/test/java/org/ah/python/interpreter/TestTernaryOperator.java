package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestTernaryOperator extends BaseTestClass {

    @Test public void testExpressionFromParsedCode() {
        executeLines(
            "a = 5 if True else 6",
            "b = 7 if False else 8"
        );

        assertEquals(module.__getattr__(context, "a").asInteger(context), 5);
        assertEquals(module.__getattr__(context, "b").asInteger(context), 8);
        contextIsEmpty();
    }

    @Test public void testNoIfEvaluation() {
        executeLines(
            "def x(a):",
            "    print(a)",
            "    return a",
            "",
            "a = x(5) if True else x(6)"
        );

        assertEquals(module.__getattr__(context, "a").asInteger(context), 5);
        assertEquals("5\n", result());
        contextIsEmpty();
    }

    @Test public void testNoElseEvaluation() {
        executeLines(
            "def x(a):",
            "    print(a)",
            "    return a",
            "",
            "a = x(5) if False else x(6)"
        );

        assertEquals(module.__getattr__(context, "a").asInteger(context), 6);
        assertEquals("6\n", result());
        contextIsEmpty();
    }
}
