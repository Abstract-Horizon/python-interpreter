package org.ah.python.interpreter.util;

import java.util.Iterator;

import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonString;

public class StringIterator implements Iterator<PythonObject> {

    private String string;
    private int pos = 0;
    
    public StringIterator(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public PythonObject next() {
        int p = pos;
        pos++;
        return PythonString.valueOf(Character.toString(string.charAt(p)));
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
