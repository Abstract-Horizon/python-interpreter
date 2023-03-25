package org.ah.libgdx.pygame.modules.pygame;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

class PyGameMixer extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameMixer.class);

    public PyGameMixer() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("Sound", new Function() { @Override public PythonObject call0(PythonObject soundName) {
            if (!PyGameModule.PRE_RUN) {
                String fileName = soundName.asString();
                String path = PyGameModule.PYGAME_MODULE.getPath();
                if (path != null && path.length() > 0) {
                    fileName = path + "/" + fileName;
                }
                FileHandle fileHandle = Gdx.files.internal(fileName);
                Sound sound = Gdx.audio.newSound(fileHandle);
                return new PyGameSound(sound);
            } else {
                return new PyGameSound(null);
            }
        }});
        TYPE.setAttribute("set_num_channels", new Function() { @Override public PythonObject call0(PythonObject numChannels) {
            return PythonNone.NONE;
        }});
        TYPE.setAttribute("init", new Function() { 
            @Override public PythonObject call0() {
                return PythonNone.NONE;
            }
            @Override public PythonObject call0(PythonObject frequency) {
                return PythonNone.NONE;
            }
            @Override public PythonObject call0(PythonObject frequency, PythonObject size) {
                return PythonNone.NONE;
            }
            @Override public PythonObject call0(PythonObject frequency, PythonObject size, PythonObject channels) {
                return PythonNone.NONE;
            }
            @Override public PythonObject call0(PythonObject frequency, PythonObject size, PythonObject channels, PythonObject buffer) {
                return PythonNone.NONE;
            }
        });
    }
    

}