package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonObject.collectionToString;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Del implements Executable {

    private List<Subscript> targets = new ArrayList<Subscript>();;

    public Del(List<Executable> targets) {
        for (Executable target : targets) {
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

    @Override
    public void evaluate(ThreadContext context) {
        throw new UnsupportedOperationException("del");
    }

}
