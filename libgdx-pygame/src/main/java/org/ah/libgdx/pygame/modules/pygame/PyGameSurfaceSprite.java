package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameSurfaceSprite extends PythonObject {

    public static PythonClass PYGAME_SPRITE_CLASS = new PythonClass("pygame.sprite") {
        {
            addMethod(new BuiltInBoundMethod("convert") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self);
            }});
            addMethod(new BuiltInBoundMethod("convert_alpha") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self);
            }});
            addMethod(new BuiltInBoundMethod("width") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self.size().asList().get(0));
            }});
            addMethod(new BuiltInBoundMethod("height") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self.size().asList().get(1));
            }});
            addMethod(new BuiltInBoundMethod("get_width") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self.size().asList().get(0));
            }});
            addMethod(new BuiltInBoundMethod("get_height") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self.size().asList().get(1));
            }});
            addMethod(new BuiltInBoundMethod("size") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                context.pushData(self.size());
            }});
            addMethod(new BuiltInBoundMethod("get_rect") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceSprite self = (PyGameSurfaceSprite)args[0];
                if (self.rect == null) {
                    self.rect = new PyGameRect(0, 0, (int)self.sprite.getWidth(), (int)self.sprite.getHeight());
                }
                context.pushData(self.rect);
            }});
        }
    };

    private Sprite sprite;
    private PythonTuple size;
    private PyGameRect rect;

    public PyGameSurfaceSprite(Sprite sprite) {
        super(PYGAME_SPRITE_CLASS);
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public PythonTuple size() {
        if (size == null) {
            size = new PythonTuple();
            List<PythonObject> list = size.asList();
            list.add(PythonInteger.valueOf((int)sprite.getWidth()));
            list.add(PythonInteger.valueOf((int)sprite.getHeight()));
        }
        return size;
    }
}