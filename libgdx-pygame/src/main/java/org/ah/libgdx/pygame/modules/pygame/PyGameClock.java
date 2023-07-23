package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.modules.BuiltInFunctions;

class PyGameClock extends PythonObject {

    public static PythonClass PYGAME_CLOCK_CLASS = new PythonClass("pygame.clock") {
        @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
            context.pushData(new PyGameClock());
        }

        {
            addMethod(new BuiltInBoundMethod("tick") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameClock self = (PyGameClock)args[0];
                lastTick = System.currentTimeMillis();
                context.pushData(self.tick(args[1]));

            }});
            addMethod(new BuiltInBoundMethod("get_fps") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.pushData(PythonFloat.valueOf(fps));
            }});
            addMethod(new BuiltInBoundMethod("get_time") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.pushData(PythonInteger.valueOf((int)(System.currentTimeMillis() - lastTick)));
            }});
        }
    };

    private static long last;
    private static long lastTick;
    private static double fps = 0;
    private static double frames[] = new double[10];

    public PyGameClock() {
        super(PYGAME_CLOCK_CLASS);
    }

    public PythonObject tick(PythonObject args) {
        int targetFps = args.asInteger() / 2;
        int lasted = 0;
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
            lasted = (int)(now - last);
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
        return PythonInteger.valueOf(lasted);
    }
}