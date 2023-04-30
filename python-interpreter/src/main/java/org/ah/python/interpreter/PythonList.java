package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonNone.NONE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PythonList extends PythonSequence {

    public static PythonClass PYTHON_LIST_CLASS = new PythonClass("list");

    static {
        populateCommonSequenceObjects(PYTHON_LIST_CLASS);

        PYTHON_LIST_CLASS.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(context, args.get(1));
            }
        });
        PYTHON_LIST_CLASS.__setattr__("__len__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__len__(context);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("append", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return ((PythonList)args.get(0)).append(context, args.get(1));
            }
        });
        PYTHON_LIST_CLASS.__setattr__("clear", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return ((PythonList)args.get(0)).clear(context);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("remove", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return ((PythonList)args.get(0)).remove(context, args.get(1));
            }
        });
    }

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PythonList.class);

    private final List<PythonObject> list;

    public PythonList() {
        this(new ArrayList<PythonObject>());
    }

    public PythonList(List<PythonObject> list) {
        this.list = list;
        this.pythonClass = PYTHON_LIST_CLASS;
    }


    public PythonType getType() {
        return TYPE;
    }


    public static PythonObject constructor(final List<PythonObject> elements) {
        final ArrayList<PythonObject> storedElements = new ArrayList<PythonObject>(elements);
            return new Constructor() {
                @Override public PythonObject __call__(ThreadContext context) {
                    PythonList list = new PythonList();
                    for (PythonObject o : storedElements) {
                        PythonObject r = o.dereference();
                        list.asList().add(r);
                    }
                    return list;
                }
                @Override public String toString() {
                    return "CreateList" + storedElements;
                }
        };
    };

    public boolean isConstant() {
        for (PythonObject v : asList()) {
            if (!v.isConstant()) {
                return false;
            }
        }
        return true;
    }

    public PythonObject dereferenceConstant() {
        return this;
    }

    @Override
    public boolean asBoolean(ThreadContext context) {
        return list.size() != 0;
    }

    public List<PythonObject> asList() {
        return list;
    }

    @Override
    public PythonInteger __len__(ThreadContext context) {
        return PythonInteger.valueOf(list.size());
    }

    public PythonObject append(ThreadContext context, PythonObject value) {
        // return PythonNone.NONE;
        context.raise(exception("AttributeError", PythonString.valueOf("list.append")));
        return NONE;
    }

    public PythonObject clear(ThreadContext context) {
        // return PythonNone.NONE;
        context.raise(exception("AttributeError", PythonString.valueOf("list.clear")));
        return NONE;
    }

    public PythonObject remove(ThreadContext context, PythonObject value) {
        // return PythonNone.NONE;
        context.raise(exception("AttributeError", PythonString.valueOf("list.remove")));
        return NONE;
    }

    private ThreadContext.Executable setItemContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            return null;
        }
    };

    @Override
    public PythonObject __getitem__(ThreadContext context, PythonObject key) {
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
            int i = key.asInteger(context);  // TODO check if it is number (integer)
            if (i < 0) {
                if (-i >= list.size()) {
                    // RAISE IndexError: list index out of range
                    throw new IllegalArgumentException("IndexError: list index out of range");
                }
                return list.get(list.size() + i);
            } else if (i >= list.size()) {
                // RAISE IndexError: list index out of range
                throw new IllegalArgumentException("IndexError: list index out of range");
            }
            return list.get(i);
        }
    }

    @Override
    public PythonObject __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();

            if (from == 0 && to == -1) {
                list.clear();
                if (value instanceof PythonList) {
                    list.addAll(((PythonList)value).asList());
                } else if (value instanceof PythonSequence) {
                    context.raise(exception("IndexError", PythonString.valueOf("__getitem__, slice key and sequence value")));
                    return NONE;
                } else {
                    list.add(value);
                }
            } else {
                if (to == -1) { to = list.size() - 1; }
                for (int i = to - 1; i >= from; i--) {
                    list.remove(i);
                }
                if (value instanceof PythonList) {
                    for (PythonObject o : ((PythonList)value).asList()) {
                        list.add(from, o);
                    }
                } else if (value instanceof PythonSequence) {
                    context.raise(exception("AttributeError", PythonString.valueOf("__setitem__, slice key and sequence value")));
                    return NONE;
                } else {
                    list.add(from, value);
                }

            }
        } else {
            int i = key.dereference().asInteger(context);
            while (i >= list.size()) {
                list.add(PythonNone.NONE);
            }
            list.set(i, value);
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonObject __delitem__(ThreadContext context, PythonObject key) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();

            if (from == 0 && to == -1) {
                list.clear();
            } else {
                if (to == -1) { to = list.size() - 1; }
                for (int i = to - 1; i >= from; i--) {
                    list.remove(i);
                }
            }
        } else {
            list.remove(key.asInteger(context));
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonIterator __iter__(ThreadContext context) {
        return new PythonIterator(new ListIterator<PythonObject>(context, list));
    }


    @Override
    public PythonObject __getattr__(ThreadContext context, String name) {
//        PythonObject o = TYPE.getAttribute(name);
//
//        if (o == null) {
//            throw new NoSuchElementException(name);
//        }
//        return o;
        return PythonNone.NONE;
    }

//    public PythonObject __getattr__(PythonObject name) {
//        if ("append".equals(name.asString())) {
//            return append;
//        }
//        if ("clear".equals(name.asString())) {
//            return clear;
//        }
//        throw new NoSuchElementException(name.asString());
//    }

    public String asString() {
        return toString();
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        res.append(collectionToString(list, ", "));
        res.append("]");
        return res.toString();
    }

    public static class ListIterator<T> implements Iterator<T> {

        private List<T> list;
        private ThreadContext context;
        int i = 0;

        public ListIterator(ThreadContext context, List<T> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public boolean hasNext() {
            return i < list.size();
        }

        @Override
        public T next() {
            if (i < list.size()) {
                int p = i;
                i++;
                return list.get(p);
            } else {
                return null;
            }
        }

        @Override
        public void remove() {
            context.raise(exception("AttributeError", PythonString.valueOf("Remove on List Iterator")));
        }
    }
}
