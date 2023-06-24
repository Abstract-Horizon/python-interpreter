package org.ah.python.interpreter;

import org.ah.python.interpreter.PythonClass.Attribute;

public class BuiltInIObject<T> extends PythonObject {

    public BuiltInIObject(PythonClass pythonClass) {
        super(pythonClass);
    }

    @SuppressWarnings("unchecked")
    @Override public void __getattr__(ThreadContext context, String name) {

        if (pythonClass.nativeClassDefinedAttribtues != null && pythonClass.nativeClassDefinedAttribtues.containsKey(name)) {
            context.pushData(((Attribute<T>)pythonClass.nativeClassDefinedAttribtues.get(name)).attribute((T)this));
        } else {
            super.__getattr__(context, name);
        }
    }

    @SuppressWarnings("unchecked")
    @Override public void __setattr__(ThreadContext context, String name, PythonObject value) {
        if (pythonClass.nativeClassDefinedAttribtues != null && pythonClass.nativeClassDefinedAttribtues.containsKey(name)) {
            ((Attribute<T>)pythonClass.nativeClassDefinedAttribtues.get(name)).assign((T)this, value);
        } else {
            super.__setattr__(context, name, value);
        }
    }

}
