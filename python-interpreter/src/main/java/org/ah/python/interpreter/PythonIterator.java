package org.ah.python.interpreter;

import java.util.Iterator;

public class PythonIterator extends PythonType {

    private Iterator<PythonObject> it;

    public PythonIterator(Iterator<PythonObject> it) {
        super("Iterator");
        this.it = it;
    }

    public PythonIterator __iter__() {
        return this;
    }

    public PythonObject __next__() {
        return next();
    }

    public PythonObject next() {
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }

    public PythonObject __getattr__(String name) {
        if (!"__next__".equals(name)) {
            return super.__getattr__(name);
        }

        PythonObject res = attributes.get(name);
        if ("__int__".equals(name)) { res = new Function() { @Override public PythonObject call0() { return __int__(); }}; attributes.put(name,  res); }
        return res;
    }
}
