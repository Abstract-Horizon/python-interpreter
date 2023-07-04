package org.ah.python.interpreter;

import java.util.Map;


public abstract class PythonNumber extends PythonObject implements Immutable {


    protected PythonNumber(PythonClass pythonClass) {
        super(pythonClass);
    }

    protected static void populateCommonNumberClassMethods(PythonClass pythonClass) {
        pythonClass.addMethod(new BuiltInBoundMethod("__neg__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__neg__(context);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__int__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__int__(context);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__float__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__float__(context);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__bool__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__bool__(context);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__add__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__add__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__sub__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__sub__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__mul__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__mul__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__truediv__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__truediv__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__floordiv__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__floordiv__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__mod__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__mod__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__pow__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__pow__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__iadd__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__iadd__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__isub__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__isub__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__imul__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__imul__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__idiv__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__idiv__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__ifloordiv__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ifloordiv__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__imod__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__imod__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__ipow__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ipow__(context, args[1]);
            }
        });
    }

    public abstract int asInteger();

    public abstract double asFloat();

    public abstract boolean asBoolean();

    public boolean isConstant() {
        return true;
    }


    public void __iadd__(ThreadContext context, PythonObject other) {
        this.__add__(context, other);
    }

    public void __isub__(ThreadContext context, PythonObject other) {
        this.__sub__(context, other);
    }

    public void __imul__(ThreadContext context, PythonObject other) {
        this.__mul__(context, other);
    }

    public void __idiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__idiv__", other);
    }

    public void __itruediv__(ThreadContext context, PythonObject other) {
        this.__truediv__(context, other);
    }

    public void __ifloordiv__(ThreadContext context, PythonObject other) {
        this.__floordiv__(context, other);
    }

    public void __imod__(ThreadContext context, PythonObject other) {
        this.__mod__(context, other);
    }

    public void __ipow__(ThreadContext context, PythonObject other) {
        this.__pow__(context, other);
    }

    public void __ipow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        this.__pow__(context, other, moduo);
    }

    public void __ilshift__(ThreadContext context, PythonObject other) {
        this.__lshift__(context, other);
    }

    public void __irshift__(ThreadContext context, PythonObject other) {
        this.__rshift__(context, other);
    }

    public void __iand__(ThreadContext context, PythonObject other) {
        this.__and__(context, other);
    }

    public void __ixor__(ThreadContext context, PythonObject other) {
        this.__xor__(context, other);
    }

    public void __ior__(ThreadContext context, PythonObject other) {
        this.__or__(context, other);
    }
}
