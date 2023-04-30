package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonNone.NONE;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.util.UnsupportedFunction;

public class Function extends PythonObject implements CallableType {

    public PythonObject __call__(ThreadContext context, PythonObject[] args) {
        if (args.length == 0) {
            return call0(context);
        } else if (args.length == 1) {
            return call0(context, args[0].dereference());
        } else if (args.length == 2) {
            return call0(context, args[0].dereference(), args[1].dereference());
        } else if (args.length == 3) {
            return call0(context, args[0].dereference(), args[1].dereference(), args[2].dereference());
        } else if (args.length == 4) {
            return call0(context, args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference());
        } else if (args.length == 5) {
            return call0(context, args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference(), args[4].dereference());
        } else if (args.length == 6) {
            return call0(context, args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference(), args[4].dereference(), args[5].dereference());
        }
        throw new UnsupportedFunction(this, "__call__");
    }

    public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        return __call__(context);
    }

    public PythonObject __call__(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        return __call__(context);
    }

    protected PythonObject call0(ThreadContext context) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__()")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg)")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg0, PythonObject arg1) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg0, arg1)")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg0, PythonObject arg1, PythonObject arg2) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg0, arg1, arg2)")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg0, arg1, arg2, arg3)")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg0, arg1, arg2, arg3, arg4)")));
        return NONE;
    }

    protected PythonObject call0(ThreadContext context, PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4, PythonObject arg5) {
        context.raise(exception("AttributeError", PythonString.valueOf("__call__(arg0, arg1, arg2, arg3, arg4, arg5)")));
        return NONE;
    }

}
