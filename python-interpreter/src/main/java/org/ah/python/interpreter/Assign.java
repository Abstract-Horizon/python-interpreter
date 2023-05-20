package org.ah.python.interpreter;

public class Assign extends PythonObject {

    private PythonObject reference;
    private PythonObject expression;
    private boolean lastInstruction = false;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            PythonObject ref;

            Reference referenceObject = (Reference)reference;

            if (referenceObject.getScope() != null) {
                ref = context.popData();
            } else {
                ref = context.getCurrentScope();
            }

            PythonObject expression = context.popData();

            System.out.println("Assigning to " + ref + " with name " + referenceObject.getName() + " value of " + expression);
            ref.__setattr__(context, referenceObject.getName(), expression);

            if (!lastInstruction) {
                return expression;
            } else {
                return null;
            }
        }
    };

    public Assign(PythonObject reference, PythonObject expression) {
        this.reference = reference;
        this.expression = expression;
    }

    public boolean isLastInstruction() { return this.lastInstruction; }
    public void setLastInstruction(boolean lastInstruction) { this.lastInstruction = lastInstruction; }

    @Override public PythonObject execute(ThreadContext context) {
        context.pushPC(continuation);
        if (reference instanceof Reference) {
            if (((Reference)reference).getScope() != null) {
                context.pushPC(((Reference)reference).getScope());
            }
        } else {
            throw new RuntimeException("Expected reference");
        }
        return expression.execute(context);
    }

//    public Reference getReference() {
//        return reference;
//    }

    public PythonObject getExpression() {
        return expression;
    }

    public PythonObject dereference() {
        // return __call__(context);
        throw new UnsupportedOperationException("Assign.dereference");
    }

    public PythonObject __call__(ThreadContext context) {
        PythonObject evaluatedExpression = expression.dereference();
//        reference.assign(evaluatedExpression);
        return evaluatedExpression;
    }

    public String toString() {
        return reference.toString() + " = " + expression.toString();
    }

    public static PythonObject createAssignment(PythonObject reference, PythonObject expression) {
        if (reference instanceof Call) {
            Call call = (Call)reference;
            if (call.function instanceof Reference) {
                Reference callReference = (Reference)call.function;
                if (callReference.name == "__getitem__") {
                    callReference.name = "__setitem__";
                    PythonObject[] newArgs = new PythonObject[call.args.length + 1];
                    System.arraycopy(call.args, 0, newArgs, 0, call.args.length);
                    newArgs[call.args.length] = expression;
                    call.args = newArgs;
                    return call;
                } else if (callReference.name == "__getattr__") {
                    callReference.name = "__setattr__";
                    PythonObject[] newArgs = new PythonObject[call.args.length + 1];
                    System.arraycopy(call.args, 0, newArgs, 0, call.args.length);
                    newArgs[call.args.length] = expression;
                    call.args = newArgs;
                    return call;
                }
            }
        }
        return new Assign(reference, expression);
    }
}
