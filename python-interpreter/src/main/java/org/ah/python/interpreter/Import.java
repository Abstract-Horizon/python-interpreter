package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Import extends PythonObject {

    private Map<String, List<String>> imports = new HashMap<String, List<String>>();

    public void addImport(String asName, List<String> qpath) {
        if (asName == null) {
            asName = qpath.get(0);
        }
        imports.put(asName, qpath);
    }


    public PythonObject __call__() {
        for (Map.Entry<String, List<String>> entry : imports.entrySet()) {
            String asName = entry.getKey();
            List<String> qname = entry.getValue();
            if (qname.size() == 1) {
                String name = qname.get(0);
                if (asName == null) {
                    asName = name;
                }
                PythonObject module = GlobalScope.MODULES.get(name);
                if (module == null) {
                    if (GlobalScope.moduleLoader != null) {
                        module = GlobalScope.moduleLoader.loadModule(name);
                        GlobalScope.MODULES.put(name, module);
                    }

                    throw new NoSuchElementException("Cannot find import " + name);
                }
                GlobalScope.currentScope().__setattr__(PythonString.valueOf(asName), module);
            } else {
                throw new UnsupportedOperationException("import for qualified values.");
            }
        }

        return PythonNone.NONE;
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
