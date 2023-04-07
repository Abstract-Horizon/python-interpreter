package org.ah.python.modules;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;
import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.ZERO;
import static org.ah.python.interpreter.PythonNone.NONE;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.Call;
import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonIterator;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonSlice;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.Scope;
import org.ah.python.interpreter.util.RangeIterator;

public class BuiltInFunctions extends Scope {

    private static final Map<String, PythonObject> functions = new HashMap<String, PythonObject>();

    public static boolean isBuiltInFunction(String name) {
        return functions.containsKey(name);
    }

    public static PythonObject getClosure(String name, PythonObject[] args) {
        return new Call(functions.get(name), args);
    }

    public static PythonObject getFunctione(String name) {
        return functions.get(name);
    }

//    public static PythonObject invoke(String name, PythonObject[] args) {
//        return functions.get(name).__call__(args);
//    }

    public static void setOutput(OutputStream os) {
        out = new PrintStream(os);
    }

    private static PrintStream out = System.out;
    private static InputStream in;
//    private static Scanner scanner;

    public static PrintInterface printInterface = new PrintInterface() {
        @Override
        public void print(String s) {
            getOutput().println(s);
        }
    };

    private static PrintStream getOutput() { return out; }
    @SuppressWarnings("unused")
    private static InputStream getInput() { return in; }
//    public static Scanner getScanner() { return scanner; }

    static {
//        in = System.in;
//        scanner = new Scanner(in);

        functions.put("abs", new Function() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                if (a.asInteger() < 0) {
                    return PythonInteger.valueOf(-a.asInteger());
                }
                return a;
            } else if (a instanceof PythonFloat) {
                if (a.asFloat() < 0) {
                    return PythonFloat.valueOf(-a.asFloat());
                }
                return a;
            }
            throw new UnsupportedOperationException("Function abs not supported on " + a.toString());
        }});
        functions.put("all", new Function() { @Override public PythonObject call0(PythonObject a) {
            PythonIterator iter = a.__iter__();
            PythonObject o = iter.next();
            while (o != null) {
                if (!o.asBoolean()) {
                    return FALSE;
                }
                o = iter.next();
            }
            return TRUE;
        }});
        functions.put("any", new Function() { @Override public PythonObject call0(PythonObject a) {
            PythonIterator iter = a.__iter__();
            PythonObject o = iter.next();
            while (o != null) {
                if (o.asBoolean()) {
                    return TRUE;
                }
                o = iter.next();
            }
            return FALSE;
        }});
        functions.put("ascii", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ascii not supported yet");
        }});
        functions.put("bin", new Function() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toBinaryString(a.asInteger()));
            }
            return PythonString.valueOf(Integer.toBinaryString(a.__index__().asInteger()));
        }});
        functions.put("bool", new Function() {
            @Override public PythonObject call0() { return FALSE; }
            @Override public PythonObject call0(PythonObject a) { return PythonBoolean.valueOf(a.asBoolean()); }
        });
        functions.put("bytearray", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function bytearray not supported yet");
        }});
        functions.put("callable", new Function() { @Override public PythonObject call0(PythonObject a) {
            return PythonBoolean.valueOf(a instanceof Function);
        }});
        functions.put("chr", new Function() { @Override public PythonObject call0(PythonObject a) {
            return PythonString.valueOf(Character.toString((char)a.asInteger()));
        }});
        functions.put("classmethod", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function classmethod not supported yet");
        }});
        functions.put("compile", new Function() { @Override public PythonObject __call__() {
            // TODO really nice thing to do!
            throw new UnsupportedOperationException("Function compile not supported yet");
        }});
        functions.put("complex", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function complex not supported yet");
        }});
        functions.put("delattr", new Function() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            object.__delattr__(name);
            return NONE;
        }});
        functions.put("dict", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function dict not supported yet");
        }});
        functions.put("dir", new Function() { @Override public PythonObject __call__() {
            // Nice thing to do
            throw new UnsupportedOperationException("Function dir not supported yet");
        }});
        functions.put("divmod", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function divmod not supported yet");
        }});
        functions.put("enumerate", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function enumerate not supported yet");
        }});
        functions.put("eval", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function eval not supported yet");
        }});
        functions.put("exec", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function exec not supported yet");
        }});
        functions.put("filter", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function filter not supported yet");
        }});
        functions.put("float", new Function() { @Override public PythonObject call0(PythonObject a) {
            return PythonFloat.valueOf(Double.parseDouble(a.asString()));
        }});
        functions.put("format", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function format not supported yet");
        }});
        functions.put("frozenset", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function frozenset not supported yet");
        }});
        functions.put("getattr", new Function() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            return object.__getattr__(name.asString());
        }});
        functions.put("globals", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function globals not supported yet");
        }});
        functions.put("hasattr", new Function() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            try {
                return PythonBoolean.valueOf(object.__getattr__(name.asString()) != null);
            } catch (Exception e) {
                return FALSE;
            }
        }});
        functions.put("hash", new Function() { @Override public PythonObject call0(PythonObject a) {
            return a.__hash__();
        }});
        functions.put("help", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function help not supported yet");
        }});
        functions.put("hex", new Function() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toHexString(a.asInteger()));
            }
            return PythonString.valueOf(Integer.toHexString(a.__index__().asInteger()));
        }});
        functions.put("id", new Function() { @Override public PythonObject call0(PythonObject a) {
            return PythonInteger.valueOf(System.identityHashCode(a));
        }});
//        functions.put("input", new Function() {
//            @Override public PythonObject call0() {
//                return PythonString.valueOf(scanner.nextLine());
//            }
//            @Override public PythonObject call0(PythonObject prompt) {
//                out.println(prompt.asString());
//                return call0();
//            }
//        });
        functions.put("int", new Function() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return a;
            } else if (a instanceof PythonFloat) {
                return PythonInteger.valueOf(a.asInteger());
            }
            return PythonInteger.valueOf(Integer.parseInt(a.asString()));
        }});
        functions.put("isinstance", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isinstance not supported yet");
        }});
        functions.put("issubclass", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function issubclass not supported yet");
        }});
        functions.put("iter", new Function() {
            @Override public PythonObject call0(PythonObject object) {
                return object.__iter__();
            }
            @Override public PythonObject call0(PythonObject object, PythonObject sentinel) {
                throw new UnsupportedOperationException("Function iter not supported yet");
            }
        });
        functions.put("len", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return arg.dereference().__len__();
        }});
        functions.put("list", new Function() {
            @Override public PythonObject call0() {
                return new PythonList();
            }
            @Override public PythonObject call0(PythonObject a) {
                PythonIterator iter = a.__iter__();
                PythonList list = new PythonList();
                PythonObject o = iter.next();
                while (o != null) {
                    list.asList().add(o);
                    o = iter.next();
                }

                return list;
            }
        });
        functions.put("locals", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function locals not supported yet");
        }});
        functions.put("map", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function map not supported yet");
        }});
        functions.put("max", new Function() { @Override public PythonObject __call__(PythonObject[] args) {
            if (args.length > 0) {
                if (args.length == 1 && args[0] instanceof PythonIterator) {
                    PythonIterator iter = args[0].__iter__();
                    PythonObject max = iter.next();
                    PythonObject o = max;
                    while (o != null) {
                        o = iter.next();
                        if (o.__gt__(max).asBoolean()) {
                            max = o;
                        }
                    }
                    return max;
                }
                PythonObject max = args[0];
                for (int i = 1; i < args.length; i++) {
                    if (args[i].__gt__(max).asBoolean()) {
                        max = args[i];
                    }
                }
            }
            throw new UnsupportedOperationException("Function max not supported yet");
        }});
        functions.put("memoryview", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function memoryview not supported yet");
        }});
        functions.put("min", new Function() { @Override public PythonObject __call__(PythonObject[] args) {
            if (args.length > 0) {
                if (args.length == 1 && args[0] instanceof PythonIterator) {
                    PythonIterator iter = args[0].__iter__();
                    PythonObject min = iter.next();
                    PythonObject o = min;
                    while (o != null) {
                        o = iter.next();
                        if (o.__lt__(min).asBoolean()) {
                            min = o;
                        }
                    }
                    return min;
                }
                PythonObject min = args[0];
                for (int i = 1; i < args.length; i++) {
                    if (args[i].__lt__(min).asBoolean()) {
                        min = args[i];
                    }
                }
            }
            throw new UnsupportedOperationException("Function max not supported yet");
        }});
        functions.put("next", new Function() {
            @Override public PythonObject call0(PythonObject a) {
                if (a instanceof PythonIterator) {
                    PythonIterator iter = (PythonIterator)a;
                    return iter.__next__();
                }
                throw new UnsupportedOperationException("Function next not supported for non iterators; " + a);
            }
            @Override public PythonObject call0(PythonObject a, PythonObject defaultO) {
                throw new UnsupportedOperationException("Function next not supported yet");
            }
        });
        functions.put("object", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function object not supported yet");
        }});
        functions.put("oct", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function oct not supported yet");
        }});
        functions.put("open", new Function() { @Override public PythonObject __call__(PythonObject[] args) {
            throw new UnsupportedOperationException("Function open not supported yet");
        }});
        functions.put("ord", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ord not supported yet");
        }});
        functions.put("pow", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        functions.put("print", new Function() {
            @Override public PythonObject __call__() {
                printInterface.print("\n");
                return PythonNone.NONE;
            }
            @Override public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                if (args.size() > 0) {
                    for (PythonObject a : args) {
                        printInterface.print(a.asString());
                    }
                } else {
                    printInterface.print("");
                }
                return PythonNone.NONE;
            }
        });
        functions.put("property", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function property not supported yet");
        }});
        functions.put("range", new Function() {
            @Override public PythonObject call0(PythonObject to) {
                return range(ZERO, to, ONE);
            }
            @Override public PythonObject call0(PythonObject from, PythonObject to) {
                return range(from, to, ONE);
            }
            @Override public PythonObject call0(PythonObject from, PythonObject to, PythonObject step) {
                return range(from, to, step);
            }
        });
        functions.put("repr", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function repr not supported yet");
        }});
        functions.put("reversed", new Function() { @Override public PythonObject call0(PythonObject a) {
            return a.__reversed__();
        }});
        functions.put("round",  new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.round(arg.asFloat()));
        }});
        functions.put("set", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function set not supported yet");
        }});
        functions.put("setattr", new Function() { @Override public PythonObject call0(PythonObject obj, PythonObject k, PythonObject v) {
            obj.__setattr__(k.asString(), v);
            return NONE;
        }});
        functions.put("slice", new Function() {
            @Override public PythonObject call0(PythonObject stop) {
                return PythonSlice.index(stop.asInteger());
            }
            @Override public PythonObject call0(PythonObject start, PythonObject stop) {
                return PythonSlice.range(start, stop);
            }
            @Override public PythonObject call0(PythonObject start, PythonObject stop, PythonObject step) {
                throw new UnsupportedOperationException("Function slice not supported yet");
            }
        });
        functions.put("sorted", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sorted not supported yet");
        }});
        functions.put("staticmethod", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function staticmethod not supported yet");
        }});
        functions.put("str", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return arg.dereference().__str__();
        }});
        functions.put("sum", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sum not supported yet");
        }});
        functions.put("super", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function super not supported yet");
        }});
        functions.put("tuple", new Function() {
            @Override public PythonObject call0() {
                return new PythonTuple();
            }
            @Override public PythonObject call0(PythonObject a) {
                PythonIterator iter = a.__iter__();
                PythonTuple list = new PythonTuple();
                PythonObject o = iter.next();
                while (o != null) {
                    list.asList().add(o);
                    o = iter.next();
                }

                return list;
            }
        });
        functions.put("type", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function type not supported yet");
        }});
        functions.put("vars", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function vars not supported yet");
        }});
        functions.put("zip", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function zip not supported yet");
        }});
        functions.put("__import__", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function __import__ not supported yet");
        }});
    }

    public static PythonIterator range(PythonObject from, PythonObject to, PythonObject step) {
        return new PythonIterator(new RangeIterator(from.asInteger(), to.asInteger(), step.asInteger()));
    }

    public static interface PrintInterface {
        void print(String s);
    }
}
