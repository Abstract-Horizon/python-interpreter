package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Return implements Executable {

    private Executable ret;

    public Return(Executable ret) {
        this.ret = ret;
    }

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject returnValue = context.popData();
            context.currentScope.close();

            context.pushData(returnValue);
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(continuation);
        ret.evaluate(context);
    }

    public String toString() {
        return "return" + (ret != PythonNone.NONE ? " " + ret : "");
    }
}
