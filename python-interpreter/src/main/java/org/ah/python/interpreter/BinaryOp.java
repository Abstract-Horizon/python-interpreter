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

    public PythonObject execute(ThreadContext context) {
        if (context.popped) {
        } else {
            context.pushPC(this);
            context.pushPC(left);
        }
        return null;
    }

    public PythonObject dereference() {
        return __call__();
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public PythonObject dereferenceConstant() {
        return __call__();
    }

    public PythonObject __call__() {
        if (op == OperatorType.Add) {
            return left.dereference().__add__(right.dereference());
        }
        if (op == OperatorType.Sub) {
            return left.dereference().__sub__(right.dereference());
        }
        if (op == OperatorType.Mult) {
            return left.dereference().__mul__(right.dereference());
        }
        if (op == OperatorType.Div) {
            return left.dereference().__div__(right.dereference());
        }
        if (op == OperatorType.Mod) {
            return left.dereference().__mod__(right.dereference());
        }
        if (op == OperatorType.BitAnd) {
            return left.dereference().__and__(right.dereference());
        }
        if (op == OperatorType.BitOr) {
            return left.dereference().__or__(right.dereference());
        }
        if (op == OperatorType.BitXor) {
            return left.dereference().__xor__(right.dereference());
        }
        if (op == OperatorType.FloorDiv) {
            return left.dereference().__floordiv__(right.dereference());
        }
        if (op == OperatorType.Pow) {
            return left.dereference().__pow__(right.dereference());
        }
        if (op == OperatorType.RShift) {
            return left.dereference().__rshift__(right.dereference());
        }
        if (op == OperatorType.LShift) {
            return left.dereference().__lshift__(right.dereference());
        }
        throw new UnsupportedOperationException("BinOp[" + op + "]");
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
