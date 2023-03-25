package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.PythonType;

class PyGameEventInstance extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameEventInstance.class);

    private PythonTuple eventPos;
    private PythonObject eventType;

    public PyGameEventInstance(PythonObject eventType, int x, int y) {
        this.eventType = eventType;
        this.eventPos = new PythonTuple();
        this.eventPos.asList().add(PythonInteger.valueOf(x));
        this.eventPos.asList().add(PythonInteger.valueOf(y));
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("type", new Proxy.ProxyAttribute<PyGameEventInstance>() {
            @Override public PythonObject attribute(PyGameEventInstance self) { return self.eventType; }
            @Override public void assign(PyGameEventInstance self, PythonObject expr) { new UnsupportedOperationException(); }
        });
        TYPE.setAttribute("pos", new Proxy.ProxyAttribute<PyGameEventInstance>() {
            @Override public PythonObject attribute(PyGameEventInstance self) { return self.eventPos; }
            @Override public void assign(PyGameEventInstance self, PythonObject expr) { new UnsupportedOperationException(); }
        });
    }
}