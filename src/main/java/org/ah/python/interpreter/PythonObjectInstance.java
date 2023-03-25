package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PythonObjectInstance extends PythonObject {

    protected Map<String, PythonObject> attributes;
    
    private PythonType type;

    public PythonObjectInstance(PythonType type) {
        this.type = type;
    }
    
    @Override
    public PythonType getType() {
        return type;
    }

    protected void ensureAttrs() {
        if (attributes == null) {
            attributes = new HashMap<String, PythonObject>();
        }
    }

    protected PythonObject getAttribute(String name) {
        if (attributes != null && attributes.containsKey(name)) {
            return attributes.get(name);
        }
        return getType().getAttribute(name);
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
        PythonObject res = getAttribute(n);
        if (res == null) {
            throw new NoSuchElementException(name + " in " + this);
        }
        return res;
    }

    @Override
    public void __setattr__(PythonObject name, PythonObject value) {
        String n = name.asString();
        setAttribute(n, value);
    }

    @Override
    public void __delattr__(PythonObject key) {
        if (attributes != null) {
            attributes.remove(key.asString());
        }
    }
}
