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
    
    @Override
    public PythonObject __call__() {
        for (Subscript subscipt : targets) {
            PythonObject scope = subscipt.getScope().dereference();
            PythonObject from = subscipt.getFrom();
            PythonObject to = subscipt.getTo();
            if (from != null) { from = from.dereference(); }
            if (to != null) { to = to.dereference(); }
            if (from != null && from.equals(to)) {
                scope.__delitem__(from);
            } else {
                PythonSlice slice = PythonSlice.range(from, to);
                scope.__delitem__(slice);
            }
        }
        return PythonNone.NONE;
    }
    
    public String toString() {
        return "del " + collectionToString(targets, ",");
    }

}
