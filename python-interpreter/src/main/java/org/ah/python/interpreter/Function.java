package org.ah.python.interpreter;

import java.util.Map;

public class Function extends PythonObject {

    public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        return __call__(context);
    }
}
