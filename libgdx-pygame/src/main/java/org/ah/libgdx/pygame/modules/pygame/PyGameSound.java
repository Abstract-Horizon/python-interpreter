package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.audio.Sound;

public class PyGameSound extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameSound.class);

    private Sound sound;
    
    public PyGameSound(Sound sound) {
        this.sound = sound;
    }
    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("play", new InstanceMethod<PyGameSound>() { 
            @Override public PythonObject call0(PyGameSound self) {
                if (!PyGameModule.PRE_RUN) {
                    self.sound.play();
                }
                return PythonNone.NONE;
            }
            @Override public PythonObject call0(PyGameSound self, PythonObject loops) {
                throw new UnsupportedOperationException("play(loops=...)");
            }
            @Override public PythonObject call0(PyGameSound self, PythonObject loops, PythonObject maxtime) {
                throw new UnsupportedOperationException("play(loops=...)");
            }
            @Override public PythonObject call0(PyGameSound self, PythonObject loops, PythonObject maxtime, PythonObject fadeMs) {
                throw new UnsupportedOperationException("play(loops=...)");
            }
        });
        TYPE.setAttribute("stop", new InstanceMethod<PyGameSound>() { 
            @Override public PythonObject call0(PyGameSound self) {
                if (!PyGameModule.PRE_RUN) {
                    self.sound.stop();
                }
                return PythonNone.NONE;
            }
        });
    }

}
