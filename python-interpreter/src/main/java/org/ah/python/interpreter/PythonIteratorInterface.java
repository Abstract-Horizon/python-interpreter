package org.ah.python.interpreter;

public interface PythonIteratorInterface {
    public void __iter__(ThreadContext context);

    public void __next__(ThreadContext context);
}
