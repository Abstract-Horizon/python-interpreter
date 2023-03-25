package org.ah.python.interpreter;

public class Assign extends PythonObject {

    private Assignable reference;
    private PythonObject expression;

    public Assign(Assignable reference, PythonObject expression) {
        this.reference = reference;
        this.expression = expression;
    }
    
    public Assignable getReference() {
        return reference;
    }

    public PythonObject getExpression() {
        return expression;
    }

    public PythonObject dereference() {
        return __call__();
    }
    
    public PythonObject __call__() {
        PythonObject evaluatedExpression = expression.dereference();
        reference.assign(evaluatedExpression);
        return evaluatedExpression;
    }

    public String toString() {
        return reference.toString() + " = " + expression.toString();
    }
}
