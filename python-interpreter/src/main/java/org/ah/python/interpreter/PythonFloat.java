package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonFloat extends PythonNumber {

    public static PythonClass PYTHON_FLOAT_CLASS = new PythonClass("float") {
        {
            populateCommonMethods();
            populateCommonNumberClassMethods(this);
        }
    };

    public static PythonFloat valueOf(String s) {
        return valueOf(Double.parseDouble(s));
    }

    public static PythonFloat valueOf(double d) {
        return new PythonFloat(d);
    }

    protected double value;

    protected PythonFloat(double value) {
        super(PYTHON_FLOAT_CLASS);
        this.value = value;
    }

    public String asString() {
        return Double.toString(value);
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

    private Executable eq_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self == other));
        }
    };

    public void __eq__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value == ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(eq_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable ne_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self != other));
        }
    };

    public void __ne__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value != ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(ne_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable lt_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self.asFloat() < other.asFloat()));
        }
    };

    public void __lt__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value < ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(lt_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable le_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self.asFloat() <= other.asFloat()));
        }
    };

    public void __le__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value <= ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(le_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable gt_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self.asFloat() > other.asFloat()));
        }
    };

    public void __gt__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value > ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(gt_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable ge_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(PythonBoolean.valueOf(self.asFloat() >= other.asFloat()));
        }
    };

    public void __ge__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(PythonBoolean.valueOf(value >= ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(ge_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }


    public void __repr__(ThreadContext context) {
        context.pushData(PythonString.valueOf(Double.toString(value)));
    }

    public void __int__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf((int)value));
    }

    public void __float__(ThreadContext context) {
        context.pushData(this);
    }

    public void __bool__(ThreadContext context) {
        context.pushData(PythonBoolean.valueOf(value != 0.0d));
    }

    public void __neg__(ThreadContext context) {
        context.pushData(PythonFloat.valueOf(-value));
    }


    private Executable add_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() + other.asFloat()));
        }
    };

    public void __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value + ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(add_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable sub_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() - other.asFloat()));
        }
    };

    public void __sub__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value - ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(sub_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable mul_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() * other.asFloat()));
        }
    };

    public void __mul__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value * ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(mul_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }


    private Executable div_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() / other.asFloat()));
        }
    };

    public void __div__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value / ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(div_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable floordiv_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() / other.asFloat()));
        }
    };

    public void __floordiv__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value / ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(floordiv_continuation);
            context.pushData(this);
            other.__float__(context);
        }
    }

    private Executable mod_continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonFloat other = (PythonFloat)context.popData();
            PythonFloat self = (PythonFloat)context.popData();
            context.pushData(valueOf(self.asFloat() - other.asFloat()));
        }
    };

    public void __mod__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonNumber) {
            context.pushData(valueOf(value % ((PythonNumber)other).asFloat()));
        } else {
            context.continuation(mod_continuation);
            context.pushData(this);
            other.__float__(context);
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

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonFloat) {
            return ((PythonFloat) o).value == value;
        }
        // TODO this needs to be sorted in one go!!!
//        if (o instanceof PythonObject) {
//            return __eq__(null, (PythonObject)o).asBoolean(null);
//        }
        return false;
    }

    public String toString() {
        return Double.toString(value);
    }
}
