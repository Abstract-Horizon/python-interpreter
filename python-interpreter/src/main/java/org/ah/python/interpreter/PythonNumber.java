package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;


public abstract class PythonNumber extends PythonObject {


    protected PythonNumber() {
    }

    protected static void populateCommonNumberClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__int__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__int__(context);
            }
        });
        pythonClass.__setattr__("__float__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__float__(context);
            }
        });
        pythonClass.__setattr__("__bool__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__bool__(context);
            }
        });
        pythonClass.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__sub__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__sub__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__mul__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__mul__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__div__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__div__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__floordiv__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__floordiv__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__mod__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__mod__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__divmod__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__divmod__(context, args.get(1));
            }
        });
        pythonClass.__setattr__("__pow__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__pow__(context, args.get(1));
            }
        });
    }

    public PythonInteger __int__(ThreadContext context) {
        return PythonInteger.valueOf(asInteger(context));
    }

    public PythonFloat __float__(ThreadContext context) {
        return PythonFloat.valueOf(asFloat(context));
    }

    public PythonBoolean __bool__(ThreadContext context) {
        return PythonBoolean.valueOf(asBoolean(context));
    }

    public boolean isConstant() {
        return true;
    }

}
