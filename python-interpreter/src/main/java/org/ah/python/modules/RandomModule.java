package org.ah.python.modules;

import static org.ah.python.interpreter.PythonNone.NONE;

import java.util.Random;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonFloat;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

public class RandomModule extends Proxy {

    private static Random random = new Random();

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, RandomModule.class);

    public RandomModule() {
    }

    public PythonType getType() { return TYPE; }

    static {

        TYPE.__setattr__("seed", new BuiltInMethod() {
            @Override public PythonObject call0() {
                throw new UnsupportedOperationException("Function seed not supported yet");
            }
            @Override public PythonObject call0(PythonObject a) {
                random.setSeed(a.asInteger());
                return NONE;
            }
            @Override public PythonObject call0(PythonObject a, PythonObject version) {
                return NONE;
            }
        });

        TYPE.__setattr__("getstate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function getstate not supported yet");
        }});
        TYPE.__setattr__("setstate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function setstate not supported yet");
        }});
        TYPE.__setattr__("getrandbits", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function getrandbits not supported yet");
        }});
        TYPE.__setattr__("randrange", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function randrange not supported yet");
        }});
        TYPE.__setattr__("sample", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function sample not supported yet");
        }});
        TYPE.__setattr__("triangular", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function triangular not supported yet");
        }});
        TYPE.__setattr__("betavariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function betavariate not supported yet");
        }});
        TYPE.__setattr__("expovariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function expovariate not supported yet");
        }});
        TYPE.__setattr__("gammavariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function gammavariate not supported yet");
        }});
        TYPE.__setattr__("gauss", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function gauss not supported yet");
        }});
        TYPE.__setattr__("lognormvariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function lognormvariate not supported yet");
        }});
        TYPE.__setattr__("normalvariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function normalvariate not supported yet");
        }});
        TYPE.__setattr__("vonmisesvariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function vonmisesvariate not supported yet");
        }});
        TYPE.__setattr__("paretovariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function paretovariate not supported yet");
        }});
        TYPE.__setattr__("weibullvariate", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function weibullvariate not supported yet");
        }});
        TYPE.__setattr__("SystemRandom", new BuiltInMethod() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function SystemRandom not supported yet");
        }});




        TYPE.__setattr__("random", new BuiltInMethod() { @Override public PythonObject call0() {
            return PythonFloat.valueOf(random.nextDouble());
        }});
        TYPE.__setattr__("randint", new BuiltInMethod() { @Override public PythonObject call0(PythonObject from, PythonObject to) {
            int f = from.asInteger();
            int t = to.asInteger();
            int r = random.nextInt(t - f + 1) + f;
            return PythonInteger.valueOf(r);
        }});
        TYPE.__setattr__("uniform", new BuiltInMethod() { @Override public PythonObject call0(PythonObject from, PythonObject to) {
            double f = from.asFloat();
            double t = to.asFloat();
            double r = random.nextDouble() * (t - f + 1) + f;
            return PythonFloat.valueOf(r);
        }});
        TYPE.__setattr__("choice", new BuiltInMethod() { @Override public PythonObject call0(PythonObject choices) {
            int len = choices.__len__().asInteger();
            int i = random.nextInt(len);

            return choices.__getitem__(PythonInteger.valueOf(i));
        }});
    }
}
