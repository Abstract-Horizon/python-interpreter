package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.List;

public class BoolOp extends PythonObject {

    private List<PythonObject> values;
    private BoolopType op;


    public BoolOp(List<PythonObject> values, BoolopType op) {
        this.values = values;
        this.op = op;
    }
    
    public PythonObject dereference() {
        return __call__();
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
        return __call__();
    }

    public PythonObject __call__() {
        if (op == BoolopType.And) {
            for (PythonObject e : values) {
                PythonObject o = e.dereference();
                if (!o.asBoolean()) {
                    return FALSE;
                }
            }
            return TRUE;
        }
        if (op == BoolopType.Or) {
            for (PythonObject e : values) {
                PythonObject o = e.dereference();
                if (o.asBoolean()) {
                    return TRUE;
                }
            }
            return FALSE;
        }
        throw new UnsupportedOperationException("BoolOp[" + op + "]");
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
