package org.ah.python.interpreter;

import org.junit.Test;

public class TestMath extends BaseTestClass {

    @Test
    public void testCos() {
        executeLines(
            "import math",
            "print(math.cos(1))"
        ).assertResult(Math.cos(1.0) + "\n");
    }

    @Test
    public void testSin() {
        executeLines(
            "import math",
            "print(math.sin(1))"
        ).assertResult(Math.sin(1.0) + "\n");
    }

    @Test
    public void testTan() {
        executeLines(
            "import math",
            "print(math.tan(1))"
        ).assertResult(Math.tan(1.0) + "\n");
    }

    @Test
    public void testAcos() {
        executeLines(
            "import math",
            "print(math.acos(1))"
        ).assertResult(Math.acos(1.0) + "\n");
    }

    @Test
    public void testAsin() {
        executeLines(
            "import math",
            "print(math.asin(1))"
        ).assertResult(Math.asin(1.0) + "\n");
    }

    @Test
    public void testAtan() {
        executeLines(
            "import math",
            "print(math.atan(1))"
        ).assertResult(Math.atan(1.0) + "\n");
    }

}
