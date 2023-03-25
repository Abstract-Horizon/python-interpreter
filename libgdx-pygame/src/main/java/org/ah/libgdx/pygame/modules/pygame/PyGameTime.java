package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

class PyGameTime extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameTime.class);
    private static long started = System.currentTimeMillis();

    public PyGameTime() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("get_ticks", new Function() { @Override public PythonObject call0() {
            return PythonInteger.valueOf((int)(System.currentTimeMillis() - started));
        }});
        TYPE.setAttribute("Clock", new Function() { @Override public PythonObject call0() {
            return new PyGameClock();
        }});
    }
}