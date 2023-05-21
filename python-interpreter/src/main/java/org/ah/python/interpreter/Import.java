package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Import extends PythonObject {

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
}
