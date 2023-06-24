package org.ah.python.interpreter;

public class Subscript extends PythonObject implements Assignable {

    public static PythonClass PYTHON_SUBSCRIPT_CLASS = new PythonClass("subscript");

    private PythonObject scope;
    private PythonObject from;
    private PythonObject to;

    public Subscript(PythonObject scope, PythonObject index) {
        super(PYTHON_SUBSCRIPT_CLASS);
        this.scope = scope;
        this.from = index;
        this.to = index;
    }

    public Subscript(PythonObject scope, PythonObject from, PythonObject to) {
        super(PYTHON_SUBSCRIPT_CLASS);
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

    public void evaluate(ThreadContext context) {
        if (from == null && to == null) {
            context.pushData(scope);
        } else if (from == null) {
            context.pushData(scope);
        } else if (to == null) {

        } else if (from == to) {

        }
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
