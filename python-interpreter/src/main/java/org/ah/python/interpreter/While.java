package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class While implements Executable {

    private Executable test;

    private Block block = new Block();
    private Block elseBlock = new Block();

    public While(Executable test) {
        this.test = test;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private class WhileContinuation implements Executable, Loop {

        @Override
        public void evaluate(ThreadContext context) {
            PythonObject a = context.popData();
            if (a instanceof PythonBoolean) {
                if (((PythonBoolean)a).asBoolean()) {
                    context.continuation(whileContinuation);
                    context.continuation(test);
                    context.continuation(ThreadContext.ContinueMark);
                    block.evaluate(context);
                } else if (!elseBlock.isEmpty()) {
                    elseBlock.evaluate(context);
                }
            } else {
                context.continuation(whileBoolContinuation);
                a.__bool__(context);
            }
        }

        @Override public void doBreak(ThreadContext context) {
            context.popData();
            if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    }

    private Executable whileContinuation = new WhileContinuation();

    private Executable whileBoolContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject a = context.popData();
            if (((PythonBoolean)a).asBoolean()) {
                context.continuation(whileContinuation);
                context.continuation(test);
                context.continuation(ThreadContext.ContinueMark);
                block.evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(whileContinuation);

        test.evaluate(context);
    }

    public String toString() {
        return "while " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }

}
