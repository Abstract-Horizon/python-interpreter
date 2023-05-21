package org.ah.python.modules;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;
import org.ah.python.interpreter.ThreadContext;

public class SysModule extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, SysModule.class);

    public static interface SystemBridge {
        void exit(int status);
    }

    public static SystemBridge systemBridge = new SystemBridge() {
        @Override public void exit(int status) {
            // DO NOTHING!
        }
    };

    public SysModule() {
    }

    @Override
    public PythonType getType() { return TYPE; }

    static {
        TYPE.__setattr__("exit", new BuiltInMethod() {
            @Override public PythonObject call0(ThreadContext context) {
                systemBridge.exit(0);
                return null;
            }
            @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
                systemBridge.exit(arg.asInteger());
                return null;
            }
        });
    }
}
