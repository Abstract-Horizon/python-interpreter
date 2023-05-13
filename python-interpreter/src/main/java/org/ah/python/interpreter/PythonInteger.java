package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.HashMap;
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

        PYTHON_INTEGER_CLASS.__setattr__("__and__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__and__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__or__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__or__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__xor__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__xor__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__lshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__lshift__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__rshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__rshift__(context, args[1]);
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

    public int asInteger(ThreadContext context) {
        return value;
    }

    public double asFloat(ThreadContext context) {
        return value;
    }

    public boolean asBoolean(ThreadContext context) {
        return value != 0;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonString __repr__(ThreadContext context) {
        return PythonString.valueOf(Integer.toString(value));
    }

    public PythonInteger __int__(ThreadContext context) {
        return this;
    }

    public PythonFloat __float__(ThreadContext context) {
        return PythonFloat.valueOf(value);
    }

    public PythonBoolean __bool__(ThreadContext context) {
        return PythonBoolean.valueOf(value != 0);
    }

    public PythonBoolean __nonzero__(ThreadContext context) {
        if (value != 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public PythonBoolean __lt__(ThreadContext context, PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(context, this);
        } else {
            if (value < d.asInteger(context)) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __le__(ThreadContext context, PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(context, this);
        } else {
            if (value <= d.asInteger(context)) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __gt__(ThreadContext context, PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__gt__(context, this);
        } else {
            if (value > d.asInteger(context)) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __ge__(ThreadContext context, PythonObject other) {
        PythonObject d = other.dereference();
        if (d instanceof PythonFloat) {
            return d.__lt__(context, this);
        } else {
            if (value >= d.asInteger(context)) { return TRUE; }
        }
        return FALSE;
    }

    public PythonBoolean __eq__(ThreadContext context, PythonObject other) {
        PythonObject r = other.dereference();

        if (other instanceof PythonBoolean) {
            return other.__eq__(context, this);
        }

        return PythonBoolean.valueOf(value == r.asInteger(context));
    }

    public PythonNumber __neg__(ThreadContext context) {
        return PythonInteger.valueOf(-value);
    }

    public PythonObject __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value + other.asFloat(context));
        } else {
            return PythonInteger.valueOf(value + other.asInteger(context));
        }
    }

    public PythonObject __sub__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value - other.asFloat(context));
        } else {
            return PythonInteger.valueOf(value - other.asInteger(context));
        }
    }

    public PythonObject __mul__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value * other.asFloat(context));
        } else {
            return PythonInteger.valueOf(value * other.asInteger(context));
        }
    }

    public PythonObject __div__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return PythonFloat.valueOf(value / other.asFloat(context));
        } else {
            return PythonInteger.valueOf(value / other.asInteger(context));
        }
    }

    public PythonObject __floordiv__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value / other.asInteger(context));
    }

    public PythonObject __mod__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value % other.asInteger(context));
    }

    public PythonObject __divmod__(ThreadContext context, PythonObject other) {
        return context.raise(exception("AttributeError", PythonString.valueOf("__divmod__")));
    }

    public PythonObject __pow__(ThreadContext context, PythonObject other) {
        return context.raise(exception("AttributeError", PythonString.valueOf("__pow__")));
    }

    public PythonObject __pow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        return context.raise(exception("AttributeError", PythonString.valueOf("__pow__")));
    }

    public PythonObject __lshift__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value << other.asInteger(context));
    }

    public PythonObject __rshift__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value >> other.asInteger(context));
    }

    public PythonObject __and__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value & other.asInteger(context));
    }

    public PythonObject __xor__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value ^ other.asInteger(context));
    }

    public PythonObject __or__(ThreadContext context, PythonObject other) {
        return PythonInteger.valueOf(value | other.asInteger(context));
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
            return __eq__(null, (PythonObject)o).asBoolean(null);
        }
        return false;
    }
}
