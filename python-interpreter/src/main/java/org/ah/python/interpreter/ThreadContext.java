package org.ah.python.interpreter;

import java.util.Stack;

public class ThreadContext {

    public static interface Executable {
        public PythonObject execute(ThreadContext context);
    }

    public Stack<Executable> pcStack = new Stack<Executable>();
    public Stack<PythonObject> dataStack = new Stack<PythonObject>();

    public PythonObject a;

    public Scope globalScope;
    public Scope currentScope;

    public boolean popped;

    public ThreadContext(Scope globalScope) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
    }

    public boolean next() {
        if (pcStack.isEmpty()) {
            return false;
        }

        Executable pc = pcStack.pop();
        PythonObject result = pc.execute(this);
        if (result != null) {
            if (a != null) {
                dataStack.push(a);
            }
            a = result;
        }

        return true;
    }

    public void pushScope(Scope scope) {
        scope.setParentScope(currentScope);
        currentScope = scope;
    }

    public void popScope() {
        if (globalScope != currentScope) {
            currentScope = currentScope.getParentScope();
        }
    }

    public void pushPC(Executable newPC) {
        popped = false;
        pcStack.push(newPC);
    }

    public Executable popPC() {
        popped = true;
        return pcStack.pop();
    }

    public PythonObject popData() {
        PythonObject existingA = a;
        if (dataStack.isEmpty()) {
            a = null;
        } else {
            a = dataStack.pop();
        }
        return existingA;
    }

    public void pushData(PythonObject value) {
        if (a == null) {
            a = value;
        } else {
            dataStack.push(a);
            a = value;
        }
    }

    public Scope getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(Scope scope) {
        this.currentScope = scope;
    }
}
