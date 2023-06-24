package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.libgdx.pygame.modules.pygame.PyGameClock.PYGAME_CLOCK_CLASS;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

class PyGameTime extends PythonObject {

    public static PythonClass PYGAME_TIME_CLASS = new PythonClass("pygame.time") {
        {
            addMethod(new BuiltInMethod("get_ticks") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.pushData(PythonInteger.valueOf((int)(System.currentTimeMillis() - started)));
            }});
//            setAttribute("Clock", new Attribute<PyGameTime>() {
//                @Override public PythonObject attribute(PyGameTime self) { return new PyGameClock(); }
//                @Override public void assign(PyGameTime self, PythonObject expr) { }
//            });
            __setattr__("Clock", PYGAME_CLOCK_CLASS);
        }
    };

    private static long started = System.currentTimeMillis();

    public PyGameTime() {
        super(PYGAME_TIME_CLASS);
    }

}