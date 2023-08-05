package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class CopyModule extends org.ah.python.interpreter.Module {

    public CopyModule() {
        super("copy");

        addMethod(new BuiltInMethod("deepcopy") {
            @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                context.pushData(args[0].deepCopy());
            }
        });
    }
}
