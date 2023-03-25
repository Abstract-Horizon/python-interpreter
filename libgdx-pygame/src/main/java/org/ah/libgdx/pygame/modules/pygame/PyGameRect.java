package org.ah.libgdx.pygame.modules.pygame;

import java.util.NoSuchElementException;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonInstantiableType;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonIterator;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.PythonType;
import org.ah.python.interpreter.util.IntIterator;

class PyGameRect extends Proxy {

    public static PythonType TYPE = new PythonInstantiableType(PythonObject.TYPE, PyGameDisplay.class) {
        @Override
        public PythonObject __call__(PythonObject[] args) {
            if (args.length == 4) {
                return new PyGameRect(args[0].asInteger(), args[1].asInteger(), args[2].asInteger(), args[3].asInteger());
            } else if (args.length == 2) {
                // TODO - this should be done in Python way - checking PythonType and if one of its ancestors is PyGameRectType...
                if (args[0] instanceof PythonTuple && args[1] instanceof PythonTuple
                        && ((PythonList)args[0]).asList().size() > 1
                        && ((PythonList)args[1]).asList().size() > 1) {
                    PythonList tuple1 = (PythonList)args[0];
                    PythonList tuple2 = (PythonList)args[1];
                    return new PyGameRect(tuple1.asList().get(0).asInteger(), tuple1.asList().get(1).asInteger(),
                            tuple2.asList().get(0).asInteger(), tuple2.asList().get(1).asInteger());
                } else {
                    throw new IllegalArgumentException("Wrong type of arguments for pygame.Rect(Tuple, Tuple) - there must be two Tuple types nad both with at least two elements");
                }
            } else if (args.length == 1) {
                // TODO - this should be done in Python way - checking PythonType and if one of its ancestors is PyGameRectType...
                if (args[0] instanceof PyGameRect) {
                    PyGameRect rect = (PyGameRect)args[0];
                    return new PyGameRect(rect.x, rect.y, rect.w, rect.h);
                } else {
                    throw new IllegalArgumentException("Wrong type of argument for pygame.Rect() - expected pygame.Rect");
                }
            } else {
                throw new IllegalArgumentException("Wrong number of arguments for pygame.Rect()");
            }
        }
    };

    static {
        TYPE.setAttribute("colliderect", new InstanceMethod<PyGameRect>() { @Override public PythonObject call0(PyGameRect self, PythonObject arg) {
            return self.colliderect(arg);
        }});
        TYPE.setAttribute("move", new InstanceMethod<PyGameRect>() {
            @Override public PythonObject call0(PyGameRect self, PythonObject x, PythonObject y) {
                return self.move(x.asInteger(), y.asInteger());
            }
            @Override public PythonObject call0(PyGameRect self, PythonObject tupple) {
                return self.move(tupple);
            }
        });
        TYPE.setAttribute("copy", new InstanceMethod<PyGameRect>() {
            @Override public PythonObject call0(PyGameRect self) {
                return new PyGameRect(self.x, self.y, self.w, self.h);
            }
        });
        TYPE.setAttribute("collidelist", new InstanceMethod<PyGameRect>() {
            @Override public PythonObject call0(PyGameRect self, PythonObject arg) {
                return self.collidelist(arg);
            }
            @Override public PythonObject call0(PyGameRect self, PythonObject x, PythonObject y) {
                return self.move(x.asInteger(), y.asInteger());
            }
         });
        TYPE.setAttribute("collidepoint", new InstanceMethod<PyGameRect>() {
            @Override public PythonObject call0(PyGameRect self, PythonObject arg) {
                return self.collidepoint(arg);
            }
            @Override public PythonObject call0(PyGameRect self, PythonObject x, PythonObject y) {
                return self.collidepoint(x, y);
            }
         }); // collidepoint
        TYPE.setAttribute("inflate", new InstanceMethod<PyGameRect>() { @Override public PythonObject call0(PyGameRect self, PythonObject w, PythonObject h) {
            return self.inflate(w.asInteger(), h.asInteger());
        }});
        TYPE.setAttribute("left", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) {
                return PythonInteger.valueOf(self.x); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.x = expr.asInteger(); }
        });
        TYPE.setAttribute("top", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.y); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.y = expr.asInteger(); }
        });
        TYPE.setAttribute("width", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.w); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.w = expr.asInteger(); }
        });
        TYPE.setAttribute("height", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.h); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.h = expr.asInteger(); }
        });
        TYPE.setAttribute("right", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.x + self.w); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.x = expr.asInteger() - self.w; }
        });
        TYPE.setAttribute("bottom", new Proxy.ProxyAttribute<PyGameRect>() {
            @Override public PythonObject attribute(PyGameRect self) { return PythonInteger.valueOf(self.y + self.h); }
            @Override public void assign(PyGameRect self, PythonObject expr) { self.y = expr.asInteger() - self.h; }
        });
        TYPE.setAttribute("center", new Proxy.ProxyAttribute<PyGameRect>() {
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

    protected int x;
    protected int y;

    protected int w;
    protected int h;

    public PyGameRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public PythonType getType() { return TYPE; }

    public PythonInteger __len__() {
        return PythonInteger.valueOf(4);
    }

    public PythonObject __getitem__(PythonObject key) {
        int i = key.asInteger();
        if (i == 0) {
            return PythonInteger.valueOf(x);
        } else if (i == 1) {
            return PythonInteger.valueOf(y);
        } else if (i == 2) {
            return PythonInteger.valueOf(w);
        } else if (i == 3) {
            return PythonInteger.valueOf(h);
        }
        throw new NoSuchElementException(Integer.toString(i));
    }

    public void __setitem__(PythonObject key, PythonObject value) {
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

    public void __delitem__(PythonObject key) {
        throw new UnsupportedOperationException("__delitem__");
    }

    public PythonIterator __iter__() {
        return new PythonIterator(new IntIterator(this));
    }

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
        } else {
            ox = arg.__getitem__(PythonInteger.valueOf(0)).asInteger();
            oy = arg.__getitem__(PythonInteger.valueOf(1)).asInteger();
            ow = arg.__getitem__(PythonInteger.valueOf(2)).asInteger();
            oh = arg.__getitem__(PythonInteger.valueOf(3)).asInteger();
        }
        return PythonBoolean.valueOf(x < ox + ow && x + w > ox && y < oy + oh && y + h > oy);
    }

    public PythonBoolean collidepoint(PythonObject arg) {
        int ox, oy;
        if (arg instanceof PyGameRect) {
            PyGameRect o = (PyGameRect)arg;
            ox = o.x;
            oy = o.y;
        } else {
            ox = arg.__getitem__(PythonInteger.valueOf(0)).asInteger();
            oy = arg.__getitem__(PythonInteger.valueOf(1)).asInteger();
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
        PythonIterator iterator = arg.__iter__();
        PythonObject o = iterator.next();
        while (o != null) {
            PythonBoolean r = colliderect(o);
            if (r.asBoolean()) {
                return PythonInteger.valueOf(i);
            }
            i++;
            o = iterator.next();
        }
        return PythonInteger.valueOf(-1);
    }
}