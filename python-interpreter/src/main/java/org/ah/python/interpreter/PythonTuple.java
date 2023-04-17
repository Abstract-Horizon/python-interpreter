package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PythonTuple extends PythonSequence {

    public static PythonClass PYTHON_TUPLE_CLASS = new PythonClass("tuple");

    static {
        PYTHON_TUPLE_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(args.get(1));
            }
        });
        PYTHON_TUPLE_CLASS.__setattr__("__getitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__getitem__(args.get(1));
            }
        });
    }

    private final List<PythonObject> list;

    public PythonTuple() {
        list = new ArrayList<PythonObject>();
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

    public List<PythonObject> asList() {
        return list;
    }

    public PythonObject __getitem__(PythonObject key) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();
            if (from == 0 && to == -1) {
                return this;
            } else {
                if (to == -1) { to = list.size() - 1; }
                return new PythonList(list.subList(from, to));
            }
        } else {
            int i = key.asInteger();
            if (i < list.size()) {
                return list.get(i);
            } else {
                throw new RuntimeException("IndexError: list index out of range");
            }
        }
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
