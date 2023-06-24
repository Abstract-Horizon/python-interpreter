package org.ah.python.interpreter;

import static org.ah.python.interpreter.ThreadContext.Executable;

public class Assign extends PythonObject {

    private Executable reference;
    private Executable expression;
    private boolean lastInstruction = false;

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject ref;

            Reference referenceObject = (Reference)reference;

            if (referenceObject.getScope() != null) {
                ref = context.popData();
            } else {
                ref = context.getCurrentScope();
            }

            PythonObject expression = context.popData();

            // System.out.println("Assigning to " + ref + " with name " + referenceObject.getName() + " value of " + expression);
            ref.__setattr__(context, referenceObject.getName(), expression);

            if (!lastInstruction) {
                context.pushData(expression);
            }
        }
    };

    public Assign(Executable reference, Executable expression, boolean lastInstruction) {
        super(null); // TODO
        this.reference = reference;
        this.expression = expression;
        this.lastInstruction = lastInstruction;
    }

    public boolean isLastInstruction() { return this.lastInstruction; }
    public void setLastInstruction(boolean lastInstruction) { this.lastInstruction = lastInstruction; }


    @Override public void evaluate(ThreadContext context) {
        if (reference instanceof Reference) {
            if (((Reference)reference).getScope() != null) {
                context.continuationWithEvaluate(continuation, ((Reference)reference).getScope(), expression);
            } else {
                context.continuationWithEvaluate(continuation, expression);
            }
        } else {
            throw new RuntimeException("Expected reference, but got " + reference);
        }
    }

    public Executable getExpression() {
        return expression;
    }

    public String toString() {
        return reference.toString() + " = " + expression.toString();
    }

    public static PythonObject createAssignment(Executable reference, Executable expression) {
        return createAssignment(reference, expression, false);
    }

    public static PythonObject createAssignment(Executable reference, Executable expression, boolean lastInstruction) {
        if (reference instanceof Call) {
            Call call = (Call)reference;
            if (call.function instanceof Reference) {
                Reference callReference = (Reference)call.function;
                if (callReference.name.equals("__getitem__")) {
                    callReference.name = "__setitem__";
                    Executable[] newArgs = new Executable[call.kargs.length + 1];
                    System.arraycopy(call.kargs, 0, newArgs, 0, call.kargs.length);
                    newArgs[call.kargs.length] = expression;
                    // call.kargs = newArgs;
                    // return call;
                    return new Call(callReference, call.kwargs, newArgs);
                } else if (callReference.name.equals("__getattr__")) {
                    callReference.name = "__setattr__";
                    Executable[] newArgs = new Executable[call.kargs.length + 1];
                    System.arraycopy(call.kargs, 0, newArgs, 0, call.kargs.length);
                    newArgs[call.kargs.length] = expression;
                    // call.kargs = newArgs;
                    // return call;
                    return new Call(callReference, call.kwargs, newArgs);
                }
            }
        }
        return new Assign(reference, expression, lastInstruction);
    }
}
