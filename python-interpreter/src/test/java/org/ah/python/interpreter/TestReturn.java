package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestReturn extends BaseTestClass {

    @Test public void testExpressionFromParsedCode() {
        executeLines(
            "def x(a):",
            "    return a + 1",
            "a = x(5)",
            "print(a)"
        );

        System.out.println(result());
        assertEquals(module.getAttribute("a").asInteger(), 6);
        contextIsEmpty();
    }

}
