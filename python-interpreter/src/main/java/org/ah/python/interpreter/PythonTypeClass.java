package org.ah.python.interpreter;

public class PythonTypeClass extends PythonClass {

    public static PythonTypeClass PYTHON_TYPE_CLASS = new PythonTypeClass();

    public PythonTypeClass() {
        super("type");
    }

}
