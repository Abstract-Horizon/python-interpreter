package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonBoolean.FALSE;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

class PyGameFontStatic extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameFontStatic.class);

    private static Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();

    public PyGameFontStatic() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("SysFont", new InstanceMethod<PyGameFontStatic>() {
            @Override public PythonObject call0(PyGameFontStatic self, PythonObject name, PythonObject size) {
                return call0(self, name, size, FALSE);
            }
            @Override public PythonObject call0(PyGameFontStatic self, PythonObject name, PythonObject size, PythonObject bold) {
                return call0(self, name, size, bold, FALSE);
            }
            @Override public PythonObject call0(PyGameFontStatic self, PythonObject name, PythonObject size, PythonObject bold, PythonObject italic) {
                String fullName = name + "-" + size;
                BitmapFont font = fonts.get(fullName);
                if (font == null) {
                    try {
                        FileHandle fntFilehandle = Gdx.files.internal("data/" + name + "-" + size + ".fnt");
                        FileHandle pngFileHandle = Gdx.files.internal("data/" + name + "-" + size + ".png");
                        font = new BitmapFont(fntFilehandle, pngFileHandle, true);
                    } catch (Exception e) {
                        font = PyGameModule.PYGAME_MODULE.getFont();
                    }
                    fonts.put(fullName, font);
                }

                return new PyGameFont(font);
            }
        });
    }
}