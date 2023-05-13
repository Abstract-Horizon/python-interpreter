package org.ah.python.interpreter;

public class Reference extends PythonObject implements Assignable {

    protected PythonObject scope;
    protected String name;

    private PythonObject dereferencedScope;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public PythonObject execute(ThreadContext context) {
            return context.a.pythonClass.__getattr__(context, name.toString());
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

    public PythonObject getScope() {
        return scope;
    }

    public String getName() {
        return name;
    }

    public PythonObject execute(ThreadContext context) {
        if (scope != null) {
            context.pushPC(continuation);
            return scope.execute(context);
        } else {
            PythonObject scope = context.getCurrentScope();
            return scope.__getattr__(context, name);
        }
    }

    protected PythonObject getDereferencedScope() {
        return dereferencedScope;
    }

    public void assign(ThreadContext context, PythonObject expr) {
        if (scope == null) {
            GlobalScope.currentScope().__setattr__(name, expr);
        } else {
            scope.dereference().__setattr__(context, name, expr);
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
