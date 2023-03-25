package org.ah.python.interpreter;

public class Subscript extends PythonObject implements Assignable {

    private PythonObject scope;
    private PythonObject from;
    private PythonObject to;
    
    public Subscript(PythonObject scope, PythonObject index) {
        this.scope = scope;
        this.from = index;
        this.to = index;
    }
    
    public Subscript(PythonObject scope, PythonObject from, PythonObject to) {
        this.scope = scope;
        this.from = from;
        this.to = to;
    }
    
    public PythonObject getScope() {
        return scope;
    }
    
    public PythonObject getFrom() {
        return from;
    }
    
    public PythonObject getTo() {
        return to;
    }
    
    public PythonObject dereference() {
        if (from == null && to == null) {
            return scope.dereference();
        } else if (from == null) {
            return scope.dereference().__getslice__(null, to.dereference());
        } else if (to == null) {
            return scope.dereference().__getslice__(from.dereference(), null);
        } else if (from == to) {
            return scope.dereference().__getitem__(from.dereference());
        } else {
            return scope.dereference().__getslice__(from.dereference(), to.dereference());
        }
    }

    public void assign(PythonObject expr) {
        PythonSlice slice;
        if (from == null && to == null) {
            slice = PythonSlice.range(0, -1);
        } else if (from == null) {
            slice = PythonSlice.range(0, to.dereference().asInteger());
        } else if (to == null) {
            slice = PythonSlice.range(from.dereference().asInteger(), -1);
        } else if (from == to) {
            scope.dereference().__setitem__(from, expr.dereference());
            return;
        } else {
            slice = PythonSlice.range(from.dereference(), to.dereference());
        }
        scope.dereference().__setitem__(slice, expr.dereference());
    }
    
    public String toString() {
        if (from == null && to == null) {
            return scope.toString() + "[:]";
        } else if (from == null) {
            return scope.toString() + "[:" + to.toString() + "]";
        } else if (to == null) {
            return scope.toString() + "[" + from.toString() + ":]";
        } else if (from == to) {
            return scope.toString() + "[" + from.toString() + "]";
        } else {
            return scope.toString() + "[" + from.toString() + ":" + to.toString() + "]";
        }
    }
}
