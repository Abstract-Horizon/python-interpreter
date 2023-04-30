package org.ah.python.interpreter;

public class PythonFloat extends PythonNumber {

    public static PythonClass PYTHON_FLOAT_CLASS = new PythonClass("float");

    static {
        populateCommonNumberClassMethods(PYTHON_FLOAT_CLASS);
    }

    public static PythonFloat valueOf(String s) {
        return valueOf(Double.parseDouble(s));
    }

    public static PythonFloat valueOf(double d) {
        return new PythonFloat(d);
    }

    private double value;

    protected PythonFloat(double value) {
        this.value = value;
        this.pythonClass = PYTHON_FLOAT_CLASS;
    }

    public double asFloat(ThreadContext context) {
        return value;
    }

    public int asInteger(ThreadContext context) {
        return (int)value;
    }

    public boolean asBoolean(ThreadContext context) {
        return value != 0.0;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonBoolean __eq__(ThreadContext context, PythonObject other) {
        PythonObject r = other.dereference();

        return PythonBoolean.valueOf(value == r.asFloat(context));
    }

    public PythonString __repr__(ThreadContext context) {
        return PythonString.valueOf(Double.toString(value));
    }

    public PythonInteger __int__(ThreadContext context) {
        return PythonInteger.valueOf((int)value);
    }

    public PythonFloat __float__(ThreadContext context) {
        return this;
    }

    public PythonBoolean __bool__(ThreadContext context) {
        return PythonBoolean.valueOf(value != 0.0d);
    }

    public PythonNumber __neg__(ThreadContext context) {
        return PythonFloat.valueOf(-value);
    }

    public PythonObject __add__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value + other.asFloat(context));
    }

    public PythonObject __sub__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value - other.asFloat(context));
    }

    public PythonObject __mul__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value * other.asFloat(context));
    }

    public PythonObject __div__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value / other.asFloat(context));
    }

    public PythonObject __floordiv__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value / other.asFloat(context));
    }

    public PythonObject __mod__(ThreadContext context, PythonObject other) {
        return PythonFloat.valueOf(value % other.asFloat(context));
    }

    public PythonObject __divmod__(ThreadContext context, PythonObject other) {
        throw new UnsupportedOperationException("__divmod__");
    }

    public PythonObject __pow__(ThreadContext context, PythonObject other) {
        throw new UnsupportedOperationException("__pow__");
    }

    public PythonObject __pow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        throw new UnsupportedOperationException("__pow__");
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonObject) {
            return __eq__(null, (PythonObject)o).asBoolean(null);
        }
        return false;
    }

    public String toString() {
        return Double.toString(value);
    }
}
