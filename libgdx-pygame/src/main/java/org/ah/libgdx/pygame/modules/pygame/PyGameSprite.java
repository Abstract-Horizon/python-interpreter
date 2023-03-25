package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;


public class PyGameSprite extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameTime.class);

    public PyGameSprite() {
    }

    public PythonType getType() { return TYPE; }

    static {
//        TYPE.setAttribute("Sprite", new Function() { @Override public PythonObject call0() {
//            return PyGameSurfaceSprite.TYPE;
//        }});
        TYPE.setAttribute("Sprite", PyGameSurfaceSprite.TYPE);
    }
}

