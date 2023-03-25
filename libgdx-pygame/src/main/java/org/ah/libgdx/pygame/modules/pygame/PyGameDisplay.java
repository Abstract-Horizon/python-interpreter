package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.ZERO;
import static org.ah.python.interpreter.PythonNone.NONE;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

class PyGameDisplay extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameDisplay.class);

    public PyGameDisplay() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("set_mode", new Function() { @Override public PythonObject call0(PythonObject resolution) {
            int w = resolution.__getitem__(ZERO).dereference().asInteger();
            int h = resolution.__getitem__(ONE).dereference().asInteger();
            if (PyGameModule.PRE_RUN) {
                throw new PyGameModule.PyGamePreRunException(w, h);
            }
            PyGameModule.PYGAME_MODULE.getCamera().setToOrtho(true, w, h);
            return new PyGameSurfaceScreen(w, h);
        }});
        TYPE.setAttribute("set_caption", new Function() { @Override public PythonObject call0(PythonObject name) {
            return NONE;
        }});
        
        TYPE.setAttribute("flip", new Function() { @Override public PythonObject call0() {
            PyGameModule.PYGAME_MODULE.flip();
            return PythonNone.NONE;
        }});
    }
    
}