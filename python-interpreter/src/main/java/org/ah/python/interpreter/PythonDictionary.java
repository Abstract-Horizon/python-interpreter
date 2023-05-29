package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonClass.populateCommonContainerClassMethods;

import java.util.HashMap;
import java.util.Map;

public class PythonDictionary extends PythonObject {

    public static PythonClass PYTHON_DICT_CLASS = new PythonClass("dict");
    {
        populateCommonContainerClassMethods(PYTHON_DICT_CLASS);
    }

    protected Map<PythonObject, PythonObject> map = new HashMap<PythonObject, PythonObject>();

    public PythonDictionary() {
        pythonClass = PYTHON_DICT_CLASS;
    }

    public Map<PythonObject, PythonObject> asMap() {
        return map;
    }

    public boolean asBoolean() {
        return map.size() != 0;
    }

    public void __len__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf(map.size()));
    }

    public void __getitem__(ThreadContext context, PythonObject key) {
        PythonObject obj = map.get(key);
        if (obj != null) {
            context.pushData(obj);
        } else {
            // TODO - as string is tricky!!!
            context.raise(exception("KeyError", PythonString.valueOf(key.asString())));
        }
    }

    public void __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        map.put(key, value);
    }

    public void __delitem__(ThreadContext context, PythonObject key) {
        map.remove(key);
    }

    public void __contains__(ThreadContext context, PythonObject value) {
        context.pushData(PythonBoolean.valueOf(map.containsKey(value)));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Dict {");
        boolean first = true;
        for (Map.Entry<PythonObject, PythonObject> entry : map.entrySet()) {
            if (first) { first = false; } else { res.append(", "); }
            res.append("\"" + entry.getKey() + "\" : " + entry.getValue());
        }
        res.append("}");
        return res.toString();
    }

}
