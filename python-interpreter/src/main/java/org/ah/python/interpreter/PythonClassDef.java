package org.ah.python.interpreter;

import java.util.Map;
import static org.ah.python.interpreter.ThreadContext.Executable;


public class PythonClassDef extends PythonObject {

    public static class PythonClassType extends PythonClass {

        private PythonClassDef pythonClassDef;

        public PythonClassType(String name, PythonClassDef pythonClassDef) {
            super(name);
            this.pythonClassDef = pythonClassDef;
            populateCommonMethods();
        }

        public void __getattr__(ThreadContext context, String name) {
            if (contains(name)) {
                context.pushData(getAttribute(name));
            } else {
                if (pythonClassDef.evaluatedParents != null) {
                    for (PythonClass parent : pythonClassDef.evaluatedParents) {
                        if (parent.contains(name)) {
                            context.pushData(parent.getAttribute(name));
                            return;
                        }
                    }
                }
                context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + this.name + "' class has no attribute '" + name + "'")));
            }
        }

    }

    private Block block = new Block();

    private String name;
    private Executable[] parentObjects;
    private PythonClass[] evaluatedParents;
    private PythonClass pythonClassType;

    public PythonClassDef(String name, Executable[] parentObjects) {
        super(null);
        this.name = name;
        this.parentObjects = parentObjects;

        pythonClassType = new PythonClassType(name, this);
    }

    public PythonClass getPythonClassType() { return pythonClassType; }

    public Block getBlock() {
        return block;
    }

    private Executable closeScopeContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.currentScope.close();
            context.popScope();
        }
    };

    private Executable parentsContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            evaluatedParents = new PythonClass[parentObjects.length];

            for (int i = 0; i < parentObjects.length; i++) {
                // PythonObject parentObject = parentObjects[i];
                PythonObject parentObject = context.popData();
                if (!(parentObject instanceof PythonClassDef)) {
                    throw new UnsupportedOperationException("Only parent class definition allowed; got " + parentObject.asString());
                }
                evaluatedParents[i] = ((PythonClassDef) parentObject).getPythonClassType();
            }

            block.evaluate(context);
        }
    };

    public void evaluate(ThreadContext context) {
        context.currentScope.__setattr__(context, name, this);
        context.pushScope(pythonClassType);
        context.continuation(closeScopeContinuation);
        if (this.parentObjects != null) {
            context.continuationWithEvaluate(parentsContinuation, parentObjects);
        } else {
            block.evaluate(context);
        }
    }

    private Executable removeReturnValueContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.popData();
        }
    };

    public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        PythonObjectInstance instance = new PythonObjectInstance(pythonClassType);
        instance.pythonClass = pythonClassType;
        context.pushData(instance);
        if (pythonClassType.contains("__init__")) {
            PythonObject[] newArgs = new PythonObject[kargs.length + 1];
            newArgs[0] = instance;
            System.arraycopy(kargs, 0, newArgs, 1, kargs.length);
            context.continuation(removeReturnValueContinuation);
            pythonClassType.getAttribute("__init__").__call__(context, kwargs, newArgs);
        } else if (kargs != null && kargs.length != 0) {
            context.raise(new PythonBaseException("TypeError", PythonString.valueOf(this.name + "() takes no arguments")));
        }
    }

    private static String parentsToString(PythonClass[] parents) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (PythonClass pythonClass : parents) {
            if (first) { first = false; } else { sb.append(", "); }
            sb.append(pythonClass.getName());
        }

        return sb.toString();
    }

    public String toString() {
        return "class " + name + "(" + parentsToString(evaluatedParents) +  ")"; // + methods;
    }
}
