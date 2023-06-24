package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED;

public class Frame extends Scope {
    private ThreadContext context;
    private int pcStackLevel;
    private int dataStackLevel;

    public Frame(ThreadContext context, Scope parentScope) {
        super(PYTHON_INTERNAL_CLASS_NOT_DEFINED, parentScope);
        this.context = context;
        this.pcStackLevel = context.pcStack.size();
        this.dataStackLevel = context.getDataStackLevel();
    }

    public void close() {
        context.pcStack.setSize(pcStackLevel);
        context.setDataStackLevel(dataStackLevel);
        context.popScope();
    }
}
