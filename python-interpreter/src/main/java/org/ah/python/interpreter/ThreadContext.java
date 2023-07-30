package org.ah.python.interpreter;

import org.ah.python.interpreter.StopIteration.StopIterationException;

public class ThreadContext {

    public static boolean DEBUG = false;

    public static int DEFAULT_DATA_STACK_SIZE = 500;

    public static int DEFAULT_PC_STACK_SIZE = 1500;

    public static interface Executable {
        public void evaluate(ThreadContext context);
    }

    public Executable[] pcStack;
    public int pcStackPtr = -1;

    public PythonObject[] dataStack;
    private int dataStackPtr = -1;

    public Scope globalScope;
    public Scope currentScope;

    public boolean popped;
    public String moduleName;
    public int line;

    public static interface CatchContinuation {
        public void evaluateException(ThreadContext context, PythonBaseException exception);
    }

    public static Executable ContinueMark = new Executable() {
        @Override public void evaluate(ThreadContext context) {
        }
    };

    public ThreadContext(Scope globalScope) {
        this(globalScope, DEFAULT_DATA_STACK_SIZE, DEFAULT_PC_STACK_SIZE);
    }

    public ThreadContext(Scope globalScope, int dataStackSize, int pcStackSize) {
        this.globalScope = globalScope;
        this.currentScope = globalScope;
        dataStack = new PythonObject[dataStackSize];
        pcStack = new Executable[pcStackSize];
        dataStackPtr = 0;
    }

    public boolean isPCStackEmpty() {
        return pcStackPtr < 0;
    }

    public Executable pcStackPop() {
        if (pcStackPtr >= 0) {
            Executable executable = pcStack[pcStackPtr];
            pcStackPtr -= 1;
            return executable;
        }
        return null;
    }

    public boolean next() {
        if (pcStackPtr < 0) {
            return false;
        }

        Executable pc = pcStack[pcStackPtr];
        pcStackPtr -= 1;
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
        pcStackPtr += 1;
        pcStack[pcStackPtr] = continuation;
    }

    public void continuationWithEvaluate(Executable continuation, Executable... objectsToEvaluate) {
        popped = false;

        pcStackPtr += 1;
        pcStack[pcStackPtr] = continuation;
        int len = objectsToEvaluate.length;
        if (len == 0) {
            return;
        }
        if (len == 1) {
            objectsToEvaluate[0].evaluate(this);
        } else {
            for (int i = 0; i < len - 1; i++) {
                pcStackPtr += 1;
                pcStack[pcStackPtr] = objectsToEvaluate[i];
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


        if (moduleName != null) {
            return moduleName + "@ line " + line + ": ";
        }
        return "@ line " + line + ": ";
    }

    public void doBreak() {
        while (pcStackPtr >= 0) {
            Executable pc = pcStack[pcStackPtr];
            pcStackPtr -= 1;
            if (pc instanceof Loop) {
                Loop loop = (Loop)pc;
                loop.doBreak(this);
                return;
            } else if (pc instanceof Cloasable) {
                ((Cloasable) pc).close(this);
            }
        }
    }

    public void doContinue() {
        while (pcStackPtr >= 0) {
            Executable pc = pcStack[pcStackPtr];
            pcStackPtr -= 1;
            if (pc == ContinueMark) {
                return;
            } else if (pc instanceof Cloasable) {
                ((Cloasable) pc).close(this);
            }
        }
    }
}
