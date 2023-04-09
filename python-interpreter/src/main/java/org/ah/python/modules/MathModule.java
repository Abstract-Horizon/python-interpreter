package org.ah.python.modules;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

public class MathModule extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, MathModule.class);

    public MathModule() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.__setattr__("ceil", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ceil not supported yet");
        }});
        TYPE.__setattr__("copysign", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function copysign not supported yet");
        }});
        TYPE.__setattr__("fabs", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fabs not supported yet");
        }});
        TYPE.__setattr__("factorial", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.__setattr__("floor", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function floor not supported yet");
        }});
        TYPE.__setattr__("fmod", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fmod not supported yet");
        }});
        TYPE.__setattr__("frexp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function frexp not supported yet");
        }});
        TYPE.__setattr__("fsum", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fsum not supported yet");
        }});
        TYPE.__setattr__("isfinite", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isfinite not supported yet");
        }});
        TYPE.__setattr__("isinf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isinf not supported yet");
        }});
        TYPE.__setattr__("isnan", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isnan not supported yet");
        }});
        TYPE.__setattr__("ldexp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ldexp not supported yet");
        }});
        TYPE.__setattr__("modf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function modf not supported yet");
        }});
        TYPE.__setattr__("trunc", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function trunc not supported yet");
        }});


        TYPE.__setattr__("exp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function exp not supported yet");
        }});
        TYPE.__setattr__("expm1", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function expm1 not supported yet");
        }});
        TYPE.__setattr__("log", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log not supported yet");
        }});
        TYPE.__setattr__("log1p", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log1p not supported yet");
        }});
        TYPE.__setattr__("log2", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log2 not supported yet");
        }});
        TYPE.__setattr__("log10", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log10 not supported yet");
        }});
        TYPE.__setattr__("pow", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        TYPE.__setattr__("sqrt", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sqrt not supported yet");
        }});


        TYPE.__setattr__("acos", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.acos(arg.asFloat()));
        }});
        TYPE.__setattr__("asin", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.asin(arg.asFloat()));
        }});
        TYPE.__setattr__("atan", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.atan(arg.asFloat()));
        }});
        TYPE.__setattr__("atan2", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function atan2 not supported yet");
        }});
        TYPE.__setattr__("cos", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.cos(arg.asFloat()));
        }});
        TYPE.__setattr__("factorial", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.__setattr__("sin", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.sin(arg.asFloat()));
        }});
        TYPE.__setattr__("tan", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.tan(arg.asFloat()));
        }});


        TYPE.__setattr__("degrees", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function degrees not supported yet");
        }});
        TYPE.__setattr__("radians", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function radians not supported yet");
        }});


        TYPE.__setattr__("acosh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function acosh not supported yet");
        }});
        TYPE.__setattr__("asinh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function asinh not supported yet");
        }});
        TYPE.__setattr__("atanh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function atanh not supported yet");
        }});
        TYPE.__setattr__("cosh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function cosh not supported yet");
        }});
        TYPE.__setattr__("sinh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sinh not supported yet");
        }});
        TYPE.__setattr__("tanh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function tanh not supported yet");
        }});


        TYPE.__setattr__("erf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function erf not supported yet");
        }});
        TYPE.__setattr__("erfc", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function erfc not supported yet");
        }});
        TYPE.__setattr__("gamma", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function gamma not supported yet");
        }});
        TYPE.__setattr__("lgamma", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function lgamma not supported yet");
        }});


        TYPE.__setattr__("pi", PythonFloat.valueOf(Math.PI));
        TYPE.__setattr__("e", PythonFloat.valueOf(Math.E));
    }
}
