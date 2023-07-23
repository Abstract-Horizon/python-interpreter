package org.ah.python.interpreter;

import org.ah.python.interpreter.StopIteration.StopIterationException;
import org.ah.python.interpreter.ThreadContext.Executable;

public class For implements Executable {

    private Reference target;
    private Executable iter;
    private Block block = new Block();
    private Block elseBlock = new Block();

    public For(Reference target, Executable iter) {
        this.target = target;
        this.iter = iter;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private Executable forContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.continuation(forContinuationIter);
            PythonObject o = context.popData();
            o.__iter__(context);
        }
    };

    private class ForContinuationIter implements Executable, Loop {
        @Override public void evaluate(ThreadContext context) {
            context.continuation(forContinuationNext);
            try {
                context.top().__next__(context); // Keep iterator on the stack
            } catch (StopIterationException e) {
                context.pcStackPop(); // Remove just above added continuation
                if (!elseBlock.isEmpty()) {
                    elseBlock.evaluate(context);
                }
            }
        }

        @Override public void doBreak(ThreadContext context) {
            // context.pcStack.pop(); // Remove just above added continuation
            if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    }

    private Executable forContinuationIter = new ForContinuationIter();

    private Executable forContinuationNext = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject value = context.popData();

            if (value != null) {
                context.continuation(forContinuationIter);
                context.continuation(ThreadContext.ContinueMark);
                context.continuation(block);
                Assign.createAssignment(target, value, true).evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuationWithEvaluate(forContinuation, iter);
    }

    public String toString() {
        return "for " + target.toString() + " in " + iter.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
