package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class Scope extends PythonObject {

    protected Map<String, PythonObject> attributes;

    protected void ensureAttrs() {
        if (attributes == null) {
            attributes = new HashMap<String, PythonObject>();
        }
    }
    
    protected PythonObject getAttribute(String name) {
        if (attributes != null && attributes.containsKey(name)) {
            return attributes.get(name);
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
