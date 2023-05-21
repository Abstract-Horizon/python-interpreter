package org.ah.python.interpreter;

public class Return extends PythonObject {

    private PythonObject ret;

    public Return(PythonObject ret) {
        this.ret = ret;
    }

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject returnValue = context.popData();
            context.currentScope.close();

            context.pushData(returnValue);
        }
    };

    public void evaluate(ThreadContext context) {
        context.pushPC(continuation);
        ret.evaluate(context);
    }

    public String toString() {
        return "return" + (ret != PythonNone.NONE ? " " + ret : "");
    }
}
