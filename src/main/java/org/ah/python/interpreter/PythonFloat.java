package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;
import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.ZERO;

public class PythonFloat extends PythonObject {

    public static PythonFloat valueOf(String s) {
        return valueOf(Double.parseDouble(s));
    }
    
    public static PythonFloat valueOf(double d) {
        return new PythonFloat(d);
    }
    
    private double value;
    
    protected PythonFloat(double value) {
        this.value = value;
    }
    
    public double asDouble() {
        return value;
    }

    public int asInteger() {
        return (int)value;
    }

    public boolean asBoolean() {
        return value != 0.0;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonBoolean __eq__(PythonObject other) {
        PythonObject r = other.dereference();

        return PythonBoolean.valueOf(value == r.asDouble());
    }

    public PythonInteger __cmp__(PythonObject other) {
        PythonObject d = other.dereference();
        if (value < d.asDouble()) {
            return PythonInteger.valueOf(-1);
        } else if (value > d.asDouble()) {
            return ONE;
        }
        return ZERO;
    }

    public PythonString __str__() {
        return PythonString.valueOf(Double.toString(value));
    }

    public PythonInteger __int__() {
        return PythonInteger.valueOf((int)value);
    }
    
    public PythonFloat __float__() {
        return this;
    }

    public PythonBoolean __nonzero__() {
        if (value != 0.0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public PythonObject __neg__() {
        return PythonFloat.valueOf(-value);
    }

    public PythonObject __add__(PythonObject other) {
        return PythonFloat.valueOf(value + other.asDouble());
    }

    public PythonObject __sub__(PythonObject other) {
        return PythonFloat.valueOf(value - other.asDouble());
    }
    
    public PythonObject __mul__(PythonObject other) {
        return PythonFloat.valueOf(value * other.asDouble());
    }
    
    public PythonObject __div__(PythonObject other) {
        return PythonFloat.valueOf(value / other.asDouble());
    }
    
    public PythonObject __floordiv__(PythonObject other) {
        return PythonFloat.valueOf(value / other.asDouble());
    }
    
    public PythonObject __mod__(PythonObject other) {
        return PythonFloat.valueOf(value % other.asDouble());
    }
    
    public PythonObject __divmod__(PythonObject other) {
        throw new UnsupportedOperationException("__divmod__");
    }
    
    public PythonObject __pow__(PythonObject other) {
        throw new UnsupportedOperationException("__pow__");
    }
    
    public PythonObject __pow__(PythonObject other, PythonObject moduo) {
        throw new UnsupportedOperationException("__pow__");
    }
    
    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonObject) {
            return __eq__((PythonObject)o).asBoolean();
        }
        return false;
    }

    public String toString() {
        return Double.toString(value);
    }
}
