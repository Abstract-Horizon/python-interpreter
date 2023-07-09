package org.ah.libgdx.pygame.modules.pygame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
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

    private static PyGameEventInstance MOUSEDOWN_EVENT = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEBUTTONDOWN);
    private static PyGameEventInstance MOUSEUP_EVENT = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEBUTTONUP);
    private static PyGameEventInstance MOUSEMOTION_EVENT = new PyGameEventInstance(PyGameModule.EVENT_TYPE_MOUSEMOTION);
    private static PyGameEventInstance KEYDOWN_EVENT = new PyGameEventInstance(PyGameModule.EVENT_TYPE_KEYDOWN);
    private static PyGameEventInstance KEYUP_EVENT = new PyGameEventInstance(PyGameModule.EVENT_TYPE_KEYUP);

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
        events.remove(MOUSEDOWN_EVENT);
        MOUSEDOWN_EVENT.eventPos.asList().set(0, PythonInteger.valueOf(x));
        MOUSEDOWN_EVENT.eventPos.asList().set(1, PythonInteger.valueOf(y));
        events.add(MOUSEDOWN_EVENT);
    }

    public void addMouseUp(int x, int y) {
        events.remove(MOUSEUP_EVENT);
        MOUSEUP_EVENT.eventPos.asList().set(0, PythonInteger.valueOf(x));
        MOUSEUP_EVENT.eventPos.asList().set(1, PythonInteger.valueOf(y));
        events.add(MOUSEUP_EVENT);
    }

    public void addMouseMotion(int x, int y) {
        events.remove(MOUSEMOTION_EVENT);
        MOUSEMOTION_EVENT.eventPos.asList().set(0, PythonInteger.valueOf(x));
        MOUSEMOTION_EVENT.eventPos.asList().set(1, PythonInteger.valueOf(y));
        events.add(MOUSEMOTION_EVENT);
    }

    public void addKeyDown(int key) {
        events.remove(KEYDOWN_EVENT);
        KEYDOWN_EVENT.key = PythonInteger.valueOf(key);
        events.add(KEYDOWN_EVENT);
    }

    public void addKeyUp(int key) {
        events.remove(KEYUP_EVENT);
        KEYUP_EVENT.key = PythonInteger.valueOf(key);
        events.add(KEYUP_EVENT);
    }
}
