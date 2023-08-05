package org.ah.python.interpreter;

public class PythonNone extends PythonObject implements Immutable {

    public static final PythonNone NONE = new PythonNone();
    public static final PythonClass NONE_CLASS = new PythonClass("None");

    private PythonNone() {
        super(new PythonClass("None"));
    }

    public boolean asBoolean() {
        return false;
    }

    public boolean isConstant() {
        return true;
    }

    public void __str__(ThreadContext context) {
        context.pushData(PythonString.valueOf("None"));
    }

    public String asString() {
        return "None";
    }

    public String toString() {
        return "None";
    }

    public PythonObject copy() {
        return this;
    }

    public PythonObject deepCopy() {
        return this;
    }
}
