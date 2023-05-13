package org.ah.python.interpreter;

import java.util.Map;

import org.ah.python.interpreter.util.StringIterator;

public class PythonString extends PythonObject {

    public static PythonClass PYTHON_STRING_CLASS = new PythonClass("str");

    static {
        PYTHON_STRING_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__add__(context, args[1]);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__len__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__len__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__int__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__int__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__float__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__float__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__bool__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__bool__(context);
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

    public PythonBoolean __eq__(ThreadContext context, PythonObject other) {
        PythonObject r = other.dereference();

        if (r instanceof PythonString) {
            return PythonBoolean.valueOf(this.value.equals(other.asString(context)));
        }
        throw new UnsupportedOperationException("__eq__ on string and " + r);
    }

    public PythonInteger __len__(ThreadContext context) {
        return PythonInteger.valueOf(value.length());
    }

    public PythonInteger __int__(ThreadContext context) {
        try {
            int i = Integer.parseInt(value);
            return PythonInteger.valueOf(i);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public PythonFloat __float__(ThreadContext context) {
        try {
            double d = Double.parseDouble(value);
            return PythonFloat.valueOf(d);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public PythonBoolean __bool__(ThreadContext context) {
        return PythonBoolean.valueOf(value.length() > 0);
    }

    public PythonString __repr__(ThreadContext context) {
        return this;
    }

    public PythonObject __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonString) {
            return PythonString.valueOf(value + other.asString(context));
        } else {
            throw new IllegalArgumentException("TypeError: can only concatenate str (not \"" + other.pythonClass + "\") to str");
        }
    }

    public String toString() {
        return value;
    }

    public PythonIterator __iter__(ThreadContext context) {
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
