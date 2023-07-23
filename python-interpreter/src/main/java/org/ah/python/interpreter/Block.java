package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Block implements Executable {

    public static interface BlockDetails {
        public int getLine();
        public String getModuleName();
    }

    private static class BlockEntry implements BlockDetails {
        protected Executable executable;
        protected int line;
        protected String moduleName;

        protected BlockEntry(Executable executable, int line, String moduleName) {
            this.executable = executable;
            this.line = line;
            this.moduleName = moduleName;
        }

        public String toString() {
            return line + ": " + executable.toString();
        }

        @Override
        public int getLine() {
            return line;
        }

        @Override
        public String getModuleName() {
            return moduleName;
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
                context.moduleName = blockEntry.moduleName;
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

    public void addStatement(Executable executable, int line, String moduleName) {
        statements.add(new BlockEntry(executable, line, moduleName));
    }

    @Override public void evaluate(ThreadContext context) {
        context.continuation(new BlockContinuation(context));
    }

    public void terminateWithReturn() {
        if (!(statements.get(statements.size() - 1).executable instanceof Return)) {
            statements.add(new BlockEntry(new Return(PythonNone.NONE), -1, null));
        }
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }
}
