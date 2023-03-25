package org.ah.libgdx.pygame.modules.pygame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

class PyGameSurfaceFont extends PythonObject {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameImage.class);

    static {
        TYPE.setAttribute("get_width", new InstanceMethod<PyGameSurfaceFont>() { @Override public PythonObject call0(PyGameSurfaceFont self) {
            return PythonInteger.valueOf(self.get_width());
        }});
        TYPE.setAttribute("get_height", new InstanceMethod<PyGameSurfaceFont>() { @Override public PythonObject call0(PyGameSurfaceFont self) {
            return PythonInteger.valueOf(self.get_width());
        }});
        TYPE.setAttribute("get_rect", new InstanceMethod<PyGameSurfaceFont>() { @Override public PythonObject call0(PyGameSurfaceFont self) {
            return self.get_rect();
        }});
    }

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont font;
    private String text;
    private float r;
    private float g;
    private float b;

    public PyGameSurfaceFont(BitmapFont font, String text, float r, float g, float b) {
        this.font = font;
        this.text = text;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public PythonType getType() { return TYPE; }

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
        BitmapFont font = PyGameModule.PYGAME_MODULE.getFont();
        layout.setText(font, text);
        // TextBounds bounds = font.getBounds(text);
        return new PyGameRect(0, 0, (int)layout.width, (int)layout.height);
    }

}