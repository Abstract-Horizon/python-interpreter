package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.Map;

public class PythonObjectInstance extends Scope {

    protected Map<String, PythonObject> attributes = new HashMap<String, PythonObject>();

    public PythonObjectInstance(PythonClass parentScope) {
        super(parentScope);
    }
}
