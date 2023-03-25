package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PythonObject {

    public static final boolean PROFILE = false;

    public static int createdObjects = 0;
    public static Map<String, Integer> createdTypes = new HashMap<String, Integer>();

    public static final Set<String> PRE_DEFINED_ATTRIBUTE_NAMES;
    public static PythonType TYPE = new PythonType(PythonObject.class);

    static {
        PRE_DEFINED_ATTRIBUTE_NAMES = new HashSet<String>();
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__len__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__int__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__long__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__float__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__str__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__init__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__self__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__call__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__del__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__repr__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__hash__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__nonzero__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__cmp__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__eq__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ne__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__lt__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__le__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__gt__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ge__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__eq__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__getattr__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__setattr__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__delattr__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__get__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__set__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__del__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__slots__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__getitem__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__setitem__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__deltitem__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__iter__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__reversed__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__contains__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__add__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__sub__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__mul__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__floordiv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__mod__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__mod__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__pow__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__lshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__and__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__xor__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__or__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__div__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__truediv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__radd__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rsub__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rmul__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rdiv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rtruediv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rfloordiv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rmod__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rdivmod__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rpow__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rlshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rrshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rand__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rxor__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__rxor__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__iadd__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__isub__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__imul__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__idiv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__itruediv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ifloordiv__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__imod__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ipow__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ilshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__irshift__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__iand__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ixor__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__ior__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__neg__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__pos__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__abs__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__invert__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__or__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__oct__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__hex__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__index__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__enter__");
        PRE_DEFINED_ATTRIBUTE_NAMES.add("__exit__");


        TYPE.setAttribute("__len__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__len__(); }});
        TYPE.setAttribute("__int__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__int__(); }});
        TYPE.setAttribute("__long__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__long__(); }});
        TYPE.setAttribute("__float__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__float__(); }});
        TYPE.setAttribute("__str__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__str__(); }});
        TYPE.setAttribute("__init__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__init__(); }});
        TYPE.setAttribute("__self__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__self__(); }});
        TYPE.setAttribute("__call__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__call__(); }});
        TYPE.setAttribute("__del__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__del__(); }});
        TYPE.setAttribute("__repr__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__repr__(); }});
        TYPE.setAttribute("__hash__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__hash__(); }});
        TYPE.setAttribute("__nonzero__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__nonzero__(); }});
        TYPE.setAttribute("__cmp__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__cmp__(o); }});
        TYPE.setAttribute("__eq__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__eq__(o); }});
        TYPE.setAttribute("__ne__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ne__(o); }});
        TYPE.setAttribute("__lt__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__lt__(o); }});
        TYPE.setAttribute("__le__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__le__(o); }});
        TYPE.setAttribute("__gt__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__gt__(o); }});
        TYPE.setAttribute("__ge__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ge__(o); }});
        TYPE.setAttribute("__eq__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__eq__(o); }});
        TYPE.setAttribute("__getattr__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k) { return self.__getattr__(k); }});
        TYPE.setAttribute("__setattr__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k, PythonObject v) { self.__setattr__(k, v); return PythonNone.NONE; }});
        TYPE.setAttribute("__delattr__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k) { self.__delattr__(k); return PythonNone.NONE; }});
        TYPE.setAttribute("__get__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject name, PythonObject type) { return self.__get__(name, type); }});
        TYPE.setAttribute("__set__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject name, PythonObject type) { return self.__set__(name, type); }});
        TYPE.setAttribute("__del__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject name, PythonObject type) { return self.__del__(name, type); }});
        TYPE.setAttribute("__slots__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__slots__(); }});
        TYPE.setAttribute("__getitem__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k) { return self.__getitem__(k); }});
        TYPE.setAttribute("__setitem__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k, PythonObject v) { self.__setitem__(k, v); return PythonNone.NONE; }});
        TYPE.setAttribute("__deltitem__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject k) { self.__delitem__(k); return PythonNone.NONE; }});
        TYPE.setAttribute("__iter__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__iter__(); }});
        TYPE.setAttribute("__reversed__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__reversed__(); }});
        TYPE.setAttribute("__contains__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__contains__(o); }});
        TYPE.setAttribute("__add__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__add__(o); }});
        TYPE.setAttribute("__sub__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__sub__(o); }});
        TYPE.setAttribute("__mul__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__mul__(o); }});
        TYPE.setAttribute("__floordiv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__floordiv__(o); }});
        TYPE.setAttribute("__mod__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__mod__(o); }});
        TYPE.setAttribute("__divmod__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__divmod__(o); }});
        TYPE.setAttribute("__pow__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__pow__(o); } @Override public PythonObject call0(PythonObject self, PythonObject o, PythonObject moduo) { return self.__pow__(o, moduo); }});
        TYPE.setAttribute("__lshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__lshift__(o); }});
        TYPE.setAttribute("__rshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rshift__(o); }});
        TYPE.setAttribute("__and__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__and__(o); }});
        TYPE.setAttribute("__xor__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__xor__(o); }});
        TYPE.setAttribute("__or__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__or__(o); }});
        TYPE.setAttribute("__div__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__div__(o); }});
        TYPE.setAttribute("__truediv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__truediv__(o); }});
        TYPE.setAttribute("__radd__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__radd__(o); }});
        TYPE.setAttribute("__rsub__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rsub__(o); }});
        TYPE.setAttribute("__rmul__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rmul__(o); }});
        TYPE.setAttribute("__rdiv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rdiv__(o); }});
        TYPE.setAttribute("__rtruediv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rtruediv__(o); }});
        TYPE.setAttribute("__rfloordiv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rfloordiv__(o); }});
        TYPE.setAttribute("__rmod__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rmod__(o); }});
        TYPE.setAttribute("__rdivmod__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rdivmod__(o); }});
        TYPE.setAttribute("__rpow__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rpow__(o); }});
        TYPE.setAttribute("__rlshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rlshift__(o); }});
        TYPE.setAttribute("__rrshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rrshift__(o); }});
        TYPE.setAttribute("__rand__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rand__(o); }});
        TYPE.setAttribute("__rxor__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rxor__(o); }});
        TYPE.setAttribute("__rxor__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__rxor__(o); }});
        TYPE.setAttribute("__iadd__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__iadd__(o); }});
        TYPE.setAttribute("__isub__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__isub__(o); }});
        TYPE.setAttribute("__imul__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__imul__(o); }});
        TYPE.setAttribute("__idiv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__idiv__(o); }});
        TYPE.setAttribute("__itruediv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__itruediv__(o); }});
        TYPE.setAttribute("__ifloordiv__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ifloordiv__(o); }});
        TYPE.setAttribute("__imod__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__imod__(o); }});
        TYPE.setAttribute("__ipow__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ipow__(o); } @Override public PythonObject call0(PythonObject self, PythonObject o, PythonObject m) { return self.__ipow__(o, m); }});
        TYPE.setAttribute("__ilshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ilshift__(o); }});
        TYPE.setAttribute("__irshift__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__irshift__(o); }});
        TYPE.setAttribute("__iand__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__iand__(o); }});
        TYPE.setAttribute("__ixor__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ixor__(o); }});
        TYPE.setAttribute("__ior__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__ior__(o); }});
        TYPE.setAttribute("__neg__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__neg__(); }});
        TYPE.setAttribute("__pos__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__pos__(); }});
        TYPE.setAttribute("__abs__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__abs__(); }});
        TYPE.setAttribute("__invert__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__invert__(); }});
        TYPE.setAttribute("__or__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject o) { return self.__or__(o); }});
        TYPE.setAttribute("__oct__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__oct__(); }});
        TYPE.setAttribute("__hex__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__hex__(); }});
        TYPE.setAttribute("__index__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__index__(); }});
        TYPE.setAttribute("__enter__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self) { return self.__enter__(); }});
        TYPE.setAttribute("__exit__", new InstanceMethod<PythonObject>() { @Override public PythonObject call0(PythonObject self, PythonObject execType, PythonObject valueType, PythonObject trackBack) { return self.__exit__(execType, valueType, trackBack); }});
    }

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

    public PythonObject dereference() {
        return this;
    }

    public String asString() {
        return __str__().asString();
    }

    public int asInteger() {
        return __int__().asInteger();
    }

    public double asDouble() {
        return __float__().asDouble();
    }

    public boolean asBoolean() {
        return true;
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

    public PythonInteger __int__() {
        throw new UnsupportedOperationException("__int__");
    }

    public PythonObject __long__() {
        throw new UnsupportedOperationException("__long__");
    }

    public PythonFloat __float__() {
        throw new UnsupportedOperationException("__float__");
    }

    public PythonString __str__() {
        throw new UnsupportedOperationException("__str__");
    }

    public PythonObject __init__() {
        throw new UnsupportedOperationException("__init__");
    }

    public PythonObject __self__() {
        return this;
    }

    public PythonObject __call__() {
        throw new UnsupportedOperationException("__call__");
    }

//    public PythonObject __call__(PythonObject[] args) {
//        return __call__();
//    }

    public PythonObject __del__() {
        throw new UnsupportedOperationException("__del__");
    }

    public PythonObject __repr__() {
        throw new UnsupportedOperationException("__repr__");
    }

    public PythonObject __hash__() {
        return PythonInteger.valueOf(hashCode());
    }

    public PythonBoolean __nonzero__() {
        return TRUE;
    }

    public PythonInteger __cmp__(PythonObject other) {
        throw new UnsupportedOperationException("__cmp__");
    }

    public PythonBoolean __eq__(PythonObject other) {
        if (other instanceof PythonInteger) {
            other = PythonBoolean.valueOf(other.asInteger() != 0);
        } else if (other instanceof PythonFloat) {
            other = PythonBoolean.valueOf(other.asDouble() != 0f);
        }
        return PythonBoolean.valueOf(this == other);
    }

    public PythonBoolean __ne__(PythonObject other) {
        return PythonBoolean.valueOf(!__eq__(other).asBoolean());
    }

    public PythonBoolean __lt__(PythonObject other) {
        return PythonBoolean.valueOf(__cmp__(other).asInteger() < 0);
    }

    public PythonBoolean __le__(PythonObject other) {
        return PythonBoolean.valueOf(__cmp__(other).asInteger() <= 0);
    }

    public PythonBoolean __gt__(PythonObject other) {
        return PythonBoolean.valueOf(__cmp__(other).asInteger() > 0);
    }

    public PythonBoolean __ge__(PythonObject other) {
        return PythonBoolean.valueOf(__cmp__(other).asInteger() >= 0);
    }

    public PythonObject __getattr__(PythonObject name) {

        PythonObject res = getType().getAttribute(name.asString());

        if (res == null) {
            throw new NoSuchElementException(name.asString());
        }

        return res;
    }

    public void __setattr__(PythonObject name, PythonObject value) {
        throw new UnsupportedOperationException("__setattr__");
    }

    public void __delattr__(PythonObject name) {
        throw new UnsupportedOperationException("__delattr__");
    }

    // Access

    public PythonObject __get__(PythonObject name, PythonObject type) {
        throw new UnsupportedOperationException("__get__");
    }

    public PythonObject __set__(PythonObject name, PythonObject type) {
        throw new UnsupportedOperationException("__set__");
    }

    public PythonObject __del__(PythonObject name, PythonObject type) {
        throw new UnsupportedOperationException("__del__");
    }


    public PythonObject __slots__() {
        throw new UnsupportedOperationException("__slots__");
    }

    // Container types

    public PythonInteger __len__() {
        throw new UnsupportedOperationException("__len__");
    }

    public PythonObject __getitem__(PythonObject key) {
        throw new UnsupportedOperationException("__getitem__");
    }

    public void __setitem__(PythonObject key, PythonObject value) {
        throw new UnsupportedOperationException("__setitem__");
    }

    public void __delitem__(PythonObject key) {
        throw new UnsupportedOperationException("__delitem__");
    }

    public PythonIterator __iter__() {
        throw new UnsupportedOperationException("__iter__");
    }

    public PythonObject __reversed__() {
        throw new UnsupportedOperationException("__reversed__");
    }

    public PythonObject __contains__(PythonObject value) {
        try {
            __getitem__(value);
            return TRUE;
        } catch (NoSuchElementException e) {
            return FALSE;
        }
    }

    public PythonObject __getslice__(PythonObject i, PythonObject j) {
        throw new UnsupportedOperationException("__getslice__");
    }

    public PythonObject __setslice__(PythonObject i, PythonObject j, PythonObject sequence) {
        throw new UnsupportedOperationException("__setslice__");
    }

    public PythonObject __delslice__(PythonObject i, PythonObject j) {
        throw new UnsupportedOperationException("__detslice__");
    }


    // Emulating numeric types
    public PythonObject __add__(PythonObject other) {
        throw new UnsupportedOperationException("__add__");
    }

    public PythonObject __sub__(PythonObject other) {
        throw new UnsupportedOperationException("__sub__");
    }

    public PythonObject __mul__(PythonObject other) {
        throw new UnsupportedOperationException("__mul__");
    }

    public PythonObject __floordiv__(PythonObject other) {
        throw new UnsupportedOperationException("__floordiv__");
    }

    public PythonObject __mod__(PythonObject other) {
        throw new UnsupportedOperationException("__mod__");
    }

    public PythonObject __divmod__(PythonObject other) {
        throw new UnsupportedOperationException("__divmod__");
    }

    public PythonObject __pow__(PythonObject other) {
        throw new UnsupportedOperationException("__pow__");
    }

    public PythonObject __pow__(PythonObject other, PythonObject moduo) {
        throw new UnsupportedOperationException("__pow__");
    }

    public PythonObject __lshift__(PythonObject other) {
        throw new UnsupportedOperationException("__lshift__");
    }

    public PythonObject __rshift__(PythonObject other) {
        throw new UnsupportedOperationException("__rshift__");
    }

    public PythonObject __and__(PythonObject other) {
        throw new UnsupportedOperationException("__and__");
    }

    public PythonObject __xor__(PythonObject other) {
        throw new UnsupportedOperationException("__xor__");
    }

    public PythonObject __or__(PythonObject other) {
        throw new UnsupportedOperationException("__or__");
    }


    public PythonObject __div__(PythonObject other) {
        throw new UnsupportedOperationException("__div__");
    }

    public PythonObject __truediv__(PythonObject other) {
        throw new UnsupportedOperationException("__truediv__");
    }


    public PythonObject __radd__(PythonObject other) {
        throw new UnsupportedOperationException("__radd__");
    }

    public PythonObject __rsub__(PythonObject other) {
        throw new UnsupportedOperationException("__rsub__");
    }

    public PythonObject __rmul__(PythonObject other) {
        throw new UnsupportedOperationException("__rmul__");
    }

    public PythonObject __rdiv__(PythonObject other) {
        throw new UnsupportedOperationException("__rdiv__");
    }

    public PythonObject __rtruediv__(PythonObject other) {
        throw new UnsupportedOperationException("__rtruediv__");
    }

    public PythonObject __rfloordiv__(PythonObject other) {
        throw new UnsupportedOperationException("__rfloordiv__");
    }

    public PythonObject __rmod__(PythonObject other) {
        throw new UnsupportedOperationException("__rmod__");
    }

    public PythonObject __rdivmod__(PythonObject other) {
        throw new UnsupportedOperationException("__rdivmod__");
    }

    public PythonObject __rpow__(PythonObject other) {
        throw new UnsupportedOperationException("__rpow__");
    }

    public PythonObject __rlshift__(PythonObject other) {
        throw new UnsupportedOperationException("__rlshift__");
    }

    public PythonObject __rrshift__(PythonObject other) {
        throw new UnsupportedOperationException("__rrshift__");
    }

    public PythonObject __rand__(PythonObject other) {
        throw new UnsupportedOperationException("__rand__");
    }

    public PythonObject __rxor__(PythonObject other) {
        throw new UnsupportedOperationException("__rxor__");
    }

    public PythonObject __ror__(PythonObject other) {
        throw new UnsupportedOperationException("__ror__");
    }


    public PythonObject __iadd__(PythonObject other) {
        throw new UnsupportedOperationException("__iadd__");
    }

    public PythonObject __isub__(PythonObject other) {
        throw new UnsupportedOperationException("__isub__");
    }

    public PythonObject __imul__(PythonObject other) {
        throw new UnsupportedOperationException("__imul__");
    }

    public PythonObject __idiv__(PythonObject other) {
        throw new UnsupportedOperationException("__idiv__");
    }

    public PythonObject __itruediv__(PythonObject other) {
        throw new UnsupportedOperationException("__itruediv__");
    }

    public PythonObject __ifloordiv__(PythonObject other) {
        throw new UnsupportedOperationException("__ifloordiv__");
    }

    public PythonObject __imod__(PythonObject other) {
        throw new UnsupportedOperationException("__imod__");
    }

    public PythonObject __ipow__(PythonObject other) {
        throw new UnsupportedOperationException("__ipow__");
    }

    public PythonObject __ipow__(PythonObject other, PythonObject moduo) {
        throw new UnsupportedOperationException("__ipow__");
    }

    public PythonObject __ilshift__(PythonObject other) {
        throw new UnsupportedOperationException("__ilshift__");
    }

    public PythonObject __irshift__(PythonObject other) {
        throw new UnsupportedOperationException("__irshift__");
    }

    public PythonObject __iand__(PythonObject other) {
        throw new UnsupportedOperationException("__iand__");
    }

    public PythonObject __ixor__(PythonObject other) {
        throw new UnsupportedOperationException("__ixor__");
    }

    public PythonObject __ior__(PythonObject other) {
        throw new UnsupportedOperationException("__ior__");
    }


    public PythonObject __neg__() {
        throw new UnsupportedOperationException("__neg__");
    }

    public PythonObject __pos__() {
        throw new UnsupportedOperationException("__pos__");
    }

    public PythonObject __abs__() {
        throw new UnsupportedOperationException("__abs__");
    }

    public PythonObject __invert__() {
        throw new UnsupportedOperationException("__invert__");
    }


    public PythonObject __complex__() {
        throw new UnsupportedOperationException("__or__");
    }


    public PythonObject __oct__() {
        throw new UnsupportedOperationException("__oct__");
    }

    public PythonObject __hex__() {
        throw new UnsupportedOperationException("__hex__");
    }


    public PythonObject __index__() {
        throw new UnsupportedOperationException("__index__");
    }


    public PythonObject __enter__() {
        throw new UnsupportedOperationException("__enter__");
    }

    public PythonObject __exit__(PythonObject execType, PythonObject execValue, PythonObject trackBack) {
        throw new UnsupportedOperationException("__exit__");
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
