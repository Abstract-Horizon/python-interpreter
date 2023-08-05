package org.ah.python.interpreter;

import java.util.Map;

public class PythonObjectInstance extends Scope {

    public PythonObjectInstance(PythonClass pythonClassType) {
        super(pythonClassType, null); // TODO is this correct? Or we should have it in both
    }

    public void __getattr__(ThreadContext context, String name) {
        if (attributes.containsKey(name)) {
            context.pushData(attributes.get(name));
        } else {
            pythonClass.__getattr__(context, name);
        }
    }

    public PythonObject copy() {
        return deepCopy();
    }

    public PythonObject deepCopy() {
        PythonObjectInstance copy = new PythonObjectInstance(pythonClass);
        copy.parentScope = parentScope;
        for (Map.Entry<String, PythonObject> entry : attributes.entrySet()) {
            copy.attributes.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return copy;
    }
}
