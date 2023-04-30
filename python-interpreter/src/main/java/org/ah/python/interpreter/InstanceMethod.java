package org.ah.python.interpreter;

import org.ah.python.interpreter.util.UnsupportedFunction;

public class InstanceMethod<T extends PythonObject> extends PythonObject implements CallableType {

    public PythonObject __call__(ThreadContext context, PythonObject[] args) {
        if (args == null || args.length < 1) {
            throw new IllegalStateException("Instance method called with no arguments");
        }
        PythonObject self = args[0];
        PythonObject[] argsReduced = new PythonObject[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            argsReduced[i - 1] = args[i];
        }
        return __call__(context, self, argsReduced);
    }

    @SuppressWarnings("unchecked")
    public PythonObject __call__(ThreadContext context, PythonObject self, PythonObject[] args) {
        if (args.length == 0) {
            return call0((T)self);
        } else if (args.length == 1) {
            return call0((T)self, args[0].dereference());
        } else if (args.length == 2) {
            return call0((T)self, args[0].dereference(), args[1].dereference());
        } else if (args.length == 3) {
            return call0((T)self, args[0].dereference(), args[1].dereference(), args[2].dereference());
        } else if (args.length == 4) {
            return call0((T)self, args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference());
        } else if (args.length == 5) {
            return call0((T)self, args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference(), args[4].dereference());
        }
        throw new UnsupportedFunction(this, "__call__");
    }

    protected PythonObject call0(T self) {
        throw new UnsupportedFunction(this, "__call__()");
    }

    protected PythonObject call0(T self, PythonObject arg) {
        throw new UnsupportedFunction(this, "__call__(arg)");
    }

    protected PythonObject call0(T self, PythonObject arg1, PythonObject arg2) {
        throw new UnsupportedFunction(this, "__call__(arg1, arg2)");
    }

    protected PythonObject call0(T self, PythonObject arg1, PythonObject arg2, PythonObject arg3) {
        throw new UnsupportedFunction(this, "__call__(arg1, arg2, arg3)");
    }

    protected PythonObject call0(T self, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4) {
        throw new UnsupportedFunction(this, "__call__(arg1, arg2, arg3, arg4)");
    }

    protected PythonObject call0(T self, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4, PythonObject arg5) {
        throw new UnsupportedFunction(this, "__call__(arg1, arg2, arg3, arg4, arg5)");
    }

}
