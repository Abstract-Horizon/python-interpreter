package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Continue implements Executable {

    public static Executable ContinueMark = new Executable() {
        @Override public void evaluate(ThreadContext context) {
        }
    };

    public String toString() { return "continue"; }

    @Override
    public void evaluate(ThreadContext context) {
        while (!context.pcStack.empty()) {
            Executable pc = context.pcStack.pop();
            if (pc == ContinueMark) {
                return;
            } else if (pc instanceof Cloasable) {
                ((Cloasable) pc).close(context);
            }
        }
    }
}
