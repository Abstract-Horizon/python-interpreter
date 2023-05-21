package org.ah.python.interpreter;

public class Reference extends PythonObject implements Assignable {

    protected PythonObject scope;
    protected String name;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            // TODO - this directly going to pythonClass is wrong! This needs to be moved to PythonObject.__getattr__
            context.a.pythonClass.__getattr__(context, name.toString());
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

    public void evaluate(ThreadContext context) {
        if (scope != null) {
            context.pushPC(continuation);
            scope.evaluate(context);
        } else {
            PythonObject scope = context.getCurrentScope();
            scope.__getattr__(context, name);
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
