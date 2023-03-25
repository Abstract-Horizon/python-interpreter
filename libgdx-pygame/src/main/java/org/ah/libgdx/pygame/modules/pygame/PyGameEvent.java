package org.ah.libgdx.pygame.modules.pygame;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

public class PyGameEvent extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameEvent.class);

    private static List<PythonObject> events = new ArrayList<PythonObject>();

    public PyGameEvent() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("get", new Function() { @Override public PythonObject call0() {
            return new PythonList(events);
        }});
    }

    public List<PythonObject> getEvents() {
        return events;
    }

    public void add(PyGameEventInstance event) {
        getEvents().add(event);
    }

    public void addMouseDown(int x, int y) {
        PyGameEventInstance event = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEBUTTONDOWN, x, y);
        add(event);
    }

    public void addMouseUp(int x, int y) {
        PyGameEventInstance event = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEBUTTONUP, x, y);
        add(event);
    }

    public void addMouseMotion(int x, int y) {
        PyGameEventInstance event = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEMOTION, x, y);
        add(event);
    }
}