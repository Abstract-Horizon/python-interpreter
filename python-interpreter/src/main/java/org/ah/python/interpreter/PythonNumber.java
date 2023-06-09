package org.ah.python.interpreter;

import java.util.Map;


public abstract class PythonNumber extends PythonObject {


    protected PythonNumber(PythonClass pythonClass) {
        super(pythonClass);
    }

    protected static void populateCommonNumberClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__int__", new BuiltInBoundMethod("__int__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__int__(context);
            }
        });
        pythonClass.__setattr__("__float__", new BuiltInBoundMethod("__float__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__float__(context);
            }
        });
        pythonClass.__setattr__("__bool__", new BuiltInBoundMethod("__bool__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__bool__(context);
            }
        });
        pythonClass.__setattr__("__add__", new BuiltInBoundMethod("__add__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__add__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__sub__", new BuiltInBoundMethod("__sub__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__sub__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__mul__", new BuiltInBoundMethod("__mul__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__mul__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__div__", new BuiltInBoundMethod("__div__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__div__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__floordiv__", new BuiltInBoundMethod("__floordiv__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__floordiv__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__mod__", new BuiltInBoundMethod("__mod__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__mod__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__divmod__", new BuiltInBoundMethod("__divmod__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__divmod__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__pow__", new BuiltInBoundMethod("__pow__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__pow__(context, args[1]);
            }
        });
    }

    public abstract int asInteger();

    public abstract double asFloat();

    public abstract boolean asBoolean();

//    public void __int__(ThreadContext context) {
//        context.pushData(PythonInteger.valueOf(asInteger(context)));
//    }
//
//    public void __float__(ThreadContext context) {
//        context.pushData(PythonFloat.valueOf(asFloat(context)));
//    }
//
//    public void __bool__(ThreadContext context) {
//        context.pushData(PythonBoolean.valueOf(asBoolean(context)));
//    }

    public boolean isConstant() {
        return true;
    }

}
