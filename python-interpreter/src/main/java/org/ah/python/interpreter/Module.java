package org.ah.python.interpreter;

import org.ah.python.modules.BuiltInFunctions;

public class Module extends Scope {

    private Block block = new Block();

    public Module() {
        super(BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE);
    }

    public Module(Scope parentScope) {
        super(parentScope);
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public void evaluate(ThreadContext context) {
        block.evaluate(context);
    }

    public String toString() {
        return "Module " + block;
    }
}
