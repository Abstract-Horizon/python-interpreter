package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.util.UnsupportedFunction;

public class Function extends PythonObject implements CallableType {

    public PythonObject __call__(PythonObject[] args) {
        if (args.length == 0) {
            return call0();
        } else if (args.length == 1) {
            return call0(args[0].dereference());
        } else if (args.length == 2) {
            return call0(args[0].dereference(), args[1].dereference());
        } else if (args.length == 3) {
            return call0(args[0].dereference(), args[1].dereference(), args[2].dereference());
        } else if (args.length == 4) {
            return call0(args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference());
        } else if (args.length == 5) {
            return call0(args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference(), args[4].dereference());
        } else if (args.length == 6) {
            return call0(args[0].dereference(), args[1].dereference(), args[2].dereference(), args[3].dereference(), args[4].dereference(), args[5].dereference());
        }
        throw new UnsupportedFunction(this, "__call__");
    }

    public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
        // This is main method to be called to initiate function call
        return __call__();
    }

    protected PythonObject call0() {
        throw new UnsupportedFunction(this, "__call__()");
    }

    protected PythonObject call0(PythonObject arg) {
        throw new UnsupportedFunction(this, "__call__(arg)");
    }

    protected PythonObject call0(PythonObject arg0, PythonObject arg1) {
        throw new UnsupportedFunction(this, "__call__(arg0, arg1)");
    }

    protected PythonObject call0(PythonObject arg0, PythonObject arg1, PythonObject arg2) {
        throw new UnsupportedFunction(this, "__call__(arg0, arg1, arg2)");
    }

    protected PythonObject call0(PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3) {
        throw new UnsupportedFunction(this, "__call__(arg0, arg1, arg2, arg3)");
    }

    protected PythonObject call0(PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4) {
        throw new UnsupportedFunction(this, "__call__(arg0, arg1, arg2, arg3, arg4)");
    }

    protected PythonObject call0(PythonObject arg0, PythonObject arg1, PythonObject arg2, PythonObject arg3, PythonObject arg4, PythonObject arg5) {
        throw new UnsupportedFunction(this, "__call__(arg0, arg1, arg2, arg3, arg4, arg5)");
    }

}
