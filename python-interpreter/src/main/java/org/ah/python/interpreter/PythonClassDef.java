package org.ah.python.interpreter;

public class PythonClassDef extends Scope {

    private String name;

    private Block block = new Block();

    private PythonClassDef parent;

    public PythonClassDef(String name, PythonObject[] parents) {
        this.name = name;
        for (PythonObject parent : parents) {
            if (!(parent instanceof PythonClassDef)) {
                throw new UnsupportedOperationException("Only parent class definition allowed; got " + parent.asString());
            }
        }

        if (parents != null) {
            if (parents.length == 1) {
                parent = (PythonClassDef)parents[0];
            } else {
                throw new UnsupportedOperationException("Class parent arguments with more than one element");
            }
        }
    }

    public void __getattr__(ThreadContext context, String name) {
        if (contains(name)) {
            context.pushData(getAttribute(name));
        } else if (parent != null && parent.contains(name)) {
            context.pushData(parent.getAttribute(name));
        } else {
            context.raise(new PythonBaseException("AttributeError", PythonString.valueOf("'" + name + "' class has no attribute '" + name + "'")));
        }
    }

    public Block getBlock() {
        return block;
    }

    public String toString() {
        //return "def " + name + "(" + collectionToString(args, ", ") + "): " + method;
        return "class " + name + "(" + "): "; // + methods;
    }
}
