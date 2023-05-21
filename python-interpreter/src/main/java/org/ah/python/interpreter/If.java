package org.ah.python.interpreter;


public class If extends PythonObject {

    private PythonObject test;
    private Block block = new Block();
    private Block elseBlock = new Block();

    public If(PythonObject test) {
        this.test = test;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private ThreadContext.Executable ifContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (context.a instanceof PythonBoolean) {
                if (((PythonBoolean)context.a).asBoolean()) {
                    block.evaluate(context);
                } else if (!elseBlock.getStatements().isEmpty()) {
                    elseBlock.evaluate(context);
                }
            } else {
                context.continuation(ifBoolContinuation);
                context.a.__bool__(context);
            }
        }
    };

    private ThreadContext.Executable ifBoolContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (((PythonBoolean)context.a).asBoolean()) {
                block.evaluate(context);
            } else if (!elseBlock.getStatements().isEmpty()) {
                elseBlock.evaluate(context);
            }
        }
    };

    public void evaluate(ThreadContext context) {
        context.pushPC(ifContinuation);

        test.evaluate(context);
    }

    public String toString() {
        return "if " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
