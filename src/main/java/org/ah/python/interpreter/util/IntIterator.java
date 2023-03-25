package org.ah.python.interpreter.util;

import java.util.Iterator;

import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;

public class IntIterator implements Iterator<PythonObject> {

    private PythonObject object;
    private int pos = 0;
    
    public IntIterator(PythonObject object) {
        this.object = object;
    }

    @Override
    public boolean hasNext() {
        return pos < object.__len__().asInteger();
    }

    @Override
    public PythonObject next() {
        int p = pos;
        pos++;
        return object.__getitem__(PythonInteger.valueOf(p));
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
