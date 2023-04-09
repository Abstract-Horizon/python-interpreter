package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PythonInteger extends PythonNumber {

    private static final PythonInteger[] positiveCache = new PythonInteger[2048];
    private static final PythonInteger[] negativeCache = new PythonInteger[2048];

    public static final PythonInteger ZERO;
    public static final PythonInteger ONE;
    public static final PythonInteger TWO;
    public static final PythonInteger THREE;
    public static final PythonInteger FOUR;
    public static final PythonInteger FIVE;

    public static Map<Integer, PythonInteger> allIntegers = new HashMap<Integer, PythonInteger>();

    public static PythonClass PYTHON_INTEGER_CLASS = new PythonClass("int");

    static {
        for (int i = 0; i < positiveCache.length; i++) {
            positiveCache[i] = new PythonInteger(i);
        }
        for (int i = 0; i < negativeCache.length; i++) {
            negativeCache[i] = new PythonInteger(-i - 1);
        }
        ZERO = positiveCache[0];
        ONE = positiveCache[1];
        TWO = positiveCache[2];
        THREE = positiveCache[3];
        FOUR = positiveCache[4];
        FIVE = positiveCache[5];

        populateCommonNumberClassMethods(PYTHON_INTEGER_CLASS);

        PYTHON_INTEGER_CLASS.__setattr__("__and__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__and__(args.get(1));
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__or__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__or__(args.get(1));
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__xor__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__xor__(args.get(1));
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__lshift__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__lshift__(args.get(1));
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__rshift__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__rshift__(args.get(1));
            }
        });
    }

    public static PythonInteger valueOf(String s) {
        return valueOf(Integer.parseInt(s));
    }

    public static PythonInteger valueOf(int i) {
        if (i >= 0 && i < positiveCache.length) {
            return positiveCache[i];
        }
        if (i > -negativeCache.length && i < 0) {
            return negativeCache[-i - 1];
        }

        PythonInteger res = allIntegers.get(i);
        if (res == null) {
            res = new PythonInteger(i);
            allIntegers.put(i, res);
        }

        return res;
    }

    private int value;

    protected PythonInteger(int value) {
        this.value = value;
        this.pythonClass = PYTHON_INTEGER_CLASS;
    }

    public int asInteger() {
        return value;
    }

    public double asFloat() {
        return value;
    }

    public boolean asBoolean() {
        return value != 0;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonString __repr__() {
        return PythonString.valueOf(Integer.toString(value));
    }

    public PythonInteger __int__() {
        return this;
    }

    public PythonFloat __float__() {
        return PythonFloat.valueOf(value);
    }

    public PythonBoolean __bool__() {
        return PythonBoolean.valueOf(value != 0);
    }

    public PythonBoolean __nonzero__() {
        if (value != 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public PythonBoolean __lt__(PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(this);
        } else {
            if (value < d.asInteger()) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __le__(PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(this);
        } else {
            if (value <= d.asInteger()) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __gt__(PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__gt__(this);
        } else {
            if (value > d.asInteger()) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __ge__(PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(this);
        } else {
            if (value >= d.asInteger()) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __eq__(PythonObject other) {
        PythonObject r = other.dereference();

        if (other instanceof PythonBoolean) {
            return other.__eq__(this);
        }

        return PythonBoolean.valueOf(value == r.asInteger());
    }

    public PythonNumber __neg__() {
        return PythonInteger.valueOf(-value);
    }

    public PythonObject __add__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value + other.asFloat());
        } else {
            return PythonInteger.valueOf(value + other.asInteger());
        }
    }

    public PythonObject __sub__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value - other.asFloat());
        } else {
            return PythonInteger.valueOf(value - other.asInteger());
        }
    }

    public PythonObject __mul__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value * other.asFloat());
        } else {
            return PythonInteger.valueOf(value * other.asInteger());
        }
    }

    public PythonObject __div__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value / other.asFloat());
        } else {
            return PythonInteger.valueOf(value / other.asInteger());
        }
    }

    public PythonObject __floordiv__(PythonObject other) {
        return PythonInteger.valueOf(value / other.asInteger());
    }

    public PythonObject __mod__(PythonObject other) {
        return PythonInteger.valueOf(value % other.asInteger());
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

    public PythonObject __lshift__(PythonObject other) {
        return PythonInteger.valueOf(value << other.asInteger());
    }

    public PythonObject __rshift__(PythonObject other) {
        return PythonInteger.valueOf(value >> other.asInteger());
    }

    public PythonObject __and__(PythonObject other) {
        return PythonInteger.valueOf(value & other.asInteger());
    }

    public PythonObject __xor__(PythonObject other) {
        return PythonInteger.valueOf(value ^ other.asInteger());
    }

    public PythonObject __or__(PythonObject other) {
        return PythonInteger.valueOf(value | other.asInteger());
    }

    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonObject) {
            return __eq__((PythonObject)o).asBoolean();
        }
        return false;
    }
}
