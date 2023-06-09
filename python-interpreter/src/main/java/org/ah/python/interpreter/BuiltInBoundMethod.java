package org.ah.python.interpreter;

public class BuiltInBoundMethod extends Function {

    public BuiltInBoundMethod(String name) {
        super(new PythonMethodClass("<built-in method " + name + ">"), name);
        this.instanceMethod = true;
    }

}
