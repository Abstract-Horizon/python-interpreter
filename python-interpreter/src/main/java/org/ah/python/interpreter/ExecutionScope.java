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
            return parentScope.getAttribute(name);
        }
        return null;
    }

    public void setAttribute(String name, PythonObject value) {
        attributes.put(name, value);
    }

    @Override
    public PythonObject __getattr__(String name) {
        if (globals.contains(name)) {
            return GlobalScope.globalScope().getAttribute(name);
        }
        return getAttribute(name);
    }

    @Override
    public void __setattr__(String name, PythonObject value) {
        if (globals.contains(name)) {
            GlobalScope.globalScope().setAttribute(name, value);
        } else {
            setAttribute(name, value);
        }
    }

    @Override
    public void __delattr__(PythonObject key) {
        if (attributes != null) {
            attributes.remove(key.asString());
        }
    }

    @Override
    public void __delitem__(PythonObject name) {
        __delitem__(name);
    }
}
