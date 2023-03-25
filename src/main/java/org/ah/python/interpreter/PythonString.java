package org.ah.python.interpreter;

import org.ah.python.interpreter.util.StringIterator;

public class PythonString extends PythonObject {

    private String value;
    
    public static PythonString valueOf(String s) {
        return new PythonString(s);
    }
    
    private PythonString(String value) {
        this.value = value;
    }
    
    public String asString() {
        return value;
    }

    public boolean isConstant() {
        return true;
    }

    public PythonBoolean __eq__(PythonObject other) {
        PythonObject r = other.dereference();
        
        if (r instanceof PythonString) {
            return PythonBoolean.valueOf(this.value.equals(other.asString()));
        }
        throw new UnsupportedOperationException("__eq__ on string and " + r);
    }
    
    public PythonInteger __len__() {
        return PythonInteger.valueOf(value.length());
    }
    
    public PythonInteger __int__() {
        try {
            int i = Integer.parseInt(value);
            return PythonInteger.valueOf(i);
        } catch (NumberFormatException e) {
            throw e;
        }
    }
    
    public PythonObject __long__() {
        throw new UnsupportedOperationException("__long__");
    }
    
    public PythonFloat __float__() {
        try {
            double d = Double.parseDouble(value);
            return PythonFloat.valueOf(d);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public PythonString __str__() {
        return this;
    }

    public PythonObject __add__(PythonObject other) {
        PythonObject o = other.dereference();
        if (other instanceof PythonString) {
            return PythonString.valueOf(value + ((PythonString)o).asString());
        } else {
            throw new IllegalArgumentException("PythonString can only add another PythonString");
        }
    }
    
    public String toString() {
        return value;
    }

    public PythonIterator __iter__() {
        return new PythonIterator(new StringIterator(value));
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof PythonString) {
            return value.equals(((PythonString)o).asString());
        }
        return false;
    }

}
