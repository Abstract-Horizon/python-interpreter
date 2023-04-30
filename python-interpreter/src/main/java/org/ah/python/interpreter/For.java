package org.ah.python.interpreter;

public class For extends Suite {

    private Reference target;
    private PythonObject iter;
    private Suite els = new Suite();
    private Block block = new Block();
    private Block elseBlock = new Block();

    public For(Reference target, PythonObject iter) {
        this.target = target;
        this.iter = iter;
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

    public PythonObject __call__(ThreadContext context) {
        PythonObject dereferencedIter = iter.dereference();
        PythonIterator iter = dereferencedIter.__iter__(context);

        PythonObject next = iter.next(context);
        while (next != null && !GlobalScope.BREAK) {
            target.assign(context, next);
            super.__call__(context);
            next = iter.next(context);
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
        return "for " + target.toString() + " in " + iter.toString() + ": " + super.toString() +
                (els != null ? " else: " + els.toString() : "");
    }
}
