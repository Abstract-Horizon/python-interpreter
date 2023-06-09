package org.ah.python.modules;

import java.util.Map;
import java.util.Random;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class RandomModule extends org.ah.python.interpreter.Module {

    private static Random random = new Random();

    public RandomModule() {
        this.__setattr__("seed", new BuiltInMethod() {
            @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                random.setSeed(args[0].asInteger());
            }
        });

        this.__setattr__("getstate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function getstate not supported yet");
        }});
        this.__setattr__("setstate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function setstate not supported yet");
        }});
        this.__setattr__("getrandbits", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function getrandbits not supported yet");
        }});
        this.__setattr__("randrange", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function randrange not supported yet");
        }});
        this.__setattr__("sample", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sample not supported yet");
        }});
        this.__setattr__("triangular", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function triangular not supported yet");
        }});
        this.__setattr__("betavariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function betavariate not supported yet");
        }});
        this.__setattr__("expovariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function expovariate not supported yet");
        }});
        this.__setattr__("gammavariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gammavariate not supported yet");
        }});
        this.__setattr__("gauss", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gauss not supported yet");
        }});
        this.__setattr__("lognormvariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function lognormvariate not supported yet");
        }});
        this.__setattr__("normalvariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function normalvariate not supported yet");
        }});
        this.__setattr__("vonmisesvariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function vonmisesvariate not supported yet");
        }});
        this.__setattr__("paretovariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function paretovariate not supported yet");
        }});
        this.__setattr__("weibullvariate", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function weibullvariate not supported yet");
        }});
        this.__setattr__("SystemRandom", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function SystemRandom not supported yet");
        }});




        this.__setattr__("random", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(random.nextDouble()));
        }});
        this.__setattr__("randint", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            int f = args[0].asInteger();
            int t = args[1].asInteger();
            int r = random.nextInt(t - f + 1) + f;
            context.pushData(PythonInteger.valueOf(r));
        }});
        this.__setattr__("uniform", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            double f = args[0].asFloat();
            double t = args[1].asFloat();
            double r = random.nextDouble() * (t - f + 1) + f;
            context.pushData(PythonFloat.valueOf(r));
        }});
        this.__setattr__("choice", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
//            int len = args[0].__len__(context).asInteger();
//            int i = random.nextInt(len);
//
//            return choices.__getitem__(context, PythonInteger.valueOf(i));
            throw new UnsupportedOperationException("random.choice");
        }});
    }
}
