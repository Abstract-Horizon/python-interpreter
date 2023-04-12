package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;


public abstract class PythonNumber extends PythonObject {


    protected PythonNumber() {
    }

    protected static void populateCommonNumberClassMethods(PythonClass pythonClass) {
        pythonClass.__setattr__("__int__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__int__();
            }
        });
        pythonClass.__setattr__("__float__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__float__();
            }
        });
        pythonClass.__setattr__("__bool__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__bool__();
            }
        });
        pythonClass.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(args.get(1));
            }
        });
        pythonClass.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(args.get(1));
            }
        });
        pythonClass.__setattr__("__add__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__add__(args.get(1));
            }
        });
        pythonClass.__setattr__("__sub__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__sub__(args.get(1));
            }
        });
        pythonClass.__setattr__("__mul__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__mul__(args.get(1));
            }
        });
        pythonClass.__setattr__("__div__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__div__(args.get(1));
            }
        });
        pythonClass.__setattr__("__floordiv__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__floordiv__(args.get(1));
            }
        });
        pythonClass.__setattr__("__mod__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__mod__(args.get(1));
            }
        });
        pythonClass.__setattr__("__divmod__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__divmod__(args.get(1));
            }
        });
        pythonClass.__setattr__("__pow__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__pow__(args.get(1));
            }
        });
    }

    public PythonInteger __int__() {
        return PythonInteger.valueOf(asInteger());
    }

    public PythonFloat __float__() {
        return PythonFloat.valueOf(asFloat());
    }

    public PythonBoolean __bool__() {
        return PythonBoolean.valueOf(asBoolean());
    }

    public boolean isConstant() {
        return true;
    }

}
