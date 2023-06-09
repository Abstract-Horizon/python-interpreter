package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Import implements Executable {

    private Map<String, List<String>> imports = new HashMap<String, List<String>>();

    public void addImport(String asName, List<String> qpath) {
        if (asName == null) {
            asName = qpath.get(0);
        }
        imports.put(asName, qpath);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("import ");
        boolean first = true;
        for (Map.Entry<String, List<String>> e : imports.entrySet()) {
            if (first) { first = false; }  else { res.append(", "); }
            res.append(e.getKey());
        }
        return res.toString();
    }

    public void evaluate(ThreadContext context) {
        for (String name : imports.keySet()) {
            if (!GlobalScope.MODULES.containsKey(name)) {
                context.raise(PythonBaseException.exception("ModuleNotFoundError", PythonString.valueOf("No module named '" + name + "'")));
                return;
            } else {
                context.currentScope.__setattr__(context, name, GlobalScope.MODULES.get(name));
            }
        }
    }
}
