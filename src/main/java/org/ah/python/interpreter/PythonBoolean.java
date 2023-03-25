package org.ah.python.interpreter;

public class PythonBoolean extends PythonObject {

    public static final PythonBoolean TRUE = new PythonBoolean(true);
    public static final PythonBoolean FALSE = new PythonBoolean(false);
    
    private boolean value;
    
    private PythonBoolean(boolean value) {
        this.value = value;
    }

    public boolean asBoolean() {
        return value;
    }

    public boolean isConstant() {
        return true;
    }

    public static PythonBoolean valueOf(boolean value) {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public PythonBoolean __eq__(PythonObject other) {
        PythonObject r = other.dereference();

        return PythonBoolean.valueOf(value == r.asBoolean());
    }

    public PythonObject __neg__() {
        return PythonBoolean.valueOf(!value);
    }

    public PythonInteger __cmp__(PythonObject other) {
        if (other instanceof PythonBoolean) {
            if (other == this) {
                return PythonInteger.valueOf(0);
            } else {
                return PythonInteger.valueOf(-2);
            }
        }
        throw new UnsupportedOperationException("__cmp__ on Boolean and " + other);
    }

    public PythonString __str__() {
        if (value) {
            return PythonString.valueOf("True");
        } else {
            return PythonString.valueOf("False");
        }
    }

    public PythonObject __and__(PythonObject other) {
        if (value) {
            return PythonBoolean.valueOf(other.asBoolean());
        }
        return FALSE;
    }
    
    public PythonObject __xor__(PythonObject other) {
        return PythonBoolean.valueOf(__eq__(other).asBoolean());
    }
    
    public PythonObject __or__(PythonObject other) {
        if (value) {
            return TRUE;
        }
        return PythonBoolean.valueOf(other.asBoolean());
    }
    
    public String toString() {
        if (asBoolean()) {
            return "True";
        } else {
            return "False";
        }
    }
}
