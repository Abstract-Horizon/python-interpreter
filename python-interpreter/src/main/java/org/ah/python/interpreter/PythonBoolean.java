package org.ah.python.interpreter;

import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonBoolean extends PythonNumber {

    public static PythonClass PYTHON_BOOL_CLASS = new PythonClass("bool");

    static {
        populateCommonNumberClassMethods(PYTHON_BOOL_CLASS);
        PYTHON_BOOL_CLASS.__setattr__("__and__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__and__(context, args[1]);
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__or__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__or__(context, args[1]);
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__xor__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__xor__(context, args[1]);
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__lshift__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__lshift__(context, args[1]);
            }
        });
        PYTHON_BOOL_CLASS.__setattr__("__rshift__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__rshift__(context, args[1]);
            }
        });
    }

    public static final PythonBoolean TRUE = new PythonBoolean(true);
    public static final PythonBoolean FALSE = new PythonBoolean(false);

    public static final PythonString TRUE_REPR = PythonString.valueOf("True");
    public static final PythonString FALSE_REPR = PythonString.valueOf("False");

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

    public PythonBoolean not() {
        if (this == TRUE) {
            return FALSE;
        } else {
            return TRUE;
        }
    }

    public String asString() {
        return value ? "True" : "False";
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

    public void __bool__(ThreadContext context) {
        if (value) {
            context.pushData(TRUE);
        } else {
            context.pushData(FALSE);
        }
    }

    public boolean isConstant() {
        return true;
    }

    private static Executable eq_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonBoolean)context.popData()).value == ((PythonBoolean)context.popData()).value));
        }
    };

    public void __eq__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonBoolean) {
            context.pushData(PythonBoolean.valueOf(value == ((PythonBoolean)other).value));
        } else {
            context.continuation(eq_continuation);
            other.__bool__(context);
        }
    }

    private static Executable ne_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonBoolean)context.popData()).value != ((PythonBoolean)context.popData()).value));
        }
    };

    public void __ne__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonBoolean) {
            context.pushData(PythonBoolean.valueOf(value != ((PythonBoolean)other).value));
        } else {
            context.continuation(ne_continuation);
            other.__bool__(context);
        }
    }

//    public void __lt__(ThreadContext context, PythonObject other) {
//        context.pushData(PythonBoolean.valueOf(asInteger(context) < other.__int__(context).asInteger(context)));
//    }
//
//    public void __le__(ThreadContext context, PythonObject other) {
//        context.pushData(PythonBoolean.valueOf(asInteger(context) <= other.__int__(context).asInteger(context)));
//    }
//
//    public void __gt__(ThreadContext context, PythonObject other) {
//        context.pushData(PythonBoolean.valueOf(asInteger(context) > other.__int__(context).asInteger(context)));
//    }
//
//    public void __ge__(ThreadContext context, PythonObject other) {
//        context.pushData(PythonBoolean.valueOf(asInteger(context) >= other.__int__(context).asInteger(context)));
//    }

    public void __neg__(ThreadContext context) {
        context.pushData(PythonBoolean.valueOf(!value));
    }

    public void __repr__(ThreadContext context) {
        if (value) {
            context.pushData(TRUE_REPR);
        } else {
            context.pushData(FALSE_REPR);
        }
    }

    private static Executable and_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonBoolean other = (PythonBoolean)context.popData();
            context.pushData(other);
        }
    };

    public void __and__(ThreadContext context, PythonObject other) {
        if (!value) {
            context.pushData(FALSE);
        } else if (other instanceof PythonBoolean) {
            context.pushData(other);
        } else {
            context.continuation(and_continuation);
            other.__bool__(context);
        }
    }

    private static Executable or_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonBoolean other = (PythonBoolean)context.popData();
            context.pushData(other);
        }
    };

    public void __or__(ThreadContext context, PythonObject other) {
        if (value) {
            context.pushData(TRUE);
        } else if (other instanceof PythonBoolean) {
            context.pushData(other);
        } else {
            context.continuation(or_continuation);
            other.__bool__(context);
        }
    }

    private static Executable xor_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonBoolean other = (PythonBoolean)context.popData();
            PythonBoolean self = (PythonBoolean)context.popData();
            context.pushData(PythonBoolean.valueOf(self != other));
        }
    };

    public void __xor__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonBoolean) {
            context.pushData(PythonBoolean.valueOf(value != ((PythonBoolean)other).value));
        } else {
            context.continuation(xor_continuation);
            context.pushData(this);
            other.__bool__(context);
        }
    }


//    public void __add__(ThreadContext context, PythonObject other) {
//// TODO
////        if (other instanceof PythonFloat) {
////            return __float__(context).__add__(context, other);
////        }
////
////        return __int__(context).__add__(context, other);
//    }
//
//    public void __sub__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__sub__(context, other);
//        }
//
//        return __int__(context).__sub__(context, other);
//    }
//
//    public void __mul__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__mul__(context, other);
//        }
//
//        return __int__(context).__mul__(context, other);
//    }
//
//    public void __div__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__div__(context, other);
//        }
//
//        return __int__(context).__div__(context, other);
//    }
//
//    public void __floordiv__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__floordiv__(context, other);
//        }
//
//        return __int__(context).__floordiv__(context, other);
//    }
//
//    public void __mod__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__mod__(context, other);
//        }
//
//        return __int__(context).__mod__(context, other);
//    }
//
//    public void __divmod__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__divmod__(context, other);
//        }
//
//        return __int__(context).__divmod__(context, other);
//    }
//
//    public void __pow__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonFloat) {
//            return __float__(context).__pow__(context, other);
//        }
//
//        return __int__(context).__pow__(context, other);
//    }

    public String toString() {
        if (asBoolean()) {
            return "True";
        } else {
            return "False";
        }
    }
}
