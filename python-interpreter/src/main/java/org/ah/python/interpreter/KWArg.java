package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class KWArg implements Executable {

    private Executable argName;
    private Executable argValue;

    public KWArg(Executable argName, Executable argValue) {
        this.argName = argName;
        this.argValue = argValue;
    }

    public Executable getArgName() {
        return argName;
    }

    public Executable getArgValue() {
        return argValue;
    }

    @Override
    public void evaluate(ThreadContext context) {
        throw new IllegalStateException(context.position() + " KWArg is not executable - this is internal error");
    }
}
