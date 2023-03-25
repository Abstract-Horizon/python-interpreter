package org.ah.python.interpreter;

import java.util.NoSuchElementException;

public abstract class Proxy extends PythonObject {

    public Proxy() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public PythonObject __getattr__(PythonObject name) {

        PythonObject res = super.__getattr__(name);

        if (res instanceof ProxyAttribute) {
            res = ((ProxyAttribute<Proxy>)res).attribute(this);
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void __setattr__(PythonObject name, PythonObject expr) {
        PythonObject res = getType().attributes.get(name.asString());

        if (res == null) {
            throw new NoSuchElementException(name.asString());
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
