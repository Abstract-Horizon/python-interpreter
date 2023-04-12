package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

public class PythonClass extends Scope {

    private String name;

    public PythonClass(String name) {
        this.name = name;
    }

    public String toString() {
        return "<class '" + name + "'>";
    }

    protected void populateMethods() {
        __setattr__("__repr__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__repr__();
            }
        });
        __setattr__("__str__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__str__();
            }
        });
        __setattr__("__eq__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__eq__(args.get(1));
            }
        });
        __setattr__("__ne__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__ne__(args.get(1));
            }
        });
        __setattr__("__lt__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__lt__(args.get(1));
            }
        });
        __setattr__("__le__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__le__(args.get(1));
            }
        });
        __setattr__("__gt__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__gt__(args.get(1));
            }
        });
        __setattr__("__ge__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__ge__(args.get(1));
            }
        });
    }

    public static void populateCommonAttributeClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__getattr__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__getattr__(args.get(0).asString());
            }
        });
        pythonClass.__setattr__("__setattr__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__setattr__(args.get(0).asString(), args.get(1));
            }
        });
        pythonClass.__setattr__("__delattr__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__delattr__(args.get(0).asString());
            }
        });
    }
}
