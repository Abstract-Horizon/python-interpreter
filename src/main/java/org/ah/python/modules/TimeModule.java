package org.ah.python.modules;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

public class TimeModule extends Proxy {
    
    public static PythonType TYPE = new PythonType(PythonObject.TYPE, TimeModule.class);

    public TimeModule() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("accept2dyear", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function accept2dyear not supported yet");
        }});
        TYPE.setAttribute("altzone", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function altzone not supported yet");
        }});
        TYPE.setAttribute("asctime", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function asctime not supported yet");
        }});
        TYPE.setAttribute("clock", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function clock not supported yet");
        }});
        TYPE.setAttribute("ctime", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function ctime not supported yet");
        }});
        TYPE.setAttribute("daylight", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        TYPE.setAttribute("daylight", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        TYPE.setAttribute("localtime", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function localtime not supported yet");
        }});
        TYPE.setAttribute("strftime", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function strftime not supported yet");
        }});
        TYPE.setAttribute("strptime", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function strptime not supported yet");
        }});
        TYPE.setAttribute("time", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function time not supported yet");
        }});
        TYPE.setAttribute("timezone", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function timezone not supported yet");
        }});
        TYPE.setAttribute("tzname", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function tzname not supported yet");
        }});
        TYPE.setAttribute("tzset", new Function() { @Override public PythonObject call0() {
            throw new UnsupportedOperationException("Function tzset not supported yet");
        }});


        TYPE.setAttribute("sleep", new Function() { @Override public PythonObject call0(PythonObject from) {
            try {
                Thread.sleep(from.asInteger() * 1000);
            } catch (InterruptedException ignore) {
            }
            return PythonNone.NONE;
        }});
    }
}
