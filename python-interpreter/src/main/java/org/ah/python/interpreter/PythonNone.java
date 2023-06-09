package org.ah.python.interpreter;

public class PythonNone extends PythonObject {

    public static final PythonNone NONE = new PythonNone();
    public static final PythonClass NONE_CLASS = new PythonClass("Nne");

    private PythonNone() {
        super(NONE_CLASS);
    }

    public boolean asBoolean() {
        return false;
    }

    public boolean isConstant() {
        return true;
    }

    public String toString() {
        return "None";
    }
}
