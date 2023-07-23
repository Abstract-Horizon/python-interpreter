package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonNumber;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonSequence;
import org.ah.python.interpreter.PythonSlice;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.interpreter.ThreadContext.Executable;

class PyGameVector2 extends PythonSequence {

    public static PythonClass PYGAME_VECTOR2_CLASS = new PythonClass("pygame.math.Vector2") {
        @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
            context.pushData(new PyGameVector2());
        }

        {
            addMethod(new BuiltInBoundMethod("tick") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            }});
            addMethod(new BuiltInBoundMethod("get_fps") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            }});
            addMethod(new BuiltInBoundMethod("get_time") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            }});
        }
    };

    private final List<PythonObject> list;

    public PyGameVector2() {
        super(PYGAME_VECTOR2_CLASS);
        list = new ArrayList<>();
        list.add(PythonFloat.valueOf(0.0d));
        list.add(PythonFloat.valueOf(0.0d));
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
        list.remove(value);
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
}
