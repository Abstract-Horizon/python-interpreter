package org.ah.python.interpreter;

public class UnaryOp extends PythonObject {

    private PythonObject operand;
    private UnaryopType op;

    public UnaryOp(PythonObject operand, UnaryopType op) {
        this.operand = operand;
        this.op = op;
    }

    public boolean isConstant() {
        return operand.isConstant();
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
