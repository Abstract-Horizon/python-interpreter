package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Global extends PythonObject {

    private List<PythonString> vars = new ArrayList<PythonString>();

    public Global(List<String> vars) {
        for (String v : vars) {
            this.vars.add(PythonString.valueOf(v));
        }
    }

    public PythonObject __call__() {
        Scope currentScope = GlobalScope.currentScope();
        if (!(currentScope instanceof ExecutionScope)) {
            throw new IllegalStateException("Globals are only allowed in defs");
        }
        ExecutionScope scope = (ExecutionScope)currentScope;
        for (PythonString name : vars) {
            scope.getGlobals().add(name.asString());
        }
        return PythonNone.NONE;
    }

    public String toString() {
        return "global " + collectionToString(vars, ", ");
    }
}
