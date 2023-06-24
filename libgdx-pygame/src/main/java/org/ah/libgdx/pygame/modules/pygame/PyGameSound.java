package org.ah.libgdx.pygame.modules.pygame;

import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class PyGameSound extends PythonObject {

    public static PythonClass PYGAME_SOUND_CLASS = new PythonClass("pygame.Sound") {
        public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            String soundName = args[0].asString();
            if (!PyGameModule.PRE_RUN) {
                String fileName = soundName;
                String path = PyGameModule.PYGAME_MODULE.getPath();
                if (path != null && path.length() > 0) {
                    fileName = path + "/" + fileName;
                }
                FileHandle fileHandle = Gdx.files.internal(fileName);
                Sound sound = Gdx.audio.newSound(fileHandle);
                context.pushData(new PyGameSound(sound));
            } else {
                context.pushData(new PyGameSound(null));
            }
        }

        {
            addMethod(new BuiltInBoundMethod("play") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args.length == 1) {
                    PyGameSound self = (PyGameSound)args[0];
                    if (!PyGameModule.PRE_RUN) {
                        self.sound.play();
                    }
                } else if (args.length > 1) {
                    throw new UnsupportedOperationException("play(loops=...)");
                }

//                @Override public PythonObject call0(PyGameSound self, PythonObject loops) {
//                    throw new UnsupportedOperationException("play(loops=...)");
//                }
//                @Override public PythonObject call0(PyGameSound self, PythonObject loops, PythonObject maxtime) {
//                    throw new UnsupportedOperationException("play(loops=...)");
//                }
//                @Override public PythonObject call0(PyGameSound self, PythonObject loops, PythonObject maxtime, PythonObject fadeMs) {
//                    throw new UnsupportedOperationException("play(loops=...)");
//                }
            }});
            addMethod(new BuiltInBoundMethod("stop") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSound self = (PyGameSound)args[0];
                if (!PyGameModule.PRE_RUN) {
                    self.sound.stop();
                }
                context.pushData(PythonNone.NONE);
            }});
        }
    };

    private Sound sound;

    public PyGameSound(Sound sound) {
        super(PYGAME_SOUND_CLASS);
        this.sound = sound;
    }

}
