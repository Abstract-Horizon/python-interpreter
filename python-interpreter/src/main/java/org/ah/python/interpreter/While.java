package org.ah.python.interpreter;

public class While extends PythonObject {

    private PythonObject test;

    private Block block = new Block();
    private Block elseBlock = new Block();

    public While(PythonObject test) {
        this.test = test;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    private ThreadContext.Executable whiteContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            if (context.a.asBoolean(context)) {
                context.pushPC(whiteContinuation);
                context.pushPC(test);
                return block.execute(context);
            }
            if (!elseBlock.getStatements().isEmpty()) {
                return elseBlock.execute(context);
            }
            return null;
        }
    };

    public PythonObject execute(ThreadContext context) {
        context.pushPC(whiteContinuation);

        return test.execute(context);
    }

    public String toString() {
        return "while " + test.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }

}
