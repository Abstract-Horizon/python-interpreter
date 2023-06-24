package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestReturn extends BaseTestClass {

    @Test public void testReturn() {
        executeLines(
            "def x(a):",
            "    return a + 1",
            "a = x(5)",
            "print(a)"
        );

        assertEquals(module.getAttribute("a").asInteger(), 6);
        contextIsEmpty();
    }

    @Test public void testReturnStopsFunction() {
        executeLines(
            "def x(a):",
            "    if a == 1:",
            "        return a + 1",
            "    a = a + 3",
            "    return a",
            "a = x(1)",
            "print(a)"
        );

        assertEquals("2\n", result());
        assertEquals(module.getAttribute("a").asInteger(), 2);
        contextIsEmpty();
    }

    @Test public void testReturnWithAndWithoutExplicitReturn() {
        executeLines(
            "def x(a):",
            "    return a + 1",
            "a = x(5)",
            "print(a)"
        );

        assertEquals(module.getAttribute("a").asInteger(), 6);
        contextIsEmpty();
    }

}
