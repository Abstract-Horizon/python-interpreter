package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class LogicalNot implements Executable {

    private Executable expression;

    public LogicalNot(Executable expression) {
        this.expression = expression;
    }

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject res = context.popData();
            context.pushData(PythonBoolean.valueOf(!res.asBoolean()));
        }
    };

    @Override
    public void evaluate(ThreadContext context) {
        context.continuationWithEvaluate(continuation, expression);
    }

}
