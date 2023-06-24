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
        super("random");

        addMethod(new BuiltInMethod("seed") {
            @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                random.setSeed(args[0].asInteger());
            }
        });

        addMethod(new BuiltInMethod("getstate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function getstate not supported yet");
        }});
        addMethod(new BuiltInMethod("setstate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function setstate not supported yet");
        }});
        addMethod(new BuiltInMethod("getrandbits") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function getrandbits not supported yet");
        }});
        addMethod(new BuiltInMethod("randrange") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function randrange not supported yet");
        }});
        addMethod(new BuiltInMethod("sample") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function sample not supported yet");
        }});
        addMethod(new BuiltInMethod("triangular") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function triangular not supported yet");
        }});
        addMethod(new BuiltInMethod("betavariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function betavariate not supported yet");
        }});
        addMethod(new BuiltInMethod("expovariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function expovariate not supported yet");
        }});
        addMethod(new BuiltInMethod("gammavariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gammavariate not supported yet");
        }});
        addMethod(new BuiltInMethod("gauss") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function gauss not supported yet");
        }});
        addMethod(new BuiltInMethod("lognormvariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function lognormvariate not supported yet");
        }});
        addMethod(new BuiltInMethod("normalvariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function normalvariate not supported yet");
        }});
        addMethod(new BuiltInMethod("vonmisesvariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function vonmisesvariate not supported yet");
        }});
        addMethod(new BuiltInMethod("paretovariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function paretovariate not supported yet");
        }});
        addMethod(new BuiltInMethod("weibullvariate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function weibullvariate not supported yet");
        }});
        addMethod(new BuiltInMethod("SystemRandom") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function SystemRandom not supported yet");
        }});




        addMethod(new BuiltInMethod("random") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            context.pushData(PythonFloat.valueOf(random.nextDouble()));
        }});
        addMethod(new BuiltInMethod("randint") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            int f = args[0].asInteger();
            int t = args[1].asInteger();
            int r = random.nextInt(t - f + 1) + f;
            context.pushData(PythonInteger.valueOf(r));
        }});
        addMethod(new BuiltInMethod("uniform") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            double f = args[0].asFloat();
            double t = args[1].asFloat();
            double r = random.nextDouble() * (t - f + 1) + f;
            context.pushData(PythonFloat.valueOf(r));
        }});
        addMethod(new BuiltInMethod("choice") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
//            int len = args[0].__len__(context).asInteger();
//            int i = random.nextInt(len);
//
//            return choices.__getitem__(context, PythonInteger.valueOf(i));
            throw new UnsupportedOperationException("random.choice");
        }});
    }
}
