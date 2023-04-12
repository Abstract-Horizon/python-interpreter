package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

public class PythonBoolean extends PythonNumber {

    public static PythonClass PYTHON_BOOL_CLASS = new PythonClass("bool");

    static {
        populateCommonNumberClassMethods(PYTHON_BOOL_CLASS);
        PYTHON_BOOL_CLASS.__setattr__("__and__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__and__(args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__or__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__or__(args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__xor__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__xor__(args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__lshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__lshift__(args.get(1));
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__rshift__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__rshift__(args.get(1));
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

    public int asInteger() {
        return value ? 1 : 0;
    }

    public double asFloat() {
        return value ? 1.0 : 0.0;
    }

    public boolean asBoolean() {
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

    public PythonBoolean __eq__(PythonObject other) {
        return PythonBoolean.valueOf(value == other.asBoolean());
    }

    public PythonBoolean __lt__(PythonObject other) {
        return PythonBoolean.valueOf(asInteger() < other.__int__().asInteger());
    }

    public PythonBoolean __le__(PythonObject other) {
        return PythonBoolean.valueOf(asInteger() <= other.__int__().asInteger());
    }

    public PythonBoolean __gt__(PythonObject other) {
        return PythonBoolean.valueOf(asInteger() > other.__int__().asInteger());
    }

    public PythonBoolean __ge__(PythonObject other) {
        return PythonBoolean.valueOf(asInteger() >= other.__int__().asInteger());
    }

    public PythonObject __neg__() {
        return PythonBoolean.valueOf(!value);
    }

    public PythonString __repr__() {
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

    public PythonObject __add__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__add__(other);
        }

        return __int__().__add__(other);
    }

    public PythonObject __sub__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__sub__(other);
        }

        return __int__().__sub__(other);
    }

    public PythonObject __mul__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__mul__(other);
        }

        return __int__().__mul__(other);
    }

    public PythonObject __div__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__div__(other);
        }

        return __int__().__div__(other);
    }

    public PythonObject __floordiv__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__floordiv__(other);
        }

        return __int__().__floordiv__(other);
    }

    public PythonObject __mod__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__mod__(other);
        }

        return __int__().__mod__(other);
    }

    public PythonObject __divmod__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__divmod__(other);
        }

        return __int__().__divmod__(other);
    }

    public PythonObject __pow__(PythonObject other) {
        if (other instanceof PythonFloat) {
            return __float__().__pow__(other);
        }

        return __int__().__pow__(other);
    }

    public String toString() {
        if (asBoolean()) {
            return "True";
        } else {
            return "False";
        }
    }
}
