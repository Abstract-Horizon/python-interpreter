package org.ah.python.interpreter;

public class PythonObjectClass extends PythonClass {

    public static PythonObjectClass PYTHON_OBJECT_CLASS = new PythonObjectClass();

    public PythonObjectClass() {
        super("object");
    }

}
