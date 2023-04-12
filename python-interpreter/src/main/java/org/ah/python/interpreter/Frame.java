package org.ah.python.interpreter;

public class Frame extends Scope {
    private ThreadContext context;
    private int pcStackLevel;
    private int dataStackLevel;

    public Frame(ThreadContext context, Scope parentScope) {
        super(parentScope);
        this.context = context;
        this.pcStackLevel = context.pcStack.size();
        this.dataStackLevel = context.dataStack.size();
    }

    public void close() {
        context.pcStack.setSize(pcStackLevel);
        context.dataStack.setSize(dataStackLevel);
        context.popScope();
    }
}
