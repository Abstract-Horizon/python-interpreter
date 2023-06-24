package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonBaseException;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameTransform extends org.ah.python.interpreter.Module {

    public PyGameTransform() {
        super("pygame.transform");

        addMethod(new BuiltInMethod("flip"){ @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        //{ @Override public PythonObject call0(PythonObject image, PythonObject xbool, PythonObject ybool) {
            if (args.length < 3) {
                context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.transform.flip() requires at least 3 arguments")));
            } else {
                PyGameSurfaceSprite image = (PyGameSurfaceSprite)args[0];
                boolean xbool = args[1].asBoolean();
                boolean ybool = args[2].asBoolean();
                Sprite sprite = new Sprite(image.getSprite());
                sprite.flip(xbool, ybool);
                context.pushData(new PyGameSurfaceSprite(sprite));
            }
        }});
        addMethod(new BuiltInMethod("scale") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            if (args.length < 2) {
                context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.transform.scale() requires at least 2 arguments")));
            } else if (!(args[1] instanceof ListAccessible)) {
                throw new UnsupportedOperationException("pygame.transform.scale() on arbitrary object does not work yet");
            } else {
                // @Override public PythonObject call0(PythonObject image, PythonObject size) {
                List<PythonObject> size = ((ListAccessible)args[1]).asList();
                PyGameSurfaceSprite image = (PyGameSurfaceSprite)args[0];
                Sprite sprite = new Sprite(image.getSprite());
                PythonList list = ((PythonList)size);
                sprite.setSize(list.asList().get(0).asInteger(), list.asList().get(1).asInteger());
                context.pushData(new PyGameSurfaceSprite(sprite));
            }
        }});
        addMethod(new BuiltInMethod("smoothscale") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            if (args.length < 2) {
                context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.transform.smoothscale() requires at least 2 arguments")));
            } else if (!(args[1] instanceof ListAccessible)) {
                throw new UnsupportedOperationException("pygame.transform.smoothscale() on arbitrary object does not work yet");
            } else {
                // , new Function() { @Override public PythonObject call0(PythonObject image, PythonObject size) {
                PyGameSurfaceSprite image = (PyGameSurfaceSprite)args[0];
                List<PythonObject> size = ((ListAccessible)args[1]).asList();
                Sprite sprite = new Sprite(image.getSprite());
                PythonList list = ((PythonList)size);
                sprite.setSize(list.asList().get(0).asInteger(), list.asList().get(1).asInteger());
                context.pushData(new PyGameSurfaceSprite(sprite));
            }
        }});
        addMethod(new BuiltInMethod("rotate") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                //, new Function() { @Override public PythonObject call0(PythonObject image, PythonObject angle) {
            if (args.length < 2) {
                context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.transform.rotate() requires at least 2 arguments")));
            } else {
                PyGameSurfaceSprite image = (PyGameSurfaceSprite)args[0];
                Sprite sprite = new Sprite(image.getSprite());
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.rotate(-(float)args[1].asFloat());
                context.pushData(new PyGameSurfaceSprite(sprite));
            }
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