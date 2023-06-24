package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class LogicalAnd implements Executable {

    private Executable left;
    private Executable right;

    public LogicalAnd(Executable left, Executable right) {
        this.left = left;
        this.right = right;
    }

    private Executable continuation1 = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject res = context.popData();
            if (!res.asBoolean()) {
                if (res instanceof PythonBoolean) {
                    context.pushData(res);
                } else {
                    context.pushData(PythonBoolean.FALSE);
                }
            } else {
                context.continuationWithEvaluate(continuation2, right);
            }
        }
    };

    private Executable continuation2 = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject res = context.popData();
            if (res instanceof PythonBoolean) {
                context.pushData(res);
            } else {
                context.pushData(PythonBoolean.valueOf(res.asBoolean()));
            }
        }
    };

    @Override
    public void evaluate(ThreadContext context) {
        context.continuationWithEvaluate(continuation1, left);
    }

}
