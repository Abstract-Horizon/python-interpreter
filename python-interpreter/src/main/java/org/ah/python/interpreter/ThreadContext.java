package org.ah.python.interpreter;

import java.util.Stack;

import org.ah.python.interpreter.StopIteration.StopIterationException;

public class ThreadContext {

    public static boolean DEBUG = false;

    public static int DEFAULT_DATA_STACK_SIZE = 500;

    public static interface Executable {
        public void evaluate(ThreadContext context);
    }

    public Stack<Executable> pcStack = new Stack<Executable>();
    public PythonObject[] dataStack;
    private int dataStackPtr = -1;

    public Scope globalScope;
    public Scope currentScope;

    public boolean popped;
    public int line;

    public ThreadContext(Scope globalScope) {
        this(globalScope, DEFAULT_DATA_STACK_SIZE);
    }

    public ThreadContext(Scope globalScope, int dataStackSize) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
        dataStack = new PythonObject[dataStackSize];
        dataStackPtr = 0;
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

    public PythonObject top() {
        if (dataStackPtr >= 0) {
            return dataStack[dataStackPtr];
        }
        return null;
    }

    public void setTop(PythonObject top) {
        if (dataStackPtr >= 0) {
            dataStack[dataStackPtr] = top;
        }
    }

    public PythonObject popData() {
        if (dataStackPtr >= 0) {
            PythonObject a = dataStack[dataStackPtr];
            dataStackPtr -= 1;
            return a;
        }
        throw new IllegalStateException("Stack is empty! Line " + line);
    }

    public void pushData(PythonObject value) {
        if (DEBUG && value == null) {
            throw new IllegalStateException(position() + "Pushing null to stack ");
        }
        dataStackPtr += 1;
        if (dataStackPtr >= dataStack.length) {
            for (int i = 0; i < dataStack.length; i++) {
                System.out.println(i + ": " + dataStack[i]);
            }
        }
        dataStack[dataStackPtr] = value;
    }

    public Scope getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(Scope scope) {
        this.currentScope = scope;
    }

    public PythonObject raise(PythonBaseException exception) {
        if (exception instanceof StopIteration) {
            throw new StopIterationException();
        }
        throw new RuntimeException(position() + exception.asString());
    }

    public void continuation(Executable continuation) {
        popped = false;
        pcStack.push(continuation);
    }

    public void continuationWithEvaluate(Executable continuation, Executable... objectsToEvaluate) {
        popped = false;
        pcStack.push(continuation);
        int len = objectsToEvaluate.length;
        if (len == 0) {
            return;
        }
        if (len == 1) {
            objectsToEvaluate[0].evaluate(this);
        } else {
            for (int i = 0; i < len - 1; i++) {
                pcStack.push(objectsToEvaluate[i]);
            }
            objectsToEvaluate[len - 1].evaluate(this);
        }
    }

    public int getDataStackLevel() {
        return dataStackPtr;
    }

    public void setDataStackLevel(int dataStackLevel) {
        dataStackPtr = dataStackLevel;
    }

    public String position() {
        return "@ line " + line + ": ";
    }
}
