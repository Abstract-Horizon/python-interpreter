package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;

public class GlobalScope extends PythonType {

    public static boolean CONTINUE = false;
    public static boolean BREAK = false;

    private static Stack<Scope> scopeStack = new Stack<Scope>();

    public static Scope currentScope() {
        return scopeStack.peek();
    }

    public static void pushScope(Scope scope) {
        scopeStack.push(scope);
    }

    public static void popScope() {
        scopeStack.pop();
    }

    public static Scope globalScope() {
        return scopeStack.get(0);
    }

    public static final Map<String, PythonObject> MODULES = new HashMap<String, PythonObject>();

    public static ModuleLoader moduleLoader = null;

    public static final void reset() {
        MODULES.clear();

        newmap(GlobalScope.MODULES)
            .name("sys").val(new SysModule())
            .name("math").val(new MathModule())
            .name("random").val(new RandomModule())
            .name("time").val(new TimeModule());

        scopeStack.clear();
//        Suite.BREAKOUT = false;
        CONTINUE = false;
        BREAK = false;
    }

    private GlobalScope() {
        super("GlobalScope");
    }
}
