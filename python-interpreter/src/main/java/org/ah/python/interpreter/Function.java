package org.ah.python.interpreter;

import java.util.Map;

public class Function extends PythonObject {

    protected String name;
    protected boolean instanceMethod = false;

    public Function(PythonClass pythonClass, String name) {
        super(pythonClass);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isInstanceMethod() {
        return instanceMethod;
    }

    public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        __call__(context);
    }
}
