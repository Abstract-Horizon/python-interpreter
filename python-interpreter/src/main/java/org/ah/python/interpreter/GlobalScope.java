package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.modules.CopyModule;
import org.ah.python.modules.IterToolsModule;
import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;

public class GlobalScope {

    public static boolean CONTINUE = false;
    public static boolean BREAK = false;

    public static final Map<String, PythonObject> MODULES = new HashMap<String, PythonObject>();

    public static ModuleLoader moduleLoader = null;

    public static final void reset() {
        MODULES.clear();

        newmap(GlobalScope.MODULES)
            .name("sys").val(new SysModule())
            .name("math").val(new MathModule())
            .name("random").val(new RandomModule())
            .name("copy").val(new CopyModule())
            .name("itertools").val(new IterToolsModule())
            .name("time").val(new TimeModule());

        CONTINUE = false;
        BREAK = false;
    }

    private GlobalScope() {
        // super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
    }
}
