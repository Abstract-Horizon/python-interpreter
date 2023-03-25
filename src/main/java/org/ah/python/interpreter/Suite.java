package org.ah.python.interpreter;

import java.util.List;


public class Suite extends PythonList {

    public static boolean BREAKOUT = false;

    public Suite() {
    }

    public Suite(List<PythonObject> body) {
        asList().addAll(body);
    }

    public PythonObject __call__() {
        for (PythonObject o : asList()) {
            if (BREAKOUT) {
                return PythonNone.NONE;
            }
            o.__call__();
        }

        return PythonNone.NONE;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        res.append(collectionToString(asList(), "; "));
        res.append("]");
        return res.toString();
    }
}
