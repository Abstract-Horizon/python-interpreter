package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Block implements ThreadContext.Executable {

    private List<ThreadContext.Executable> statements = new ArrayList<ThreadContext.Executable>();

    private ThreadContext.Executable twoStatementsContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (statements.size() > 2) {
                context.continuation(threeStatementsContinuation);
                statements.get(1).evaluate(context);
                return;
            }
            statements.get(1).evaluate(context);
        }

    };

    private ThreadContext.Executable threeStatementsContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (statements.size() > 3) {
                context.continuation(new MoreStatementsContinuation());
                statements.get(2).evaluate(context);
                return;
            }
            statements.get(2).evaluate(context);
        }

    };

    private class MoreStatementsContinuation implements ThreadContext.Executable {
        private int ptr = 2;

        @Override public void evaluate(ThreadContext context) {
            ptr += 1;
            if (ptr < statements.size() - 1) {
                context.continuation(this);
            }
            statements.get(ptr).evaluate(context);
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

    public List<ThreadContext.Executable> getStatements() {
        return statements;
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
            statements.get(0).evaluate(context);
            return;
        }

        context.continuation(twoStatementsContinuation);
        statements.get(0).evaluate(context);
    }
}
