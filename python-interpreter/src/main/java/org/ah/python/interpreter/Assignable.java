package org.ah.python.interpreter;

public interface Assignable {

    void assign(ThreadContext context, PythonObject expr);

}
