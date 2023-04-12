package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Block implements ThreadContext.Executable {

    private List<ThreadContext.Executable> statements = new ArrayList<ThreadContext.Executable>();

    private ThreadContext.Executable twoStatementsContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            context.popData();
            if (statements.size() > 2) {
                context.pushPC(threeStatementsContinuation);
            }
            return doExecute(context, statements.get(1));
        }

    };

    private ThreadContext.Executable threeStatementsContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            context.popData();
            if (statements.size() > 3) {
                context.pushPC(new MoreStatementsContinuation());
            }
            return doExecute(context, statements.get(2));
        }

    };

    private ThreadContext.Executable closeScopeContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            context.popData();
            context.currentScope.close();
            return PythonNone.NONE;
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
        if (size == 1) {
            return doExecute(context, statements.get(0));
        }

        context.pushPC(twoStatementsContinuation);
        return doExecute(context, statements.get(0));
    }

    public PythonObject doExecute(ThreadContext context, ThreadContext.Executable executable) {
        if (closeScope) {
            context.pushPC(closeScopeContinuation);
//            PythonObject result = executable.execute(context);
//            context.currentScope.close();
//            return result;
        }
        return executable.execute(context);
    }

    private class MoreStatementsContinuation implements ThreadContext.Executable {
        private int ptr = 2;

        @Override public PythonObject execute(ThreadContext context) {
            context.popData();
            ptr += 1;
            if (ptr < statements.size() - 1) {
                context.pushPC(this);
            }
            return doExecute(context, statements.get(ptr));
        }
    };


}
