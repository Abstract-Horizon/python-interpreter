package org.ah.python.interpreter;

public class BuiltInMethod extends Function {

    private String name;

    public BuiltInMethod() {
        this("unknown method");
    }

    public BuiltInMethod(String name) {
        this.name = name;
        this.pythonClass = new PythonMethodClass("<built-in function " + name + ">");
    }

    public String getName() {
        return name;
    }
}
