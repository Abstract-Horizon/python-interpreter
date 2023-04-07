package org.ah.python.interpreter;

import java.util.List;
import java.util.Map;

public class PythonClass extends Attributes {

    private String name;

    public PythonClass(String name) {
        this.name = name;
    }

    public String toString() {
        return "<class '" + name + "'>";
    }

    protected void populateMethods() {
        __setattr__("__repr__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__repr__();
            }
        });
        __setattr__("__str__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__str__();
            }
        });
        __setattr__("__eq__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__eq__(args.get(1));
            }
        });
        __setattr__("__ne__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__ne__(args.get(1));
            }
        });
        __setattr__("__lt__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__lt__(args.get(1));
            }
        });
        __setattr__("__le__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__le__(args.get(1));
            }
        });
        __setattr__("__gt__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__gt__(args.get(1));
            }
        });
        __setattr__("__ge__", new BuiltInFunction() {
            public PythonObject __call__(List<PythonObject> args, Map<String, PythonObject> kwargs) {
                return args.get(0).__ge__(args.get(1));
            }
        });
    }
}
