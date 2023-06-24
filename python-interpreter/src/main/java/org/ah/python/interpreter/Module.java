package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED;

import org.ah.python.modules.BuiltInFunctions;

public class Module extends Scope {

    private String name;
    private Block block = new Block();

    public Module(String name) {
        super(PYTHON_INTERNAL_CLASS_NOT_DEFINED, BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public void evaluate(ThreadContext context) {
        block.evaluate(context);
    }

    public String toString() {
        return "<module '" + name + ". from '<not implemented>'";
    }
}
