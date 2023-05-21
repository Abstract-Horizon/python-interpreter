package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class TernaryOperator extends PythonObject {

    private PythonObject condition;
    private PythonObject ifExpression;
    private PythonObject elseExpression;

    public TernaryOperator(PythonObject condition, PythonObject ifExpression, PythonObject elseExpression) {
        this.condition = condition;
        this.ifExpression = ifExpression;
        this.elseExpression = elseExpression;
    }

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject cond = context.popData();
            if (cond instanceof PythonBoolean) {
                if (((PythonBoolean)cond).asBoolean()) {
                    ifExpression.evaluate(context);
                } else {
                    elseExpression.evaluate(context);
                }
            } else {
                context.continuation(boolContinuation);
                cond.__bool__(context);
            }
        }
    };

    private Executable boolContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject cond = context.popData();
            if (((PythonBoolean)cond).asBoolean()) {
                ifExpression.evaluate(context);
            } else {
                elseExpression.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(continuation);
        condition.evaluate(context);
    }

}
