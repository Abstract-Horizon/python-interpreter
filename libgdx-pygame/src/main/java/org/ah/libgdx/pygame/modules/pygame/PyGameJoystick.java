package org.ah.libgdx.pygame.modules.pygame;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

class PyGameJoystick extends org.ah.python.interpreter.Module {

    public PyGameJoystick() {
        super("pygame.joystick");

        addMethod(new BuiltInMethod("get_count") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonInteger.valueOf(0));
        }});
    }
}