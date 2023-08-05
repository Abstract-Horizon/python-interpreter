package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class SysModule extends org.ah.python.interpreter.Module {

    public static interface SystemBridge {
        void exit(int status);
    }

    public static SystemBridge systemBridge = new SystemBridge() {
        @Override public void exit(int status) {
            // DO NOTHING!
        }
    };

    public SysModule() {
        super("sys");

        addMethod(new BuiltInMethod("exit") {
            @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args == null || args.length == 0) {
                    systemBridge.exit(0);
                } else {
                    systemBridge.exit(args[0].asInteger());
                }
            }
        });
    }
}
