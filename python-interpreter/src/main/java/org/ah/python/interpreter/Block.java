package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Block implements Executable {

    private static class BlockEntry {
        protected Executable executable;
        protected int line;

        protected BlockEntry(Executable executable, int line) {
            this.executable = executable;
            this.line = line;
        }

        public String toString() {
            return line + ": " + executable.toString();
        }
    }

    private class BlockContinuation implements Executable, Cloasable {
        private int dataStackLevel;
        private int ptr = 0;

        public BlockContinuation(ThreadContext context) {
            this.dataStackLevel = context.getDataStackLevel();
        }

        @Override
        public void evaluate(ThreadContext context) {
            context.setDataStackLevel(dataStackLevel);
            if (ptr >= statements.size()) {
                if (closeScope) {
                    context.currentScope.close();
                }
            } else {
                BlockEntry blockEntry = statements.get(ptr);
                ptr += 1;
                context.line = blockEntry.line;
                context.continuation(this);
                blockEntry.executable.evaluate(context);
            }
        }

        @Override
        public void close(ThreadContext context) {
            context.setDataStackLevel(dataStackLevel);
            if (closeScope) {
                context.currentScope.close();
            }
        }
    }

    private List<BlockEntry> statements = new ArrayList<BlockEntry>();

    private boolean closeScope;

    public Block() {
        this(false);
    }

    public Block(boolean closeScope) {
        this.closeScope = closeScope;
    }

    public void addStatement(Executable executable, int line) {
        statements.add(new BlockEntry(executable, line));
    }

    @Override public void evaluate(ThreadContext context) {
        context.continuation(new BlockContinuation(context));
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
