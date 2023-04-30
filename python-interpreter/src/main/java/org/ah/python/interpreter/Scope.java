package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope extends PythonObject {

    protected Scope parentScope;
    protected Map<String, PythonObject> attributes;

    public static PythonClass classDict() {
        return new PythonClass("dict");
    }

    public static PythonClass CLASS_DICT = classDict();

    public Scope() {
        this(null);
    }

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
        attributes = new HashMap<String, PythonObject>();
        pythonClass = CLASS_DICT;
    }

//    public Scope(Map<String, PythonObject> attributes) {
//        this.attributes = attributes;
//        pythonClass = CLASS_DICT;
//    }

    public void close() {
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope scope) {
        this.parentScope = scope;
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

    public void __setitem__(ThreadContext context, String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __delitem__(ThreadContext context, String attr) {
        attributes.remove(attr);
    }

    public PythonObject __getattr__(ThreadContext context, String attr) {
        if (attributes.containsKey(attr)) {
            return attributes.get(attr);
        }
        if (parentScope != null) {
            return parentScope.__getattr__(context, attr);
        }
        return PythonNone.NONE;
    }

    public PythonObject __setattr__(String attr, PythonObject o) {
        return attributes.put(attr, o);
    }

    public PythonObject __setattr__(ThreadContext context, String attr, PythonObject o) {
        return attributes.put(attr, o);
    }

    public PythonObject __delattr__(ThreadContext context, String attr) {
        return attributes.remove(attr);
    }

    public static void populateCommonContainerClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__(null, "__getitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__getitem__(context, args.get(0));
            }
        });
        pythonClass.__setattr__(null, "__setitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                args.get(0).__setitem__(context, args.get(0), args.get(1));
                return PythonNone.NONE;
            }
        });
        pythonClass.__setattr__(null, "__delitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                args.get(0).__delitem__(context, args.get(0));
                return PythonNone.NONE;
            }
        });
    }
}
