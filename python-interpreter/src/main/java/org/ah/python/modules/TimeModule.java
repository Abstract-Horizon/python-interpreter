package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class TimeModule extends org.ah.python.interpreter.Module {

    public TimeModule() {
        this.__setattr__("accept2dyear", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function accept2dyear not supported yet");
        }});
        this.__setattr__("altzone", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function altzone not supported yet");
        }});
        this.__setattr__("asctime", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function asctime not supported yet");
        }});
        this.__setattr__("clock", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function clock not supported yet");
        }});
        this.__setattr__("ctime", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ctime not supported yet");
        }});
        this.__setattr__("daylight", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        this.__setattr__("daylight", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        this.__setattr__("localtime", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function localtime not supported yet");
        }});
        this.__setattr__("strftime", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function strftime not supported yet");
        }});
        this.__setattr__("strptime", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function strptime not supported yet");
        }});
        this.__setattr__("time", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function time not supported yet");
        }});
        this.__setattr__("timezone", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function timezone not supported yet");
        }});
        this.__setattr__("tzname", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tzname not supported yet");
        }});
        this.__setattr__("tzset", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tzset not supported yet");
        }});


        this.__setattr__("sleep", new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            try {
                Thread.sleep((int)(args[0].asFloat() * 1000));
            } catch (InterruptedException ignore) {
            }
        }});
    }
}
