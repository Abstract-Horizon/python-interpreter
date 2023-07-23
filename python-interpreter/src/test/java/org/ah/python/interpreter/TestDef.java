package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.ah.python.modules.BuiltInFunctions;
import org.junit.Test;

public class TestDef extends BaseTestClass {

    @Test public void testDefOnModule() {

        Def def = new Def("my_method", new Def.Argument[] {new Def.Argument("x", null)});
        def.getBlock().addStatement(
                new Call(BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE.getAttribute("print"), PythonString.valueOf("Print from method")),
                1,
                "test.py"
        );

        Block block = new Block();
        block.addStatement(def, 2, "test.py");

        Call call = new Call(new Reference(null, "my_method"), PythonInteger.valueOf(3));

        block.addStatement(call, 3, "test.py");

        context.continuation(block);

        for (int i = 0; i < 10000 && context.next(); i++) {}

        contextIsEmpty();
    }

    @Test public void testDefOnModuleAccessingParameter() {

        Def def = new Def("my_method", new Def.Argument[] {new Def.Argument("x", null)});
        def.getBlock().addStatement(
            new Call(BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE.getAttribute("print"),
                new Call(
                    new Reference(PythonString.valueOf("Print from method, x="), "__add__"),
                    new Reference(null, "x")
                )
            ), 1, "test.py"
        );

        Block block = new Block();
        block.addStatement(def, 2, "test.py");

        Call call = new Call(new Reference(null, "my_method"), PythonString.valueOf("value_of_x"));

        block.addStatement(call, 3, "test.py");

        context.continuation(block);


        for (int i = 0; i < 10000 && context.next(); i++) {}

        String resultString = result();
        assertEquals(resultString, "Print from method, x=value_of_x\n");
        contextIsEmpty();
    }

    @Test public void testParsedDefAndInvocation() {
        executeLines(
            "def x():",
            "    print(\"TRUE\")",
            "",
            "x()"
        );

        assertEquals("TRUE\n", result());
        contextIsEmpty();
    }

    @Test public void testParsedDefAndInvocation2() {
        executeLines(
            "def x(a):",
            "    b = a + 1",
            "    print(b)",
            "",
            "x(8)"
        );

        assertEquals("9\n", result());
        contextIsEmpty();
    }

    @Test public void testParsedDefWithTwoParameters() {
        executeLines(
            "def x(a, b):",
            "    return a * 2 + b",
            "",
            "print(x(4, 1))"
        );

        assertEquals("9\n", result());
        contextIsEmpty();
    }

    @Test public void testNoStackLeftAfterInvocation() {
        executeLines(
            "def x(a):",
            "    b = a + 1",
            "    return b",
            "x(1)",
            "print(x(8))",
            "x(2)"
        );

        assertEquals("9\n", result());
        contextIsEmpty();
    }

    @Test public void testNoStackLeftAfterInvocation2() {
        executeLines(
            "def x(a):",
            "    return a",
            "x(1)",
            "x(2)",
            "x(3)"
        );

        contextIsEmpty();
    }
}
