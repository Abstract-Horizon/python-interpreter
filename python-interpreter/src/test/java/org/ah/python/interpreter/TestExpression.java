package org.ah.python.interpreter;

import java.util.Arrays;

import org.junit.Test;


public class TestExpression extends BaseTestClass {

    @Test public void testExpressionFromParsedCode() {
        PythonObject parsedModule = Interpreter.convertLines(
                "a = 5",
                "a.__add__(6)",
                "invoke()",
                ""
              );

        System.out.println(parsedModule);
    }

    @Test public void testSimpleIntegerAdd() {

        PythonObject two = PythonInteger.valueOf(2);
        PythonObject three = PythonInteger.valueOf(3);

        Reference refAdd = new Reference(three, "__add__");
        Call callAdd = new Call(refAdd, Arrays.asList(two));

        ThreadContext context = new ThreadContext();
        Module module = new Module();
        context.setCurrentScope(module);

        context.pushPC(callAdd);

        while (context.next()) {

        };


        System.out.println(context.a);
    }

    @Test public void testSimpleIntegerAddSub() {

        PythonObject one = PythonInteger.valueOf(1);
        PythonObject two = PythonInteger.valueOf(2);
        PythonObject three = PythonInteger.valueOf(3);

        Reference refAdd = new Reference(three, "__add__");
        Call callAdd = new Call(refAdd, Arrays.asList(two));

        Reference refSub = new Reference(callAdd, "__sub__");
        Call callSub = new Call(refSub, Arrays.asList(one));

        ThreadContext context = new ThreadContext();
        Module module = new Module();
        context.setCurrentScope(module);

        context.pushPC(callSub);

        while (context.next()) {

        };


        System.out.println(context.a);
    }

    @Test public void testExpressionAddingStrings() {

        PythonObject one = PythonString.valueOf("123");
        PythonObject two = PythonString.valueOf("456");

        Reference refAdd = new Reference(one, "__add__");
        Call callAdd = new Call(refAdd, Arrays.asList(two));

        ThreadContext context = new ThreadContext();
        Module module = new Module();
        context.setCurrentScope(module);

        context.pushPC(callAdd);

        while (context.next()) {

        };


        System.out.println(context.a);
    }
}
