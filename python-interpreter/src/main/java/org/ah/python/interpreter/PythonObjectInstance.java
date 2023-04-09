package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.Map;

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

    public void setAttribute(String name, PythonObject value) {
        if (attributes == null) {
            ensureAttrs();
        }
        attributes.put(name, value);
    }

    public PythonObject __getattr__(String name) {
        if (attributes != null && attributes.containsKey(name)) {
            return attributes.get(name);
        }
        return getType().__getattr__(name);
    }

    public PythonObject __setattr__(String name, PythonObject value) {
        setAttribute(name, value);
        return PythonNone.NONE;
    }

    public PythonObject __delattr__(PythonObject key) {
        if (attributes != null) {
            attributes.remove(key.asString());
        }
        return PythonNone.NONE;
    }
}
