package org.ah.libgdx.pygame.modules.pygame;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

class PyGameMixer extends org.ah.python.interpreter.Module {

    public PyGameMixer() {
        super("pygame.mixer");

        __setattr__("Sound", PyGameSound.PYGAME_SOUND_CLASS);

        addMethod(new BuiltInMethod("set_num_channels") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        }});

        addMethod(new BuiltInMethod("init") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
//            @Override public PythonObject call0() {
//                return PythonNone.NONE;
//            }
//            @Override public PythonObject call0(PythonObject frequency) {
//                return PythonNone.NONE;
//            }
//            @Override public PythonObject call0(PythonObject frequency, PythonObject size) {
//                return PythonNone.NONE;
//            }
//            @Override public PythonObject call0(PythonObject frequency, PythonObject size, PythonObject channels) {
//                return PythonNone.NONE;
//            }
//            @Override public PythonObject call0(PythonObject frequency, PythonObject size, PythonObject channels, PythonObject buffer) {
//                return PythonNone.NONE;
//            }
        }});
    }
}