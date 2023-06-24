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

    private class BlockContinuation implements Executable {
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
    }

    private List<BlockEntry> statements = new ArrayList<BlockEntry>();

//    private Executable twoStatementsContinuation = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//            if (statements.size() > 2) {
//                context.continuation(threeStatementsContinuation);
//                statements.get(1).executable.evaluate(context);
//                return;
//            }
//            context.line = statements.get(1).line;
//            statements.get(1).executable.evaluate(context);
//        }
//
//    };

//    private Executable threeStatementsContinuation = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//            if (statements.size() > 3) {
//                context.continuation(new MoreStatementsContinuation());
//                context.line = statements.get(2).line;
//                statements.get(2).executable.evaluate(context);
//                return;
//            }
//            context.line = statements.get(2).line;
//            statements.get(2).executable.evaluate(context);
//        }
//
//    };

//    private class MoreStatementsContinuation implements Executable {
//        private int ptr = 2;
//
//        @Override public void evaluate(ThreadContext context) {
//            ptr += 1;
//            if (ptr < statements.size() - 1) {
//                context.continuation(this);
//            }
//            context.line = statements.get(ptr).line;
//            statements.get(ptr).executable.evaluate(context);
//        }
//    };
//
//    private Executable closeScopeContinuation = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//            context.currentScope.close();
//        }
//    };

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
//        int size = statements.size();
//        if (size == 0) {
//            return;
//        }
//        if (closeScope) {
//            context.continuation(closeScopeContinuation);
//        }
//        if (size > 1) {
//            context.continuation(twoStatementsContinuation);
//        }
//        context.line = statements.get(0).line;
//        statements.get(0).executable.evaluate(context);
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
