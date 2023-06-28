package org.ah.libgdx.pygame.modules.pygame;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.BuiltInIObject;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.ThreadContext;

class PyGameRect extends BuiltInIObject<PyGameRect> implements ListAccessible {

    public static PythonClass PYGAME_RECT_CLASS = new PythonClass("pygame.Rect") {
        public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            int x = 0;
            int y = 0;
            int w = 0;
            int h = 0;
            if (args.length == 4) {
                x = args[0].asInteger();
                y = args[1].asInteger();
                w = args[2].asInteger();
                h = args[3].asInteger();
            } else if (args.length == 2) {
                // TODO - this should be done in Python way - checking PythonType and if one of its ancestors is PyGameRectType...
                if (args[0] instanceof ListAccessible && args[1] instanceof ListAccessible
                        && ((PythonList)args[0]).asList().size() > 1
                        && ((PythonList)args[1]).asList().size() > 1) {
                    List<PythonObject> tuple1 = ((PythonList)args[0]).asList();
                    List<PythonObject> tuple2 = ((PythonList)args[1]).asList();
                    x = tuple1.get(0).asInteger();
                    y = tuple1.get(1).asInteger();
                    w = tuple2.get(0).asInteger();
                    h = tuple1.get(1).asInteger();
                } else {
                    throw new IllegalArgumentException("Wrong type of arguments for pygame.Rect(Tuple, Tuple) - there must be two Tuple types nad both with at least two elements");
                }
            } else if (args.length == 1) {
                // TODO - this should be done in Python way - checking PythonType and if one of its ancestors is PyGameRectType...
                if (args[0] instanceof PyGameRect) {
                    PyGameRect rect = (PyGameRect)args[0];
                    x = rect.x;
                    y = rect.y;
                    w = rect.w;
                    h = rect.h;
                } else {
                    throw new IllegalArgumentException("Wrong type of argument for pygame.Rect() - expected pygame.Rect");
                }
            } else {
                throw new IllegalArgumentException("Wrong number of arguments for pygame.Rect()");
            }
            PyGameRect newPyGameRect = new PyGameRect(x, y, w, h);
            context.pushData(newPyGameRect);
        }
        {
            addMethod(new BuiltInBoundMethod("__getitem__") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameRect self = (PyGameRect)args[0];

                self.__getitem__(context, args[1]);
            }});
            addMethod(new BuiltInBoundMethod("__setitem__") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameRect self = (PyGameRect)args[0];

                self.__setitem__(context, args[1], args[2]);
            }});
            addMethod(new BuiltInBoundMethod("colliderect") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameRect self = (PyGameRect)args[0];

                context.pushData(self.colliderect(args[1]));
            }});
            addMethod(new BuiltInBoundMethod("move") {
                @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    PyGameRect self = (PyGameRect)args[0];

                    if (args[1] instanceof PythonTuple) {
                        context.pushData(self.move(args[1]));
                    } else {
                        context.pushData(self.move(args[1].asInteger(), args[2].asInteger()));
                    }
                }
            });
            addMethod(new BuiltInBoundMethod("copy") {
                @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    PyGameRect self = (PyGameRect)args[0];

                    context.pushData(new PyGameRect(self.x, self.y, self.w, self.h));
                }
            });
            addMethod(new BuiltInBoundMethod("collidelist") {
                @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    PyGameRect self = (PyGameRect)args[0];
                    context.pushData(self.collidelist(args[1]));
                }
             });
            addMethod(new BuiltInBoundMethod("collidepoint") {
                @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    PyGameRect self = (PyGameRect)args[0];

                    if (args[1] instanceof PythonTuple) {
                        context.pushData(self.collidepoint(args[1]));
                    } else {
                        context.pushData(self.collidepoint(args[1], args[2]));
                    }
                }
             }); // collidepoint
            addMethod(new BuiltInBoundMethod("inflate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameRect self = (PyGameRect)args[0];

                context.pushData(self.inflate(args[1].asInteger(), args[2].asInteger()));
            }});

            setAttribute("x", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.x); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.x = expr.asInteger(); }
            });
            setAttribute("left", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.x); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.x = expr.asInteger(); }
            });
            setAttribute("y", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.y); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.y = expr.asInteger(); }
            });
            setAttribute("top", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.y); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.y = expr.asInteger(); }
            });
            setAttribute("w", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.w); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.w = expr.asInteger(); }
            });
            setAttribute("width", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.w); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.w = expr.asInteger(); }
            });
            setAttribute("h", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.h); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.h = expr.asInteger(); }
            });
            setAttribute("height", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.h); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.h = expr.asInteger(); }
            });
            setAttribute("right", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.x + self.w); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.x = expr.asInteger() - self.w; }
            });
            setAttribute("bottom", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.y + self.h); }
                @Override public void assign(PyGameRect self, PythonObject expr) { self.y = expr.asInteger() - self.h; }
            });
            setAttribute("center", new Attribute<PyGameRect>() {
                @Override public PythonObject attribute(PyGameRect self) {
                    PythonTuple tuple = new PythonTuple();
                    tuple.asList().add(PythonInteger.valueOf(self.x + self.w / 2));
                    tuple.asList().add(PythonInteger.valueOf(self.y + self.h / 2));
                    return tuple;
                }
                @Override public void assign(PyGameRect self, PythonObject expr) {
                    if (expr instanceof PythonList) {
                        PythonList list = (PythonList)expr;

                        self.x = list.asList().get(0).asInteger() - self.w / 2;
                        self.y = list.asList().get(1).asInteger() - self.h / 2;
                    } else {
                        throw new UnsupportedOperationException("center expected a list");
                    }
                }
            });
        }
    };

    protected int x;
    protected int y;

    protected int w;
    protected int h;

    public PyGameRect(int x, int y, int w, int h) {
        super(PYGAME_RECT_CLASS);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    public PythonInteger __len__() {
        return PythonInteger.valueOf(4);
    }

    public void __getitem__(ThreadContext context, PythonObject key) {
        int i = key.asInteger();
        if (i == 0) {
            context.pushData(PythonInteger.valueOf(x));
        } else if (i == 1) {
            context.pushData(PythonInteger.valueOf(y));
        } else if (i == 2) {
            context.pushData(PythonInteger.valueOf(w));
        } else if (i == 3) {
            context.pushData(PythonInteger.valueOf(h));
        } else {
            throw new NoSuchElementException(Integer.toString(i));
        }
    }

    public void __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        int i = key.asInteger();
        if (i == 0) {
            x = value.asInteger();
        } else if (i == 1) {
            y = value.asInteger();
        } else if (i == 2) {
            w = value.asInteger();
        } else if (i == 3) {
            h = value.asInteger();
        } else {
            throw new NoSuchElementException(Integer.toString(i));
        }
    }

    public void __delitem__(ThreadContext context, PythonObject key) {
        throw new UnsupportedOperationException(context.position() + "__delitem__");
    }

//    public PythonIterator __iter__() {
//        return new PythonIterator(new IntIterator(this));
//    }

    public PythonObject __reversed__() {
        throw new UnsupportedOperationException("__reversed__");
    }

    public PythonObject __contains__(PythonObject value) {
        int i = value.asInteger();
        return PythonBoolean.valueOf(i >= 0 && i <= 3);
    }

    public PyGameRect move(int dx, int dy) {
        return new PyGameRect(x + dx, y + dy, w, h);
    }

    public PyGameRect move(PythonObject o) {

        if (o instanceof PythonList) {
            PythonList list = (PythonList)o;

            return new PyGameRect(x + list.asList().get(0).asInteger(), y + list.asList().get(1).asInteger(), w, h);
        }

        throw new UnsupportedOperationException("move expected a list");
    }

    public PyGameRect inflate(int w, int h) {
        return new PyGameRect(x, y, this.w * w, this.h * h);
    }

    public PythonBoolean colliderect(PythonObject arg) {
        int ox, oy, ow, oh;
        if (arg instanceof PyGameRect) {
            PyGameRect o = (PyGameRect)arg;
            ox = o.x;
            oy = o.y;
            ow = o.w;
            oh = o.h;
        } else if (arg instanceof ListAccessible) {
            List<PythonObject> list = ((ListAccessible)arg).asList();
            ox = list.get(0).asInteger();
            oy = list.get(1).asInteger();
            ow = list.get(2).asInteger();
            oh = list.get(3).asInteger();

        } else {
            // TODO this requires continuation for executing __getitem__
            // ox = arg.__getitem__(PythonInteger.valueOf(0)).asInteger();
            // oy = arg.__getitem__(PythonInteger.valueOf(1)).asInteger();
            // ow = arg.__getitem__(PythonInteger.valueOf(2)).asInteger();
            // oh = arg.__getitem__(PythonInteger.valueOf(3)).asInteger();
            throw new UnsupportedOperationException("colliderect on arbitrary object does not work yet");
        }
        return PythonBoolean.valueOf(x < ox + ow && x + w > ox && y < oy + oh && y + h > oy);
    }

    public PythonBoolean collidepoint(PythonObject arg) {
        int ox, oy;
        if (arg instanceof PyGameRect) {
            PyGameRect o = (PyGameRect)arg;
            ox = o.x;
            oy = o.y;
        } else if (arg instanceof ListAccessible) {
            List<PythonObject> list = ((ListAccessible)arg).asList();
            ox = list.get(0).asInteger();
            oy = list.get(1).asInteger();
        } else {
            // TODO this requires continuation for executing __getitem__
            // ox = arg.__getitem__(PythonInteger.valueOf(0)).asInteger();
            // oy = arg.__getitem__(PythonInteger.valueOf(1)).asInteger();
            throw new UnsupportedOperationException("collidepoint on arbitrary object does not work yet");
        }
        return PythonBoolean.valueOf(ox >= x && ox <= x + w && oy >= y && oy <= y + h);
    }

    public PythonBoolean collidepoint(PythonObject px, PythonObject py) {
        int ox = px.asInteger();
        int oy = py.asInteger();
        return PythonBoolean.valueOf(ox >= x && ox <= x + w && oy >= y && oy <= y + h);
    }

    public PythonInteger collidelist(PythonObject arg) {
        int i = 0;
        if (arg instanceof ListAccessible) {
            List<PythonObject> list = ((ListAccessible)arg).asList();
            for (PythonObject o : list) {
                PythonBoolean r = colliderect(o);
                if (r.asBoolean()) {
                    return PythonInteger.valueOf(i);
                }
            }
            return PythonInteger.valueOf(-1);
        } else {
            // TODO this requires continuation for executing iterator
            // PythonIterator iterator = arg.__iter__();
            // PythonObject o = iterator.next();
            // while (o != null) {
            //     PythonBoolean r = colliderect(o);
            //     if (r.asBoolean()) {
            //         return PythonInteger.valueOf(i);
            //     }
            //     i++;
            //     o = iterator.next();
            // }
            // return PythonInteger.valueOf(-1);
            throw new UnsupportedOperationException("collidelist on arbitrary object does not work yet");
        }
    }

    @Override
    public List<PythonObject> asList() {
        return Arrays.<PythonObject>asList(
            PythonInteger.valueOf(x),
            PythonInteger.valueOf(y),
            PythonInteger.valueOf(w),
            PythonInteger.valueOf(h)
         );
    }
}