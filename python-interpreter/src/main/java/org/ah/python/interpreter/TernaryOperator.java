package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class TernaryOperator extends PythonObject implements Executable {

    private Executable condition;
    private Executable ifExpression;
    private Executable elseExpression;

    public TernaryOperator(Executable condition, Executable ifExpression, Executable elseExpression) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
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
