package org.ah.python.interpreter;

public class Return extends PythonObject {

    private PythonObject ret;
    
    public Return(PythonObject ret) {
        this.ret = ret;
    }
    
    public PythonObject __call__() {
        Suite.BREAKOUT = true;
        Def.RETURN = ret.dereference();
        return PythonNone.NONE;
    }
    
    public String toString() {
        return "return" + (ret != PythonNone.NONE ? " " + ret : "");
    }
}
