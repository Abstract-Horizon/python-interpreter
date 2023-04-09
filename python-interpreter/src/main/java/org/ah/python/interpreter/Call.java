package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Call extends PythonObject {

    public static final PythonObject[] EMPTY_ARRAY = new PythonObject[0];

    private PythonObject function;
    private PythonObject[] args;
    private PythonObject[] args1;
    private PythonObject[] args2;
    private PythonObject[] args3;
    private PythonObject[] args4;
    private PythonObject[] args5;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

        @Override public PythonObject execute(ThreadContext context) {
            PythonObject function = context.popData();

            if (function instanceof Function) {
                int argNo = args.length;

                if (!(context.a instanceof Scope)) {
                    argNo += 1;
                }

                List<PythonObject> args = new ArrayList<PythonObject>();
                for (int i = 0; i < argNo; i++) {
                    args.add(context.popData());
                }

                boolean first = true;
                for (PythonObject arg : args) {
                    if (first) { first = false; } else { System.out.print(", "); }
                    System.out.print(arg);
                }
                return ((Function)function).execute(context, args, null);
            }
            throw new RuntimeException("TypeError: object '" + function.pythonClass + "' is not callable");

        }
    };

    public Call(PythonObject function, List<PythonObject> args) {
        this.function = function;
        this.args = new PythonObject[args.size()]; // System.out.println("*");
        args.toArray(this.args);
    }

    public Call(PythonObject function, PythonObject[] args) {
        this.function = function;
        this.args = args;
    }

    public PythonObject execute(ThreadContext context) {
        context.pushPC(continuation);
        if (args.length == 0) {
            return function.execute(context);
        }

        context.pushPC(function);
        for (int i = 0; i < args.length - 1; i++) {
            context.pushPC(args[i]);
        }

        return args[args.length - 1].execute(context);
    }

    public PythonObject dereference() {
        // TODO this seems wrong! Why not passing parameters, and what kind of parameters would they be, then?
        return __call__();
    }

    public PythonObject __call__() {
        PythonObject[] finalArgs;
        PythonObject derefFunct = function.dereference();

        int l = this.args.length;
        if (l == 0) {
            finalArgs = EMPTY_ARRAY;
        } else if (l == 1) {
            if (args1 == null) { args1 = new PythonObject[1]; /* System.out.println("*");*/ }
            finalArgs = args1;
            finalArgs[0] = this.args[0].dereference();
        } else if (l == 2) {
            if (args2 == null) { args2 = new PythonObject[2]; /* System.out.println("*");*/ }
            finalArgs = args2;
            finalArgs[0] = this.args[0].dereference();
            finalArgs[1] = this.args[1].dereference();
        } else if (l == 3) {
            if (args3 == null) { args3 = new PythonObject[3]; /* System.out.println("*");*/ }
            finalArgs = args3;
            finalArgs[0] = this.args[0].dereference();
            finalArgs[1] = this.args[1].dereference();
            finalArgs[2] = this.args[2].dereference();
        } else if (l == 4) {
            if (args4 == null) { args4 = new PythonObject[4]; /* System.out.println("*");*/ }
            finalArgs = args4;
            finalArgs[0] = this.args[0].dereference();
            finalArgs[1] = this.args[1].dereference();
            finalArgs[2] = this.args[2].dereference();
            finalArgs[3] = this.args[3].dereference();
        } else if (l == 5) {
            if (args5 == null) { args5 = new PythonObject[5]; /* System.out.println("*");*/ }
            finalArgs = args5;
            finalArgs[0] = this.args[0].dereference();
            finalArgs[1] = this.args[1].dereference();
            finalArgs[2] = this.args[2].dereference();
            finalArgs[3] = this.args[3].dereference();
            finalArgs[4] = this.args[4].dereference();
        } else {
            finalArgs = new PythonObject[this.args.length];/* System.out.println("*");*/
            int i = 0;
            for (PythonObject a : this.args) {
                PythonObject d = a.dereference();
                finalArgs[i] = d;
                i++;
            }
        }

        if (derefFunct instanceof InstanceMethod) {
            if (function instanceof Reference) {
                Reference reference = (Reference)function;
                @SuppressWarnings("unchecked")
                InstanceMethod<PythonObject> instanceMethod = (InstanceMethod<PythonObject>)derefFunct;
                return instanceMethod.__call__(reference.getDereferencedScope(), finalArgs);
            } else {
                throw new UnsupportedOperationException("__call__ on instance method and not on a referenced object. Obj: " +  function);
            }
        } else if (derefFunct instanceof CallableType) {
            CallableType function = (CallableType)derefFunct;
            return function.__call__(finalArgs);
        } else {
            throw new IllegalStateException("Calling uncallable type: " + derefFunct.getClass().getName());
        }
    }


//    @Override
//    public PythonObject __call__(PythonObject[] args) {
//        PythonObject[] finalArgs;
//        if (args.length > 0) {
//            if (this.args.length == 0) {
//                int l = args.length;
//                if (l == 0) {
//                    finalArgs = EMPTY_ARRAY;
//                } else if (l == 1) {
//                    if (args1 == null) { args1 = new PythonObject[1]; /* System.out.println("*");*/ }
//                    finalArgs = args1;
//                    finalArgs[0] = args[0].dereference();
//                } else if (l == 2) {
//                    if (args2 == null) { args2 = new PythonObject[2]; /* System.out.println("*");*/ }
//                    finalArgs = args2;
//                    finalArgs[0] = args[0].dereference();
//                    finalArgs[1] = args[1].dereference();
//                } else if (l == 3) {
//                    if (args3 == null) { args3 = new PythonObject[3]; /* System.out.println("*");*/ }
//                    finalArgs = args3;
//                    finalArgs[0] = args[0].dereference();
//                    finalArgs[1] = args[1].dereference();
//                    finalArgs[2] = args[2].dereference();
//                } else if (l == 4) {
//                    if (args4 == null) { args4 = new PythonObject[4]; /* System.out.println("*");*/ }
//                    finalArgs = args4;
//                    finalArgs[0] = args[0].dereference();
//                    finalArgs[1] = args[1].dereference();
//                    finalArgs[2] = args[2].dereference();
//                    finalArgs[3] = args[3].dereference();
//                } else if (l == 5) {
//                    if (args5 == null) { args5 = new PythonObject[5]; /* System.out.println("*");*/ }
//                    finalArgs = args5;
//                    finalArgs[0] = args[0].dereference();
//                    finalArgs[1] = args[1].dereference();
//                    finalArgs[2] = args[2].dereference();
//                    finalArgs[3] = args[3].dereference();
//                    finalArgs[4] = args[4].dereference();
//                } else {
//                    finalArgs = new PythonObject[this.args.length];/* System.out.println("*");*/
//                    int i = 0;
//                    for (PythonObject a : this.args) {
//                        PythonObject d = a.dereference();
//                        finalArgs[i] = d;
//                        i++;
//                    }
//                }
//            } else {
//                finalArgs = new PythonObject[this.args.length + args.length];/* System.out.println("*");*/
//                int i = 0;
//                for (PythonObject a : this.args) {
//                    PythonObject d = a.dereference();
//                    finalArgs[i] = d;
//                    i++;
//                }
//                for (PythonObject a : args) {
//                    PythonObject d = a.dereference();
//                    finalArgs[i] = d;
//                    i++;
//                }
//            }
//            PythonObject derefFunct = function.dereference();
//            if (derefFunct instanceof InstanceMethod) {
//                if (function instanceof Reference) {
//                    Reference reference = (Reference)function;
//                    @SuppressWarnings("unchecked")
//                    InstanceMethod<PythonObject> instanceMethod = (InstanceMethod<PythonObject>)derefFunct;
//                    return instanceMethod.__call__(reference.getDereferencedScope(), finalArgs);
//                } else {
//                    throw new UnsupportedOperationException("__call__ on instance method and not on a referenced object. Obj: " +  function);
//                }
//            } else {
//                return derefFunct.__call__(finalArgs);
//            }
//        } else {
//            return __call__();
//        }
//
//    }

    public String toString() {
        return "Call[" + function + "](" + collectionToString(Arrays.asList(args), ", ") + ")";
    }
}
