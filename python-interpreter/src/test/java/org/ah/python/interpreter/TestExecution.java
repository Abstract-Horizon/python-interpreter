package org.ah.python.interpreter;

import static java.util.Arrays.asList;

import org.junit.Test;


public class TestExecution extends BaseTestClass {

    @Test
    public void testPrint() {

        PythonObject ptrue = PythonBoolean.TRUE;
        PythonObject pfalse = PythonBoolean.FALSE;

        ThreadContext context = new ThreadContext();

        PythonObject o = new BoolOp(asList(ptrue, new BoolOp(asList(pfalse, ptrue), BoolopType.Or)), BoolopType.And);
        context.pushPC(o);

        while (context.next()) {

        };


        System.out.println(context.a);
    }
}
