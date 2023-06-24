package org.ah.libgdx.pygame.modules.pygame;

import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

class PyGameSurfaceFont extends PythonObject {

    public static PythonClass PYGAME_FONT_CLASS = new PythonClass("pygame.font.Font") {
        {
            addMethod(new BuiltInBoundMethod("get_width") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceFont self = (PyGameSurfaceFont)args[0];
                context.pushData(PythonInteger.valueOf(self.get_width()));
            }});
            addMethod(new BuiltInBoundMethod("get_height") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceFont self = (PyGameSurfaceFont)args[0];
                context.pushData(PythonInteger.valueOf(self.get_width()));
            }});
            addMethod(new BuiltInBoundMethod("get_rect") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                PyGameSurfaceFont self = (PyGameSurfaceFont)args[0];
                context.pushData(self.get_rect());
            }});
        }
    };

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont font;
    private String text;
    private float r;
    private float g;
    private float b;
    private PyGameRect rect;

    public PyGameSurfaceFont(BitmapFont font, String text, float r, float g, float b) {
        super(PYGAME_FONT_CLASS);
        this.font = font;
        this.text = text;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw(int x, int y) {
        //BitmapFont font = PyGameModule.PYGAME_MODULE.getFont();
        font.setColor(r, g, b, 1f);
        font.draw(PyGameModule.PYGAME_MODULE.getSpriteBatch(), text, x, y);
    }

    public int get_width() {
        // BitmapFont font = PyGameModule.PYGAME_MODULE.getFont();
        layout.setText(font, text);
        return (int)layout.width;
        // return (int)font.getData().getBounds(text).width;
    }

    public int get_height() {
        // BitmapFont font = PyGameModule.PYGAME_MODULE.getFont();
        layout.setText(font, text);
        return (int)layout.height;
        // return (int)font.getGlyphLayout(text).height;
    }

    public PyGameRect get_rect() {
        if (rect == null) {
            BitmapFont font = PyGameModule.PYGAME_MODULE.getFont();
            layout.setText(font, text);
            // TextBounds bounds = font.getBounds(text);
            rect = new PyGameRect(0, 0, (int)layout.width, (int)layout.height);
        }
        return rect;
    }

}