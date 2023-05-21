package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Del extends PythonObject {

    private List<Subscript> targets = new ArrayList<Subscript>();;

    public Del(List<PythonObject> targets) {
        for (PythonObject target : targets) {
            if (target instanceof Subscript) {
                this.targets.add((Subscript)target);
            } else {
                throw new UnsupportedOperationException("Cannot delete object " +  target);
            }
        }
    }

    public String toString() {
        return "del " + collectionToString(targets, ",");
    }

}
