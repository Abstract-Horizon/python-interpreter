package org.ah.python.interpreter;

import static org.ah.python.interpreter.ThreadContext.Executable;

public class AssignInPlace extends PythonObject {

    private Executable reference;
    private Executable expression;
    private String operation;

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

            // System.out.println("Assigning in place to " + ref + " with name " + referenceObject.getName() + " value of " + expression);
            if (expression instanceof Immutable) {
                context.pushData(ref);
                context.pushData(expression);
                context.continuation(immutableContinuation);
                ref.__getattr__(context, referenceObject.getName());
            } else {
                ref.evaluateObjectMethod(context, operation, ref, expression);
            }
        }
    };

    private Executable immutableContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.continuation(assignContinuation);
            PythonObject ref = context.popData();
            PythonObject expression = context.popData();
            ref.evaluateObjectMethod(context, operation, expression);
        }
    };

    private Executable assignContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            Reference referenceObject = (Reference)reference;
            PythonObject value = context.popData();
            PythonObject ref = context.popData();
            ref.__setattr__(context, referenceObject.getName(), value);
        }
    };

    public AssignInPlace(Executable reference, Executable expression, String operation) {
        super(null); // TODO
        this.reference = reference;
        this.expression = expression;
        this.operation = operation;
    }


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
//
//    public static PythonObject createAssignment(Executable reference, Executable expression) {
//        return createAssignment(reference, expression, false);
//    }
//
//    public static PythonObject createAssignment(Executable reference, Executable expression, boolean lastInstruction) {
//        if (reference instanceof Call) {
//            Call call = (Call)reference;
//            if (call.function instanceof Reference) {
//                Reference callReference = (Reference)call.function;
//                if (callReference.name.equals("__getitem__")) {
//                    callReference.name = "__setitem__";
//                    Executable[] newArgs = new Executable[call.kargs.length + 1];
//                    System.arraycopy(call.kargs, 0, newArgs, 0, call.kargs.length);
//                    newArgs[call.kargs.length] = expression;
//                    // call.kargs = newArgs;
//                    // return call;
//                    return new Call(callReference, call.kwargs, newArgs);
//                } else if (callReference.name.equals("__getattr__")) {
//                    callReference.name = "__setattr__";
//                    Executable[] newArgs = new Executable[call.kargs.length + 1];
//                    System.arraycopy(call.kargs, 0, newArgs, 0, call.kargs.length);
//                    newArgs[call.kargs.length] = expression;
//                    // call.kargs = newArgs;
//                    // return call;
//                    return new Call(callReference, call.kwargs, newArgs);
//                }
//            }
//        }
//        return new AssignInPlace(reference, expression, lastInstruction);
//    }
}
