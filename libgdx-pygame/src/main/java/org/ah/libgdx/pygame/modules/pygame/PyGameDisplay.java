package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

class PyGameDisplay extends PythonObject {

    public static PythonClass PYGAME_DISPLAY_CLASS = new PythonClass("pygame.display") {
        {
            addMethod(new BuiltInMethod("set_mode") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args[0] instanceof ListAccessible) {
                    List<PythonObject> list = ((ListAccessible)args[0]).asList();
                    int w = list.get(0).asInteger();
                    int h = list.get(1).asInteger();
                    if (PyGameModule.PRE_RUN) {
                        throw new PyGameModule.PyGamePreRunException(w, h);
                    }
                    PyGameModule.PYGAME_MODULE.getCamera().setToOrtho(true, w, h);
                    context.pushData(new PyGameSurfaceScreen(w, h));
                } else {
                    throw new UnsupportedOperationException("set_mode on arbitrary object does not work yet");
                }
            }});
            addMethod(new BuiltInBoundMethod("set_caption") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            }});

            addMethod(new BuiltInBoundMethod("flip") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameModule.PYGAME_MODULE.flip();
            }});
        }

    };

    public PyGameDisplay() {
        super(PYGAME_DISPLAY_CLASS);
    }

}