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
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__and__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__or__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__or__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__xor__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__xor__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__lshift__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__lshift__(context, args[1]);
            }
        });
        PYTHON_INTEGER_CLASS.__setattr__("__rshift__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__rshift__(context, args[1]);
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

    protected int value;

    protected PythonInteger(int value) {
        this.value = value;
        this.pythonClass = PYTHON_INTEGER_CLASS;
    }

    public String asString() {
        return Integer.toString(value);
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

    public void __repr__(ThreadContext context) {
        context.pushData(PythonString.valueOf(Integer.toString(value)));
    }

    public void __int__(ThreadContext context) {
        context.pushData(this);
    }

    public void __float__(ThreadContext context) {
        context.pushData(PythonFloat.valueOf(value));
    }

    public void __bool__(ThreadContext context) {
        context.pushData(PythonBoolean.valueOf(value != 0));
    }

    public void __nonzero__(ThreadContext context) {
        if (value != 0) {
            context.pushData(TRUE);
        } else {
            context.pushData(FALSE);
        }
    }

    private static ThreadContext.Executable lt_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value > ((PythonInteger)context.popData()).value));
        }
    };

    public void __lt__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__lt__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value < ((PythonInteger)other).value));
        } else {
            context.continuation(lt_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable le_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value >= ((PythonInteger)context.popData()).value));
        }
    };

    public void __le__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__le__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value <= ((PythonInteger)other).value));
        } else {
            context.continuation(le_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable gt_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value < ((PythonInteger)context.popData()).value));
        }
    };

    public void __gt__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__gt__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value > ((PythonInteger)other).value));
        } else {
            context.continuation(gt_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable ge_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value <= ((PythonInteger)context.popData()).value));
        }
    };

    public void __ge__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__ge__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value >= ((PythonInteger)other).value));
        } else {
            context.continuation(ge_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable eq_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value == ((PythonInteger)context.popData()).value));
        }
    };

    public void __eq__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__eq__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value == ((PythonInteger)other).value));
        } else {
            context.continuation(eq_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable ne_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonBoolean.valueOf(((PythonInteger)context.popData()).value != ((PythonInteger)context.popData()).value));
        }
    };

    public void __ne__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            other.__ne__(context, this);
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonBoolean.valueOf(value != ((PythonInteger)other).value));
        } else {
            context.continuation(ne_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    public void __neg__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf(-value));
    }

    private static ThreadContext.Executable add_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonInteger.valueOf(((PythonInteger)context.popData()).value + ((PythonInteger)context.popData()).value));
        }
    };

    public void __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value + ((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value + ((PythonInteger)other).value));
        } else {
            context.continuation(add_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable sub_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value - other.value));
        }
    };

    public void __sub__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value - ((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value - ((PythonInteger)other).value));
        } else {
            context.continuation(sub_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable mul_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.pushData(PythonInteger.valueOf(((PythonInteger)context.popData()).value * ((PythonInteger)context.popData()).value));
        }
    };

    public void __mul__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value * ((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value * ((PythonInteger)other).value));
        } else {
            context.continuation(mul_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable div_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value / other.value));
        }
    };

    public void __div__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value / ((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value / ((PythonInteger)other).value));
        } else {
            context.continuation(div_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }


    private static ThreadContext.Executable floordiv_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value / other.value));
        }
    };

    public void __floordiv__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value / (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value / ((PythonInteger)other).value));
        } else {
            context.continuation(floordiv_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable mod_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value % other.value));
        }
    };

    public void __mod__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value % (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value % ((PythonInteger)other).value));
        } else {
            context.continuation(mod_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }


    public void __divmod__(ThreadContext context, PythonObject other) {
        context.raise(exception("NotImplementedError", PythonString.valueOf("__divmod__")));
    }

    public void __pow__(ThreadContext context, PythonObject other) {
        context.raise(exception("NotImplementedError", PythonString.valueOf("__pow__")));
    }

    public void __pow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        context.raise(exception("NotImplementedError", PythonString.valueOf("__pow__")));
    }

    private static ThreadContext.Executable lshift_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value << other.value));
        }
    };

    public void __lshift__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value << (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value << ((PythonInteger)other).value));
        } else {
            context.continuation(lshift_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable rshift_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value >> other.value));
        }
    };

    public void __rshift__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value >> (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value >> ((PythonInteger)other).value));
        } else {
            context.continuation(rshift_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable and_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value & other.value));
        }
    };

    public void __and__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value & (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value & ((PythonInteger)other).value));
        } else {
            context.continuation(and_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable or_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value | other.value));
        }
    };

    public void __or__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value | (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value | ((PythonInteger)other).value));
        } else {
            context.continuation(or_continuation);
            context.pushData(this);
            other.__int__(context);
        }
    }

    private static ThreadContext.Executable xor_continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonInteger other = (PythonInteger)context.popData();
            PythonInteger self = (PythonInteger)context.popData();
            context.pushData(PythonInteger.valueOf(self.value ^ other.value));
        }
    };

    public void __xor__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonFloat) {
            context.pushData(PythonFloat.valueOf(value ^ (int)((PythonFloat)other).value));
        } else if (other instanceof PythonInteger) {
            context.pushData(PythonInteger.valueOf(value ^ ((PythonInteger)other).value));
        } else {
            context.continuation(xor_continuation);
            context.pushData(this);
            other.__int__(context);
        }
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
        if (o instanceof PythonInteger) {
            return ((PythonInteger) o).value == value;
        }
//        if (o instanceof PythonObject) {
//            return __eq__(null, (PythonObject)o).asBoolean(null);
//        }
        return false;
    }
}
