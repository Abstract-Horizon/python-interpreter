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

//    public PythonObject __getattr__(ThreadContext context, String name) {
//        if (attributes != null && attributes.containsKey(name)) {
//            return attributes.get(name);
//        }
//        return getType().__getattr__(context, name);
//    }

    public void __setattr__(ThreadContext context, String name, PythonObject value) {
        setAttribute(name, value);
    }

    public void __delattr__(ThreadContext context, PythonObject key) {
//        if (attributes != null) {
//            attributes.remove(key.asString(context));
//        }
    }
}
