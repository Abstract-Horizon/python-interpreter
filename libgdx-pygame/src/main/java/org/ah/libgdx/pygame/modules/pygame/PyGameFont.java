package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInBoundMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonBaseException;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

class PyGameFont extends PythonObject {

    public static PythonClass PYGAME_FONT_CLASS = new PythonClass("pygame.font") {
        public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            if (args.length < 3) {
                context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.font.Font() requires at least 3 arguments")));
            }
            String name = args[1].asString();
            double size = args[2].asFloat();
            @SuppressWarnings("unused")
            boolean bold = false;
            @SuppressWarnings("unused")
            boolean italic = false;

            if (args.length > 4) {
                bold = args[3].asBoolean();
            }
            if (args.length > 5) {
                italic = args[4].asBoolean();
            }

            String fullName = name + "-" + size;
            BitmapFont font = PyGameFontStatic.fonts.get(fullName);
            if (font == null) {
                try {
                    FileHandle fntFilehandle = Gdx.files.internal("data/" + name + "-" + size + ".fnt");
                    FileHandle pngFileHandle = Gdx.files.internal("data/" + name + "-" + size + ".png");
                    font = new BitmapFont(fntFilehandle, pngFileHandle, true);
                } catch (Exception e) {
                    font = PyGameModule.PYGAME_MODULE.getFont();
                }
                PyGameFontStatic.fonts.put(fullName, font);
            }

            context.pushData(new PyGameFont(font));
        }

        {
            addMethod(new BuiltInBoundMethod("render") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args.length < 3) {

                } else {
                    PyGameFont self = (PyGameFont)args[0];
                    String s = args[1].asString();
                    float r, g, b = 0;

                    if (args[3] instanceof ListAccessible) {
                        List<PythonObject> colorList = ((ListAccessible)args[1]).asList();
                        r = colorList.get(0).asInteger() / 255.0f;
                        g = colorList.get(1).asInteger() / 255.0f;
                        b = colorList.get(2).asInteger() / 255.0f;
                    } else {
                        throw new UnsupportedOperationException("pygame.Font.render on arbitrary object does not work yet");
                    }
                    if (args.length > 3) {
                        // TODO add background colour with 'none' option to PyGameSurfaceFont constructor
                    }

                    context.pushData(new PyGameSurfaceFont(self.getFont(), s, r, g, b));
                }
            };
        });
    }};

    private BitmapFont font;

    public PyGameFont(BitmapFont font) {
        super(PYGAME_FONT_CLASS);
        this.font = font;
    }

    public BitmapFont getFont() { return font; }
}
