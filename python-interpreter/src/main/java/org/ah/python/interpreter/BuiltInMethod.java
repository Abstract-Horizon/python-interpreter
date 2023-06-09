package org.ah.python.interpreter;

public class BuiltInMethod extends Function {

    public BuiltInMethod() {
        this("unknown method");
    }

    public BuiltInMethod(String name) {
        super(new PythonMethodClass("<built-in function " + name + ">"), name);
    }
}
