package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;

class PyGameMouse extends org.ah.python.interpreter.Module {

    public PythonTuple mousePos = new PythonTuple();

    public PythonTuple mouseButtons = new PythonTuple();

    public PyGameMouse() {
        super("pygame.mouse");

        mousePos.asList().add(PythonInteger.ZERO);
        mousePos.asList().add(PythonInteger.ZERO);

        mouseButtons.asList().add(PythonBoolean.FALSE);
        mouseButtons.asList().add(PythonBoolean.FALSE);
        mouseButtons.asList().add(PythonBoolean.FALSE);

        addMethod(new BuiltInMethod("get_pos") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            List<PythonObject> list = mousePos.asList();
            list.set(0, PythonInteger.valueOf(Gdx.input.getX()));
            list.set(1, PythonInteger.valueOf(Gdx.input.getY()));
            context.pushData(mousePos);
        }});

        addMethod(new BuiltInMethod("get_pressed") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            List<PythonObject> list = mouseButtons.asList();
            for (int i = 0; i < 3; i++) {
                list.set(i, PythonBoolean.valueOf(Gdx.input.isButtonPressed(i)));
            }
            context.pushData(mouseButtons);
        }});
    }
}