package org.ah.python.interpreter;

public class Break extends PythonObject {

    public PythonObject __call__(ThreadContext context) {
        Suite.BREAKOUT = true;
        GlobalScope.BREAK = true;
        return PythonNone.NONE;
    }

    public String toString() {
        return "break";
    }
}
