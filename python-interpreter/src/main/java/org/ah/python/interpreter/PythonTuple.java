package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.PythonList.ListIterator;

public class PythonTuple extends PythonSequence implements Immutable {

    public static PythonClass PYTHON_TUPLE_CLASS = new PythonClass("tuple") {
        {
            addMethod(new BuiltInBoundMethod("__add__") {
                public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__add__(context, args[1]);
                }
            });
            addMethod(new BuiltInBoundMethod("__getitem__") {
                public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__getitem__(context, args[1]);
                }
            });
            addMethod(new BuiltInBoundMethod("__iter__") {
                public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__iter__(context);
                }
            });
        }
    };

    private final List<PythonObject> list;

    public PythonTuple(PythonObject... objects) {
        super(PYTHON_TUPLE_CLASS);
        list = new ArrayList<PythonObject>(Arrays.asList(objects));
    }

    public static PythonObject constructor(final List<PythonObject> elements) {
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
            throw new UnsupportedOperationException("Tuple.constructor for non constructor values");
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

    @Override
    public void __iter__(ThreadContext context) {
        context.pushData(new PythonJavaIterator(new ListIterator<PythonObject>(context, list)));
    }

    public String asString() {
        return toString();
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("(");
        res.append(collectionToString(asList(), ", "));
        res.append(")");
        return res.toString();
    }

    public PythonObject copy() {
        return deepCopy();
    }

    public PythonObject deepCopy() {
        PythonTuple copy = new PythonTuple();
        for (PythonObject object : list) {
            copy.asList().add(object.deepCopy());
        }
        return copy;
    }
}
