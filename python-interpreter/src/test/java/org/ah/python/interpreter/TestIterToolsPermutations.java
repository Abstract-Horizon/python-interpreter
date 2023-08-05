package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestIterToolsPermutations  extends BaseTestClass {

    @Test
    public void canReadAccessElements() {
        executeLines(
            "from itertools import permutations",
            "perms = permutations((-1, 0, 1), 2)",
            "print(str(list(perms)))"
        );
        assertEquals("[(-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0)]\n", result());
        contextIsEmpty();
    }

}
