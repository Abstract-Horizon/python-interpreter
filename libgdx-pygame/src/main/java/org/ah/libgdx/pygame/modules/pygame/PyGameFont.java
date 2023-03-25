package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.TWO;
import static org.ah.python.interpreter.PythonInteger.ZERO;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

class PyGameFont extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameFont.class);

    private BitmapFont font;

    public PyGameFont(BitmapFont font) {
        this.font = font;
    }

    public PythonType getType() { return TYPE; }

    public BitmapFont getFont() { return font; }

    static {
        TYPE.setAttribute("render", new InstanceMethod<PyGameFont>() {
            @Override public PythonObject call0(PyGameFont self, PythonObject text, PythonObject aa, PythonObject colour) {
                String s = text.asString();

                float r = colour.__getitem__(ZERO).dereference().asInteger() / 255.0f;
                float g = colour.__getitem__(ONE).dereference().asInteger() / 255.0f;
                float b = colour.__getitem__(TWO).dereference().asInteger() / 255.0f;

                return new PyGameSurfaceFont(self.getFont(), s, r, g, b);
            }
            @Override public PythonObject call0(PyGameFont self, PythonObject text, PythonObject aa, PythonObject colour, PythonObject background) {
                String s = text.asString();

                // TODO add background colour with 'none' option to PyGameSurfaceFont constructor

                float r = colour.__getitem__(ZERO).dereference().asInteger() / 255.0f;
                float g = colour.__getitem__(ONE).dereference().asInteger() / 255.0f;
                float b = colour.__getitem__(TWO).dereference().asInteger() / 255.0f;

                return new PyGameSurfaceFont(self.getFont(), s, r, g, b);
            }
        });
    }
}