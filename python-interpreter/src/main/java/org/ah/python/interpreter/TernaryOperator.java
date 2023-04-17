package org.ah.python.interpreter;

public class TernaryOperator extends PythonObject {

    private PythonObject condition;
    private PythonObject ifExpression;
    private PythonObject elseExpression;

    public TernaryOperator(PythonObject condition, PythonObject ifExpression, PythonObject elseExpression) {
        this.condition = condition;
        this.ifExpression = ifExpression;
        this.elseExpression = elseExpression;
    }

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            PythonObject cond = context.popData();
            if (cond.asBoolean()) {
                return ifExpression.execute(context);
            } else {
                return elseExpression.execute(context);
            }
        }
    };

    public PythonObject execute(ThreadContext context) {
        context.pushPC(continuation);
        return condition.execute(context);
    }

}
