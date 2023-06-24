package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.BUILT_IN_METHOD_CLASS;

public class BuiltInBoundMethod extends Function {

    public BuiltInBoundMethod(String name) {
//        super(new PythonMethodClass("<built-in method " + name + ">"), name);
        super(BUILT_IN_METHOD_CLASS, name);
//        super();
        this.name = name;
        this.instanceMethod = true;
    }

}
