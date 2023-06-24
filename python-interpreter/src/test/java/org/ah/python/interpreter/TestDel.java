package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDel extends BaseTestClass {

    @Test public void testAssignParsed() {
        executeLines(
            "a = [1, 2, 3]",
            "del a[1]",
            "print(a)"
        );

        assertEquals("[1, 3]\n", result());

        contextIsEmpty();
    }
}
