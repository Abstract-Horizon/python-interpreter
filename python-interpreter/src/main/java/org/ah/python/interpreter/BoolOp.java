package org.ah.python.interpreter;

import java.util.List;

public class BoolOp extends PythonObject {

    private List<PythonObject> values;
    private BoolopType op;


    public BoolOp(List<PythonObject> values, BoolopType op) {
        this.values = values;
        this.op = op;
    }

    public void evaluate(ThreadContext context) {
        if (op == BoolopType.And) {

        } else if (op == BoolopType.Or) {

        }

        context.pushData(this);
    }

    public PythonObject dereference() {
        throw new UnsupportedOperationException("dereference");
    }

    public boolean isConstant() {
        for (PythonObject v : values) {
            if (!v.isConstant()) {
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
        for (PythonObject o : values) {
            if (first) { first = false; } else { res.append(operator); }
            res.append(o);
        }
        return res.toString();
    }
 }
