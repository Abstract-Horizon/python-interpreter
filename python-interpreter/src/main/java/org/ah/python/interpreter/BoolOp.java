package org.ah.python.interpreter;

import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class BoolOp extends PythonObject {

    private List<Executable> values;
    private BoolopType op;


    public BoolOp(List<Executable> values, BoolopType op) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.values = values;
        this.op = op;
    }

    public void evaluate(ThreadContext context) {
        if (op == BoolopType.And) {

        } else if (op == BoolopType.Or) {

        }

//        context.pushData(this);
    }

    public PythonObject dereference() {
        throw new UnsupportedOperationException("dereference");
    }

    public boolean isConstant() {
        for (Executable v : values) {
            if (v instanceof PythonObject && !((PythonObject)v).isConstant()) {
                return false;
            }
        }
        return true;
    }

    public PythonObject dereferenceConstant() {
        throw new UnsupportedOperationException("BoolOp.dereference");
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("(");
        boolean first = true;
        String operator = null;
        if (op == BoolopType.And) {
            operator = " and ";
        } else if (op == BoolopType.Or) {
            operator = " or ";
        } else {
            throw new UnsupportedOperationException("BoolOp[" + op + "]");
        }
        for (Executable o : values) {
            if (first) { first = false; } else { res.append(operator); }
            res.append(o);
        }
        return res.toString();
    }
 }
