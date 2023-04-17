package org.ah.python.modules;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;
import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.ZERO;
import static org.ah.python.interpreter.PythonNone.NONE;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
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
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.interpreter.util.RangeIterator;

public class BuiltInFunctions extends Scope {

//    private static final Map<String, PythonObject> functions = new HashMap<String, PythonObject>();

    public static BuiltInFunctions BUILT_IN_FUNCTIONS_SCOPE;

    public static boolean isBuiltInFunction(String name) {
        return BUILT_IN_FUNCTIONS_SCOPE.attributes.containsKey(name);
    }

    public static PythonObject getClosure(String name, PythonObject[] args) {
        return new Call(BUILT_IN_FUNCTIONS_SCOPE.attributes.get(name), args);
    }

    public static PythonObject getFunction(String name) {
        return BUILT_IN_FUNCTIONS_SCOPE.attributes.get(name);
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

    static {
        BUILT_IN_FUNCTIONS_SCOPE = new BuiltInFunctions();

        Map<String, PythonObject> attributes = BUILT_IN_FUNCTIONS_SCOPE.attributes;

        attributes.put("abs", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
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
        attributes.put("all", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
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
        attributes.put("any", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
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
        attributes.put("ascii", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ascii not supported yet");
        }});
        attributes.put("bin", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toBinaryString(a.asInteger()));
            }
            return PythonString.valueOf(Integer.toBinaryString(a.__index__().asInteger()));
        }});
        attributes.put("bool", new BuiltInMethod() {
            @Override public PythonObject call0() { return FALSE; }
            @Override public PythonObject call0(PythonObject a) { return PythonBoolean.valueOf(a.asBoolean()); }
        });
        attributes.put("bytearray", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function bytearray not supported yet");
        }});
        attributes.put("callable", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return PythonBoolean.valueOf(a instanceof Function);
        }});
        attributes.put("chr", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return PythonString.valueOf(Character.toString((char)a.asInteger()));
        }});
        attributes.put("classmethod", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function classmethod not supported yet");
        }});
        attributes.put("compile", new BuiltInMethod() { @Override public PythonObject __call__() {
            // TODO really nice thing to do!
            throw new UnsupportedOperationException("Function compile not supported yet");
        }});
        attributes.put("complex", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function complex not supported yet");
        }});
        attributes.put("delattr", new BuiltInMethod() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            object.__delattr__(name.asString());
            return NONE;
        }});
        attributes.put("dict", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function dict not supported yet");
        }});
        attributes.put("dir", new BuiltInMethod() { @Override public PythonObject __call__() {
            // Nice thing to do
            throw new UnsupportedOperationException("Function dir not supported yet");
        }});
        attributes.put("divmod", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function divmod not supported yet");
        }});
        attributes.put("enumerate", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function enumerate not supported yet");
        }});
        attributes.put("eval", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function eval not supported yet");
        }});
        attributes.put("exec", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function exec not supported yet");
        }});
        attributes.put("filter", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function filter not supported yet");
        }});
        attributes.put("float", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return PythonFloat.valueOf(Double.parseDouble(a.asString()));
        }});
        attributes.put("format", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function format not supported yet");
        }});
        attributes.put("frozenset", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function frozenset not supported yet");
        }});
        attributes.put("getattr", new BuiltInMethod() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            return object.__getattr__(name.asString());
        }});
        attributes.put("globals", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function globals not supported yet");
        }});
        attributes.put("hasattr", new BuiltInMethod() { @Override public PythonObject call0(PythonObject object, PythonObject name) {
            try {
                return PythonBoolean.valueOf(object.__getattr__(name.asString()) != null);
            } catch (Exception e) {
                return FALSE;
            }
        }});
        attributes.put("hash", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return a.__hash__();
        }});
        attributes.put("help", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function help not supported yet");
        }});
        attributes.put("hex", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toHexString(a.asInteger()));
            }
            return PythonString.valueOf(Integer.toHexString(a.__index__().asInteger()));
        }});
        attributes.put("id", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return PythonInteger.valueOf(System.identityHashCode(a));
        }});
//        attributes.put("input", new BuiltInMethod() {
//            @Override public PythonObject call0() {
//                return PythonString.valueOf(scanner.nextLine());
//            }
//            @Override public PythonObject call0(PythonObject prompt) {
//                out.println(prompt.asString());
//                return call0();
//            }
//        });
        attributes.put("int", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            if (a instanceof PythonInteger) {
                return a;
            } else if (a instanceof PythonFloat) {
                return PythonInteger.valueOf(a.asInteger());
            }
            return PythonInteger.valueOf(Integer.parseInt(a.asString()));
        }});
        attributes.put("isinstance", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isinstance not supported yet");
        }});
        attributes.put("issubclass", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function issubclass not supported yet");
        }});
        attributes.put("iter", new BuiltInMethod() {
            @Override public PythonObject call0(PythonObject object) {
                return object.__iter__();
            }
            @Override public PythonObject call0(PythonObject object, PythonObject sentinel) {
                throw new UnsupportedOperationException("Function iter not supported yet");
            }
        });
        attributes.put("len", new BuiltInMethod() { @Override public PythonObject call0(PythonObject arg) {
            return arg.dereference().__len__();
        }});
        attributes.put("list", new BuiltInMethod() {
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
        attributes.put("locals", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function locals not supported yet");
        }});
        attributes.put("map", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function map not supported yet");
        }});
        attributes.put("max", new BuiltInMethod() { @Override public PythonObject __call__(PythonObject[] args) {
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
        attributes.put("memoryview", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function memoryview not supported yet");
        }});
        attributes.put("min", new BuiltInMethod() { @Override public PythonObject __call__(PythonObject[] args) {
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
        attributes.put("next", new BuiltInMethod() {
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
        attributes.put("object", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function object not supported yet");
        }});
        attributes.put("oct", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function oct not supported yet");
        }});
        attributes.put("open", new BuiltInMethod() { @Override public PythonObject __call__(PythonObject[] args) {
            throw new UnsupportedOperationException("Function open not supported yet");
        }});
        attributes.put("ord", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ord not supported yet");
        }});
        attributes.put("pow", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        attributes.put("print", new BuiltInMethod() {
            @Override public PythonObject __call__() {
                printInterface.print("\n");
                return PythonNone.NONE;
            }
            @Override public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
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
        attributes.put("property", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function property not supported yet");
        }});
        attributes.put("range", new BuiltInMethod() {
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
        attributes.put("repr", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function repr not supported yet");
        }});
        attributes.put("reversed", new BuiltInMethod() { @Override public PythonObject call0(PythonObject a) {
            return a.__reversed__();
        }});
        attributes.put("round",  new BuiltInMethod() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.round(arg.asFloat()));
        }});
        attributes.put("set", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function set not supported yet");
        }});
        attributes.put("setattr", new BuiltInMethod() { @Override public PythonObject call0(PythonObject obj, PythonObject k, PythonObject v) {
            obj.__setattr__(k.asString(), v);
            return NONE;
        }});
        attributes.put("slice", new BuiltInMethod() {
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
        attributes.put("sorted", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sorted not supported yet");
        }});
        attributes.put("staticmethod", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function staticmethod not supported yet");
        }});
        attributes.put("str", new BuiltInMethod() { @Override public PythonObject call0(PythonObject arg) {
            return arg.dereference().__str__();
        }});
        attributes.put("sum", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sum not supported yet");
        }});
        attributes.put("super", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function super not supported yet");
        }});
        attributes.put("tuple", new BuiltInMethod() {
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
        attributes.put("type", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function type not supported yet");
        }});
        attributes.put("vars", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function vars not supported yet");
        }});
        attributes.put("zip", new BuiltInMethod() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function zip not supported yet");
        }});
        attributes.put("__import__", new BuiltInMethod() { @Override public PythonObject __call__() {
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
