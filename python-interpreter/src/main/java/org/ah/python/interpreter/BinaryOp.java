package org.ah.python.interpreter;

public class BinaryOp extends PythonObject {


    private PythonObject left;
    private PythonObject right;
    private OperatorType op;


    public BinaryOp(PythonObject left, PythonObject right, OperatorType op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public void evaluate(ThreadContext context) {
        if (!context.popped) {
            context.pushPC(this);
            context.pushPC(left);
        }
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public String toString() {
        if (op == OperatorType.Add) {
            return "(" + left.toString() + "+" + right.toString() + ")";
        }
        if (op == OperatorType.Sub) {
            return "(" + left.toString() + "-" + right.toString() + ")";
        }
        if (op == OperatorType.Mult) {
            return "(" + left.toString() + "*" + right.toString() + ")";
        }
        if (op == OperatorType.Div) {
            return "(" + left.toString() + "/" + right.toString() + ")";
        }
        if (op == OperatorType.FloorDiv) {
            return "(" + left.toString() + "//" + right.toString() + ")";
        }
        if (op == OperatorType.Mod) {
            return "(" + left.toString() + "%" + right.toString() + ")";
        }
        if (op == OperatorType.BitAnd) {
            return "(" + left.toString() + "&" + right.toString() + ")";
        }
        if (op == OperatorType.BitOr) {
            return "(" + left.toString() + "|" + right.toString() + ")";
        }
        if (op == OperatorType.BitXor) {
            return "(" + left.toString() + "^" + right.toString() + ")";
        }
        if (op == OperatorType.RShift) {
            return "(" + left.toString() + ">>" + right.toString() + ")";
        }
        if (op == OperatorType.LShift) {
            return "(" + left.toString() + "<<" + right.toString() + ")";
        }
        return "Unknown operation: " + op;
    }
}
