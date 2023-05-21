package org.ah.python.interpreter;


public class PythonClassType extends PythonType implements CallableType {

    public static final PythonObject[] EMPTY_ARGS = new PythonObject[0];

    private Scope parentScope;

    public PythonClassType(String name, Scope parentScope) {
        super(name);
        this.parentScope = parentScope;
    }

    public PythonClassType(PythonType parent, String name) {
        super(parent, name);
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public PythonObject __call__(ThreadContext context, PythonObject[] args) {
        PythonObjectInstance self = new PythonObjectInstance(this);

// TODO!!!
//        PythonObject initMethod = getAttribute(context, "__init__");
//        if (initMethod != null) {
//            if (initMethod instanceof InstanceMethod) {
//                @SuppressWarnings("unchecked")
//                InstanceMethod<PythonObjectInstance> initInstanceMethod = (InstanceMethod<PythonObjectInstance>)initMethod;
//
//                initInstanceMethod.__call__(context, self, EMPTY_ARGS);
//            } else {
//                throw new IllegalStateException("__init__ method is not instance method!");
//            }
//        }
        return self;
    }

//    public PythonObject getAttribute(ThreadContext context, String name) {
//        if (attributes != null && attributes.containsKey(name)) {
//            return attributes.get(name);
//        }
//        PythonObject res = null;
//        if (getParent() != null) {
//            res = getParent().__getattr__(context, name);
//        }
//        if (res == null && parentScope != null) {
//            res = parentScope.__getattr__(context, name);
//        }
////        if (res == null) {
////            throw new NoSuchElementException(name + " in " + this);
////        }
//        return res;
//    }

    public void setParentType(PythonType parentType) {
        super.setParent(parentType);
    }

}
