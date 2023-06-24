package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

class PyGameFontStatic extends org.ah.python.interpreter.Module {

    protected static Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();

    public PyGameFontStatic() {
        super("pygame.font");
        {
            __setattr__("SysFont", PyGameFont.PYGAME_FONT_CLASS);
        };
    }
}