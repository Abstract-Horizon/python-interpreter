package org.ah.python.interpreter;

import java.util.HashSet;
import java.util.Set;

public class ExecutionScope extends Scope {

    private Scope parentScope = null;
    private Set<String> globals = new HashSet<String>();

    public ExecutionScope() {
    }

    public ExecutionScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public Set<String> getGlobals() {
        return globals;
    }

    protected void setParentScope(PythonType parentScope) {
        this.parentScope = parentScope;
    }

    protected PythonObject getAttribute(String name) {
        if (attributes != null && attributes.containsKey(name)) {
            return attributes.get(name);
        }
        if (parentScope != null) {
            // TODO item or attr? return parentScope.__getitem__(name);
            return parentScope.__getattr__(name);
        }
        return null;
    }

    public void setAttribute(String name, PythonObject value) {
        attributes.put(name, value);
    }

    @Override
    public PythonObject __getattr__(String name) {
        if (globals.contains(name)) {
            return GlobalScope.globalScope().__getattr__(name);
        }
        return getAttribute(name);
    }

    @Override
    public PythonObject __setattr__(String name, PythonObject value) {
        if (globals.contains(name)) {
            GlobalScope.globalScope().__setattr__(name, value);
        } else {
            setAttribute(name, value);
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonObject __delattr__(String key) {
        if (attributes != null) {
            attributes.remove(key);
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonObject __delitem__(PythonObject name) {
        __delitem__(name);
        return PythonNone.NONE;
    }
}
