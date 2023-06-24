package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class MathModule extends org.ah.python.interpreter.Module {

    public MathModule() {
        super("math");

        addMethod(new BuiltInMethod("ceil") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ceil not supported yet");
        }});
        addMethod(new BuiltInMethod("copysign") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function copysign not supported yet");
        }});
        addMethod(new BuiltInMethod("fabs") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fabs not supported yet");
        }});
        addMethod(new BuiltInMethod("factorial") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        addMethod(new BuiltInMethod("floor") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function floor not supported yet");
        }});
        addMethod(new BuiltInMethod("fmod") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fmod not supported yet");
        }});
        addMethod(new BuiltInMethod("frexp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function frexp not supported yet");
        }});
        addMethod(new BuiltInMethod("fsum") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function fsum not supported yet");
        }});
        addMethod(new BuiltInMethod("isfinite") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isfinite not supported yet");
        }});
        addMethod(new BuiltInMethod("isinf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isinf not supported yet");
        }});
        addMethod(new BuiltInMethod("isnan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function isnan not supported yet");
        }});
        addMethod(new BuiltInMethod("ldexp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ldexp not supported yet");
        }});
        addMethod(new BuiltInMethod("modf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function modf not supported yet");
        }});
        addMethod(new BuiltInMethod("trunc") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function trunc not supported yet");
        }});


        addMethod(new BuiltInMethod("exp") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function exp not supported yet");
        }});
        addMethod(new BuiltInMethod("expm1") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function expm1 not supported yet");
        }});
        addMethod(new BuiltInMethod("log") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log not supported yet");
        }});
        addMethod(new BuiltInMethod("log1p") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log1p not supported yet");
        }});
        addMethod(new BuiltInMethod("log2") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log2 not supported yet");
        }});
        addMethod(new BuiltInMethod("log10") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function log10 not supported yet");
        }});
        addMethod(new BuiltInMethod("pow") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function pow not supported yet");
        }});
        addMethod(new BuiltInMethod("sqrt") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sqrt not supported yet");
        }});


        addMethod(new BuiltInMethod("acos") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.acos(args[0].asFloat())));
        }});
        addMethod(new BuiltInMethod("asin") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.asin(args[0].asFloat())));
        }});
        addMethod(new BuiltInMethod("atan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.atan(args[0].asFloat())));
        }});
        addMethod(new BuiltInMethod("atan2") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function atan2 not supported yet");
        }});
        addMethod(new BuiltInMethod("cos") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.cos(args[0].asFloat())));
        }});
        addMethod(new BuiltInMethod("factorial") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function factorial not supported yet");
        }});
        addMethod(new BuiltInMethod("sin") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.sin(args[0].asFloat())));
        }});
        addMethod(new BuiltInMethod("tan") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(Math.tan(args[0].asFloat())));
        }});


        addMethod(new BuiltInMethod("degrees") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function degrees not supported yet");
        }});
        addMethod(new BuiltInMethod("radians") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function radians not supported yet");
        }});


        addMethod(new BuiltInMethod("acosh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function acosh not supported yet");
        }});
        addMethod(new BuiltInMethod("asinh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function asinh not supported yet");
        }});
        addMethod(new BuiltInMethod("atanh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function atanh not supported yet");
        }});
        addMethod(new BuiltInMethod("cosh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function cosh not supported yet");
        }});
        addMethod(new BuiltInMethod("sinh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sinh not supported yet");
        }});
        addMethod(new BuiltInMethod("tanh") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tanh not supported yet");
        }});


        addMethod(new BuiltInMethod("erf") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function erf not supported yet");
        }});
        addMethod(new BuiltInMethod("erfc") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function erfc not supported yet");
        }});
        addMethod(new BuiltInMethod("gamma") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gamma not supported yet");
        }});
        addMethod(new BuiltInMethod("lgamma") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function lgamma not supported yet");
        }});


        this.__setattr__("pi", PythonFloat.valueOf(Math.PI));
        this.__setattr__("e", PythonFloat.valueOf(Math.E));    }
}
