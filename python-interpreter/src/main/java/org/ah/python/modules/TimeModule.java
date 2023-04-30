package org.ah.python.modules;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;
import org.ah.python.interpreter.ThreadContext;

public class TimeModule extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, TimeModule.class);

    public TimeModule() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.__setattr__("accept2dyear", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function accept2dyear not supported yet");
        }});
        TYPE.__setattr__("altzone", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function altzone not supported yet");
        }});
        TYPE.__setattr__("asctime", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function asctime not supported yet");
        }});
        TYPE.__setattr__("clock", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function clock not supported yet");
        }});
        TYPE.__setattr__("ctime", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function ctime not supported yet");
        }});
        TYPE.__setattr__("daylight", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        TYPE.__setattr__("daylight", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        TYPE.__setattr__("localtime", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function localtime not supported yet");
        }});
        TYPE.__setattr__("strftime", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function strftime not supported yet");
        }});
        TYPE.__setattr__("strptime", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function strptime not supported yet");
        }});
        TYPE.__setattr__("time", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function time not supported yet");
        }});
        TYPE.__setattr__("timezone", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function timezone not supported yet");
        }});
        TYPE.__setattr__("tzname", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function tzname not supported yet");
        }});
        TYPE.__setattr__("tzset", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context) {
            throw new UnsupportedOperationException("Function tzset not supported yet");
        }});


        TYPE.__setattr__("sleep", new BuiltInMethod() { @Override public PythonObject call0(ThreadContext context, PythonObject from) {
            try {
                Thread.sleep(from.asInteger(context) * 1000);
            } catch (InterruptedException ignore) {
            }
            return PythonNone.NONE;
        }});
    }
}
