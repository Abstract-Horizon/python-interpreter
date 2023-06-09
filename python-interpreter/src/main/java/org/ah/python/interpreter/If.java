package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class If implements Executable {

    private Executable test;
    private Block block = new Block();
    private Block elseBlock = new Block();

    public If(Executable test) {
        this.test = test;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private Executable ifContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject a = context.popData();
            if (a instanceof PythonBoolean) {
                if (((PythonBoolean)a).asBoolean()) {
                    block.evaluate(context);
                } else if (!elseBlock.isEmpty()) {
                    elseBlock.evaluate(context);
                }
            } else {
                context.continuation(ifBoolContinuation);
                a.__bool__(context);
            }
        }
    };

    private Executable ifBoolContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            PythonObject a = context.popData();

            if (((PythonBoolean)a).asBoolean()) {
                block.evaluate(context);
            } else if (!elseBlock.isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.continuation(ifContinuation);

        test.evaluate(context);
    }

    public String toString() {
        return "if " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
