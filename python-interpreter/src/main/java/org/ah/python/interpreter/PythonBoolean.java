package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

public class PythonBoolean extends PythonNumber {

    public static PythonClass PYTHON_BOOL_CLASS = new PythonClass("bool");

    static {
        populateCommonNumberClassMethods(PYTHON_BOOL_CLASS);
        PYTHON_BOOL_CLASS.__setattr__("__and__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__and__(context, args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__or__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__or__(context, args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__xor__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__xor__(context, args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__lshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__lshift__(context, args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__rshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__rshift__(context, args.get(1));
            }
        });
    }

    public static final PythonBoolean TRUE = new PythonBoolean(true);
    public static final PythonBoolean FALSE = new PythonBoolean(false);

    private boolean value;

    private PythonBoolean(boolean value) {
        this.value = value;
        this.pythonClass = PYTHON_BOOL_CLASS;
    }

    public static PythonBoolean valueOf(boolean value) {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public int asInteger(ThreadContext context) {
        return value ? 1 : 0;
    }

    public double asFloat(ThreadContext context) {
        return value ? 1.0 : 0.0;
    }

    public boolean asBoolean(ThreadContext context) {
        return value;
    }

    public PythonBoolean __bool__() {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public boolean isConstant() {
        return true;
    }

    public PythonBoolean __eq__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(value == other.asBoolean(context));
    }

    public PythonBoolean __lt__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(asInteger(context) < other.__int__(context).asInteger(context));
    }

    public PythonBoolean __le__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(asInteger(context) <= other.__int__(context).asInteger(context));
    }

    public PythonBoolean __gt__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(asInteger(context) > other.__int__(context).asInteger(context));
    }

    public PythonBoolean __ge__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(asInteger(context) >= other.__int__(context).asInteger(context));
    }

    public PythonObject __neg__(ThreadContext context) {
        return PythonBoolean.valueOf(!value);
    }

    public PythonString __repr__(ThreadContext context) {
        if (value) {
            return PythonString.valueOf("True");
        } else {
            return PythonString.valueOf("False");
        }
    }

    public PythonObject __and__(ThreadContext context, PythonObject other) {
        if (value) {
            return PythonBoolean.valueOf(other.asBoolean(context));
        }
        return FALSE;
    }

    public PythonObject __xor__(ThreadContext context, PythonObject other) {
        return PythonBoolean.valueOf(__eq__(context, other).asBoolean(context));
    }

    public PythonObject __or__(ThreadContext context, PythonObject other) {
        if (value) {
            return TRUE;
        }
        return PythonBoolean.valueOf(other.asBoolean(context));
    }

    public PythonObject __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__add__(context, other);
        }

        return __int__(context).__add__(context, other);
    }

    public PythonObject __sub__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__sub__(context, other);
        }

        return __int__(context).__sub__(context, other);
    }

    public PythonObject __mul__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__mul__(context, other);
        }

        return __int__(context).__mul__(context, other);
    }

    public PythonObject __div__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__div__(context, other);
        }

        return __int__(context).__div__(context, other);
    }

    public PythonObject __floordiv__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__floordiv__(context, other);
        }

        return __int__(context).__floordiv__(context, other);
    }

    public PythonObject __mod__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__mod__(context, other);
        }

        return __int__(context).__mod__(context, other);
    }

    public PythonObject __divmod__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__divmod__(context, other);
        }

        return __int__(context).__divmod__(context, other);
    }

    public PythonObject __pow__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__(context).__pow__(context, other);
        }

        return __int__(context).__pow__(context, other);
    }

    public String toString() {
        if (asBoolean(null)) {
            return "True";
        } else {
            return "False";
        }
    }
}
