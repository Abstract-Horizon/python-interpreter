package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameTransform extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameTransform.class);

    public PyGameTransform() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("flip", new Function() { @Override public PythonObject call0(PythonObject image, PythonObject xbool, PythonObject ybool) {
            Sprite sprite = new Sprite(((PyGameSurfaceSprite)image).getSprite());
            sprite.flip(xbool.asBoolean(), ybool.asBoolean());
            return new PyGameSurfaceSprite(sprite);
        }});
        TYPE.setAttribute("scale", new Function() { @Override public PythonObject call0(PythonObject image, PythonObject size) {
            Sprite sprite = new Sprite(((PyGameSurfaceSprite)image).getSprite());
            PythonList list = ((PythonList)size);
            sprite.setSize(list.asList().get(0).asInteger(), list.asList().get(1).asInteger());
            return new PyGameSurfaceSprite(sprite);
        }});
        TYPE.setAttribute("smoothscale", new Function() { @Override public PythonObject call0(PythonObject image, PythonObject size) {
            Sprite sprite = new Sprite(((PyGameSurfaceSprite)image).getSprite());
            PythonList list = ((PythonList)size);
            sprite.setSize(list.asList().get(0).asInteger(), list.asList().get(1).asInteger());
            return new PyGameSurfaceSprite(sprite);
        }});
        TYPE.setAttribute("rotate", new Function() { @Override public PythonObject call0(PythonObject image, PythonObject angle) {
            Sprite sprite = new Sprite(((PyGameSurfaceSprite)image).getSprite());
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.rotate(-(float)angle.asDouble());
            return new PyGameSurfaceSprite(sprite);
        }});
    }

    static Map<String, Sprite> sprites = new HashMap<String, Sprite>();

    static Sprite loadSprite(String imageName) {
        String fileName = imageName;
        String path = PyGameModule.PYGAME_MODULE.getPath();
        if (path != null && path.length() > 0) {
            fileName = path + "/" + imageName;
        }
        FileHandle fh = Gdx.files.internal(fileName);
        Texture t = new Texture(fh);
        Sprite sprite = new Sprite(t);
        sprite.setFlip(false, true);

        sprites.put(imageName, sprite);
        return sprite;
    }

    public PythonObject load(PythonObject name) {
        return PythonNone.NONE;
    }
}