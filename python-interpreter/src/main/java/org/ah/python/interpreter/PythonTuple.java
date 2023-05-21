package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PythonTuple extends PythonSequence {

    public static PythonClass PYTHON_TUPLE_CLASS = new PythonClass("tuple");

    static {
        PYTHON_TUPLE_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__add__(context, args[1]);
            }
        });
        PYTHON_TUPLE_CLASS.__setattr__("__getitem__", new BuiltInBoundMethod() {
            public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                args[0].__getitem__(context, args[1]);
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
            throw new UnsupportedOperationException("Tuple.constructor");
//            return new Constructor() {
//                @Override public PythonObject __call__(ThreadContext context) {
//                    PythonTuple tuple = new PythonTuple();
//                    for (PythonObject o : storedElements) {
//                        PythonObject r = o.dereference();
//                        tuple.asList().add(r);
//                    }
//                    return tuple;
//                }
//                @Override public String toString() {
//                    return "CreateTuple" + storedElements;
//                }
//            };
        }
    };

    public boolean isConstant() {
        return true;
    }

    public List<PythonObject> asList() {
        return list;
    }

    public void __getitem__(ThreadContext context, PythonObject key) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();
            if (from == 0 && to == -1) {
                context.pushData(this);
            } else {
                if (to == -1) { to = list.size() - 1; }
                context.pushData(new PythonList(list.subList(from, to)));
            }
        } else if (key instanceof PythonNumber) {
            int i = ((PythonNumber)key).asInteger();
            if (i < list.size()) {
                context.pushData(list.get(i));
            } else {
                context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
            }
        } else {
            context.raise(exception("TypeError", PythonString.valueOf("list indices must be integers or slices, not " + key.pythonClass.name)));
        }
    }

    @Override
    public void __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        context.raise(exception("AttributeError", PythonString.valueOf("__setitem__)")));
    }

    @Override
    public void __delitem__(ThreadContext context, PythonObject key) {
        context.raise(exception("AttributeError", PythonString.valueOf("__delitem__)")));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Tuple(");
        res.append(collectionToString(asList(), ", "));
        res.append(")");
        return res.toString();
    }

}
