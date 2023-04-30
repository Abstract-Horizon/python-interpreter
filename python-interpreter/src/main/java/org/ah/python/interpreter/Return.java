package org.ah.python.interpreter;

public class Return extends PythonObject {

    private PythonObject ret;

    public Return(PythonObject ret) {
        this.ret = ret;
    }

    public PythonObject __call__(ThreadContext context) {
        Suite.BREAKOUT = true;
        Def.RETURN = ret.dereference();
        return PythonNone.NONE;
    }

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            PythonObject returnValue = context.popData();
            context.currentScope.close();
            // context.pushData(returnValue);

            return returnValue;
        }
    };

    public PythonObject execute(ThreadContext context) {
        context.pushPC(continuation);
        return ret.execute(context);
    }

    public String toString() {
        return "return" + (ret != PythonNone.NONE ? " " + ret : "");
    }
}
