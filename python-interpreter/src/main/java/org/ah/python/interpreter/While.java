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

    private Executable whiteContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (context.a instanceof PythonBoolean) {
                if (((PythonBoolean)context.a).asBoolean()) {
                    context.continuation(whiteContinuation);
                    context.continuation(test);
                    block.evaluate(context);
                } else if (!elseBlock.isEmpty()) {
                    elseBlock.evaluate(context);
                }
            } else {
                context.continuation(whiteBoolContinuation);
                context.a.__bool__(context);
            }
        }
    };

    private Executable whiteBoolContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (((PythonBoolean)context.a).asBoolean()) {
                context.continuation(whiteContinuation);
                context.continuation(test);
                block.evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(whiteContinuation);

        test.evaluate(context);
    }

    public String toString() {
        return "while " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }

}
