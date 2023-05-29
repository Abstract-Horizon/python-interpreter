package org.ah.python.interpreter;

import java.util.Arrays;
import java.util.Map;

public class Call extends PythonObject {

    public static final PythonObject[] EMPTY_ARRAY = new PythonObject[0];

    protected PythonObject function;
    protected PythonObject[] kargs;
    protected Map<String, PythonObject> kwargs;

    private ThreadContext.Executable evaluateFunctionAndArgsContinuation = new ThreadContext.Executable() {

        @Override public void evaluate(ThreadContext context) {
            PythonObject function = context.popData();

            if (function instanceof BuiltInBoundMethod) {
                int argNo = kargs.length + 1;

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
                int argNo = kargs.length;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                function.__call__(context, null, args);
            } else {
                int argNo = kargs.length;
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


    public Call(PythonObject function, PythonObject... kargs) {
        this(function, null, kargs);
    }

    public Call(PythonObject function, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        this.function = function;
        this.kargs = kargs;
        this.kwargs = kwargs;
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
