package org.ah.python.interpreter;

import java.util.Map;


public abstract class PythonNumber extends PythonObject {


    protected PythonNumber(PythonClass pythonClass) {
        super(pythonClass);
    }

    protected static void populateCommonNumberClassMethods(PythonClass pythonClass) {
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
        pythonClass.addMethod(new BuiltInBoundMethod("__div__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__div__(context, args[1]);
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
        pythonClass.addMethod(new BuiltInBoundMethod("__divmod__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__divmod__(context, args[1]);
            }
        });
        pythonClass.addMethod(new BuiltInBoundMethod("__pow__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__pow__(context, args[1]);
            }
        });
    }

    public abstract int asInteger();

    public abstract double asFloat();

    public abstract boolean asBoolean();

    public boolean isConstant() {
        return true;
    }

}
