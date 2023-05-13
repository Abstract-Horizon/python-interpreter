package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.List;
import java.util.Map;

public abstract class PythonSequence extends PythonObject {

    public static PythonClass PYTHON_SEQUENCE_CLASS = new PythonClass("Sequence");

    public static void populateCommonSequenceObjects(PythonClass pythonClass) {
        pythonClass.__setattr__("__getitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__getitem__(context, args[1]);
            }
        });
        pythonClass.__setattr__("__setitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__setitem__(context, args[1], args[2]);
            }
        });
        pythonClass.__setattr__("__delitem__", new BuiltInBoundMethod() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return args[0].__delitem__(context, args[1]);
            }
        });
    }

    public PythonBoolean __nonzero__(ThreadContext context) {
        if (__len__(context).asInteger(context) != 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public boolean asBoolean(ThreadContext context) {
        return __len__(context).asInteger(context) != 0;
    }

    public abstract List<PythonObject> asList();

}
