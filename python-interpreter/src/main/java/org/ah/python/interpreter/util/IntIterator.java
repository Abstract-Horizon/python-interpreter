package org.ah.python.interpreter.util;

import java.util.Iterator;

import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class IntIterator implements Iterator<PythonObject> {

    private PythonObject object;
    private ThreadContext context;
    private int pos = 0;

    public IntIterator(ThreadContext context, PythonObject object) {
        this.object = object;
        this.context = context;
    }

    @Override
    public boolean hasNext() {
        return pos < object.__len__(context).asInteger(context);
    }

    @Override
    public PythonObject next() {
        int p = pos;
        pos++;
        return object.__getitem__(context, PythonInteger.valueOf(p));
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
