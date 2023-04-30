package org.ah.python.interpreter;

public class While extends Suite {

    private PythonObject test;
    private Suite els = new Suite();
    private Suite body = null;

    private Block block = new Block();
    private Block elseBlock = new Block();

    public While(PythonObject test) {
        this.test = test;
    }

    public Suite getElse() {
        return els;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    public Suite getBody() {
        if (body == null) {
            body = new Suite(asList());
        }
        return body;
    }

    public PythonObject __call__(ThreadContext context) {

        while (test.dereference().asBoolean(context) && !GlobalScope.BREAK) {
            super.__call__(context);
            if (GlobalScope.CONTINUE) {
                GlobalScope.CONTINUE = false;
                Suite.BREAKOUT = false;
            }
        }

        if (els != null && !GlobalScope.BREAK) {
            els.__call__(context);
        }

        if (GlobalScope.BREAK) {
            GlobalScope.BREAK = false;
            Suite.BREAKOUT = false;
        }
        return PythonNone.NONE;
    }

    public String toString() {
        return "while " + test.toString() + ": " + super.toString() +
                (els != null ? " else: " + els.toString() : "");
    }

}
