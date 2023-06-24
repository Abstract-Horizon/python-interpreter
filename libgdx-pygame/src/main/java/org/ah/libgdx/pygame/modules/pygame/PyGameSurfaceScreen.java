package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameSurfaceScreen extends PythonObject {

    public static PythonClass PYGAME_SCREEN_SURFACE_CLASS = new PythonClass("pygame.surface.ScreenSurface") {
        {
            addMethod(new BuiltInBoundMethod("blit") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                // PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];
                int x, y = 0;
                PyGameSurfaceSprite sprite = (PyGameSurfaceSprite)args[1];
                if (args[2] instanceof PyGameRect) {
                    PyGameRect rect = (PyGameRect)args[2];
                    x = rect.x;
                    y = rect.y;
                } else if (args[2] instanceof ListAccessible) {
                    List<PythonObject> rectlist = ((ListAccessible)args[2]).asList();
                    x = rectlist.get(0).asInteger();
                    y = rectlist.get(1).asInteger();
                } else {
                    throw new UnsupportedOperationException("pygame.surface.ScreenSurface.blit second parameter on arbitrary object does not work yet, need rect or list; " + args[1]);
                }

                context.pushData(blit(sprite, x, y));
            }});
            addMethod(new BuiltInBoundMethod("fill") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                // PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(fill(args[1]));
            }});
            addMethod(new BuiltInBoundMethod("width") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(self.size().asList().get(0));
            }});
            addMethod(new BuiltInBoundMethod("height") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(self.size().asList().get(1));
            }});
            addMethod(new BuiltInBoundMethod("get_width") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(self.size().asList().get(0));
            }});
            addMethod(new BuiltInBoundMethod("get_height") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(self.size().asList().get(1));
            }});
            addMethod(new BuiltInBoundMethod("size") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];

                context.pushData(self.size());
            }});
            addMethod(new BuiltInBoundMethod("get_rect") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceScreen self = (PyGameSurfaceScreen)args[0];
                if (self.rect == null) {
                    self.rect = new PyGameRect(0, 0, self.getWidth(), self.getHeight());
                }

                context.pushData(self.rect);
            }});
        }
    };

    private int w;
    private int h;
    private PythonTuple size;
    private PyGameRect rect;

    public PyGameSurfaceScreen(int w, int h) {
        super(PYGAME_SCREEN_SURFACE_CLASS);
        this.w = w;
        this.h = h;
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
        float r, g, b = 0;
        float a = 1f;

        if (colour instanceof ListAccessible) {
            List<PythonObject> colorList = ((ListAccessible)colour).asList();
            r = colorList.get(0).asInteger() / 255.0f;
            g = colorList.get(1).asInteger() / 255.0f;
            b = colorList.get(2).asInteger() / 255.0f;
            if (colorList.size() > 3) {
                a = colorList.get(3).asInteger() / 255.0f;
            }
        } else {
            throw new UnsupportedOperationException("pygame.Font.render on arbitrary object does not work yet");
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