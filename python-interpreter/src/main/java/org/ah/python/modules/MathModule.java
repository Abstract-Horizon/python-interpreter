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
        TYPE.setAttribute("ceil", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ceil not supported yet");
        }});
        TYPE.setAttribute("copysign", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function copysign not supported yet");
        }});
        TYPE.setAttribute("fabs", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fabs not supported yet");
        }});
        TYPE.setAttribute("factorial", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.setAttribute("floor", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function floor not supported yet");
        }});
        TYPE.setAttribute("fmod", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fmod not supported yet");
        }});
        TYPE.setAttribute("frexp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function frexp not supported yet");
        }});
        TYPE.setAttribute("fsum", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function fsum not supported yet");
        }});
        TYPE.setAttribute("isfinite", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isfinite not supported yet");
        }});
        TYPE.setAttribute("isinf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isinf not supported yet");
        }});
        TYPE.setAttribute("isnan", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function isnan not supported yet");
        }});
        TYPE.setAttribute("ldexp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function ldexp not supported yet");
        }});
        TYPE.setAttribute("modf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function modf not supported yet");
        }});
        TYPE.setAttribute("trunc", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function trunc not supported yet");
        }});


        TYPE.setAttribute("exp", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function exp not supported yet");
        }});
        TYPE.setAttribute("expm1", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function expm1 not supported yet");
        }});
        TYPE.setAttribute("log", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log not supported yet");
        }});
        TYPE.setAttribute("log1p", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log1p not supported yet");
        }});
        TYPE.setAttribute("log2", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log2 not supported yet");
        }});
        TYPE.setAttribute("log10", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function log10 not supported yet");
        }});
        TYPE.setAttribute("pow", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        TYPE.setAttribute("sqrt", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sqrt not supported yet");
        }});


        TYPE.setAttribute("acos", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.acos(arg.asDouble()));
        }});
        TYPE.setAttribute("asin", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.asin(arg.asDouble()));
        }});
        TYPE.setAttribute("atan", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.atan(arg.asDouble()));
        }});
        TYPE.setAttribute("atan2", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function atan2 not supported yet");
        }});
        TYPE.setAttribute("cos", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.cos(arg.asDouble()));
        }});
        TYPE.setAttribute("factorial", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        TYPE.setAttribute("sin", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.sin(arg.asDouble()));
        }});
        TYPE.setAttribute("tan", new Function() { @Override public PythonObject call0(PythonObject arg) {
            return PythonFloat.valueOf(Math.tan(arg.asDouble()));
        }});


        TYPE.setAttribute("degrees", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function degrees not supported yet");
        }});
        TYPE.setAttribute("radians", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function radians not supported yet");
        }});


        TYPE.setAttribute("acosh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function acosh not supported yet");
        }});
        TYPE.setAttribute("asinh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function asinh not supported yet");
        }});
        TYPE.setAttribute("atanh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function atanh not supported yet");
        }});
        TYPE.setAttribute("cosh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function cosh not supported yet");
        }});
        TYPE.setAttribute("sinh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function sinh not supported yet");
        }});
        TYPE.setAttribute("tanh", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function tanh not supported yet");
        }});


        TYPE.setAttribute("erf", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function erf not supported yet");
        }});
        TYPE.setAttribute("erfc", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function erfc not supported yet");
        }});
        TYPE.setAttribute("gamma", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function gamma not supported yet");
        }});
        TYPE.setAttribute("lgamma", new Function() { @Override public PythonObject __call__() {
            throw new UnsupportedOperationException("Function lgamma not supported yet");
        }});


        TYPE.setAttribute("pi", PythonFloat.valueOf(Math.PI));
        TYPE.setAttribute("e", PythonFloat.valueOf(Math.E));
    }
}
