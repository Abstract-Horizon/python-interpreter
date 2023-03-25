package org.ah.python.modules;

import static org.ah.python.interpreter.PythonNone.NONE;

import java.util.Random;

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

        TYPE.setAttribute("seed", new Function() { 
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

        TYPE.setAttribute("getstate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function getstate not supported yet");
        }});
        TYPE.setAttribute("setstate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function setstate not supported yet");
        }});
        TYPE.setAttribute("getrandbits", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function getrandbits not supported yet");
        }});
        TYPE.setAttribute("randrange", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function randrange not supported yet");
        }});
        TYPE.setAttribute("sample", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function sample not supported yet");
        }});
        TYPE.setAttribute("triangular", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function triangular not supported yet");
        }});
        TYPE.setAttribute("betavariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function betavariate not supported yet");
        }});
        TYPE.setAttribute("expovariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function expovariate not supported yet");
        }});
        TYPE.setAttribute("gammavariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function gammavariate not supported yet");
        }});
        TYPE.setAttribute("gauss", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function gauss not supported yet");
        }});
        TYPE.setAttribute("lognormvariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function lognormvariate not supported yet");
        }});
        TYPE.setAttribute("normalvariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function normalvariate not supported yet");
        }});
        TYPE.setAttribute("vonmisesvariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function vonmisesvariate not supported yet");
        }});
        TYPE.setAttribute("paretovariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function paretovariate not supported yet");
        }});
        TYPE.setAttribute("weibullvariate", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function weibullvariate not supported yet");
        }});
        TYPE.setAttribute("SystemRandom", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function SystemRandom not supported yet");
        }});




        TYPE.setAttribute("random", new Function() { @Override public PythonObject call0() {
            return PythonFloat.valueOf(random.nextDouble());
        }});
        TYPE.setAttribute("randint", new Function() { @Override public PythonObject call0(PythonObject from, PythonObject to) {
            int f = from.asInteger();
            int t = to.asInteger();
            int r = (int)(random.nextInt(t - f + 1) + f);
            return PythonInteger.valueOf(r);
        }});
        TYPE.setAttribute("uniform", new Function() { @Override public PythonObject call0(PythonObject from, PythonObject to) {
            double f = from.asDouble();
            double t = to.asDouble();
            double r = random.nextDouble() * (t - f + 1) + f;
            return PythonFloat.valueOf(r);
        }});
        TYPE.setAttribute("choice", new Function() { @Override public PythonObject call0(PythonObject choices) {
            int len = choices.__len__().asInteger();
            int i = random.nextInt(len);
            
            return choices.__getitem__(PythonInteger.valueOf(i));
        }});
    }
}
