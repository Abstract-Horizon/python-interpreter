package org.ah.libgdx.pygame.modules.pygame;

import java.util.Arrays;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

class PyGameKey extends PythonObject {

    public static PythonClass PYGAME_KEY_CLASS = new PythonClass("pygame.key") {
        {
            addMethod(new BuiltInMethod("get_pressed") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                int len = PyGameModule.KEYS.length;
                PythonObject[] keysCopy = new PythonObject[len];
                System.arraycopy(PyGameModule.KEYS, 0, keysCopy, 0, len);
                context.pushData(new PythonList(Arrays.asList(keysCopy)));
            }});
        }
    };

    public PyGameKey() {
        super(PYGAME_KEY_CLASS);
    }
}