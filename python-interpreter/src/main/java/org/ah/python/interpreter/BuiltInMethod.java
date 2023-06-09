package org.ah.python.interpreter;

public class BuiltInMethod extends Function {

    private String name;

    public BuiltInMethod() {
        this("unknown method");
    }

    public BuiltInMethod(String name) {
        super(new PythonMethodClass("<built-in function " + name + ">"));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
