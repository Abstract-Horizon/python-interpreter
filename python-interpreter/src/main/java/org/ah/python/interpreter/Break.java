package org.ah.python.interpreter;

import static org.ah.python.interpreter.ThreadContext.Executable;

public class Break implements Executable {

    public String toString() {
        return "break";
    }

    @Override
    public void evaluate(ThreadContext context) {
        throw new UnsupportedOperationException("break");
    }
}
