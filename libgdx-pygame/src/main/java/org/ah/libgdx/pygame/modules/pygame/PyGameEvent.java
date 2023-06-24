package org.ah.libgdx.pygame.modules.pygame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class PyGameEvent extends PythonObject {

    public static PythonClass PYGAME_EVENT_CLASS = new PythonClass("pygame.event") {
        {
            addMethod(new BuiltInMethod("get") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.pushData(new PythonList(events));
            }});
        }
    };

    private static List<PythonObject> events = new ArrayList<PythonObject>();

    public PyGameEvent() {
        super(PYGAME_EVENT_CLASS);
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