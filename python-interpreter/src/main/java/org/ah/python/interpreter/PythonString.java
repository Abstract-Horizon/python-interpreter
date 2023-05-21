package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.Map;

import org.ah.python.interpreter.util.StringIterator;

public class PythonString extends PythonObject {

    public static PythonClass PYTHON_STRING_CLASS = new PythonClass("str");

    static {
        PYTHON_STRING_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__add__(context, args[1]);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__len__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__len__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__int__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__int__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__float__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__float__(context);
            }
        });
        PYTHON_STRING_CLASS.__setattr__("__bool__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__bool__(context);
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

    public void __eq__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonString) {
            context.pushData(PythonBoolean.valueOf(this.value.equals(other.asString())));
        } else {
            context.pushData(PythonBoolean.FALSE);
        }
    }

    public void __len__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf(value.length()));
    }

    public void __int__(ThreadContext context) {
        try {
            int i = Integer.parseInt(value);
            context.pushData(PythonInteger.valueOf(i));
        } catch (NumberFormatException e) {
            context.raise(exception("ValueError", PythonString.valueOf("invalid literal for int() with base 10: '" + value + "'")));
        }
    }

    public void __float__(ThreadContext context) {
        try {
            double d = Double.parseDouble(value);
            context.pushData(PythonFloat.valueOf(d));
        } catch (NumberFormatException e) {
            context.raise(exception("ValueError", PythonString.valueOf("invalid literal for int() with base 10: '" + value + "'")));
        }
    }

    public void __bool__(ThreadContext context) {
        context.pushData(PythonBoolean.valueOf(value.length() > 0));
    }

    public void __repr__(ThreadContext context) {
        context.pushData(this);
    }

    public void __add__(ThreadContext context, PythonObject other) {
        if (other instanceof PythonString) {
            context.pushData(PythonString.valueOf(value + other.asString()));
        } else {
            context.raise(exception("TypeError", PythonString.valueOf("can only concatenate str (not \\\"\" + other.pythonClass + \"\\\") to str)")));
        }
    }

    public String toString() {
        return value;
    }

    public void __iter__(ThreadContext context) {
        context.pushData(new PythonIterator(new StringIterator(value)));
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
