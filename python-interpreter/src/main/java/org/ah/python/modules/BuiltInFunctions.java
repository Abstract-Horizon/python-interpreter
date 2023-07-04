package org.ah.python.modules;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.ZERO;
import static org.ah.python.interpreter.PythonNone.NONE;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonJavaIterator;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNumber;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonSlice;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.Scope;
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.interpreter.StopIteration.StopIterationException;
import org.ah.python.interpreter.ThreadContext.Executable;
import org.ah.python.interpreter.util.RangeIterator;

public class BuiltInFunctions extends Scope {

    public BuiltInFunctions() {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED, null);
    }

    public static BuiltInFunctions BUILT_IN_FUNCTIONS_SCOPE;

//    public static boolean isBuiltInFunction(String name) {
//        return BUILT_IN_FUNCTIONS_SCOPE.attributes.containsKey(name);
//    }

//    public static PythonObject getFunction(String name) {
//        return BUILT_IN_FUNCTIONS_SCOPE.attributes.get(name);
//    }

    public static void setOutput(OutputStream os) {
        out = new PrintStream(os);
    }

    private static PrintStream out = System.out;
    private static InputStream in;

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
        BUILT_IN_FUNCTIONS_SCOPE = new BuiltInFunctions() {
            {
                addMethod(new BuiltInMethod("abs") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    PythonObject a = args[0];
                    if (a instanceof PythonInteger) {
                        int i = ((PythonInteger)a).asInteger();
                        if (i < 0) {
                            context.pushData(PythonInteger.valueOf(-i));
                        } else {
                            context.pushData(a);
                        }
                    } else if (a instanceof PythonFloat) {
                        double d = ((PythonFloat)a).asFloat();
                        if (d < 0) {
                            context.pushData(PythonFloat.valueOf(-d));
                        } else {
                            context.pushData(a);
                        }
                    } else {
                        context.raise(exception("TypeError", PythonString.valueOf("bad operand type for abs(): '" + a.getPythonClass().getName() + "'")));
                    }
                }});
                addMethod(new BuiltInMethod("all") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Bulitin.all");
        //            PythonIterator iter = args[0].__iter__(context);
        //            PythonObject o = iter.next(context);
        //            while (o != null) {
        //                if (!o.asBoolean(context)) {
        //                    context.pushData(FALSE);
        //                    return;
        //                }
        //                o = iter.next(context);
        //            }
        //            context.pushData(TRUE);
                }});
                addMethod(new BuiltInMethod("any") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Bulitin.any");
        //            PythonIterator iter = args[0].__iter__(context);
        //            PythonObject o = iter.next(context);
        //            while (o != null) {
        //                if (o.asBoolean(context)) {
        //                    context.pushData(TRUE);
        //                    return;
        //                }
        //                o = iter.next(context);
        //            }
        //            context.pushData(FALSE);
                }});
                addMethod(new BuiltInMethod("ascii") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args)  {
                    throw new UnsupportedOperationException("Function ascii not supported yet");
                }});

                final Executable bin_continuation = new Executable() { @Override public void evaluate(ThreadContext context) {
                    context.pushData(PythonString.valueOf(Integer.toBinaryString(((PythonInteger)context.popData()).asInteger())));
                }};

                addMethod(new BuiltInMethod("bin") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonInteger) {
                        context.pushData(PythonString.valueOf("0b" + Integer.toBinaryString(((PythonInteger)args[0]).asInteger())));
                    } else {
                        context.continuation(bin_continuation);

                        args[0].__int__(context);
                    }
                }});
                addMethod(new BuiltInMethod("bool") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        args[0].__bool__(context);
                    }
                });
                addMethod(new BuiltInMethod("bytearray") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function bytearray not supported yet");
                }});
                addMethod(new BuiltInMethod("callable") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    context.pushData(PythonBoolean.valueOf(args[0] instanceof Function));
                }});
                addMethod(new BuiltInMethod("chr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonInteger) {
                        context.pushData(PythonString.valueOf(Character.toString((char)((PythonInteger)args[0]).asInteger())));
                    } else {
                        context.raise(exception("TypeError", PythonString.valueOf("an integer is required (got type '" + args[0].getPythonClass().getName() + ")")));
                    }
                }});
                addMethod(new BuiltInMethod("classmethod") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function classmethod not supported yet");
                }});
                addMethod(new BuiltInMethod("compile") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    // TODO really nice thing to do!
                    throw new UnsupportedOperationException("Function compile not supported yet");
                }});
                addMethod(new BuiltInMethod("complex") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function complex not supported yet");
                }});
                addMethod(new BuiltInMethod("delattr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__delattr__(context, args[1].asString());
                    context.pushData(NONE);
                }});
                addMethod(new BuiltInMethod("dict") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function dict not supported yet");
                }});
                addMethod(new BuiltInMethod("dir") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    // Nice thing to do
                    throw new UnsupportedOperationException("Function dir not supported yet");
                }});
                addMethod(new BuiltInMethod("divmod") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function divmod not supported yet");
                }});
                addMethod(new BuiltInMethod("enumerate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function enumerate not supported yet");
                }});
                addMethod(new BuiltInMethod("eval") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function eval not supported yet");
                }});
                addMethod(new BuiltInMethod("exec") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function exec not supported yet");
                }});
                addMethod(new BuiltInMethod("filter") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function filter not supported yet");
                }});
                addMethod(new BuiltInMethod("float") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__float__(context);
                }});
                addMethod(new BuiltInMethod("format") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function format not supported yet");
                }});
                addMethod(new BuiltInMethod("frozenset") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function frozenset not supported yet");
                }});
                addMethod(new BuiltInMethod("getattr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__getattr__(context, args[1].asString());
                }});
                addMethod(new BuiltInMethod("globals") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function globals not supported yet");
                }});
                addMethod(new BuiltInMethod("hasattr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    try {
                        // TODO - try to find a way to add try/catch around this!
                        args[0].__getattr__(context, args[1].asString());
                    } catch (Exception e) {
                        context.pushData(FALSE);
                    }
                }});
                addMethod(new BuiltInMethod("hash") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__hash__(context);
                }});
                addMethod(new BuiltInMethod("help") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function help not supported yet");
                }});
                final Executable hex_continuation = new Executable() {
                    @Override public void evaluate(ThreadContext context) {
                        context.pushData(PythonString.valueOf("0x" + Integer.toHexString(((PythonInteger)context.popData()).asInteger())));
                    }
                };
                addMethod(new BuiltInMethod("hex") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonInteger) {
                        context.pushData(PythonString.valueOf("0x" + Integer.toHexString(((PythonInteger)args[0]).asInteger())));
                    } else {
                        context.continuation(hex_continuation);
                        args[0].__int__(context);
                    }
                }});
                addMethod(new BuiltInMethod("id") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    context.pushData(PythonInteger.valueOf(System.identityHashCode(args[0])));
                }});
        //        addMethod(new BuiltInMethod() {
        //            @Override public PythonObject call0(ThreadContext context, ) {
        //                return PythonString.valueOf(scanner.nextLine());
        //            }
        //            @Override public PythonObject call0(ThreadContext context, PythonObject prompt) {
        //                out.println(prompt.asString(context));
        //                return call0();
        //            }
        //        });
                addMethod(new BuiltInMethod("int") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonInteger) {
                        context.pushData(args[0]);
                    } else if (args[0] instanceof PythonFloat) {
                        context.pushData(PythonInteger.valueOf(((PythonFloat)args[0]).asInteger()));
                    } else {
                        args[0].__int__(context);
                    }
                }});
                addMethod(new BuiltInMethod("isinstance") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function isinstance not supported yet");
                }});
                addMethod(new BuiltInMethod("issubclass") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function issubclass not supported yet");
                }});

                addMethod(new BuiltInMethod("iter") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        args[0].__iter__(context);
                    }
                });
                addMethod(new BuiltInMethod("len") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__len__(context);
                }});
                final Executable list_continuation = new Executable() {
                    @Override public void evaluate(ThreadContext context) {
                        PythonJavaIterator iter = (PythonJavaIterator)context.popData();
                        PythonList list = new PythonList();
                        try {
                            PythonObject o = iter.next(context);
                            while (o != null) {
                                list.asList().add(o);
                                o = iter.next(context);
                            }
                        } catch (StopIterationException ignore) {}

                        context.pushData(list);
                    }
                };
                addMethod(new BuiltInMethod("list") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        context.continuation(list_continuation);
                        args[0].__iter__(context);
                    }
                });
                addMethod(new BuiltInMethod("locals") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function locals not supported yet");
                }});
                addMethod(new BuiltInMethod("map") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function map not supported yet");
                }});
                addMethod(new BuiltInMethod("max") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        //            if (args.length > 0) {
        //                if (args.length == 1 && args[0] instanceof PythonIterator) {
        //                    PythonIterator iter = args[0].__iter__(context);
        //                    PythonObject max = iter.next(context);
        //                    PythonObject o = max;
        //                    while (o != null) {
        //                        o = iter.next(context);
        //                        if (o.__gt__(context, max).asBoolean(context)) {
        //                            max = o;
        //                        }
        //                    }
        //                    context.pushData(max);
        //                    return;
        //                }
        //                PythonObject max = args[0];
        //                for (int i = 1; i < args.length; i++) {
        //                    if (args[i].__gt__(context, max).asBoolean(context)) {
        //                        max = args[i];
        //                    }
        //                }
        //            }
                    throw new UnsupportedOperationException("Function max not supported yet");
                }});
                addMethod(new BuiltInMethod("memoryview") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function memoryview not supported yet");
                }});
                addMethod(new BuiltInMethod("min") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        //            if (args.length > 0) {
        //                if (args.length == 1 && args[0] instanceof PythonIterator) {
        //                    PythonIterator iter = args[0].__iter__(context);
        //                    PythonObject min = iter.next(context);
        //                    PythonObject o = min;
        //                    while (o != null) {
        //                        o = iter.next(context);
        //                        if (o.__lt__(context, min).asBoolean(context)) {
        //                            min = o;
        //                        }
        //                    }
        //                    context.pushData(min);
        //                    return;
        //                }
        //                PythonObject min = args[0];
        //                for (int i = 1; i < args.length; i++) {
        //                    if (args[i].__lt__(context, min).asBoolean(context)) {
        //                        min = args[i];
        //                    }
        //                }
        //            }
                    throw new UnsupportedOperationException("Function max not supported yet");
                }});

                addMethod(new BuiltInMethod("next") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        //                PythonIterator iter = (PythonIterator)args[0];
                        args[0].__next__(context);
        //                }
        //                throw new UnsupportedOperationException("Function next not supported for non iterators; " + args[0]);
                    }
                });
                addMethod(new BuiltInMethod("object") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function object not supported yet");
                }});
                addMethod(new BuiltInMethod("oct") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function oct not supported yet");
                }});
                addMethod(new BuiltInMethod("open") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function open not supported yet");
                }});
                addMethod(new BuiltInMethod("ord") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonString) {
                        String s = args[0].asString();
                        if (s.length() != 1) {
                            context.raise(exception("TypeError", PythonString.valueOf("ord() expected a character, but string of length " + s.length() + " found")));
                        } else {
                            context.pushData(PythonInteger.valueOf(s.charAt(0)));
                        }
                    } else {
                        context.raise(exception("TypeError", PythonString.valueOf("ord() expected string of length 1, but " + args[0].getPythonClass().getName() + " found")));
                    }
                }});
                addMethod(new BuiltInMethod("pow") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function pow not supported yet");
                }});
                addMethod(new BuiltInMethod("print") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        if (args.length > 0) {
                            for (PythonObject a : args) {
                                printInterface.print(a.asString());
                            }
                        } else {
                            printInterface.print("");
                        }
                    }
                });
                addMethod(new BuiltInMethod("property") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function property not supported yet");
                }});
                addMethod(new BuiltInMethod("range") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        if (args.length == 1) {
                            if (args[0] instanceof PythonNumber) {
                                context.pushData(range(context, ZERO, (PythonNumber)args[0], ONE));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("range still does not support index of type " + args[0].getPythonClass().getName())));
                            }
                        } else if (args.length == 2) {
                            if (args[0] instanceof PythonNumber && args[1] instanceof PythonNumber) {
                                context.pushData(range(context, (PythonNumber)args[0], (PythonNumber)args[1], ONE));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("range still does not support index of type " + args[0].getPythonClass().getName() + "," + args[1].getPythonClass().getName())));
                            }
                        } else if (args.length == 3) {
                            if (args[0] instanceof PythonNumber && args[1] instanceof PythonNumber && args[2] instanceof PythonNumber) {
                                context.pushData(range(context, (PythonNumber)args[0], (PythonNumber)args[1], (PythonNumber)args[2]));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("range still does not support index of type " + args[0].getPythonClass().getName() + "," + args[1].getPythonClass().getName() + "," + args[2].getPythonClass().getName())));
                            }
                        } else if (args.length == 0) {
                            context.pushData(context.raise(exception("TypeError", PythonString.valueOf("range expected at least 1 argument, got " + args.length))));
                        } else {
                            context.pushData(context.raise(exception("TypeError", PythonString.valueOf("range expected at most 3 arguments, got " + args.length))));
                        }
                    }
                });
                addMethod(new BuiltInMethod("repr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].evaluateObjectMethod(context, "__repr__");
                }});
                addMethod(new BuiltInMethod("reversed") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].evaluateObjectMethod(context, "__reversed__");
                }});
                addMethod( new BuiltInMethod("round") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    if (args[0] instanceof PythonNumber) {
                        context.pushData(PythonFloat.valueOf(Math.round(((PythonNumber)args[0]).asFloat())));
                    } else {
                        args[0].__round__(context);
                    }
                }});
                addMethod(new BuiltInMethod("set") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function set not supported yet");
                }});
                addMethod(new BuiltInMethod("setattr") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__setattr__(context, args[1].asString(), args[2]);
                    context.pushData(NONE);
                }});
                addMethod(new BuiltInMethod("slice") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        if (args.length == 1) {
                            if (args[0] instanceof PythonNumber) {
                                context.pushData(PythonSlice.index(((PythonNumber)args[0]).asInteger()));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("slice still does not support index of type " + args[0].getPythonClass().getName())));
                            }
                        } else if (args.length == 2) {
                            if (args[0] instanceof PythonNumber && args[1] instanceof PythonNumber) {
                                context.pushData(PythonSlice.range(((PythonNumber)args[0]).asInteger(), ((PythonNumber)args[1]).asInteger()));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("slice still does not support index of type " + args[0].getPythonClass().getName() + "," + args[1].getPythonClass().getName())));
                            }
                        } else if (args.length == 3) {
                            if (args[0] instanceof PythonNumber && args[1] instanceof PythonNumber && args[2] instanceof PythonNumber) {
                                context.pushData(PythonSlice.range(((PythonNumber)args[0]).asInteger(), ((PythonNumber)args[1]).asInteger(), ((PythonNumber)args[2]).asInteger()));
                            } else {
                                context.raise(exception("TypeError", PythonString.valueOf("slice still does not support index of type " + args[0].getPythonClass().getName() + "," + args[1].getPythonClass().getName())));
                            }
                            throw new UnsupportedOperationException("Function slice not supported yet");
                        } else if (args.length == 0) {
                            context.raise(exception("TypeError", PythonString.valueOf("slice expected at least 1 arguments, got 0")));
                        } else {
                            context.raise(exception("TypeError", PythonString.valueOf("slice expected at most 3 arguments, got " + args.length)));
                        }
                    }
                });
                addMethod(new BuiltInMethod("sorted") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function sorted not supported yet");
                }});
                addMethod(new BuiltInMethod("staticmethod") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function staticmethod not supported yet");
                }});
                addMethod(new BuiltInMethod("str") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].evaluateObjectMethod(context, "__str__");
                }});
                addMethod(new BuiltInMethod("sum") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function sum not supported yet");
                }});
                addMethod(new BuiltInMethod("super") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function super not supported yet");
                }});

                final Executable tuple_continuation = new Executable() {
                    @Override public void evaluate(ThreadContext context) {
                        PythonJavaIterator iter = (PythonJavaIterator)context.popData();
                        PythonTuple tuple = new PythonTuple();
                        try {
                            PythonObject o = iter.next(context);
                            while (o != null) {
                                tuple.asList().add(o);
                                o = iter.next(context);
                            }
                        } catch (StopIterationException ignore) {}

                        context.pushData(tuple);
                    }
                };
                addMethod(new BuiltInMethod("tuple") {
                    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                        context.continuation(tuple_continuation);
                        args[0].__iter__(context);
                    }
                });
                addMethod(new BuiltInMethod("type") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function type not supported yet");
                }});
                addMethod(new BuiltInMethod("vars") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function vars not supported yet");
                }});
                addMethod(new BuiltInMethod("zip") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function zip not supported yet");
                }});
                addMethod(new BuiltInMethod("__import__") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    throw new UnsupportedOperationException("Function __import__ not supported yet");
                }});
            }
        };
    }

    public static PythonJavaIterator range(ThreadContext context, PythonNumber from, PythonNumber to, PythonNumber step) {
        return new PythonJavaIterator(new RangeIterator(from.asInteger(), to.asInteger(), step.asInteger()));
    }

    public static interface PrintInterface {
        void print(String s);
    }
}
