package org.ah.python.interpreter;

public class UnaryOp extends PythonObject {

    private PythonObject operand;
    private UnaryopType op;

    public UnaryOp(PythonObject operand, UnaryopType op) {
        this.operand = operand;
        this.op = op;
    }
    
    public PythonObject dereference() {
        return __call__();
    }

    public boolean isConstant() {
        return operand.isConstant();
    }
    
    public PythonObject dereferenceConstant() {
        return __call__();
    }

    public PythonObject __call__() {
        if (op == UnaryopType.USub) {
            PythonObject value = operand.dereference();
            return value.__neg__();
        }
        if (op == UnaryopType.UAdd) {
            PythonObject value = operand.dereference();
            return value;
        }
        if (op == UnaryopType.Not) {
            PythonObject value = operand.dereference();
            return value.__neg__();
        }
        throw new UnsupportedOperationException("UnaryOp[" + op + "]");
    }
    
    public String toString() {
        if (op == UnaryopType.USub) {
            return "-" + operand;
        } else if (op == UnaryopType.UAdd) {
            return "+" + operand;
        } else if (op == UnaryopType.Not) {
            return "not " + operand;
        } else {
            throw new UnsupportedOperationException("UnaryOp[" + op + "]");
        }
    }
}
