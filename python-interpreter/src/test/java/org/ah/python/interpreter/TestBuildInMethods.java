package org.ah.python.interpreter;

import org.junit.Test;

public class TestBuildInMethods extends BaseTestClass {

    @Test
    public void testAbs() {
        executeLines(
            "a = 2",
            "print(abs(a))",
            "a = -2",
            "print(abs(a))"
        ).assertResult("2\n2\n");
    }

    @Test
    public void testAll() {
        // TODO
    }

    @Test
    public void testAny() {
        // TODO
    }

    @Test
    public void testAscii() {
        // TODO
    }

    @Test
    public void testBin() {
        executeLines(
            "a = 17",
            "print(bin(a))"
        ).assertResult("0b10001\n");
    }

    @Test
    public void testBool() {
        executeLines(
            "a = 17",
            "print(bool(a))",
            "a = 0",
            "print(bool(a))",
            "a = -1",
            "print(bool(a))",
            "a = 17.1",
            "print(bool(a))",
            "a = 0.0",
            "print(bool(a))",
            "a = True",
            "print(bool(a))",
            "a = False",
            "print(bool(a))",
            "a = \"a\"",
            "print(bool(a))",
            "a = \"\"",
            "print(bool(a))"
        ).assertResult("True\nFalse\nTrue\n"
                + "True\nFalse\n"
                + "True\nFalse\n"
                + "True\nFalse\n");
    }

    @Test
    public void testCallable() {
        // TODO
    }

    @Test
    public void testChr() {
        executeLines(
            "a = 65",
            "print(chr(a))"
        ).assertResult("A\n");
    }

    @Test
    public void testGetAttr() {
        // TODO
    }

    @Test
    public void testHasAttr() {
        // TODO
    }

    @Test
    public void testSetAttr() {
        // TODO
    }

    @Test
    public void testDelAttr() {
        // TODO
    }

    @Test
    public void testDict() {
        // TODO
    }

    @Test
    public void testDir() {
        // TODO
    }

    @Test
    public void testDivmod() {
        // TODO
    }

    @Test
    public void testEnumerate() {
        // TODO
    }

    @Test
    public void testEval() {
        // TODO
    }

    @Test
    public void testExec() {
        // TODO
    }

    @Test
    public void testFilter() {
        // TODO
    }

    @Test
    public void testFloat() {
        executeLines(
            "a = 65",
            "print(float(a))",
            "a = \"65\"",
            "print(float(a))",
            "a = 65.0",
            "print(float(a))"
        ).assertResult("65.0\n65.0\n65.0\n");
    }

    @Test
    public void testFormat() {
        // TODO
    }

    @Test
    public void testFrozenset() {
        // TODO
    }

    @Test
    public void testGlobals() {
        // TODO
    }

    @Test
    public void testHash() {
        executeLines(
            "a = 65",
            "print(hash(a))",
            "a = \"65\"",
            "print(hash(a))",
            "a = 65.0",
            "print(hash(a))"
        ).assertResult("65\n1727\n1079001088\n");
    }

    @Test
    public void testHelp() {
        // TODO
    }

    @Test
    public void testHex() {
        executeLines(
            "a = 65",
            "print(hex(a))",
            "a = \"65\"",
            "print(hex(a))",
            "a = 65.0",
            "print(hex(a))"
        ).assertResult("0x41\n0x41\n0x41\n");
    }

    @Test
    public void testId() {
        // TODO
    }

    @Test
    public void testInt() {
        executeLines(
            "a = 65",
            "print(int(a))",
            "a = \"65\"",
            "print(int(a))",
            "a = 65.0",
            "print(int(a))"
        ).assertResult("65\n65\n65\n");
    }

    @Test
    public void testIsInstance() {
        // TODO
    }

    @Test
    public void testIsSubclass() {
        // TODO
    }

    @Test
    public void testIter() {
        executeLines(
            "class X():",
            "    def __init__(self):",
            "        self.i = 65",
            "    def __iter__(self):",
            "        self.i = 65",
            "        return self",
            "    def __next__(self):",
            "        value = self.i",
            "        if self.i < 68:",
            "            self.i = self.i + 1",
            "            return value",
            // "        raise StopIteration()",
            "",
            "x = X()",
            "print(next(iter(x)))"
        ).assertResult("65\n");
    }

    @Test
    public void testLen() {
        executeLines(
            "class X():",
            "    def __len__(self):",
            "        return 7",
            "",
            "a = [1, 2, 3]",
            "",
            "x = X()",
            "print(len(a))",
            "print(len(x))"
        ).assertResult("3\n7\n");
    }

    @Test
    public void testList() {
        executeLines(
            "l = list((1, 2, 3))",
            "print(l)"
        ).assertResult("[1, 2, 3]\n");
    }

    @Test
    public void testLocals() {
        // TODO
    }

    @Test
    public void testMap() {
        // TODO
    }

    @Test
    public void testMax() {
//        executeLines(
//                "print(max(1, 2, 3))",
//                "print(max(4, 3, 2))",
//                "print(max(4, 5, 3))"
//        ).assertResult("3\n4\n5\n");
    }

    @Test
    public void testObject() {
        // TODO
    }

    @Test
    public void testOct() {
        // TODO
    }

    @Test
    public void testOpen() {
        // TODO
    }

    @Test
    public void testOrd() {
        executeLines(
            "print(ord(\"A\"))"
        ).assertResult("65\n");
    }

    @Test
    public void testPow() {
        // TODO
    }

    @Test
    public void testProperty() {
        // TODO
    }

    @Test
    public void testRange() {
        executeLines(
            "for i in range(2):",
            "    print(i)",
            "print(\"-\")",
            "for i in range(1, 4):",
            "    print(i)",
            "print(\"-\")",
            "for i in range(1, 6, 2):",
            "    print(i)"
        ).assertResult("0\n1\n-\n1\n2\n3\n-\n1\n3\n5\n");
     }

    @Test
    public void testRepr() {
        executeLines(
            "print(repr(1))",
            "class C:",
            "    def __repr__():",
            "        return \"yes\"",
            "print(repr(C()))"
        ).assertResult("1\nyes\n");
    }

    @Test
    public void testReversed() {
        executeLines(
            "print(reversed([1, 2, 3]))",
            "class C:",
            "    def __reversed__():",
            "        return \"yes\"",
            "print(reversed(C()))"
        ).assertResult("[3, 2, 1]\nyes\n");
    }

    @Test
    public void testRound() {
        executeLines(
            "print(round(1.4))",
            "print(round(1.6))"
        ).assertResult("1.0\n2.0\n");
    }

    @Test
    public void testSet() {
        // TODO
    }

    @Test
    public void testSlice() {
        // TODO
    }

    @Test
    public void testSorted() {
        // TODO
    }

    @Test
    public void testStaticMethod() {
        // TODO
    }

    @Test
    public void testStr() {
        executeLines(
            "print(str(1))",
            "class C:",
            "    def __str__():",
            "        return \"yes\"",
            "print(str(C()))"
        ).assertResult("1\nyes\n");
    }

    @Test
    public void testSum() {
        // TODO
    }

    @Test
    public void testSuper() {
        // TODO
    }

    @Test
    public void testTuple() {
        executeLines(
            "l = tuple((1, 2, 3))",
            "print(l)"
        ).assertResult("(1, 2, 3)\n");
    }

    @Test
    public void testType() {
        // TODO
    }

    @Test
    public void testVars() {
        // TODO
    }

    @Test
    public void testZip() {
        // TODO
    }

    @Test
    public void test__import__() {
        // TODO
    }
}
