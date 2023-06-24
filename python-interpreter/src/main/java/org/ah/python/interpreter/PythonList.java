package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonList extends PythonSequence {

    public static PythonClass PYTHON_LIST_CLASS = new PythonClass("list");

    static {
        populateCommonSequenceObjects(PYTHON_LIST_CLASS);

        PYTHON_LIST_CLASS.__setattr__("__add__", new BuiltInBoundMethod("__add__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__add__(context, args[1]);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("__len__", new BuiltInBoundMethod("__len__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__len__(context);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("__iter__", new BuiltInBoundMethod("__iter__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                args[0].__iter__(context);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("append", new BuiltInBoundMethod("append") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                ((PythonList)args[0]).append(context, args[1]);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("clear", new BuiltInBoundMethod("clear") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                ((PythonList)args[0]).clear(context);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("remove", new BuiltInBoundMethod("remove") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                ((PythonList)args[0]).remove(context, args[1]);
            }
        });
        PYTHON_LIST_CLASS.__setattr__("__reversed__", new BuiltInBoundMethod("__reversed__") {
            public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                ((PythonList)args[0]).__reversed__(context);
            }
        });
    }

    private final List<PythonObject> list;

    public PythonList() {
        this(new ArrayList<PythonObject>());
    }

    public PythonList(List<PythonObject> list) {
        super(PYTHON_LIST_CLASS);
        this.list = list;
    }

    public boolean isConstant() {
        for (PythonObject v : asList()) {
            if (!v.isConstant()) {
                return false;
            }
        }
        return true;
    }

    public void __bool__(ThreadContext context) {
        context.pushData(PythonBoolean.valueOf(list.size() != 0));
    }


    public List<PythonObject> asList() {
        return list;
    }

    @Override
    public void __len__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf(list.size()));
    }

    public void append(ThreadContext context, PythonObject value) {
        list.add(value);
    }

    public void clear(ThreadContext context) {
        list.clear();
    }

    public void remove(ThreadContext context, PythonObject value) {
        context.raise(exception("AttributeError", PythonString.valueOf("list.remove")));
    }

//    private Executable setItemContinuation = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//        }
//    };

    @Override
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
            int i = ((PythonNumber)key).asInteger();  // TODO check if it is number (integer)
            if (i < 0) {
                if (-i >= list.size()) {
                    context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
                } else {
                    context.pushData(list.get(list.size() + i));
                }
            } else if (i >= list.size()) {
                context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
            } else {
                context.pushData(list.get(i));
            }
        } else {
            context.continuation(new Executable() {
                @Override public void evaluate(ThreadContext context) {
                    int i = ((PythonNumber)context.popData()).asInteger();
                    if (i < 0) {
                        if (-i >= list.size()) {
                            context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
                        } else {
                            context.pushData(list.get(list.size() + i));
                        }
                    } else if (i >= list.size()) {
                        context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
                    } else {
                        context.pushData(list.get(i));
                    }
                }
            });
        }
    }

    @Override
    public void __setitem__(ThreadContext context, PythonObject key, final PythonObject value) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();

            if (from == 0 && to == -1) {
                list.clear();
                if (value instanceof PythonList) {
                    list.addAll(((PythonList)value).asList());
                } else if (value instanceof PythonSequence) {
                    context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
                    // return context.raise(exception("IndexError", PythonString.valueOf("__getitem__, slice key and sequence value")));
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
                    context.raise(exception("IndexError", PythonString.valueOf("list index out of range")));
                    // return context.raise(exception("AttributeError", PythonString.valueOf("__setitem__, slice key and sequence value")));
                } else {
                    list.add(from, value);
                }

            }
        } else if (key instanceof PythonNumber) {
            int i = ((PythonNumber)key).asInteger();
            while (i >= list.size()) {
                list.add(PythonNone.NONE);
            }
            list.set(i, value);
        } else {
            context.continuation(new Executable() {
                @Override public void evaluate(ThreadContext context) {
                    int i = ((PythonNumber)context.popData()).asInteger();
                    while (i >= list.size()) {
                        list.add(PythonNone.NONE);
                    }
                    list.set(i, value);
                }
            });
            key.__int__(context);
        }
    }

    @Override
    public void __delitem__(ThreadContext context, PythonObject key) {
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
        } else if (key instanceof PythonNumber) {
            list.remove(((PythonNumber)key).asInteger());
        } else {
            context.continuation(new Executable() {
                @Override public void evaluate(ThreadContext context) {
                    list.remove(((PythonInteger)context.popData()).value);
                }
            });
            key.__int__(context);
        }
    }

    @Override
    public void __iter__(ThreadContext context) {
        // context.pushData(new PythonIterator(new ListIterator<PythonObject>(context, list)));
        context.pushData(new PythonIterator(list.iterator()));
    }

    @Override
    public void __reversed__(ThreadContext context) {
        List<PythonObject> copy = new ArrayList<PythonObject>(list);
        Collections.reverse(copy);
        context.pushData(new PythonList(copy));
    }


//    @Override
//    public void __getattr__(ThreadContext context, String name) {
////        PythonObject o = TYPE.getAttribute(name);
////
////        if (o == null) {
////            throw new NoSuchElementException(name);
////        }
////        return o;
//        return PythonNone.NONE;
//    }

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



//    public static PythonObject constructor(final List<PythonObject> elements, PythonClass pythonClass) {
//        final ArrayList<PythonObject> storedElements = new ArrayList<PythonObject>(elements);
//            return new Constructor() {
//                @Override public PythonObject __call__(ThreadContext context) {
//                    PythonList list = new PythonList();
//                    for (PythonObject o : storedElements) {
//                        PythonObject r = o.dereference();
//                        list.asList().add(r);
//                    }
//                    return list;
//                }
//                @Override public String toString() {
//                    return "CreateList" + storedElements;
//                }
//        };
//    };

}
