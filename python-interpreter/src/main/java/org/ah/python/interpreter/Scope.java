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

    public Scope(PythonClass pythonClass, Scope parentScope) {
        super(pythonClass);
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

    public void addMethod(Function method) {
        attributes.put(method.getName(), method);
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
        if (ThreadContext.DEBUG && o == null) {
            throw new IllegalStateException("Trying to set attribute with name '" + attr + "' to null!");
        }
        attributes.put(attr, o);
    }

    public void __setitem__(ThreadContext context, String attr, PythonObject o) {
        if (ThreadContext.DEBUG && o == null) {
            throw new IllegalStateException(context.position() + "Trying to set attribute with name '" + attr + "' to null!");
        }
        attributes.put(attr, o);
    }

    public void __delitem__(ThreadContext context, String attr) {
        attributes.remove(attr);
    }

    public void __getattr__(ThreadContext context, String name) {
        if (attributes.containsKey(name)) {
            PythonObject value = attributes.get(name);
            if (ThreadContext.DEBUG && value == null) {
                throw new IllegalStateException(context.position() + "Attribute with name '" + name + "' is null!");
            }
            context.pushData(value);
            return;
        } else if (parentScope != null) {
            PythonObject value = parentScope.getAttribute(name);
            if (value != null) {
                context.pushData(value);
                return;
            }
        }

        if (this instanceof PythonClass) {
            context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + ((PythonClass)this).getName() + "' object has no attribute '" + name + "'")));
        } else if (this instanceof Module) {
            context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("Module '" + ((Module)this).getName() + "' has no attribute '" + name + "'")));
        } else if (pythonClass != null) {
            context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + pythonClass.getName() + "' object has no attribute '" + name + "'")));
        } else {
            context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + toString() + "' object has no attribute '" + name + "'")));
        }
    }

    public PythonObject getattr(ThreadContext context, String name) {
        __getattr__(context, name);
        PythonObject data = context.popData();
        return data;
    }

    public void __setattr__(String attr, PythonObject o) {
        if (ThreadContext.DEBUG && o == null) {
            throw new IllegalStateException("Trying to set attribute with name '" + attr + "' to null!");
        }
        attributes.put(attr, o);
    }

    public void __setattr__(ThreadContext context, String attr, PythonObject o) {
        if (ThreadContext.DEBUG && o == null) {
            throw new IllegalStateException(context.position() + "Trying to set attribute with name '" + attr + "' to null!");
        }
        attributes.put(attr, o);
    }

    public void __delattr__(ThreadContext context, String attr) {
        attributes.remove(attr);
    }
}
