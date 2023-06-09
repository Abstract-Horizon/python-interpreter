package org.ah.python.interpreter;

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
}
