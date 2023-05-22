package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import org.ah.python.modules.BuiltInFunctions;
import org.junit.Test;
//import org.ah.python.interpreter.Modules;

public class TestDef extends BaseTestClass {

//    @Test
//    public void canInterpretDef() {
//        PythonObject module = Interpreter.convert(
//                "def x():\n"
//              + "    print(\"TRUE\")\n"
//              + "\n"
//              + "x()\n"
//              );
//
//        module.__call__();
//
//        assertEquals("TRUE\n", result());
//    }

    @Test public void testDefOnModule() {

        Def def = new Def("my_method", new Def.Argument[] {new Def.Argument("x", null)});
        def.getBlock().getStatements().add(
                new Call(BuiltInFunctions.getFunction("print"), PythonString.valueOf("Print from method"))
        );

        Block block = new Block();
        block.getStatements().add(def);

        Call call = new Call(new Reference(null, "my_method"), PythonInteger.valueOf(3));

        block.getStatements().add(call);

        context.continuation(block);

        for (int i = 0; i < 10000 && context.next(); i++) {}

        System.out.println(result());
//        assertEquals(context.a.asInteger(), 5);
        contextIsEmpty();
    }

    @Test public void testDefOnModuleAccessingParameter() {

        Def def = new Def("my_method", new Def.Argument[] {new Def.Argument("x", null)});
        def.getBlock().getStatements().add(
            new Call(BuiltInFunctions.getFunction("print"),
                new Call(
                    new Reference(PythonString.valueOf("Print from method, x="), "__add__"),
                    new Reference(null, "x")
                )
            )
        );

        Block block = new Block();
        block.getStatements().add(def);

        Call call = new Call(new Reference(null, "my_method"), PythonString.valueOf("value_of_x"));

        block.getStatements().add(call);

        context.continuation(block);


        for (int i = 0; i < 10000 && context.next(); i++) {}

        String resultString = result();
        System.out.println(resultString);
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
}
