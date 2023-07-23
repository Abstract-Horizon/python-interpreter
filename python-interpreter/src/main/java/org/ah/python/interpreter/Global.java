package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonObject.collectionToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Global implements Executable {

    private Set<String> vars = new HashSet<String>();

    public Global(List<String> vars) {
        this.vars = new HashSet<String>(vars);
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
        if (context.currentScope instanceof Frame) {
            Frame frame = ((Frame)context.currentScope);
            Scope scope = frame;
            while (scope != null && !(scope instanceof Module)) {
                scope = scope.parentScope;
            }
            if (scope == null) {
                new RuntimeException(context.position() + " cannot find enclosed module for scope " + context.currentScope);
            }
            frame.setModule((Module)scope);
            frame.setGlobals(vars);
        } else {
            new RuntimeException(context.position() + " unexpected context for global " + context.currentScope);
        }
    }

    public String toString() {
        return "global " + collectionToString(vars, ", ");
    }

}
