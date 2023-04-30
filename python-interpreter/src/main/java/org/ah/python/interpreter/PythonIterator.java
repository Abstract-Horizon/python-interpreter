package org.ah.python.interpreter;

import java.util.Iterator;

public class PythonIterator extends PythonType {

    private Iterator<PythonObject> it;

    public PythonIterator(Iterator<PythonObject> it) {
        super("Iterator");
        this.it = it;
    }

    public PythonIterator __iter__(ThreadContext context) {
        return this;
    }

    public PythonObject __next__(ThreadContext context) {
        return next(context);
    }

    public PythonObject next(ThreadContext context) {
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }

    public PythonObject __getattr__(final ThreadContext context, String name) {
        if (!"__next__".equals(name)) {
            return super.__getattr__(context, name);
        }

        PythonObject res = attributes.get(name);
        if ("__int__".equals(name)) { res = new Function() { @Override public PythonObject call0(ThreadContext context) { return __int__(context); }}; attributes.put(name,  res); }
        return res;
    }
}
