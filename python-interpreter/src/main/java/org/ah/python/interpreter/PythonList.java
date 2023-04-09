package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PythonList extends PythonSequence {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PythonList.class);

    private final List<PythonObject> list;

    public PythonList() {
        list = new ArrayList<PythonObject>();
    }

    public PythonList(List<PythonObject> list) {
        this.list = list;
    }

    static {
        TYPE.__setattr__("append", new InstanceMethod<PythonList>() { @Override public PythonObject call0(PythonList self, PythonObject arg) {
            self.list.add(arg.dereference());
            return PythonNone.NONE;
        }});

        TYPE.__setattr__("clear", new InstanceMethod<PythonList>() { @Override public PythonObject call0(PythonList self) {
            self.list.clear();
            return PythonNone.NONE;
        }});

        TYPE.__setattr__("remove", new InstanceMethod<PythonList>() { @Override public PythonObject call0(PythonList self, PythonObject arg) {
            self.list.remove(arg.dereference());
            return PythonNone.NONE;
        }});

    }

    public PythonType getType() {
        return TYPE;
    }


    public static PythonObject constructor(final List<PythonObject> elements) {
        final ArrayList<PythonObject> storedElements = new ArrayList<PythonObject>(elements);
            return new Constructor() {
                @Override public PythonObject __call__() {
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
    public boolean asBoolean() {
        return list.size() != 0;
    }

    public List<PythonObject> asList() {
        return list;
    }

    @Override
    public PythonInteger __len__() {
        return PythonInteger.valueOf(list.size());
    }

    @Override
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
            int i = key.dereference().asInteger();
            if (i < list.size()) {
                return list.get(i);
            } else {
                return PythonNone.NONE;
            }
        }
    }

    @Override
    public PythonObject __setitem__(PythonObject key, PythonObject value) {
        if (key instanceof PythonSlice) {
            PythonSlice slice = (PythonSlice)key;
            int from = slice.getFrom();
            int to = slice.getTo();

            if (from == 0 && to == -1) {
                list.clear();
                if (value instanceof PythonList) {
                    list.addAll(((PythonList)value).asList());
                } else if (value instanceof PythonSequence) {
                    throw new UnsupportedOperationException("__getitem__, slice key and sequence value");
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
                    throw new UnsupportedOperationException("__setitem__, slice key and sequence value");
                } else {
                    list.add(from, value);
                }

            }
        } else {
            int i = key.dereference().asInteger();
            while (i >= list.size()) {
                list.add(PythonNone.NONE);
            }
            list.set(i, value);
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonObject __delitem__(PythonObject key) {
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
            list.remove(key.asInteger());
        }
        return PythonNone.NONE;
    }

    @Override
    public PythonIterator __iter__() {
        return new PythonIterator(new ListIterator<PythonObject>(list));
    }


    @Override
    public PythonObject __getattr__(String name) {
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
        int i = 0;

        public ListIterator(List<T> list) {
            this.list = list;
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
            throw new UnsupportedOperationException("Remove on List Iterator");
        }
    }
}
