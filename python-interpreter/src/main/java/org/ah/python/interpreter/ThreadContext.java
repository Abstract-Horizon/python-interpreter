package org.ah.python.interpreter;

import java.util.Stack;

public class ThreadContext {

    public static interface Executable {
        public void evaluate(ThreadContext context);
    }

    public Stack<Executable> pcStack = new Stack<Executable>();
    public Stack<PythonObject> dataStack = new Stack<PythonObject>();

    public PythonObject a;

    public Scope globalScope;
    public Scope currentScope;

    public boolean popped;
    public int line;

    public ThreadContext(Scope globalScope) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
    }

    public boolean next() {
        if (pcStack.isEmpty()) {
            return false;
        }

        Executable pc = pcStack.pop();
        pc.evaluate(this);

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
//
//    public Executable popPC() {
//        popped = true;
//        return pcStack.pop();
//    }

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

    public PythonObject raise(PythonBaseException exception) {
        throw new RuntimeException("@ line " + line + ", " + exception.asString());
    }

    public void continuation(Executable continuation) {
        popped = false;
        pcStack.push(continuation);
    }

    public void continuationWithEvaluate(Executable continuation, PythonObject... objectsToEvaluate) {
        popped = false;
        pcStack.push(continuation);
        int len = objectsToEvaluate.length;
        if (len == 0) {
            return;
        }
        if (len == 1) {
            objectsToEvaluate[0].evaluate(this);
        } else {
//            for (int i = len - 2; i >= 0; i--) {
//                pcStack.push(objectsToEvaluate[i]);
//            }
            for (int i = 0; i < len - 1; i++) {
                pcStack.push(objectsToEvaluate[i]);
            }
            objectsToEvaluate[len - 1].evaluate(this);
        }
    }
}
