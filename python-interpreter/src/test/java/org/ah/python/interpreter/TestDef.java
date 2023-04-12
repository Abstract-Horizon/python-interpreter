package org.ah.python.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

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

        Def def = new Def("my_method");
        def.getArguments().add(new Reference(null, "x"));
        def.getBlock().getStatements().add(
                new Call(BuiltInFunctions.getFunction("print"), Arrays.<PythonObject>asList(PythonString.valueOf("Print from method")))
        );

        Block block = new Block();
        block.getStatements().add(def);

        Call call = new Call(new Reference(null, "my_method"), Arrays.<PythonObject>asList(PythonInteger.valueOf(3)));

        block.getStatements().add(call);

        context.pushPC(block);


        for (int i = 0; i < 10000 && context.next(); i++) {}

        System.out.println(result());
//        assertEquals(context.a.asInteger(), 5);
        contextIsEmpty();
    }

    @Test public void testDefOnModuleAccessingParameter() {

        Def def = new Def("my_method");
        def.getArguments().add(new Reference(null, "x"));
        def.getBlock().getStatements().add(
            new Call(BuiltInFunctions.getFunction("print"), Arrays.<PythonObject>asList(
                new Call(
                    new Reference(PythonString.valueOf("Print from method, x="), "__add__"),
                    Arrays.<PythonObject>asList(
                            new Reference(null, "x"))
                )
            )
        ));

        Block block = new Block();
        block.getStatements().add(def);

        Call call = new Call(new Reference(null, "my_method"), Arrays.<PythonObject>asList(PythonString.valueOf("value_of_x")));

        block.getStatements().add(call);

        context.pushPC(block);


        for (int i = 0; i < 10000 && context.next(); i++) {}

        String resultString = result();
        System.out.println(resultString);
        assertEquals(resultString, "Print from method, x=value_of_x\n");
        contextIsEmpty();
    }
}
