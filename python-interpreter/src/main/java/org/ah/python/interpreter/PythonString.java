package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.util.StringIterator;

public class PythonString extends PythonObject {

    public static PythonClass PYTHON_STRING_CLASS = new PythonClass("str");

    static {
        PYTHON_STRING_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(args.get(1));
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__len__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__len__();
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__int__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__int__();
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__float__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__float__();
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__bool__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__bool__();
            }
        });
    }

    private String value;

    public static PythonString valueOf(String s) {
        return new PythonString(s);
    }

    private PythonString(String value) {
        this.value = value;
        this.pythonClass = PYTHON_STRING_CLASS;
    }

    public String asString() {
        return value;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonBoolean __eq__(PythonObject other) {
        PythonObject r = other.dereference();

        if (r instanceof PythonString) {
            return PythonBoolean.valueOf(this.value.equals(other.asString()));
        }
        throw new UnsupportedOperationException("__eq__ on string and " + r);
    }

    public PythonInteger __len__() {
        return PythonInteger.valueOf(value.length());
    }

    public PythonInteger __int__() {
        try {
            int i = Integer.parseInt(value);
            return PythonInteger.valueOf(i);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public PythonFloat __float__() {
        try {
            double d = Double.parseDouble(value);
            return PythonFloat.valueOf(d);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public PythonBoolean __bool__() {
        return PythonBoolean.valueOf(value.length() > 0);
    }

    public PythonString __repr__() {
        return this;
    }

    public PythonObject __add__(PythonObject other) {
        if (other instanceof PythonString) {
            return PythonString.valueOf(value + other.asString());
        } else {
            throw new IllegalArgumentException("TypeError: can only concatenate str (not \"" + other.pythonClass + "\") to str");
        }
    }

    public String toString() {
        return value;
    }

    public PythonIterator __iter__() {
        return new PythonIterator(new StringIterator(value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonString) {
            return value.equals(((PythonString)o).asString());
        }
        return false;
    }

}
