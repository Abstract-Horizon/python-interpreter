package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PythonTuple extends PythonList {

    public PythonTuple() {
    }

    public static PythonObject constructor(final List<PythonObject> elements) {
        final ArrayList<PythonObject> storedElements = new ArrayList<PythonObject>(elements);

        boolean constant = true;
        Iterator<PythonObject> iterator = elements.iterator();
        while (constant && iterator.hasNext()) {
            constant = constant && iterator.next().isConstant();
        }

        if (constant) {
            PythonTuple tuple = new PythonTuple();
            for (PythonObject o : elements) {
                tuple.asList().add(o.dereferenceConstant());
            }
            return tuple;
        } else {
            return new Constructor() {
                @Override public PythonObject __call__() {
                    PythonTuple tuple = new PythonTuple();
                    for (PythonObject o : storedElements) {
                        PythonObject r = o.dereference();
                        tuple.asList().add(r);
                    }
                    return tuple;
                }
                @Override public String toString() {
                    return "CreateTuple" + storedElements;
                }
            };
        }
    };

    public boolean isConstant() {
        return true;
    }

    @Override
    public PythonObject __setitem__(PythonObject key, PythonObject value) {
        throw new UnsupportedOperationException("__setitem__");
    }

    @Override
    public PythonObject __delitem__(PythonObject key) {
        throw new UnsupportedOperationException("__delitem__");
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Tuple(");
        res.append(collectionToString(asList(), ", "));
        res.append(")");
        return res.toString();
    }

}
