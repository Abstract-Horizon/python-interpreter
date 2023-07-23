package org.ah.python.interpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Call extends PythonObject implements Executable {

    public static final PythonObject[] EMPTY_ARRAY = new PythonObject[0];

    protected Executable function;
    protected Executable[] kargs;
    protected Map<String, Executable> kwargs;
    private Executable evaluateFunctionAndArgsContinuation;

    public static class EvaluateFunctionAndArgsContinuation implements Executable {

        private int kargsLength;

        public EvaluateFunctionAndArgsContinuation(int kargsLength) {
            this.kargsLength = kargsLength;
        }

        @Override public void evaluate(ThreadContext context) {
            PythonObject function = context.popData();
            int argNo = kargsLength;
            if (function instanceof Function && ((Function)function).isInstanceMethod()) {
                argNo += 1;
            }

            PythonObject[] args = new PythonObject[argNo];
            for (int i = 0; i < argNo; i++) {
                args[i] = context.popData();
            }

            function.__call__(context, null, args);
        }
    };


    public Call(Executable function, Executable... kargs) {
        this(function, null, kargs);
    }

    public Call(Executable function, Map<String, Executable> kwargs, Executable... kargs) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.function = function;

        Executable[] newKArgs = null;
        for (int i = 0; i < kargs.length; i++) {
            if (kargs[i] instanceof KWArg) {
                if (newKArgs == null) {
                    newKArgs = new Executable[i];
                    if (i > 0) {
                        System.arraycopy(kargs, 0, newKArgs, 0, i);
                    }
                }
                if (this.kwargs == null) {
                    kwargs = new HashMap<>();
                }
                KWArg kwarg = ((KWArg)kargs[i]);

                if (kwarg.getArgName() instanceof Reference) {
                    kwargs.put(((Reference)kwarg.getArgName()).name, kwarg.getArgValue());
                } else {
                    throw new IllegalStateException("SyntaxError: expression cannot contain assignment; i=" + i + ", " + function + " for " + PythonObject.arrayToString(kargs));
                }

            } else if (newKArgs != null) {
                throw new IllegalStateException("SyntaxError: positional argument follows keyword argument; i=" + i + ", " + function + " for " + PythonObject.arrayToString(kargs));
            }
        }
        if (newKArgs == null) {
            this.kargs = kargs;
        } else {
            this.kargs = newKArgs;
        }
        this.kwargs = kwargs;
        this.evaluateFunctionAndArgsContinuation = new EvaluateFunctionAndArgsContinuation(this.kargs.length);
    }

    public void evaluate(ThreadContext context) {
        if (kargs.length == 0) {
            context.continuationWithEvaluate(evaluateFunctionAndArgsContinuation, function);
        } else {
            context.continuation(evaluateFunctionAndArgsContinuation);
            context.continuationWithEvaluate(function, kargs); // TODO evaluate kwargs, too!!!
        }
    }

    public String toString() {
        return "Call[" + function + "](" + collectionToString(Arrays.asList(kargs), ", ") + ")";
    }

    public static void invoke(ThreadContext context, PythonObject object, String methodName, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        PythonClass pythonClass = object.pythonClass;
        for (int i = kargs.length - 1; i >= 0; i--) {
            context.pushData(kargs[i]);
        }
        context.pushData(object);
        context.continuation(new EvaluateFunctionAndArgsContinuation(kargs.length));

        pythonClass.__getattr__(context, methodName);
    }
}
