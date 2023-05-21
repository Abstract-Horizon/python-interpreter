package org.ah.python.interpreter;

public class PythonNoop extends PythonObject {

    public static final PythonNoop NOOP = new PythonNoop();

    private PythonNoop() {
    }

}
