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
        @Override public PythonObject execute(ThreadContext context) {
            if (context.a.asBoolean(context)) {
                return block.execute(context);
            }
            if (!elseBlock.getStatements().isEmpty()) {
                return elseBlock.execute(context);
            }
            return null;
        }
    };

    public PythonObject execute(ThreadContext context) {
        context.pushPC(ifContinuation);

        return test.execute(context);
    }

    public String toString() {
        return "if " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
