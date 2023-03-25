package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameImage extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameImage.class);

    public PyGameImage() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("load", new Function() { @Override public PythonObject call0(PythonObject name) {
            Sprite sprite = null;
            if (!PyGameModule.PRE_RUN) {
                String imageName = name.asString();
                sprite = sprites.get(imageName);
                if (sprite== null) {
                    sprite = loadSprite(imageName);
                }
            }
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