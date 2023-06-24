package org.ah.python.interpreter;

public class BuiltInMethod extends Function {

    public BuiltInMethod(String name) {
        super(new PythonClass("<built-in function " + name + ">"), name);
    }
}
