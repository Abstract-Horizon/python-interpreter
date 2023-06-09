package org.ah.python.modules;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

public class TimeModule extends org.ah.python.interpreter.Module {

    public TimeModule() {
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function accept2dyear not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function altzone not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function asctime not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function clock not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function ctime not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function daylight not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function localtime not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function strftime not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function strptime not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function time not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function timezone not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tzname not supported yet");
        }});
        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            throw new UnsupportedOperationException("Function tzset not supported yet");
        }});


        addMethod(new BuiltInMethod() { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            try {
                Thread.sleep((int)(args[0].asFloat() * 1000));
            } catch (InterruptedException ignore) {
            }
        }});
    }
}
