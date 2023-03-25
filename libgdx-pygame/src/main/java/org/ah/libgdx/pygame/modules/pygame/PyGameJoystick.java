package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

class PyGameJoystick extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameJoystick.class);

    public PyGameJoystick() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("get_count", new Function() { @Override public PythonObject call0() {
            return PythonInteger.valueOf(0);
        }});
    }
}