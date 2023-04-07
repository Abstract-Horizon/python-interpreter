package org.ah.python.interpreter;

import java.util.NoSuchElementException;

public abstract class Proxy extends PythonObject {

    public Proxy() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public PythonObject __getattr__(String name) {

        PythonObject res = super.__getattr__(name);

        if (res instanceof ProxyAttribute) {
            res = ((ProxyAttribute<Proxy>)res).attribute(this);
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void __setattr__(String name, PythonObject expr) {
        PythonObject res = getType().attributes.get(name);

        if (res == null) {
            throw new NoSuchElementException(name);
        }

        if (res instanceof ProxyAttribute) {
            ((ProxyAttribute<Proxy>)res).assign(this, expr);
        }
    }

    public static abstract class ProxyAttribute<T extends PythonObject> extends InstanceMethod<T> {

        protected abstract PythonObject attribute(T self);
        protected abstract void assign(T self, PythonObject expr);

        @SuppressWarnings("unchecked")
        @Override
        protected PythonObject call0(PythonObject self) {
            return attribute((T)self);
        }
    }
}
