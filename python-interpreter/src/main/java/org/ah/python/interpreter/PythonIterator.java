package org.ah.python.interpreter;

import java.util.Iterator;

public class PythonIterator extends PythonObject {

    public static PythonClass PYTHON_ITERATOR_CLASS = new PythonClass("iterator");

    private Iterator<PythonObject> it;

    public PythonIterator(Iterator<PythonObject> it) {
        super(PYTHON_ITERATOR_CLASS);
        this.it = it;
    }

    public void __iter__(ThreadContext context) {
        context.pushData(this);
    }

    public void __next__(ThreadContext context) {
        context.pushData(next(context));
    }

    public PythonObject next(ThreadContext context) {
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }

// TODO
//    public PythonObject __getattr__(final ThreadContext context, String name) {
//        if (!"__next__".equals(name)) {
//            return super.__getattr__(context, name);
//        }
//
//        PythonObject res = attributes.get(name);
//        if ("__int__".equals(name)) { res = new Function() { @Override public PythonObject call0(ThreadContext context) { return __int__(context); }}; attributes.put(name,  res); }
//        return res;
//    }
}
