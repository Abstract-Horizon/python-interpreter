package org.ah.python.interpreter;


public class If extends Suite {

    private PythonObject test;
    private Suite els = new Suite();
    
    public If(PythonObject test) {
        this.test = test;
    }
    
    public Suite getElse() {
        return els;
    }

    public PythonObject __call__() {
        if (test.dereference().asBoolean()) {
            super.__call__();
        } else {
            if (!els.asList().isEmpty()) {
                els.__call__();
            }
        }
        
        return PythonNone.NONE;
    }
    
    public String toString() {
        return "if " + test.toString() + ": " + super.toString() + 
                (els != null ? " else: " + els.toString() : "");
    }
}
