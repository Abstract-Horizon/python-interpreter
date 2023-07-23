package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Same implements Executable {

    private Executable left;
    private Executable right;

    public Same(Executable left, Executable right) {
        this.left = left;
        this.right = right;
    }

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject right = context.popData();
            PythonObject left = context.popData();

            context.pushData(
                PythonBoolean.valueOf(
                    left == right
                    || (left instanceof PythonBoolean && right instanceof PythonBoolean
                            && ((PythonBoolean)left).asBoolean() == ((PythonBoolean)right).asBoolean())
                    || (left instanceof PythonInteger && right instanceof PythonInteger
                            && ((PythonInteger)left).asInteger() == ((PythonInteger)right).asInteger())
                    || (left instanceof PythonFloat && right instanceof PythonFloat
                            && ((PythonFloat)left).asFloat() == ((PythonFloat)right).asFloat())
                    || (left instanceof PythonString && right instanceof PythonString
                            && ((PythonString)left).asString().equals(((PythonString)right).asString()))
            ));
        }
    };

    @Override
    public void evaluate(ThreadContext context) {
        context.continuationWithEvaluate(continuation, left, right);
    }

}
