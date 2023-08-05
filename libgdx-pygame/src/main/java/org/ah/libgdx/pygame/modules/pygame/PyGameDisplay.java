package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class PyGameDisplay extends PythonObject {

    public static interface WindowSizeCallback {
        public void setSize(int width, int height);
    }


    public static WindowSizeCallback windowSizeCallback;

    public static PythonClass PYGAME_DISPLAY_CLASS = new PythonClass("pygame.display") {
        {
            addMethod(new BuiltInMethod("set_mode") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args[0] instanceof ListAccessible) {
                    List<PythonObject> list = ((ListAccessible)args[0]).asList();
                    PyGameModule.DISPLAY_WIDTH = list.get(0).asInteger();
                    PyGameModule.DISPLAY_HEIGHT = list.get(1).asInteger();
                    if (windowSizeCallback != null) {
                        windowSizeCallback.setSize(PyGameModule.DISPLAY_WIDTH, PyGameModule.DISPLAY_HEIGHT);
                    }
                    context.pushData(new PyGameSurfaceScreen(PyGameModule.DISPLAY_WIDTH, PyGameModule.DISPLAY_HEIGHT));
                } else {
                    throw new UnsupportedOperationException("set_mode on arbitrary object does not work yet");
                }
            }});
            addMethod(new BuiltInBoundMethod("set_caption") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                // TODO
            }});

            addMethod(new BuiltInBoundMethod("set_icon") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                // TODO
            }});

            addMethod(new BuiltInBoundMethod("flip") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameModule.PYGAME_MODULE.flip();
            }});

            addMethod(new BuiltInBoundMethod("update") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameModule.PYGAME_MODULE.flip();
            }});
        }

    };

    public PyGameDisplay() {
        super(PYGAME_DISPLAY_CLASS);
    }

}