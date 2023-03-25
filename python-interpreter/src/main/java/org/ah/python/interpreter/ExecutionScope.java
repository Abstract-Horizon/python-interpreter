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
        if (attributes == null) {
            ensureAttrs();
        }
        attributes.put(name, value);
    }
    
    @Override
    public PythonObject __getattr__(PythonObject name) {
        String n = name.asString();
        if (globals.contains(n)) {
            return GlobalScope.globalScope().getAttribute(n);
        }
        return getAttribute(n);
    }

    @Override
    public void __setattr__(PythonObject name, PythonObject value) {
        String n = name.asString();
        if (globals.contains(n)) {
            GlobalScope.globalScope().setAttribute(n, value);
        } else {
            setAttribute(n, value);
        }
    }

    @Override
    public void __delattr__(PythonObject key) {
        if (attributes != null) {
            attributes.remove(key.asString());
        }
    }
    
    @Override
    public PythonObject __getitem__(PythonObject name) {
        return __getattr__(name);
    }
    
    @Override
    public void __setitem__(PythonObject name, PythonObject value) {
        __setattr__(name, value);
    }
    
    @Override
    public void __delitem__(PythonObject name) {
        __delitem__(name);
    }
}
