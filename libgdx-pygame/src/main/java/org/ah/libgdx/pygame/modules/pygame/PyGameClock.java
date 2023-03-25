package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashSet;
import java.util.Set;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;
import org.ah.python.modules.BuiltInFunctions;

class PyGameClock extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameClock.class);

    private static long last;
    private static long lastTick;
    private static double fps = 0;
    private static double frames[] = new double[10];

    private static PythonObject tick = new InstanceMethod<PyGameClock>() { @Override public PythonObject call0(PyGameClock self, PythonObject args) {
        lastTick = System.currentTimeMillis();
        return self.tick(args);
    }};

    private static PythonObject get_fps = new Function() { @Override public PythonObject call0() {
        return PythonFloat.valueOf(fps);
    }};

    public PyGameClock() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("tick", tick);
        TYPE.setAttribute("get_fps", get_fps);
        TYPE.setAttribute("get_time", new Function() { @Override public PythonObject call0() {
            return PythonInteger.valueOf((int)(System.currentTimeMillis() - lastTick));
        }});
    }

    public PythonObject tick(PythonObject args) {
        int targetFps = args.asInteger() / 2;
        if (last > 0) {
            long now = System.currentTimeMillis();
            double msPerFrame = 1000.0 / targetFps;
            double lastFrameTime = now - last;
            if (lastFrameTime < msPerFrame) {
                while (now - last < msPerFrame) {
                    try {
                        int sleepTime = (int)(msPerFrame - now + last);
                        if (sleepTime > 0) {
                            Thread.sleep(sleepTime);
                        }
                    } catch (InterruptedException ignore) { }
                    now = System.currentTimeMillis();
                }
                lastFrameTime = now - last;
            } else if (PyGameModule.DEBUG) {
                BuiltInFunctions.printInterface.print("Warning frame lasted: " + lastFrameTime + "ms (target=" + msPerFrame + ") fps:" + fps);
            }
            double total = 0;
            for (int i = 0; i < frames.length - 2; i++) {
                frames[i + 1] = frames[i];
                total = total + frames[i];
            }
            frames[0] = lastFrameTime;
            total = total + lastFrameTime;

            fps = (1000.0 * frames.length) / total;
            last = now;

            if (PyGameModule.DEBUG) {
                System.out.println("Created Objects: " + PythonObject.createdObjects + ":   " + PythonObject.createdTypes);
                PythonObject.createdObjects = 0;
                Set<String> names = new HashSet<String>(PythonObject.createdTypes.keySet());
                for (String name : names) {
                    PythonObject.createdTypes.put(name, 0);
                }
            }
//            PyGameModule.createdSprites = 0;
//            System.out.println("Created Objects: " + PythonObject.createdObjects + " sprites=" + PyGameModule.createdSprites);
//            System.out.println("startSpriteBatch=" + PyGameModule.startSpriteBatch + " startShapeBatch=" + PyGameModule.startShapeBatch);
//            PyGameModule.startSpriteBatch = 0;
//            PyGameModule.startShapeBatch = 0;
        } else {
            double msPerFrame = 1000.0 / targetFps;
            for (int i = 0; i < frames.length; i++) {
                frames[i] = msPerFrame;
            }
            last = System.currentTimeMillis();
        }
        return PythonNone.NONE;
    }
}