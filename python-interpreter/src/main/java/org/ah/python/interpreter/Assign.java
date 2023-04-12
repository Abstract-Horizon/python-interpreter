package org.ah.python.interpreter;

public class Assign extends PythonObject {

    private Reference reference;
    private PythonObject expression;
    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            PythonObject ref;

            if (reference.getScope() != null) {
                ref = context.popData();
            } else {
                ref = context.getCurrentScope();
            }

            PythonObject expression = context.popData();

            // System.out.println("Assigning to " + ref + " with name " + reference.getName() + " value of " + expression);
            ref.__setattr__(reference.getName(), expression);
            return expression;
        }
    };

    public Assign(Reference reference, PythonObject expression) {
        this.reference = reference;
        this.expression = expression;
    }

    @Override public PythonObject execute(ThreadContext context) {
        context.pushPC(continuation);
        if (reference.getScope() != null) {
            context.pushPC(reference.getScope());
        }
        return expression.execute(context);
    }

    public Reference getReference() {
        return reference;
    }

    public PythonObject getExpression() {
        return expression;
    }

    public PythonObject dereference() {
        return __call__();
    }

    public PythonObject __call__() {
        PythonObject evaluatedExpression = expression.dereference();
//        reference.assign(evaluatedExpression);
        return evaluatedExpression;
    }

    public String toString() {
        return reference.toString() + " = " + expression.toString();
    }
}
