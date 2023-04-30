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

        attributes.put("abs", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            if (a instanceof PythonInteger) {
                if (a.asInteger(context) < 0) {
                    return PythonInteger.valueOf(-a.asInteger(context));
                }
                return a;
            } else if (a instanceof PythonFloat) {
                if (a.asFloat(context) < 0) {
                    return PythonFloat.valueOf(-a.asFloat(context));
                }
                return a;
            }
            throw new UnsupportedOperationException("Function abs not supported on " + a.toString());
        }});
        attributes.put("all", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            PythonIterator iter = a.__iter__(context);
            PythonObject o = iter.next(context);
            while (o != null) {
                if (!o.asBoolean(context)) {
                    return FALSE;
                }
                o = iter.next(context);
            }
            return TRUE;
        }});
        attributes.put("any", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            PythonIterator iter = a.__iter__(context);
            PythonObject o = iter.next(context);
            while (o != null) {
                if (o.asBoolean(context)) {
                    return TRUE;
                }
                o = iter.next(context);
            }
            return FALSE;
        }});
        attributes.put("ascii", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function ascii not supported yet");
        }});
        attributes.put("bin", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toBinaryString(a.asInteger(context)));
            }
            return PythonString.valueOf(Integer.toBinaryString(a.__index__(context).asInteger(context)));
        }});
        attributes.put("bool", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context) { return FALSE; }
            @Override public PythonObject call0(ThreadContext context, PythonObject a) { return PythonBoolean.valueOf(a.asBoolean(context)); }
        });
        attributes.put("bytearray", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function bytearray not supported yet");
        }});
        attributes.put("callable", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return PythonBoolean.valueOf(a instanceof Function);
        }});
        attributes.put("chr", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return PythonString.valueOf(Character.toString((char)a.asInteger(context)));
        }});
        attributes.put("classmethod", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function classmethod not supported yet");
        }});
        attributes.put("compile", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            // TODO really nice thing to do!
            throw new UnsupportedOperationException("Function compile not supported yet");
        }});
        attributes.put("complex", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function complex not supported yet");
        }});
        attributes.put("delattr", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject object, PythonObject name) {
            object.__delattr__(context, name.asString(context));
            return NONE;
        }});
        attributes.put("dict", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function dict not supported yet");
        }});
        attributes.put("dir", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            // Nice thing to do
            throw new UnsupportedOperationException("Function dir not supported yet");
        }});
        attributes.put("divmod", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function divmod not supported yet");
        }});
        attributes.put("enumerate", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function enumerate not supported yet");
        }});
        attributes.put("eval", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function eval not supported yet");
        }});
        attributes.put("exec", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function exec not supported yet");
        }});
        attributes.put("filter", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function filter not supported yet");
        }});
        attributes.put("float", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return PythonFloat.valueOf(Double.parseDouble(a.asString(context)));
        }});
        attributes.put("format", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function format not supported yet");
        }});
        attributes.put("frozenset", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function frozenset not supported yet");
        }});
        attributes.put("getattr", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject object, PythonObject name) {
            return object.__getattr__(context, name.asString(context));
        }});
        attributes.put("globals", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function globals not supported yet");
        }});
        attributes.put("hasattr", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject object, PythonObject name) {
            try {
                return PythonBoolean.valueOf(object.__getattr__(context, name.asString(context)) != null);
            } catch (Exception e) {
                return FALSE;
            }
        }});
        attributes.put("hash", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return a.__hash__(context);
        }});
        attributes.put("help", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function help not supported yet");
        }});
        attributes.put("hex", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            if (a instanceof PythonInteger) {
                return PythonString.valueOf(Integer.toHexString(a.asInteger(context)));
            }
            return PythonString.valueOf(Integer.toHexString(a.__index__(context).asInteger(context)));
        }});
        attributes.put("id", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return PythonInteger.valueOf(System.identityHashCode(a));
        }});
//        attributes.put("input", new BuiltInMethod() {
//            @Override public PythonObject call0(ThreadContext context, ) {
//                return PythonString.valueOf(scanner.nextLine());
//            }
//            @Override public PythonObject call0(ThreadContext context, PythonObject prompt) {
//                out.println(prompt.asString(context));
//                return call0();
//            }
//        });
        attributes.put("int", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            if (a instanceof PythonInteger) {
                return a;
            } else if (a instanceof PythonFloat) {
                return PythonInteger.valueOf(a.asInteger(context));
            }
            return PythonInteger.valueOf(Integer.parseInt(a.asString(context)));
        }});
        attributes.put("isinstance", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function isinstance not supported yet");
        }});
        attributes.put("issubclass", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function issubclass not supported yet");
        }});
        attributes.put("iter", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context, PythonObject object) {
                return object.__iter__(context);
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject object, PythonObject sentinel) {
                throw new UnsupportedOperationException("Function iter not supported yet");
            }
        });
        attributes.put("len", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return arg.dereference().__len__(context);
        }});
        attributes.put("list", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context) {
                return new PythonList();
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject a) {
                PythonIterator iter = a.__iter__(context);
                PythonList list = new PythonList();
                PythonObject o = iter.next(context);
                while (o != null) {
                    list.asList().add(o);
                    o = iter.next(context);
                }

                return list;
            }
        });
        attributes.put("locals", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function locals not supported yet");
        }});
        attributes.put("map", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function map not supported yet");
        }});
        attributes.put("max", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context, PythonObject[] args) {
            if (args.length > 0) {
                if (args.length == 1 && args[0] instanceof PythonIterator) {
                    PythonIterator iter = args[0].__iter__(context);
                    PythonObject max = iter.next(context);
                    PythonObject o = max;
                    while (o != null) {
                        o = iter.next(context);
                        if (o.__gt__(context, max).asBoolean(context)) {
                            max = o;
                        }
                    }
                    return max;
                }
                PythonObject max = args[0];
                for (int i = 1; i < args.length; i++) {
                    if (args[i].__gt__(context, max).asBoolean(context)) {
                        max = args[i];
                    }
                }
            }
            throw new UnsupportedOperationException("Function max not supported yet");
        }});
        attributes.put("memoryview", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function memoryview not supported yet");
        }});
        attributes.put("min", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context, PythonObject[] args) {
            if (args.length > 0) {
                if (args.length == 1 && args[0] instanceof PythonIterator) {
                    PythonIterator iter = args[0].__iter__(context);
                    PythonObject min = iter.next(context);
                    PythonObject o = min;
                    while (o != null) {
                        o = iter.next(context);
                        if (o.__lt__(context, min).asBoolean(context)) {
                            min = o;
                        }
                    }
                    return min;
                }
                PythonObject min = args[0];
                for (int i = 1; i < args.length; i++) {
                    if (args[i].__lt__(context, min).asBoolean(context)) {
                        min = args[i];
                    }
                }
            }
            throw new UnsupportedOperationException("Function max not supported yet");
        }});
        attributes.put("next", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context, PythonObject a) {
                if (a instanceof PythonIterator) {
                    PythonIterator iter = (PythonIterator)a;
                    return iter.__next__(context);
                }
                throw new UnsupportedOperationException("Function next not supported for non iterators; " + a);
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject a, PythonObject defaultO) {
                throw new UnsupportedOperationException("Function next not supported yet");
            }
        });
        attributes.put("object", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function object not supported yet");
        }});
        attributes.put("oct", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function oct not supported yet");
        }});
        attributes.put("open", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context, PythonObject[] args) {
            throw new UnsupportedOperationException("Function open not supported yet");
        }});
        attributes.put("ord", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function ord not supported yet");
        }});
        attributes.put("pow", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        attributes.put("print", new BuiltInMethod() {
            @Override public PythonObject __call__(ThreadContext context) {
                printInterface.print("\n");
                return PythonNone.NONE;
            }
            @Override public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                if (args.size() > 0) {
                    for (PythonObject a : args) {
                        printInterface.print(a.asString(context));
                    }
                } else {
                    printInterface.print("");
                }
                return PythonNone.NONE;
            }
        });
        attributes.put("property", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function property not supported yet");
        }});
        attributes.put("range", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context, PythonObject to) {
                return range(context, ZERO, to, ONE);
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject from, PythonObject to) {
                return range(context, from, to, ONE);
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject from, PythonObject to, PythonObject step) {
                return range(context, from, to, step);
            }
        });
        attributes.put("repr", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function repr not supported yet");
        }});
        attributes.put("reversed", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject a) {
            return a.__reversed__(context);
        }});
        attributes.put("round",  new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.round(arg.asFloat(context)));
        }});
        attributes.put("set", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function set not supported yet");
        }});
        attributes.put("setattr", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject obj, PythonObject k, PythonObject v) {
            obj.__setattr__(context, k.asString(context), v);
            return NONE;
        }});
        attributes.put("slice", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context, PythonObject stop) {
                return PythonSlice.index(stop.asInteger(context));
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject start, PythonObject stop) {
                return PythonSlice.range(context, start, stop);
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject start, PythonObject stop, PythonObject step) {
                throw new UnsupportedOperationException("Function slice not supported yet");
            }
        });
        attributes.put("sorted", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function sorted not supported yet");
        }});
        attributes.put("staticmethod", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function staticmethod not supported yet");
        }});
        attributes.put("str", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return arg.dereference().__str__(context);
        }});
        attributes.put("sum", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function sum not supported yet");
        }});
        attributes.put("super", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function super not supported yet");
        }});
        attributes.put("tuple", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context) {
                return new PythonTuple();
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject a) {
                PythonIterator iter = a.__iter__(context);
                PythonTuple list = new PythonTuple();
                PythonObject o = iter.next(context);
                while (o != null) {
                    list.asList().add(o);
                    o = iter.next(context);
                }

                return list;
            }
        });
        attributes.put("type", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function type not supported yet");
        }});
        attributes.put("vars", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function vars not supported yet");
        }});
        attributes.put("zip", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function zip not supported yet");
        }});
        attributes.put("__import__", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function __import__ not supported yet");
        }});
    }

    public static PythonIterator range(ThreadContext context, PythonObject from, PythonObject to, PythonObject step) {
        return new PythonIterator(new RangeIterator(from.asInteger(context), to.asInteger(context), step.asInteger(context)));
    }

    public static interface PrintInterface {
        void print(String s);
    }
}
