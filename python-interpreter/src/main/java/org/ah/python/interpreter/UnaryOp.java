package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class UnaryOp extends PythonObject {

    private Executable operand;
    private UnaryopType op;

    public UnaryOp(Executable operand, UnaryopType op) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.operand = operand;
        this.op = op;
    }

    public boolean isConstant() {
        return operand instanceof PythonObject && ((PythonObject)operand).isConstant();
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
