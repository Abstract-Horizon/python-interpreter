package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameImage extends org.ah.python.interpreter.Module {

    public static PythonClass PYGAME_IMAGE_CLASS = new PythonClass("pygame.image") {
    };

    public PyGameImage() {
        super("pygame.image");

        addMethod(new BuiltInMethod("load") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            Sprite sprite = null;
            if (!PyGameModule.PRE_RUN) {
                String imageName = args[0].asString();
                sprite = sprites.get(imageName);
                if (sprite== null) {
                    sprite = loadSprite(imageName);
                }
            }
            context.pushData(new PyGameSurfaceSprite(sprite));
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