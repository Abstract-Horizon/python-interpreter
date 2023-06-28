package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBaseException.exception;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonObject implements Executable {

    public static final boolean PROFILE = false;

    public static int createdObjects = 0;
    public static Map<String, Integer> createdTypes = new HashMap<String, Integer>();

    protected PythonClass pythonClass;

    public PythonObject(PythonClass pythonClass) {
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
        this.pythonClass = pythonClass;
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

    public void evaluateObjectMethod(ThreadContext context, String methodName, PythonObject... kargs) {
        evaluateObjectMethod(context, methodName, null, kargs);
    }

    public void evaluateObjectMethod(ThreadContext context, String methodName, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        if (pythonClass != null) {
            Call.invoke(context, this, methodName, kwargs, kargs);
//            for (int i = kargs.length - 1; i >= 0; i--) {
//                context.pushData(kargs[i]);
//            }
//            // TODO we do this thinking it is OK - but we don't know!!!
//            context.pushData(this);
//            context.continuation(new EvaluateFunctionAndArgsContinuation(kargs.length));
//            // context.continuation(evaluateContinuation);
//
//            pythonClass.__getattr__(context, methodName);
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
        evaluateObjectMethod(context, "__call__", (Map<String, PythonObject>)null);
    }

    public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... kargs) {
        evaluateObjectMethod(context, "__call__", kwargs, kargs);
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
        context.pushData(PythonBoolean.valueOf(this == other));
    }

    public void __ne__(ThreadContext context, PythonObject other) {
        __eq__(context, other);
        // TODO this can be done better!!!
        context.setTop(((PythonBoolean)context.top()).not());
    }

    public void __lt__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__lt__", other);
    }

    public void __le__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__le__", other);
    }

    public void __gt__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__gt__", other);
    }

    public void __ge__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ge__", other);
    }

    public void __getattr__(ThreadContext context, String name) {
        // TODO set try catch around next instruction!!!
         if (pythonClass == null) {
             throw new NullPointerException(context.position() + name);
         }
        pythonClass.__getattr__(context, name);

        // On catch - try with getting '__getattr__' attribute :D
    }

    public void __setattr__(ThreadContext context, String name, PythonObject value) {
        evaluateObjectMethod(context, "__setattr__", PythonString.valueOf(name), value);
    }

    public void __delattr__(ThreadContext context, String name) {
        evaluateObjectMethod(context, "__delattr__");
    }

    // Access

    public void __get__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__get__", name, type);
    }

    public void __set__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__set__", name, type);
    }

    public void __del__(ThreadContext context, PythonObject name, PythonObject type) {
        evaluateObjectMethod(context, "__del__", name, type);
    }


    public void __slots__(ThreadContext context) {
        evaluateObjectMethod(context, "__slots__");
    }

    // Container types

    public void __len__(ThreadContext context) {
        evaluateObjectMethod(context, "__len__");
    }

    public void __getitem__(ThreadContext context, PythonObject key) {
        evaluateObjectMethod(context, "__getitem__", key);
    }

    public void __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        evaluateObjectMethod(context, "__setitem__", key, value);
    }

    public void __delitem__(ThreadContext context, PythonObject key) {
        evaluateObjectMethod(context, "__delitem__", key);
    }

    public void __iter__(ThreadContext context) {
        evaluateObjectMethod(context, "__iter__");
    }

    public void __reversed__(ThreadContext context) {
        evaluateObjectMethod(context, "__reversed__");
    }

    public void __contains__(ThreadContext context, PythonObject value) {
        evaluateObjectMethod(context, "__contains__", value);
//        try {
//            __getitem__(context, value);
//            return TRUE;
//        } catch (NoSuchElementException e) {
//            return FALSE;
//        }
    }

    public void __getslice__(ThreadContext context, PythonObject i, PythonObject j) {
        evaluateObjectMethod(context, "__getslice__", i, j);
    }

    public void __setslice__(ThreadContext context, PythonObject i, PythonObject j, PythonObject sequence) {
        evaluateObjectMethod(context, "__setslice__", i, j, sequence);
    }

    public void __delslice__(ThreadContext context, PythonObject i, PythonObject j) {
        evaluateObjectMethod(context, "__delslice__", i, j);
    }


    // Emulating numeric types
    public void __neg__(ThreadContext context) {
        evaluateObjectMethod(context, "__neg__");
    }

    public void __add__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__", other);
    }

    public void __sub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__", other);
    }

    public void __mul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__delslice__", other);
    }

    public void __floordiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__floordiv__", other);
    }

    public void __mod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__mod__", other);
    }

    public void __divmod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__divmod__", other);
    }

    public void __pow__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__pow__", other);
    }

    public void __pow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        evaluateObjectMethod(context, "__pow__", other, moduo);
    }

    public void __lshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__lshift__", other);
    }

    public void __rshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rshift__", other);
    }

    public void __and__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__and__", other);
    }

    public void __xor__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__xor__", other);
    }

    public void __or__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__or__", other);
    }

    public void __div__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__div__", other);
    }

    public void __truediv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__truediv__", other);
    }


    public void __radd__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__radd__", other);
    }

    public void __rsub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rsub__", other);
    }

    public void __rmul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rmul__", other);
    }

    public void __rdiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rdiv__", other);
    }

    public void __rtruediv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rtruediv__", other);
    }

    public void __rfloordiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rfloordiv__", other);
    }

    public void __rmod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rmod__", other);
    }

    public void __rdivmod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rdivmod__", other);
    }

    public void __rpow__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rpow__", other);
    }

    public void __rlshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rlshift__", other);
    }

    public void __rrshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rrshift__", other);
    }

    public void __rand__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rand__", other);
    }

    public void __rxor__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__rxor__", other);
    }

    public void __ror__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ror__", other);
    }


    public void __iadd__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__iadd__", other);
    }

    public void __isub__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__isub__", other);
    }

    public void __imul__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__imul__", other);
    }

    public void __idiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__idiv__", other);
    }

    public void __itruediv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__itruediv__", other);
    }

    public void __ifloordiv__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ifloordiv__", other);
    }

    public void __imod__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__imod__", other);
    }

    public void __ipow__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ipow__", other);
    }

    public void __ipow__(ThreadContext context, PythonObject other, PythonObject moduo) {
        evaluateObjectMethod(context, "__ipow__", other);
    }

    public void __ilshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ilshift__", other);
    }

    public void __irshift__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__irshift__", other);
    }

    public void __iand__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__iand__", other);
    }

    public void __ixor__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ixor__", other);
    }

    public void __ior__(ThreadContext context, PythonObject other) {
        evaluateObjectMethod(context, "__ior__", other);
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
        evaluateObjectMethod(context, "__exit__", execType, execValue, trackBack);
    }

    public void __next__(ThreadContext context) {
        evaluateObjectMethod(context, "__next__");
    }



    public void __round__(ThreadContext context) {
        evaluateObjectMethod(context, "__round__");
    }


    public static String collectionToString(Collection<?> col) {
        return collectionToString(col, ", ");
    }

    public static String collectionToString(Collection<?> col, String delimiter) {
        StringBuilder res = new StringBuilder();
        boolean first = true;
        for (Object o : col) {
            if (first) { first = false; } else { res.append(delimiter); }
            res.append(o.toString());
        }

        return res.toString();
    }

    public static String arrayToString(Object[] col) {
        return arrayToString(col, ", ");
    }

    public static String arrayToString(Object[] col, String delimiter) {
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
