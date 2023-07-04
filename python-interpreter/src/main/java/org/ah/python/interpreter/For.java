package org.ah.python.interpreter;

import static org.ah.python.interpreter.Continue.ContinueMark;

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

    private class ForContinuationNext implements Executable, Loop {
        String ID = "forContinuation";

        @Override public void evaluate(ThreadContext context) {
            PythonObject value = context.popData();

            if (value != null) {
                context.continuation(forContinuationIter);
                context.continuation(ContinueMark);
                context.continuation(block);
                Assign.createAssignment(target, value, true).evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }

        @Override public void doBreak(ThreadContext context) {
            context.pcStack.pop(); // Remove just above added continuation
            if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
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
                context.pcStack.pop(); // Remove just above added continuation
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

//    private Executable forContinuationIter = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//            context.continuation(forContinuationNext);
//            try {
//                context.top().__next__(context); // Keep iterator on the stack
//            } catch (StopIterationException e) {
//                context.pcStack.pop(); // Remove just above added continuation
//                if (!elseBlock.isEmpty()) {
//                    elseBlock.evaluate(context);
//                }
//            }
//        }
//    };

//    private Executable forContinuationNext = new ForContinuationNext();
    private Executable forContinuationNext = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject value = context.popData();

            if (value != null) {
                context.continuation(forContinuationIter);
                context.continuation(ContinueMark);
                context.continuation(block);
                Assign.createAssignment(target, value, true).evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

//    private Executable forContinuation2 = new Executable() {
//        String ID = "forContinuation2";
//        @Override public void evaluate(ThreadContext context) {
//            context.continuation(forContinuation2Next);
//            PythonObject iter = context.top();
//            iter.__next__(context);
//        }
//    };
//
//    private Executable forContinuation2Next = new Executable() {
//        String ID = "forContinuation2Next";
//        @Override public void evaluate(ThreadContext context) {
//            PythonObject value = context.popData();
//
//            if (value != null) {
//                context.continuation(forContinuation2);
//                context.continuation(ContinueMark);
//                context.continuation(block);
//                Assign.createAssignment(target, value, true).evaluate(context);
//            } else if (!elseBlock.isEmpty()) {
//                elseBlock.evaluate(context);
//            }
//        }
//    };

    public void evaluate(ThreadContext context) {
        context.continuationWithEvaluate(forContinuation, iter);
        // iter.evaluate(context);
    }

    public String toString() {
        return "for " + target.toString() + " in " + iter.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
