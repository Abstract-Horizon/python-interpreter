package org.ah.python.interpreter;

public abstract class PythonInstantiableType extends PythonType implements CallableType {

    public PythonInstantiableType(PythonType parent, Class<? extends PythonObject> cls) {
        super(parent, cls);
    }

}
