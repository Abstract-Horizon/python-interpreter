package org.ah.libgdx.pygame.modules.pygame;

import java.util.Arrays;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

class PyGameKey extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameKey.class);
    
    public PyGameKey() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("get_pressed", new Function() { @Override public PythonObject call0() {
            int len = PyGameModule.KEYS.length;
            PythonObject[] keysCopy = new PythonObject[len];
            System.arraycopy(PyGameModule.KEYS, 0, keysCopy, 0, len);
            return new PythonList(Arrays.asList(keysCopy));
//            return PyGameModule.KEYS;
        }});
    }
}