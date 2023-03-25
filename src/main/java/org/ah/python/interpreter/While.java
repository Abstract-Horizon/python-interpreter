package org.ah.python.interpreter;

public class While extends Suite {

    private PythonObject test;
    private Suite els = new Suite();
    private Suite body = null;

    public While(PythonObject test) {
        this.test = test;
    }

    public Suite getElse() {
        return els;
    }

    public Suite getBody() {
        if (body == null) {
            body = new Suite(asList());
        }
        return body;
    }
    
    public PythonObject __call__() {
        
        while (test.dereference().asBoolean() && !GlobalScope.BREAK) {
            super.__call__();
            if (GlobalScope.CONTINUE) {
                GlobalScope.CONTINUE = false;
                Suite.BREAKOUT = false;
            }
        }
        
        if (els != null && !GlobalScope.BREAK) {
            els.__call__();
        }

        if (GlobalScope.BREAK) {
            GlobalScope.BREAK = false;
            Suite.BREAKOUT = false;
        }
        return PythonNone.NONE;
    }
    
    public String toString() {
        return "while " + test.toString() + ": " + super.toString() + 
                (els != null ? " else: " + els.toString() : "");
    }

}
