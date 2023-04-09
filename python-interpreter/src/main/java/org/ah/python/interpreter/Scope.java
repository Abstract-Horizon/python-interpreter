package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope extends PythonObject {

    protected Map<String, PythonObject> attributes;

    public static PythonClass classDict() {
        return new PythonClass("dict");
    }

    public static PythonClass CLASS_DICT = classDict();

    public Scope() {
        attributes = new HashMap<String, PythonObject>();
        pythonClass = CLASS_DICT;
    }

    public Scope(Map<String, PythonObject> attributes) {
        this.attributes = attributes;
        pythonClass = CLASS_DICT;
    }

    public PythonObject __getitem__(String attr) {
        if (attributes.containsKey(attr)) {
            return attributes.get(attr);
        }
        return PythonNone.NONE;
    }

    public void __setitem__(String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __delitem__(String attr) {
        attributes.remove(attr);
    }

    public PythonObject __getattr__(String attr) {
        if (attributes.containsKey(attr)) {
            return attributes.get(attr);
        }
        return PythonNone.NONE;
    }

    public PythonObject __setattr__(String attr, PythonObject o) {
        return attributes.put(attr, o);
    }

    public PythonObject __delattr__(String attr) {
        return attributes.remove(attr);
    }

    public static void populateCommonAttributeClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__getattr__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__getattr__(args.get(0).asString());
            }
        });
        pythonClass.__setattr__("__setattr__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__setattr__(args.get(0).asString(), args.get(1));
            }
        });
        pythonClass.__setattr__("__delattr__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__delattr__(args.get(0).asString());
            }
        });
    }

    public static void populateCommonContainerClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__getitem__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__getitem__(args.get(0));
            }
        });
        pythonClass.__setattr__("__setitem__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                args.get(0).__setitem__(args.get(0), args.get(1));
                return PythonNone.NONE;
            }
        });
        pythonClass.__setattr__("__delitem__", new BuiltInFunction() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                args.get(0).__delitem__(args.get(0));
                return PythonNone.NONE;
            }
        });
    }
}
