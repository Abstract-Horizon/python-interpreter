package org.ah.python.interpreter;

import static org.ah.python.interpreter.ThreadContext.Executable;

public class Break implements Executable {

    public String toString() {
        return "break";
    }

    @Override
    public void evaluate(ThreadContext context) {
        while (!context.pcStack.empty()) {
            Executable pc = context.pcStack.pop();
            if (pc instanceof Loop) {
                Loop loop = (Loop)pc;
                loop.doBreak(context);
                return;
            } else if (pc instanceof Cloasable) {
                ((Cloasable) pc).close(context);
            }
        }
    }
}
