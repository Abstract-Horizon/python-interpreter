package org.ah.python.interpreter;

public interface CallableType {

    PythonObject __call__(ThreadContext context, PythonObject[] args);

}
