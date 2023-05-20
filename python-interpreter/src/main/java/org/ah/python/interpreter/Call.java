package org.ah.python.interpreter;

import java.util.Arrays;
import java.util.List;

public class Call extends PythonObject {

    public static final PythonObject[] EMPTY_ARRAY = new PythonObject[0];

    protected PythonObject function;
    protected PythonObject[] args;
    private PythonObject[] args1;
    private PythonObject[] args2;
    private PythonObject[] args3;
    private PythonObject[] args4;
    private PythonObject[] args5;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

        @Override public PythonObject execute(ThreadContext context) {
            PythonObject function = context.popData();

            if (function instanceof BuiltInMethod) {
                int argNo = args.length;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                return ((Function)function).execute(context, args, null);
            } else if (function instanceof BuiltInBoundMethod) {
                int argNo = args.length + 1;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                return ((Function)function).execute(context, args, null);
            } else if (function instanceof BoundMethod) {
                throw new UnsupportedOperationException("Not implemented for BoundMethod");
            } else {
                int argNo = args.length;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                return ((Function)function).execute(context, args, null);
            }
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
        // return __call__();
        throw new UnsupportedOperationException("Call.dereference");
    }

    public PythonObject __call__(ThreadContext context) {
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
                return instanceMethod.__call__(context, reference.getDereferencedScope(), finalArgs);
            } else {
                throw new UnsupportedOperationException("__call__ on instance method and not on a referenced object. Obj: " +  function);
            }
        } else if (derefFunct instanceof CallableType) {
            CallableType function = (CallableType)derefFunct;
            return function.__call__(context, finalArgs);
        } else {
            throw new IllegalStateException("Calling uncallable type: " + derefFunct.getClass().getName());
        }
    }


    public String toString() {
        return "Call[" + function + "](" + collectionToString(Arrays.asList(args), ", ") + ")";
    }
}
