package org.ah.python.interpreter;


public class Module extends Scope {

    private Suite suite = new Suite();
    private Block block = new Block();

    public Module() {
    }

    public Module(Scope parentScope) {
        super(parentScope);
    }

    public Block getBlock() {
        return block;
    }

    public Suite getSuite() {
        return suite;
    }

    @Override
    public PythonObject execute(ThreadContext context) {
        return block.execute(context);
    }

    public PythonObject __call__() {
        GlobalScope.pushScope(this);
        try {
            return suite.__call__();
        } finally {
            GlobalScope.popScope();
        }
    }

    public String toString() {
        return "Module " + suite;
    }
}
