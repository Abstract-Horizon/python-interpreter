package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.BuiltInIObject;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;

class PyGameEventInstance extends BuiltInIObject<PyGameEventInstance> {

    public static PythonClass PYGAME_EVENT_INSTANCE_CLASS = new PythonClass("pygame.Event") {
        {
            setAttribute("type", new Attribute<PyGameEventInstance>() {
                @Override public PythonObject attribute(PyGameEventInstance self) {
                    return self.eventType;
                }
                @Override public void assign(PyGameEventInstance self, PythonObject expr) { new UnsupportedOperationException(); }
            });
            setAttribute("pos", new Attribute<PyGameEventInstance>() {
                @Override public PythonObject attribute(PyGameEventInstance self) { return self.eventPos; }
                @Override public void assign(PyGameEventInstance self, PythonObject expr) { new UnsupportedOperationException(); }
            });
        }

    };

    private PythonTuple eventPos;
    private PythonObject eventType;

    public PyGameEventInstance(PythonObject eventType, int x, int y) {
        super(PYGAME_EVENT_INSTANCE_CLASS);
        this.eventType = eventType;
        this.eventPos = new PythonTuple();
        this.eventPos.asList().add(PythonInteger.valueOf(x));
        this.eventPos.asList().add(PythonInteger.valueOf(y));
    }
}