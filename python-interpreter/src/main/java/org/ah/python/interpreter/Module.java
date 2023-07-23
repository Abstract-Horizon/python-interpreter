package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;
import org.ah.python.modules.BuiltInFunctions;

public class Module extends Scope {

    private String name;
    private Block block = new Block();
    private String prevModuleName;

    public static PythonClass MODULE_PYTHON_CLASS = new PythonClass("Module");

    public Module(String name) {
        super(MODULE_PYTHON_CLASS, BuiltInFunctions.BUILT_IN_FUNCTIONS_SCOPE);
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

    private Executable finishLoading = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.moduleName = prevModuleName;
        }
    };

    @Override
    public void evaluate(ThreadContext context) {
        context.continuation(finishLoading);
        prevModuleName = context.moduleName;
        block.evaluate(context);
    }

    public String toString() {
        return "<module '" + name + ". from '<not implemented>'";
    }
}
