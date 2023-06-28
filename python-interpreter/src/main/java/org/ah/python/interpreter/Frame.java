package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED;

import java.util.HashSet;
import java.util.Set;

public class Frame extends Scope {
    private ThreadContext context;
    private int pcStackLevel;
    private int dataStackLevel;
    protected Set<String> globals;


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

    public void __getattr__(ThreadContext context, String name) {
        if (globals != null && globals.contains(name)) {
            context.globalScope.__getattr__(context, name);
        } else {
            super.__getattr__(context, name);
        }
    }

    public void __setattr__(String attr, PythonObject o) {
        if (globals != null && globals.contains(attr)) {
            context.globalScope.__setattr__(attr, o);
        } else {
            super.__setattr__(attr, o);
        }
    }

    public void __setattr__(ThreadContext context, String attr, PythonObject o) {
        if (globals != null && globals.contains(attr)) {
            context.globalScope.__setattr__(context, attr, o);
        } else {
            super.__setattr__(context, attr, o);
        }
    }

    public void __delattr__(ThreadContext context, String name) {
        if (globals != null && globals.contains(name)) {
            context.globalScope.__delattr__(context, name);
        } else {
            super.__delattr__(context, name);
        }
    }

    public void setGlobals(Set<String> globals) {
        if (this.globals == null) {
            this.globals = new HashSet<String>(globals);
        } else {
            this.globals.addAll(globals);
        }
    }
}
