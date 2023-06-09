package org.ah.python.interpreter;

import java.util.Arrays;
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

            if (function instanceof BuiltInBoundMethod) {
                // int argNo = kargs.length + 1;
                int argNo = kargsLength + 1;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                function.__call__(context, null, args);
//            } else if (function instanceof BoundMethod) {
//                int argNo = kargs.length + 1;
//
//                PythonObject[] args = new PythonObject[argNo];
//                for (int i = 0; i < argNo; i++) {
//                    args[i] = context.popData();
//                }
//
//                function.__call__(context, null, args);
            } else if (function instanceof BuiltInMethod) {
                int argNo = kargsLength;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                function.__call__(context, null, args);
            } else {
                int argNo = kargsLength;
                if (function instanceof Def && ((Def)function).isInstanceMethod()) {
                    argNo += 1;
                }

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                function.__call__(context, null, args);
            }
        }
    };


    public Call(Executable function, Executable... kargs) {
        this(function, null, kargs);
    }

    public Call(Executable function, Map<String, Executable> kwargs, Executable... kargs) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.function = function;

        this.kargs = kargs;
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
}
