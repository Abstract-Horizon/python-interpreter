package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED;

import org.ah.python.modules.BuiltInFunctions;

public class Module extends Scope {

    private Block block = new Block();

    public Module() {
        super(PYTHON_INTERNAL_CLASS_NOT_DEFINED, BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE);
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
