package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Compare extends PythonList {

    
    private PythonObject left;
    private List<CmpopType> ops = new ArrayList<CmpopType>();
    
    public Compare(PythonObject left, List<PythonObject> operands, List<CmpopType> ops) {
        this.left = left;
        super.asList().addAll(operands);
        this.ops.addAll(ops);
    }

    public PythonObject dereference() {
        return __call__();
    }

    public boolean isConstant() {
        if (!left.isConstant()) {
            return false;
        }
        for (PythonObject v : asList()) {
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
        
        PythonObject current = left.dereference();
        
        for (int i = 0; i < ops.size(); i++) {
            CmpopType op = ops.get(i);
            PythonObject right = asList().get(i).dereference();
            if (op == CmpopType.Eq) {
                current = current.__eq__(right);
            } else if (op == CmpopType.NotEq) {
                current = current.__ne__(right);
            } else if (op == CmpopType.Lt) {
                current = current.__lt__(right);
            } else if (op == CmpopType.LtE) {
                current = current.__le__(right);
            } else if (op == CmpopType.Gt) {
                current = current.__gt__(right);
            } else if (op == CmpopType.GtE) {
                current = current.__ge__(right);
            } else if (op == CmpopType.In) {
                current = current.__contains__(right);
            } else if (op == CmpopType.NotIn) {
                current = current.__contains__(right).__neg__();
            } else if (op == CmpopType.Is) {
                throw new UnsupportedOperationException("Is");
            }
        }

        return current;
    }

    
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("(");
        res.append(left);
        for (int i = 0; i < ops.size(); i++) {
            CmpopType op = ops.get(i);
            PythonObject right = asList().get(i);
            if (op == CmpopType.Eq) {
                res.append(" == ");
            } else if (op == CmpopType.NotEq) {
                res.append(" != ");
            } else if (op == CmpopType.Lt) {
                res.append(" < ");
            } else if (op == CmpopType.LtE) {
                res.append(" <= ");
            } else if (op == CmpopType.Gt) {
                res.append(" > ");
            } else if (op == CmpopType.GtE) {
                res.append(" => ");
            } else if (op == CmpopType.In) {
                res.append(" in ");
            } else if (op == CmpopType.NotIn) {
                res.append(" not in ");
            } else if (op == CmpopType.Is) {
                throw new UnsupportedOperationException("Is");
            }
            res.append(right);
        }
        return res.toString();
    }
}
