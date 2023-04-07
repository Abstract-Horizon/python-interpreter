package org.ah.python.interpreter;

public class Reference extends PythonObject implements Assignable {

    private PythonObject scope;
    private String name;

    private PythonObject dereferencedScope;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

        @Override public PythonObject execute(ThreadContext context) {
            return context.a.pythonClass.__getattr__(name.toString());
        }

    };

    public Reference(PythonObject scope, PythonString name) {
        this.scope = scope;
        this.name = name.asString();
    }

    public Reference(PythonObject scope, String name) {
        this.scope = scope;
        this.name = name;
    }

    public PythonObject execute(ThreadContext context) {
        if (scope != null) {
            context.pushPC(continuation);
            return scope.execute(context);
        } else {
            PythonObject scope = context.getCurrentScope();
            return scope.__getattr__(name);
        }
    }

//    public PythonObject dereference() {
//        if (scope == null) {
//            dereferencedScope = GlobalScope.currentScope().dereference();
//            return dereferencedScope.__getattr__(name);
//        } else {
//            // TODO something is wrong here...
//            PythonObject o = scope.dereference();
//            while (o instanceof Reference) {
//                o = ((Reference)o).dereference();
//            }
//            dereferencedScope = o.dereference();
//            return dereferencedScope.__getattr__(name);
//        }
//    }

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
            return "Reference(" + name + "@currentScope)";
        } else {
            return "Reference(" + name + "@" + scope.toString() + ")";
        }
    }
}
