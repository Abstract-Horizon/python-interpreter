package org.ah.python.interpreter;

import java.util.Map;

public class PythonClassDef extends PythonObject {

    public static class PythonClassType extends PythonClass {

        private PythonClassDef pythonClassDef;

        public PythonClassType(String name, PythonClassDef pythonClassDef) {
            super(name);
            this.pythonClassDef = pythonClassDef;
        }

        public void __getattr__(ThreadContext context, String name) {
            if (contains(name)) {
                context.pushData(getAttribute(name));
            } else if (pythonClassDef.parent != null && pythonClassDef.parent.contains(name)) {
                context.pushData(pythonClassDef.parent.getAttribute(name));
            } else {
                context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + this.name + "' class has no attribute '" + name + "'")));
            }
        }

    }

    private Block block = new Block();

    private String name;
    private PythonClass parent;
    private PythonClass pythonClassType;

    public PythonClassDef(String name, PythonObject[] parents) {
        this.name = name;

        pythonClassType = new PythonClassType(name, this);

        if (parents != null) {
            for (PythonObject parent : parents) {
                if (!(parent instanceof PythonClassDef)) {
                    throw new UnsupportedOperationException("Only parent class definition allowed; got " + parent.asString());
                }
            }

            if (parents.length == 1) {
                parent = ((PythonClassDef)parents[0]).getPythonClassType();
            } else {
                throw new UnsupportedOperationException("Class parent arguments with more than one element");
            }
        }
    }

    public PythonClass getPythonClassType() { return pythonClassType; }

    public Block getBlock() {
        return block;
    }

    private ThreadContext.Executable closeScopeContinuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.currentScope.close();
            context.popScope();
        }
    };

    public void evaluate(ThreadContext context) {
        context.currentScope.__setattr__(context, name, this);
        context.pushScope(pythonClassType);
        context.continuation(closeScopeContinuation);
        block.evaluate(context);
    }

    public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        PythonObjectInstance instance = new PythonObjectInstance(pythonClassType);
        instance.pythonClass = pythonClassType;
        context.pushData(instance);
        // TODO invoke __init__ if existing!
    }

    public String toString() {
        return "class " + name + "(" + (parent != null ? parent.name : "") +  ")"; // + methods;
    }
}
