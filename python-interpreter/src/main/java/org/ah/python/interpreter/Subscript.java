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
//        if (from == null && to == null) {
//            return scope.dereference();
//        } else if (from == null) {
//            return scope.dereference().__getslice__(context, null, to.dereference());
//        } else if (to == null) {
//            return scope.dereference().__getslice__(context, from.dereference(), null);
//        } else if (from == to) {
//            return scope.dereference().__getitem__(context, from.dereference());
//        } else {
//            return scope.dereference().__getslice__(context, from.dereference(), to.dereference());
//        }
        throw new UnsupportedOperationException("Superscript.dereference");
    }

    public void evaluate(ThreadContext context) {
        if (from == null && to == null) {
            context.pushData(scope);
        } else if (from == null) {

        } else if (to == null) {

        } else if (from == to) {

        }
    }

//    public void assign(ThreadContext context, PythonObject expr) {
//        PythonSlice slice;
//        if (from == null && to == null) {
//            slice = PythonSlice.range(0, -1);
//        } else if (from == null) {
//            slice = PythonSlice.range(0, to.dereference().asInteger(context));
//        } else if (to == null) {
//            slice = PythonSlice.range(from.dereference().asInteger(context), -1);
//        } else if (from == to) {
//            scope.dereference().__setitem__(context, from, expr.dereference());
//            return;
//        } else {
//            slice = PythonSlice.range(context, from.dereference(), to.dereference());
//        }
//        scope.dereference().__setitem__(context, slice, expr.dereference());
//    }

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
