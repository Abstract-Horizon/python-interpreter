package org.ah.python.interpreter;

import static org.ah.python.interpreter.ThreadContext.Executable;

public class Reference extends PythonObject implements Assignable {

    protected PythonObject scope;
    protected String name;

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            // PythonObject o = context.popData();
            // TODO - this directly going to pythonClass is wrong! This needs to be moved to PythonObject.__getattr__
            PythonObject o = context.a;
            context.continuation(checkBoundMethodContinuation);
            o.__getattr__(context, name.toString());
        }
    };

    private Executable checkBoundMethodContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            // TODO optimise BuiltInBoundMethod and Def.isInstanceMethod
            if (!(context.a instanceof BuiltInBoundMethod
                    || (context.a instanceof Def && ((Def)context.a).isInstanceMethod()))) {
                // Remove 'self'
                PythonObject data = context.a;
                context.popData();
                context.a = data;
            }
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
            context.continuation(continuation);
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
