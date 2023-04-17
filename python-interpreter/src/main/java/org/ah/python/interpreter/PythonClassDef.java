package org.ah.python.interpreter;

import java.util.List;


public class PythonClassDef extends PythonObject {

    private String name;

    private Suite methods = new Suite();
    private Block block = new Block();

    private PythonObject parent;

    public PythonClassDef(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    public void setParentArgs(List<PythonObject> parents) {
        if (parents != null) {
            if (parents.size() == 1 ) {
                parent = parents.get(0);
            } else {
                throw new UnsupportedOperationException("Class parent arguments with more than one element");
            }
        }
    }

    public Suite getSuite() {
        return methods;
    }

    public PythonObject __call__() {
        Scope parentScope = GlobalScope.currentScope();

        PythonClassType classType = new PythonClassType(name, parentScope) {
            final PythonClassType self = this;

            @Override public PythonObject __call__() {
                // TODO Constructor!!!
                // return PythonNone.NONE;
                // throw new UnsupportedOperationException("Constructor not yet implemented");

                Proxy obj = new Proxy() {
                    @Override public PythonType getType() {
                        return self;
                    }
                };

                return obj;
            }
        };

        if (parent != null) {
            PythonObject dereferencedParent = parent.dereference();

            if (!(dereferencedParent instanceof PythonType)) {
                throw new IllegalArgumentException("Parent argument of a class must be another class.");
            }

            classType.setParentType((PythonType)dereferencedParent);
        }
        parentScope.__setattr__(name, classType);

        GlobalScope.pushScope(classType);

        try {
            getSuite().__call__();
            return PythonNone.NONE;
        } finally {
            GlobalScope.popScope();
        }
    }

    public String toString() {
        //return "def " + name + "(" + collectionToString(args, ", ") + "): " + method;
        return "class " + name + "(" + "): " + methods;
    }
}
