package org.ah.python.interpreter;

import java.util.List;


public class PythonClassDef extends PythonObject {

    private String name;

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

    public String toString() {
        //return "def " + name + "(" + collectionToString(args, ", ") + "): " + method;
        return "class " + name + "(" + "): "; // + methods;
    }
}
