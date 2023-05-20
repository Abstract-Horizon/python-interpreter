package org.ah.python.interpreter;

public class For extends PythonObject {

    private Reference target;
    private PythonObject iter;
    private Block block = new Block();
    private Block elseBlock = new Block();

    public For(Reference target, PythonObject iter) {
        this.target = target;
        this.iter = iter;
    }

    public Block getBlock() {
        return block;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

//    public PythonObject __call__(ThreadContext context) {
//        PythonObject dereferencedIter = iter.dereference();
//        PythonIterator iter = dereferencedIter.__iter__(context);
//
//        PythonObject next = iter.next(context);
//        while (next != null && !GlobalScope.BREAK) {
//            target.assign(context, next);
//            super.__call__(context);
//            next = iter.next(context);
//            if (GlobalScope.CONTINUE) {
//                GlobalScope.CONTINUE = false;
//                Suite.BREAKOUT = false;
//            }
//        }
//
//        if (els != null && !GlobalScope.BREAK) {
//            els.__call__(context);
//        }
//
//        if (GlobalScope.BREAK) {
//            GlobalScope.BREAK = false;
//            Suite.BREAKOUT = false;
//        }
//        return PythonNone.NONE;
//    }

    private ThreadContext.Executable forContinuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {

            PythonObject iter = context.a;
            PythonObject value = iter.__next__(context);
            // context.popData(); // assign value

            if (value != null) {
                context.pushPC(forContinuation2);
                // context.pushData(iter);
                context.pushPC(block);
                return Assign.createAssignment(target, value).execute(context);
            }

            if (!elseBlock.getStatements().isEmpty()) {
                return elseBlock.execute(context);
            }
            return null;
        }
    };

    private ThreadContext.Executable forContinuation2 = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {

            context.popData(); // assign value
            PythonObject iter = context.a;
            PythonObject value = iter.__next__(context);

            if (value != null) {
                context.pushPC(forContinuation2);
                context.pushPC(block);
                return Assign.createAssignment(target, value).execute(context);
            }

            if (!elseBlock.getStatements().isEmpty()) {
                return elseBlock.execute(context);
            }
            return null;
        }
    };

    public PythonObject execute(ThreadContext context) {
        context.pushPC(forContinuation);
        return iter.execute(context);
    }

    public String toString() {
        return "for " + target.toString() + " in " + iter.toString() + ": " + block.toString() +
                (elseBlock != null ? " else: " + elseBlock.toString() : "");
    }
}
