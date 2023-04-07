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

    public double asFloat() {
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

        return PythonBoolean.valueOf(value == r.asFloat());
    }

    public PythonString __repr__() {
        return PythonString.valueOf(Double.toString(value));
    }

    public PythonInteger __int__() {
        return PythonInteger.valueOf((int)value);
    }

    public PythonFloat __float__() {
        return this;
    }

    public PythonBoolean __bool__() {
        return PythonBoolean.valueOf(value != 0.0d);
    }

    public PythonNumber __neg__() {
        return PythonFloat.valueOf(-value);
    }

    public PythonObject __add__(PythonObject other) {
        return PythonFloat.valueOf(value + other.asFloat());
    }

    public PythonObject __sub__(PythonObject other) {
        return PythonFloat.valueOf(value - other.asFloat());
    }

    public PythonObject __mul__(PythonObject other) {
        return PythonFloat.valueOf(value * other.asFloat());
    }

    public PythonObject __div__(PythonObject other) {
        return PythonFloat.valueOf(value / other.asFloat());
    }

    public PythonObject __floordiv__(PythonObject other) {
        return PythonFloat.valueOf(value / other.asFloat());
    }

    public PythonObject __mod__(PythonObject other) {
        return PythonFloat.valueOf(value % other.asFloat());
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
