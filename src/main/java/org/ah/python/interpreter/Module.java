package org.ah.python.interpreter;


public class Module extends ExecutionScope {

    private Suite suite = new Suite();

    public Module() {
    }
    
    public Suite getSuite() {
        return suite;
    }
    
    public PythonObject __call__() {
        GlobalScope.pushScope(this);
        try {
            return suite.__call__();
        } finally {
            GlobalScope.popScope();
        }
    }

    public String toString() {
        return "Module " + suite;
    }
}
