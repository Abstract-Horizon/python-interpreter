package org.ah.python.interpreter;

public class PythonNone extends PythonObject {

    public static final PythonNone NONE = new PythonNone();
    
    private PythonNone() { }

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
