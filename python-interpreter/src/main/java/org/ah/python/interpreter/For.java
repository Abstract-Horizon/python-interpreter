package org.ah.python.interpreter;

public class For extends PythonObject {

    private Reference target;
    private PythonObject iter;
    private Block block = new Block();
    private Block elseBlock = new Block();

    public For(Reference target, PythonObject iter) {
        this.target = target;
        this.iter = iter;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private ThreadContext.Executable forContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.continuation(forContinuationNext);
            context.a.__next__(context); // Keep iterator on the stack
        }
    };

    private ThreadContext.Executable forContinuationNext = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject value = context.popData();

            if (value != null) {
                context.pushPC(forContinuation);
                context.pushPC(block);
                Assign.createAssignment(target, value, true).evaluate(context);
            } else if (!elseBlock.getStatements().isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    private ThreadContext.Executable forContinuation2 = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.continuation(forContinuation2Next);
            PythonObject iter = context.a;
            iter.__next__(context);
        }
    };

    private ThreadContext.Executable forContinuation2Next = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject value = context.popData();

            if (value != null) {
                context.pushPC(forContinuation2);
                context.pushPC(block);
                Assign.createAssignment(target, value, true).evaluate(context);
            } else if (!elseBlock.getStatements().isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(forContinuation);
        iter.evaluate(context);
    }

    public String toString() {
        return "for " + target.toString() + " in " + iter.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
