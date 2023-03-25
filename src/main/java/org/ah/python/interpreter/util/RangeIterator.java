package org.ah.python.interpreter.util;

import java.util.Iterator;

import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;

public class RangeIterator implements Iterator<PythonObject> {

    private int pos = 0;
    private int to = 0;
    private int step = 1;
    
    public RangeIterator(int from, int to, int step) {
        this.pos = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public boolean hasNext() {
        if (step > 0) {
            return pos < to;
        } else {
            return pos > to;
        }
    }

    @Override
    public PythonObject next() {
        int p = pos;
        pos = pos + step;
        return PythonInteger.valueOf(p);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
