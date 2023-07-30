package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Import implements Executable {

    public static class AsName {
        protected List<String> dottedName;
        protected String asName;

        public AsName(List<String> dottedName, String asName) {
            this.dottedName = dottedName;
            this.asName = asName;
        }

        public String toString() {
            return PythonObject.collectionToString(dottedName) + (asName != null ? ("as " + asName) : "");
        }
    }

    private List<AsName> imports = new ArrayList<AsName>();

    private List<String> from;
    private List<AsName> fromImports = new ArrayList<AsName>();

    public void addImport(AsName asName) {
        imports.add(asName);
    }

    public void setFrom(List<String> from) {
        this.from = from;
    }

    public void addFromImports(AsName asName) {
        fromImports.add(asName);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        if (from != null) {
            res.append("from ");
            res.append(PythonObject.collectionToString(from));
            res.append(" import ");
            boolean first = true;
            for (AsName e : fromImports) {
                if (first) { first = false; }  else { res.append(", "); }
                res.append(e);
            }
            return res.toString();
        } else {
            res.append("import ");
            boolean first = true;
            for (AsName e : imports) {
                if (first) { first = false; }  else { res.append(", "); }
                res.append(e);
            }
            return res.toString();
        }
    }

    private Executable removeScope = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.popScope();
        }
    };

    protected void ensureLoaded(ThreadContext context, List<String> dottedName) {
        if (dottedName.size() != 1) {
            context.raise(exception("NotImplementedError", PythonString.valueOf("dotted name imports")));
        }
        String name = dottedName.get(dottedName.size() - 1);

        if (!GlobalScope.MODULES.containsKey(name)) {
            Module module = GlobalScope.moduleLoader.loadModule(name);
            GlobalScope.MODULES.put(name, module);
            context.pushScope(module);
            context.continuation(removeScope);
            context.continuation(module);
        }
    }


    public void evaluate(ThreadContext context) {
        if (from != null) {
            ensureLoaded(context, from);
            String moduleName = from.get(from.size() - 1);
            Module module = (Module)GlobalScope.MODULES.get(moduleName);

            for (AsName asName : fromImports) {
                String name = asName.dottedName.get(0);
                String toName = asName.asName != null ? asName.asName : name;

                context.currentScope.__setattr__(context, toName, module.getattr(context, name));
            }
        } else {
            for (AsName asName : imports) {
                ensureLoaded(context, asName.dottedName);
                String moduleName = asName.dottedName.get(asName.dottedName.size() - 1);
                Module module = (Module)GlobalScope.MODULES.get(moduleName);

                String importName = asName.asName != null ? asName.asName : moduleName;
                context.currentScope.__setattr__(context, importName, module);
            }
        }
    }
}
