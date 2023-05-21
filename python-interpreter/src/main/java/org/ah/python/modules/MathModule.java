package org.ah.python.modules;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;
import org.ah.python.interpreter.ThreadContext;

public class MathModule extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, MathModule.class);

    public MathModule() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.__setattr__("ceil", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function ceil not supported yet");
        }});
        TYPE.__setattr__("copysign", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function copysign not supported yet");
        }});
        TYPE.__setattr__("fabs", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function fabs not supported yet");
        }});
        TYPE.__setattr__("factorial", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.__setattr__("floor", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function floor not supported yet");
        }});
        TYPE.__setattr__("fmod", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function fmod not supported yet");
        }});
        TYPE.__setattr__("frexp", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function frexp not supported yet");
        }});
        TYPE.__setattr__("fsum", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function fsum not supported yet");
        }});
        TYPE.__setattr__("isfinite", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function isfinite not supported yet");
        }});
        TYPE.__setattr__("isinf", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function isinf not supported yet");
        }});
        TYPE.__setattr__("isnan", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function isnan not supported yet");
        }});
        TYPE.__setattr__("ldexp", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function ldexp not supported yet");
        }});
        TYPE.__setattr__("modf", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function modf not supported yet");
        }});
        TYPE.__setattr__("trunc", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function trunc not supported yet");
        }});


        TYPE.__setattr__("exp", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function exp not supported yet");
        }});
        TYPE.__setattr__("expm1", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function expm1 not supported yet");
        }});
        TYPE.__setattr__("log", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function log not supported yet");
        }});
        TYPE.__setattr__("log1p", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function log1p not supported yet");
        }});
        TYPE.__setattr__("log2", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function log2 not supported yet");
        }});
        TYPE.__setattr__("log10", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function log10 not supported yet");
        }});
        TYPE.__setattr__("pow", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        TYPE.__setattr__("sqrt", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function sqrt not supported yet");
        }});


        TYPE.__setattr__("acos", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.acos(arg.asFloat()));
        }});
        TYPE.__setattr__("asin", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.asin(arg.asFloat()));
        }});
        TYPE.__setattr__("atan", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.atan(arg.asFloat()));
        }});
        TYPE.__setattr__("atan2", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function atan2 not supported yet");
        }});
        TYPE.__setattr__("cos", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.cos(arg.asFloat()));
        }});
        TYPE.__setattr__("factorial", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.__setattr__("sin", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.sin(arg.asFloat()));
        }});
        TYPE.__setattr__("tan", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject arg) {
            return PythonFloat.valueOf(Math.tan(arg.asFloat()));
        }});


        TYPE.__setattr__("degrees", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function degrees not supported yet");
        }});
        TYPE.__setattr__("radians", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function radians not supported yet");
        }});


        TYPE.__setattr__("acosh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function acosh not supported yet");
        }});
        TYPE.__setattr__("asinh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function asinh not supported yet");
        }});
        TYPE.__setattr__("atanh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function atanh not supported yet");
        }});
        TYPE.__setattr__("cosh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function cosh not supported yet");
        }});
        TYPE.__setattr__("sinh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function sinh not supported yet");
        }});
        TYPE.__setattr__("tanh", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function tanh not supported yet");
        }});


        TYPE.__setattr__("erf", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function erf not supported yet");
        }});
        TYPE.__setattr__("erfc", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function erfc not supported yet");
        }});
        TYPE.__setattr__("gamma", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function gamma not supported yet");
        }});
        TYPE.__setattr__("lgamma", new BuiltInMethod() { @Override public PythonObject __call__(ThreadContext context) {
            throw new UnsupportedOperationException("Function lgamma not supported yet");
        }});


        TYPE.__setattr__("pi", PythonFloat.valueOf(Math.PI));
        TYPE.__setattr__("e", PythonFloat.valueOf(Math.E));
    }
}
