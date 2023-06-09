package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;


public class Def extends PythonObject {

    public static class Argument extends PythonObject {
        private String name;
        private PythonObject defaultValue;

        public Argument(String name, PythonObject defaultValue) {
            super(PYTHON_INTERNAL_CLASS_NOT_DEFINED);
            this.name = name;
            this.defaultValue = defaultValue;
        }

        public String getName() { return name; }
        public PythonObject getDefaultValue() { return defaultValue; }
        public void setDefaultValue(PythonObject defaultValue) { this.defaultValue = defaultValue; }

        @Override
        public void evaluate(ThreadContext context) {
            if (defaultValue != null) {
                defaultValue.evaluate(context);
            }
        }

        public String toString() {
            if (defaultValue == null) {
                return "arg[" + name + "]";
            } else {
                return "arg[" + name + "=" + defaultValue.toString() + "]";
            }
        }
    }

    protected String name;
    private Argument[] args = null;
    private Argument[] argsToBeEvaluated = null;
    private Argument[] evaluatedArguments = null;
    private Scope functionScope = null;
    private boolean instanceMethod = false;

    private Block block = new Block();

    public static PythonObject RETURN = null;

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            Def.this.evaluatedArguments = new Argument[args.length];

            for (int i = 0; i < args.length; i++) {
                Argument r = args[i];
                if (r.defaultValue != null && !r.defaultValue.isConstant()) {
                    PythonObject o = context.popData();
                    Def.this.evaluatedArguments[i] = new Argument(r.name, o);
                } else {
                    Def.this.evaluatedArguments[i] = new Argument(r.name, r.defaultValue);
                }
            }

            functionScope = context.currentScope;
            if (functionScope instanceof PythonClassDef.PythonClassType) {
                instanceMethod = true;
            }

            System.out.println("Assigning def to " + context.currentScope + " with name " + name + (instanceMethod ? "as instance method" : ""));

            context.currentScope.__setattr__(context, name, Def.this);
        }
    };

    public Def(String name, Argument[] args) {
        super(new PythonMethodClass("<function " + name + ">"));
        this.name = name;
        this.args = args;
        List<Argument> argsToBeEvaluated = new ArrayList<Argument>();
        for (Argument arg : args) {
            if (arg.defaultValue != null && !arg.defaultValue.isConstant()) {
                argsToBeEvaluated.add(arg);
            }
        }
        this.argsToBeEvaluated = argsToBeEvaluated.toArray(new Argument[argsToBeEvaluated.size()]);
    }

    public boolean isInstanceMethod() {
        return instanceMethod;
    }

    public Block getBlock() {
        return block;
    }

    @Override public void evaluate(ThreadContext context) {
        if (argsToBeEvaluated.length > 0) {
            context.continuationWithEvaluate(continuation, argsToBeEvaluated);
        } else {
            continuation.evaluate(context);
        }
    }

    private static Executable closeScopeContinuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            context.currentScope.close();
            context.pushData(PythonNone.NONE);
        }
    };

    @Override public void __call__(ThreadContext context) {
        this.__call__(context, null);
    }

    @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        System.out.println("Executing function " + name + " with args " + arrayToString(evaluatedArguments, ", "));

        // Bind arguments
        Frame frame = new Frame(context, functionScope);
        context.pushScope(frame);

        // int r = 0;
        int argsSize = args.length;
        for (int i = 0; i < evaluatedArguments.length; i++) {
            Argument r = evaluatedArguments[i];
            String name = r.getName();
            if (i < argsSize) {
                frame.__setattr__(context, name, kargs[i]);
            } else if (r.defaultValue != null) {
                frame.__setattr__(context, name, r.defaultValue);
            } else if (kwargs != null && kwargs.containsKey(name)) {
                frame.__setattr__(context, name, kwargs.get(name));
            } else {
                throw new IllegalArgumentException(name + " is not satisfied nor with default value");
            }
        }

        context.continuation(closeScopeContinuation);

        block.terminateWithReturn();
        block.evaluate(context);
    }

    public String toString() {
        return "def " + name + "(" + arrayToString(args, ", ") + "): "; //  + method;
    }
}
