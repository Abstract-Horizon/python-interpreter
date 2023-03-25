package org.ah.python.interpreter;

public class Continue extends PythonObject {

    public PythonObject __call__() {
        Suite.BREAKOUT = true;
        GlobalScope.CONTINUE = true;
        return PythonNone.NONE;
    }
 
    public String toString() { return "continue"; }
}
