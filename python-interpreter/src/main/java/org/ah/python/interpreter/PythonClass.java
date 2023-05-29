package org.ah.python.interpreter;

import java.util.Map;

public class PythonClass extends Scope {

    protected String name;

    public PythonClass(String name) {
        this.name = name;
        populateCommonMethods();
    }

    public String toString() {
        return "<class '" + name + "'>";
    }

    protected void populateCommonMethods() {
        __setattr__("__repr__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__repr__(context);
            }
        });
        __setattr__("__str__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__str__(context);
            }
        });
        __setattr__("__eq__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__eq__(context, args[1]);
            }
        });
        __setattr__("__ne__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ne__(context, args[1]);
            }
        });
        __setattr__("__lt__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__lt__(context, args[1]);
            }
        });
        __setattr__("__le__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__le__(context, args[1]);
            }
        });
        __setattr__("__gt__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__gt__(context, args[1]);
            }
        });
        __setattr__("__ge__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__ge__(context, args[1]);
            }
        });
    }

    public static void populateCommonAttributeClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__getattr__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__getattr__(context, args[0].asString());
            }
        });
        pythonClass.__setattr__("__setattr__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__setattr__(context, args[0].asString(), args[1]);
            }
        });
        pythonClass.__setattr__("__delattr__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__delattr__(context, args[0].asString());
            }
        });
    }

    public String getName() {
        return name;
    }
}
