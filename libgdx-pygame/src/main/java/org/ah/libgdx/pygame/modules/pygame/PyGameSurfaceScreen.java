package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.THREE;
import static org.ah.python.interpreter.PythonInteger.TWO;
import static org.ah.python.interpreter.PythonInteger.ZERO;

import java.util.List;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameSurfaceScreen extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameSurfaceScreen.class);

    private int w;

    private int h;

    private PythonTuple size;

    public PyGameSurfaceScreen(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("blit", new Function() {
            @Override public PythonObject call0(PythonObject sprite, PythonObject rect) {
                int x = rect.__getitem__(ZERO).dereference().asInteger();
                int y = rect.__getitem__(ONE).dereference().asInteger();
                return blit(sprite, x, y);
            }
            @Override public PythonObject call0(PythonObject sprite, PythonObject x, PythonObject y) {
                return blit(sprite, x.asInteger(), y.asInteger());
            }
        });
        TYPE.setAttribute("fill", new Function() { @Override public PythonObject call0(PythonObject colour) {
            return fill(colour);
        }});
        TYPE.setAttribute("width", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return self.size().asList().get(0);
        }});
        TYPE.setAttribute("height", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return self.size().asList().get(1);
        }});
        TYPE.setAttribute("get_width", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return self.size().asList().get(0);
        }});
        TYPE.setAttribute("get_height", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return self.size().asList().get(1);
        }});
        TYPE.setAttribute("size", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return self.size();
        }});
        TYPE.setAttribute("get_rect", new InstanceMethod<PyGameSurfaceScreen>() { @Override public PythonObject call0(PyGameSurfaceScreen self) {
            return new PyGameRect(0, 0, self.getWidth(), self.getHeight());
        }});
    }

    public static PythonObject blit(PythonObject spritePyObject, int x, int y) {
        if (spritePyObject instanceof PyGameSurfaceSprite) {
            PyGameSurfaceSprite spriteObject = (PyGameSurfaceSprite)spritePyObject;
            Sprite sprite = spriteObject.getSprite();
            sprite.setPosition(x, y);
            sprite.draw(PyGameModule.PYGAME_MODULE.getSpriteBatch());
        } else if (spritePyObject instanceof PyGameSurfaceFont) {
            PyGameSurfaceFont fontObject = (PyGameSurfaceFont)spritePyObject;
            fontObject.draw(x, y);
        } else {
            throw new IllegalArgumentException("Expected sprite to be of PyModuleSurfaceSprite type but was " + spritePyObject);
        }
        return PythonNone.NONE;
    }

    public static PythonObject fill(PythonObject colour) {
        float r = colour.__getitem__(ZERO).dereference().asInteger() / 255.0f;
        float g = colour.__getitem__(ONE).dereference().asInteger() / 255.0f;
        float b = colour.__getitem__(TWO).dereference().asInteger() / 255.0f;
        float a = 1f;
        if (colour.__len__().asInteger() > 3) {
            a = colour.__getitem__(THREE).dereference().asInteger() / 255.0f;
        }
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        return PythonNone.NONE;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public PythonTuple size() {
        if (size == null) {
            size = new PythonTuple();
            List<PythonObject> list = size.asList();
            list.add(PythonInteger.valueOf(getWidth()));
            list.add(PythonInteger.valueOf(getHeight()));
        }
        return size;
    }
}