package org.ah.python.interpreter;

import java.util.Map;

public class PythonClass extends Scope {

    public static PythonClass PYTHON_INTERNAL_CLASS_NOT_DEFINED = new PythonClass("python-internal-class-not-defined");
    public static PythonClass PYTHON_CLASS = new PythonClass("class");

    protected String name;

    public PythonClass(String name) {
        super(null, null);
        this.name = name;
        populateCommonMethods();
    }

    public String toString() {
        return "<class '" + name + "'>";
    }

    protected void populateCommonMethods() {
        __setattr__("__repr__", new BuiltInBoundMethod("__repr__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__repr__(context);
            }
        });
        __setattr__("__str__", new BuiltInBoundMethod("__str__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__str__(context);
            }
        });
        __setattr__("__eq__", new BuiltInBoundMethod("__eq__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__eq__(context, args[1]);
            }
        });
        __setattr__("__ne__", new BuiltInBoundMethod("__ne__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ne__(context, args[1]);
            }
        });
        __setattr__("__lt__", new BuiltInBoundMethod("__lt__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__lt__(context, args[1]);
            }
        });
        __setattr__("__le__", new BuiltInBoundMethod("__le__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__le__(context, args[1]);
            }
        });
        __setattr__("__gt__", new BuiltInBoundMethod("__gt__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__gt__(context, args[1]);
            }
        });
        __setattr__("__ge__", new BuiltInBoundMethod("__ge__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ge__(context, args[1]);
            }
        });
    }

    public static void populateCommonAttributeClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__getattr__", new BuiltInBoundMethod("__getattr__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__getattr__(context, args[0].asString());
            }
        });
        pythonClass.__setattr__("__setattr__", new BuiltInBoundMethod("__setattr__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__setattr__(context, args[0].asString(), args[1]);
            }
        });
        pythonClass.__setattr__("__delattr__", new BuiltInBoundMethod("__delattr__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__delattr__(context, args[0].asString());
            }
        });
    }

    public static void populateCommonContainerClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__(null, "__getitem__", new BuiltInBoundMethod("__getitem__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__getitem__(context, args[1]);
            }
        });
        pythonClass.__setattr__(null, "__setitem__", new BuiltInBoundMethod("__setitem__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__setitem__(context, args[1], args[2]);
                context.pushData(PythonNone.NONE);
            }
        });
        pythonClass.__setattr__(null, "__delitem__", new BuiltInBoundMethod("__delitem__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__delitem__(context, args[1]);
                context.pushData(PythonNone.NONE);
            }
        });
    }

    public String getName() {
        return name;
    }
}
