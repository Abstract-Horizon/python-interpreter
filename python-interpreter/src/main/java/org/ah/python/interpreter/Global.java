package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonObject.collectionToString;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Global implements Executable {

    private List<PythonString> vars = new ArrayList<PythonString>();

    public Global(List<String> vars) {
        for (String v : vars) {
            this.vars.add(PythonString.valueOf(v));
        }
    }

//    public PythonObject __call__(ThreadContext context) {
//        Scope currentScope = GlobalScope.currentScope();
//        if (!(currentScope instanceof ExecutionScope)) {
//            throw new IllegalStateException("Globals are only allowed in defs");
//        }
//        ExecutionScope scope = (ExecutionScope)currentScope;
//        for (PythonString name : vars) {
//            scope.getGlobals().add(name.asString());
//        }
//        return PythonNone.NONE;
//    }
    @Override
    public void evaluate(ThreadContext context) {
        throw new UnsupportedOperationException("global");
    }

    public String toString() {
        return "global " + collectionToString(vars, ", ");
    }

}
