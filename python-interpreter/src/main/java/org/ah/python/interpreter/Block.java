package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Block implements ThreadContext.Executable {

    private static class BlockEntry {
        protected ThreadContext.Executable executable;
        protected int line;

        protected BlockEntry(ThreadContext.Executable executable, int line) {
            this.executable = executable;
            this.line = line;
        }
    }

    private List<BlockEntry> statements = new ArrayList<BlockEntry>();

    private ThreadContext.Executable twoStatementsContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (statements.size() > 2) {
                context.continuation(threeStatementsContinuation);
                statements.get(1).executable.evaluate(context);
                return;
            }
            context.line = statements.get(1).line;
            statements.get(1).executable.evaluate(context);
        }

    };

    private ThreadContext.Executable threeStatementsContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (statements.size() > 3) {
                context.continuation(new MoreStatementsContinuation());
                context.line = statements.get(2).line;
                statements.get(2).executable.evaluate(context);
                return;
            }
            context.line = statements.get(2).line;
            statements.get(2).executable.evaluate(context);
        }

    };

    private class MoreStatementsContinuation implements ThreadContext.Executable {
        private int ptr = 2;

        @Override public void evaluate(ThreadContext context) {
            ptr += 1;
            if (ptr < statements.size() - 1) {
                context.continuation(this);
            }
            context.line = statements.get(ptr).line;
            statements.get(ptr).executable.evaluate(context);
        }
    };

    private ThreadContext.Executable closeScopeContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.currentScope.close();
        }
    };

    private boolean closeScope;

    public Block() {
        this(false);
    }

    public Block(boolean closeScope) {
        this.closeScope = closeScope;
    }

    public void addStatement(ThreadContext.Executable executable, int line) {
        statements.add(new BlockEntry(executable, line));
    }

    @Override public void evaluate(ThreadContext context) {
        int size = statements.size();
        if (size == 0) {
            return;
        }
        if (closeScope) {
            context.continuation(closeScopeContinuation);
        }
        if (size == 1) {
            context.line = statements.get(0).line;
            statements.get(0).executable.evaluate(context);
            return;
        }

        context.continuation(twoStatementsContinuation);
        context.line = statements.get(0).line;
        statements.get(0).executable.evaluate(context);
    }

    public void terminateWithReturn() {
        if (!(statements.get(statements.size() - 1).executable instanceof Return)) {
            statements.add(new BlockEntry(new Return(PythonNone.NONE), -1));
        }
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }
}
