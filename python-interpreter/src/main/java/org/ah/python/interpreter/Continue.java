package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Continue implements Executable {

    public String toString() { return "continue"; }

    @Override
    public void evaluate(ThreadContext context) {
        context.doContinue();
    }
}
