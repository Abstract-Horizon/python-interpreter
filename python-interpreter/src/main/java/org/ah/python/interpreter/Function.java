package org.ah.python.interpreter;

import java.util.Map;

public class Function extends PythonObject {

    public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        __call__(context);
    }
}
