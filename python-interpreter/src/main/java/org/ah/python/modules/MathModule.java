package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class MathModule extends org.ah.python.interpreter.Module {

    public MathModule() {
        this.__setattr__("ceil", new BuiltInMethod("ceil") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ceil not supported yet");
        }});
        this.__setattr__("copysign", new BuiltInMethod("copysign") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function copysign not supported yet");
        }});
        this.__setattr__("fabs", new BuiltInMethod("fabs") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fabs not supported yet");
        }});
        this.__setattr__("factorial", new BuiltInMethod("factorial") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        this.__setattr__("floor", new BuiltInMethod("floor") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function floor not supported yet");
        }});
        this.__setattr__("fmod", new BuiltInMethod("fmod") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fmod not supported yet");
        }});
        this.__setattr__("frexp", new BuiltInMethod("frexp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function frexp not supported yet");
        }});
        this.__setattr__("fsum", new BuiltInMethod("fsum") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fsum not supported yet");
        }});
        this.__setattr__("isfinite", new BuiltInMethod("isfinite") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isfinite not supported yet");
        }});
        this.__setattr__("isinf", new BuiltInMethod("isinf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isinf not supported yet");
        }});
        this.__setattr__("isnan", new BuiltInMethod("isnan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isnan not supported yet");
        }});
        this.__setattr__("ldexp", new BuiltInMethod("ldexp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ldexp not supported yet");
        }});
        this.__setattr__("modf", new BuiltInMethod("modf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function modf not supported yet");
        }});
        this.__setattr__("trunc", new BuiltInMethod("trunc") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function trunc not supported yet");
        }});


        this.__setattr__("exp", new BuiltInMethod("exp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function exp not supported yet");
        }});
        this.__setattr__("expm1", new BuiltInMethod("expm1") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function expm1 not supported yet");
        }});
        this.__setattr__("log", new BuiltInMethod("log") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log not supported yet");
        }});
        this.__setattr__("log1p", new BuiltInMethod("log1p") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log1p not supported yet");
        }});
        this.__setattr__("log2", new BuiltInMethod("log2") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log2 not supported yet");
        }});
        this.__setattr__("log10", new BuiltInMethod("log10") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log10 not supported yet");
        }});
        this.__setattr__("pow", new BuiltInMethod("pow") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        this.__setattr__("sqrt", new BuiltInMethod("sqrt") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sqrt not supported yet");
        }});


        this.__setattr__("acos", new BuiltInMethod("acos") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.acos(args[0].asFloat())));
        }});
        this.__setattr__("asin", new BuiltInMethod("asin") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.asin(args[0].asFloat())));
        }});
        this.__setattr__("atan", new BuiltInMethod("atan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.atan(args[0].asFloat())));
        }});
        this.__setattr__("atan2", new BuiltInMethod("atan2") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function atan2 not supported yet");
        }});
        this.__setattr__("cos", new BuiltInMethod("cos") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.cos(args[0].asFloat())));
        }});
        this.__setattr__("factorial", new BuiltInMethod("factorial") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        this.__setattr__("sin", new BuiltInMethod("sin") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.sin(args[0].asFloat())));
        }});
        this.__setattr__("tan", new BuiltInMethod("tan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.tan(args[0].asFloat())));
        }});


        this.__setattr__("degrees", new BuiltInMethod("degrees") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function degrees not supported yet");
        }});
        this.__setattr__("radians", new BuiltInMethod("radians") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function radians not supported yet");
        }});


        this.__setattr__("acosh", new BuiltInMethod("acosh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function acosh not supported yet");
        }});
        this.__setattr__("asinh", new BuiltInMethod("asinh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function asinh not supported yet");
        }});
        this.__setattr__("atanh", new BuiltInMethod("atanh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function atanh not supported yet");
        }});
        this.__setattr__("cosh", new BuiltInMethod("cosh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function cosh not supported yet");
        }});
        this.__setattr__("sinh", new BuiltInMethod("sinh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sinh not supported yet");
        }});
        this.__setattr__("tanh", new BuiltInMethod("tanh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tanh not supported yet");
        }});


        this.__setattr__("erf", new BuiltInMethod("erf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function erf not supported yet");
        }});
        this.__setattr__("erfc", new BuiltInMethod("erfc") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function erfc not supported yet");
        }});
        this.__setattr__("gamma", new BuiltInMethod("gamma") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gamma not supported yet");
        }});
        this.__setattr__("lgamma", new BuiltInMethod("lgamma") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function lgamma not supported yet");
        }});


        this.__setattr__("pi", PythonFloat.valueOf(Math.PI));
        this.__setattr__("e", PythonFloat.valueOf(Math.E));
    }
}
