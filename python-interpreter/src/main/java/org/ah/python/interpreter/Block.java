package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Block implements ThreadContext.Executable {

    private List<ThreadContext.Executable> statements = new ArrayList<ThreadContext.Executable>();

    private ThreadContext.Executable twoStatementsContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
//            context.popData();
            if (statements.size() > 2) {
                context.pushPC(threeStatementsContinuation);
                return statements.get(1).execute(context);
            }
            return statements.get(1).execute(context);
        }

    };

    private ThreadContext.Executable threeStatementsContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
//            context.popData();
            if (statements.size() > 3) {
                context.pushPC(new MoreStatementsContinuation());
                return statements.get(2).execute(context);
            }
            return statements.get(2).execute(context);
        }

    };

    private class MoreStatementsContinuation implements ThreadContext.Executable {
        private int ptr = 2;

        @Override public PythonObject execute(ThreadContext context) {
//            context.popData();
            ptr += 1;
            if (ptr < statements.size() - 1) {
                context.pushPC(this);
            }
            return statements.get(ptr).execute(context);
        }
    };

    private ThreadContext.Executable closeScopeContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
//            context.popData();
            context.currentScope.close();
//            return PythonNone.NONE;
            return null;
        }
    };

    private boolean closeScope;

    public Block() {
        this(false);
    }

    public Block(boolean closeScope) {
        this.closeScope = closeScope;
    }

    public List<ThreadContext.Executable> getStatements() {
        return statements;
    }

    @Override public PythonObject execute(ThreadContext context) {
        int size = statements.size();
        if (size == 0) {
            return null;
        }
        if (closeScope) {
            context.pushPC(closeScopeContinuation);
        }
        if (size == 1) {
            return statements.get(0).execute(context);
        }

        context.pushPC(twoStatementsContinuation);
        return statements.get(0).execute(context);
    }
}
