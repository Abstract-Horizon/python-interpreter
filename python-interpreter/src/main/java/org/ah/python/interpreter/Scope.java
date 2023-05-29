package org.ah.python.interpreter;

import java.util.HashMap;
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

    public void close() {
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope scope) {
        this.parentScope = scope;
    }

    public PythonObject getAttribute(String name) {
        PythonObject attr = attributes.get(name);
        if (attr != null) {
            return attr;
        }
        if (parentScope != null) {
            return parentScope.getAttribute(name);
        }
        return null;
    }

    public boolean contains(String name) {
        return attributes.containsKey(name) || (parentScope != null && parentScope.contains(name));
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

    public void __getattr__(ThreadContext context, String name) {
        if (attributes.containsKey(name)) {
            context.pushData(attributes.get(name));
        } else if (parentScope != null) {
            parentScope.__getattr__(context, name);
        } else {
            if (this instanceof PythonClass) {
                context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + ((PythonClass)this).getName() + "' object has no attribute '" + name + "'")));
            } else if (pythonClass != null) {
                context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + pythonClass.getName() + "' object has no attribute '" + name + "'")));
            } else {
                context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + toString() + "' object has no attribute '" + name + "'")));
            }
        }
    }

    public PythonObject getattr(ThreadContext context, String name) {
        __getattr__(context, name);
        PythonObject data = context.popData();
        return data;
    }

    public void __setattr__(String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __setattr__(ThreadContext context, String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __delattr__(ThreadContext context, String attr) {
        attributes.remove(attr);
    }

    public static void populateCommonContainerClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__(null, "__getitem__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__getitem__(context, args[0]);
            }
        });
        pythonClass.__setattr__(null, "__setitem__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__setitem__(context, args[0], args[1]);
                context.pushData(PythonNone.NONE);
            }
        });
        pythonClass.__setattr__(null, "__delitem__", new BuiltInBoundMethod() {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__delitem__(context, args[0]);
                context.pushData(PythonNone.NONE);
            }
        });
    }
}
