package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.libgdx.pygame.modules.pygame.PyGameVector2.PYGAME_VECTOR2_CLASS;
import static org.ah.python.interpreter.PythonBaseException.exception;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.ThreadContext;

class PyGameMath extends PythonObject {

    public static PythonClass PYGAME_MATH_CLASS = new PythonClass("pygame.math") {
        {
            addMethod(new BuiltInMethod("lerp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.raise(exception("NotImplementedError", PythonString.valueOf("pygame.math.lerp")));
            }});
            __setattr__("Vector2", PYGAME_VECTOR2_CLASS);
        }
    };

    public PyGameMath() {
        super(PYGAME_MATH_CLASS);
    }

}