package org.ah.python.interpreter;

import java.util.Arrays;
import java.util.List;

public class Call extends PythonObject {

    public static final PythonObject[] EMPTY_ARRAY = new PythonObject[0];

    protected PythonObject function;
    protected PythonObject[] args;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

        @Override public void evaluate(ThreadContext context) {
            PythonObject function = context.popData();

            if (function instanceof BuiltInMethod) {
                int argNo = args.length;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                ((Function)function).execute(context, args, null);
            } else if (function instanceof BuiltInBoundMethod) {
                int argNo = args.length + 1;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                ((Function)function).execute(context, args, null);
            } else if (function instanceof BoundMethod) {
                throw new UnsupportedOperationException("Not implemented for BoundMethod");
            } else {
                int argNo = args.length;

                PythonObject[] args = new PythonObject[argNo];
                for (int i = 0; i < argNo; i++) {
                    args[i] = context.popData();
                }

                ((Function)function).execute(context, args, null);
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

    public void evaluate(ThreadContext context) {
        context.pushPC(continuation);
        if (args.length == 0) {
            function.evaluate(context);
            return;
        }

        context.pushPC(function);
        for (int i = 0; i < args.length - 1; i++) {
            context.pushPC(args[i]);
        }

        args[args.length - 1].evaluate(context);
    }

    public String toString() {
        return "Call[" + function + "](" + collectionToString(Arrays.asList(args), ", ") + ")";
    }
}
