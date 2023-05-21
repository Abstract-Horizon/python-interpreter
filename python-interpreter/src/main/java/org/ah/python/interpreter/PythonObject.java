package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PythonObject implements ThreadContext.Executable {

    public static final boolean PROFILE = false;

    public static int createdObjects = 0;
    public static Map<String, Integer> createdTypes = new HashMap<String, Integer>();

    public static PythonType TYPE = new PythonType(PythonObject.class);

    protected PythonClass pythonClass = PythonObjectClass.PYTHON_OBJECT_CLASS;

    public PythonObject() {
        if (PROFILE) {
            createdObjects = createdObjects + 1;
            String name = getClass().getName();
            Integer soFar = createdTypes.get(name);
            if (soFar == null) {
                soFar = 0;
            }
            soFar = soFar + 1;
            createdTypes.put(name, soFar);
        }
    }


    public PythonClass getPythonClass() {
        return pythonClass;
    }

    public void evaluate(ThreadContext context) {
        context.pushData(this);
        return;
    }

    public String asString() {
        if (pythonClass != null) {
            return "<" + pythonClass.asString() + ">";
        } else {
            return toString();
        }
    }

    public int asInteger() {
        throw new UnsupportedOperationException("asInteger on " + getPythonClass().getName());
    }

    public double asFloat() {
        throw new UnsupportedOperationException("asFloat on " + getPythonClass().getName());
    }

    public boolean asBoolean() {
        throw new UnsupportedOperationException("asBoolean on " + getPythonClass().getName());
    }

    public boolean isConstant() {
        return false;
    }

    public PythonObject dereferenceConstant() {
        return this;
    }

    public PythonType getType() {
        return TYPE;
    }

    public void evaluateObjectMethod(ThreadContext context, String methodName) {
        if (pythonClass != null) {
            context.raise(exception("AttributeError", PythonString.valueOf(methodName + "@" + pythonClass.getName())));
        } else {
            context.raise(exception("AttributeError", PythonString.valueOf(methodName + "@" + toString())));
        }
    }

    public void __int__(ThreadContext context) {
        evaluateObjectMethod(context, "__int__");
    }

    public void __float__(ThreadContext context) {
        evaluateObjectMethod(context, "__float__");
    }

    public void __bool__(ThreadContext context) {
        evaluateObjectMethod(context, "__bool__");
    }

    public void __str__(ThreadContext context) {
        __repr__(context);
    }

    public void __repr__(ThreadContext context) {
        context.pushData(PythonString.valueOf(asString()));
    }

    public void __init__(ThreadContext context) {
        evaluateObjectMethod(context, "__bool__");
    }

    public void __self__(ThreadContext context) {
        context.pushData(this);
    }

    public void __call__(ThreadContext context) {
        evaluateObjectMethod(context, "__call__");
    }

    public void __del__(ThreadContext context) {
        evaluateObjectMethod(context, "__del__");
    }

    public void __hash__(ThreadContext context) {
        context.pushData(PythonInteger.valueOf(hashCode()));
    }

    public void __nonzero__(ThreadContext context) {
        context.pushData(TRUE);
    }

    public void __eq__(ThreadContext context, PythonObject other) {
//        if (other instanceof PythonInteger) {
//            other = PythonBoolean.valueOf(other.asInteger(context) != 0);
//        } else if (other instanceof PythonFloat) {
//            other = PythonBoolean.valueOf(other.asFloat(context) != 0f);
//        }
        context.pushData(PythonBoolean.valueOf(this == other));
    }

    public void __ne__(ThreadContext context, PythonObject other) {
        __eq__(context, other);
        // TODO this can be done better!!!
        context.a = ((PythonBoolean)context.a).not();
    }

    public void __lt__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__lt__");
    }

    public void __le__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__le__");
    }

    public void __gt__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__gt__");
    }

    public void __ge__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ge__");
    }

    public void __getattr__(ThreadContext context, String name) {
        // TODO set try catch around next instruction!!!
        pythonClass.__getattr__(context, name);

        // On catch - try with getting '__getattr__' attribute :D
    }

    public void __setattr__(ThreadContext context, String name, PythonObject value) {
        evaluateObjectMethod(context, "__setattr__");
    }

    public void __delattr__(ThreadContext context, String name) {
        evaluateObjectMethod(context, "__delattr__");
    }

    // Access

    public void __get__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__get__");
    }

    public void __set__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__set__");
    }

    public void __del__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__del__");
    }


    public void __slots__(ThreadContext context) {
        evaluateObjectMethod(context, "__slots__");
    }

    // Container types

    public void __len__(ThreadContext context) {
        context.raise(exception("AttributeError", PythonString.valueOf("__len__")));
    }

    public void __getitem__(ThreadContext context, PythonObject key) {
        evaluateObjectMethod(context, "__getitem__");
    }

    public void __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        evaluateObjectMethod(context, "__setitem__");
    }

    public void __delitem__(ThreadContext context, PythonObject key) {
        evaluateObjectMethod(context, "__delitem__");
    }

    public void __iter__(ThreadContext context) {
        evaluateObjectMethod(context, "__iter__");
    }

    public void __reversed__(ThreadContext context) {
        evaluateObjectMethod(context, "__reversed__");
    }

    public void __contains__(ThreadContext context, PythonObject value) {
        evaluateObjectMethod(context, "__contains__");
//        try {
//            __getitem__(context, value);
//            return TRUE;
//        } catch (NoSuchElementException e) {
//            return FALSE;
//        }
    }

    public void __getslice__(ThreadContext context, PythonObject i, PythonObject j) {
        evaluateObjectMethod(context, "__getslice__");
    }

    public void __setslice__(ThreadContext context, PythonObject i, PythonObject j, PythonObject sequence) {
        evaluateObjectMethod(context, "__setslice__");
    }

    public void __delslice__(ThreadContext context, PythonObject i, PythonObject j) {
        evaluateObjectMethod(context, "__delslice__");
    }


    // Emulating numeric types
    public void __neg__(ThreadContext context) {
        evaluateObjectMethod(context, "__neg__");
    }

    public void __add__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__");
    }

    public void __sub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__");
    }

    public void __mul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__");
    }

    public void __floordiv__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__floordiv__)")));
    }

    public void __mod__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__mod__)")));
    }

    public void __divmod__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__divmod__)")));
    }

    public void __pow__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__pow__)")));
    }

    public void __pow__(ThreadContext context, PythonObject other, PythonObject moduo) {
         context.raise(exception("AttributeError", PythonString.valueOf("__pow__)")));
    }

    public void __lshift__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__lshift__)")));
    }

    public void __rshift__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__rshift__)")));
    }

    public void __and__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__and__)")));
    }

    public void __xor__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__xor__)")));
    }

    public void __or__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__or__)")));
    }

    public void __div__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__div__)")));
    }

    public void __truediv__(ThreadContext context, PythonObject other) {
        context.raise(exception("AttributeError", PythonString.valueOf("__truediv__)")));
    }


    public void __radd__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__radd__");
    }

    public void __rsub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rsub__");
    }

    public void __rmul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rmul__");
    }

    public void __rdiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rdiv__");
    }

    public void __rtruediv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rtruediv__");
    }

    public void __rfloordiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rfloordiv__");
    }

    public void __rmod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rmod__");
    }

    public void __rdivmod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rdivmod__");
    }

    public void __rpow__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rpow__");
    }

    public void __rlshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rlshift__");
    }

    public void __rrshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rrshift__");
    }

    public void __rand__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rand__");
    }

    public void __rxor__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rxor__");
    }

    public void __ror__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ror__");
    }


    public void __iadd__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__iadd__");
    }

    public void __isub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__isub__");
    }

    public void __imul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__imul__");
    }

    public void __idiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__idiv__");
    }

    public void __itruediv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__itruediv__");
    }

    public void __ifloordiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ifloordiv__");
    }

    public void __imod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__imod__");
    }

    public void __ipow__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ipow__");
    }

    public void __ipow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        evaluateObjectMethod(context, "__ipow__");
    }

    public void __ilshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ilshift__");
    }

    public void __irshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__irshift__");
    }

    public void __iand__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__iand__");
    }

    public void __ixor__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ixor__");
    }

    public void __ior__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ior__");
    }

    public void __pos__(ThreadContext context) {
        evaluateObjectMethod(context, "__pos__");
    }

    public void __abs__(ThreadContext context) {
        evaluateObjectMethod(context, "__abs__");
    }

    public void __invert__(ThreadContext context) {
        evaluateObjectMethod(context, "__invert__");
    }


    public void __complex__(ThreadContext context) {
        evaluateObjectMethod(context, "__complex__");
    }


    public void __oct__(ThreadContext context) {
        evaluateObjectMethod(context, "__oct__");
    }

    public void __hex__(ThreadContext context) {
        evaluateObjectMethod(context, "__hex__");
    }


    public void __index__(ThreadContext context) {
        evaluateObjectMethod(context, "__index__");
    }


    public void __enter__(ThreadContext context) {
        evaluateObjectMethod(context, "__enter__");
    }

    public void __exit__(ThreadContext context, PythonObject execType, PythonObject execValue, PythonObject trackBack) {
        evaluateObjectMethod(context, "__exit__");
    }

    public void __next__(ThreadContext context) {
        evaluateObjectMethod(context, "__next__");
    }



    public void __round__(ThreadContext context) {
        evaluateObjectMethod(context, "__round__");
    }


    protected static String collectionToString(Collection<?> col, String delimiter) {
        StringBuilder res = new StringBuilder();
        boolean first = true;
        for (Object o : col) {
            if (first) { first = false; } else { res.append(delimiter); }
            res.append(o.toString());
        }

        return res.toString();
    }


    public String toString() {
        String name = getClass().getName();
        int i = name.lastIndexOf(".");
        name = name.substring(i + 1);
        if ("PythonTuple".equals(name)) {
            throw new RuntimeException();
        }
        return name + "@" + System.identityHashCode(this);
    }
}
