package org.ah.python.interpreter;

public class Reference extends PythonObject implements Assignable {

    private PythonObject scope;
    private PythonString name;
    
    private PythonObject dereferencedScope;
    
    public Reference(PythonObject scope, PythonString name) {
        this.scope = scope;
        this.name = name;
    }
    
    public PythonObject dereference() {
        if (scope == null) {
            dereferencedScope = GlobalScope.currentScope().dereference();
            return dereferencedScope.__getattr__(name);
        } else {
            // TODO something is wrong here...
            PythonObject o = scope.dereference();
            while (o instanceof Reference) {
                o = ((Reference)o).dereference();
            }
            dereferencedScope = o.dereference();
            return dereferencedScope.__getattr__(name);
        }
    }
    
    protected PythonObject getDereferencedScope() {
        return dereferencedScope;
    }

    public void assign(PythonObject expr) {
        if (scope == null) {
            GlobalScope.currentScope().__setattr__(name, expr);
        } else {
            scope.dereference().__setattr__(name, expr);
        }
    }
    
    public String toString() {
        if (scope == null) {
            return "Reference(" + name.asString() + "@currentScope)";
        } else {
            return "Reference(" + name.asString() + "@" + scope.toString() + ")";
        }
    }
}
