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
        // return __call__(null);
        throw new UnsupportedOperationException("BinaryOp.dereference");
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public PythonObject dereferenceConstant() {
        throw new UnsupportedOperationException("BinaryOp.dereference");
    }

    public PythonObject __call__(ThreadContext context) {
        if (op == OperatorType.Add) {
            return left.dereference().__add__(context, right.dereference());
        }
        if (op == OperatorType.Sub) {
            return left.dereference().__sub__(context, right.dereference());
        }
        if (op == OperatorType.Mult) {
            return left.dereference().__mul__(context, right.dereference());
        }
        if (op == OperatorType.Div) {
            return left.dereference().__div__(context, right.dereference());
        }
        if (op == OperatorType.Mod) {
            return left.dereference().__mod__(context, right.dereference());
        }
        if (op == OperatorType.BitAnd) {
            return left.dereference().__and__(context, right.dereference());
        }
        if (op == OperatorType.BitOr) {
            return left.dereference().__or__(context, right.dereference());
        }
        if (op == OperatorType.BitXor) {
            return left.dereference().__xor__(context, right.dereference());
        }
        if (op == OperatorType.FloorDiv) {
            return left.dereference().__floordiv__(context, right.dereference());
        }
        if (op == OperatorType.Pow) {
            return left.dereference().__pow__(context, right.dereference());
        }
        if (op == OperatorType.RShift) {
            return left.dereference().__rshift__(context, right.dereference());
        }
        if (op == OperatorType.LShift) {
            return left.dereference().__lshift__(context, right.dereference());
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
