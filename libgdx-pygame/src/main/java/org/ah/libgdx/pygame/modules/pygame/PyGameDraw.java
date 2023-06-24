package org.ah.libgdx.pygame.modules.pygame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.ListAccessible;
import org.ah.python.interpreter.PythonBaseException;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonString;
import org.ah.python.interpreter.ThreadContext;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

class PyGameDraw extends PythonObject {

    public static PythonClass PYGAME_DRAW_CLASS = new PythonClass("pygame.draw") {
        {
            addMethod(new BuiltInMethod("rect") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args.length < 3) {
                    context.raise(PythonBaseException.exception("TypeError", PythonString.valueOf("pygame.draw.rect() requires at least 3 arguments")));
                } else {
                    int r, g, b = 0;
                    int x, y, w, h = 0;

                    if (args[1] instanceof ListAccessible) {
                        List<PythonObject> colorList = ((ListAccessible)args[1]).asList();
                        r = colorList.get(0).asInteger();
                        g = colorList.get(1).asInteger();
                        b = colorList.get(2).asInteger();
                    } else {
                        throw new UnsupportedOperationException("pygame.draw on arbitrary object does not work yet");
                    }

                    if (args[2] instanceof PyGameRect) {
                        PyGameRect rect = (PyGameRect)args[2];
                        x = rect.x;
                        y = rect.y;
                        w = rect.w;
                        h = rect.h;
                    } else if (args[2] instanceof ListAccessible) {
                        List<PythonObject> rectlist = ((ListAccessible)args[1]).asList();
                        x = rectlist.get(0).asInteger();
                        y = rectlist.get(1).asInteger();
                        w = rectlist.get(2).asInteger();
                        h = rectlist.get(3).asInteger();
                    } else {
                        throw new UnsupportedOperationException("pygame.draw on arbitrary object does not work yet");
                    }

                    if (args.length > 3) {
                        ShapeRenderer shapeRenderer = PyGameModule.PYGAME_MODULE.getShapeRenderer(ShapeType.Line);

                        shapeRenderer.setColor(r, g, b, 1);
                        shapeRenderer.rect(x, y, w, h);
                        // TODO add line width
                    } else {
                        int colourKey = r + (g << 8) + (b << 16);

                        Texture texture = rectTextures.get(colourKey);
                        if (texture == null) {
                            texture = createTexture(colourKey, r, g, b);
                        }
                        PyGameModule.PYGAME_MODULE.getSpriteBatch().draw(texture, x, y, w, h);
                    }
                }
            };
        });
        }
    };

    public PyGameDraw() {
        super(PYGAME_DRAW_CLASS);
    }



    static ModelBuilder modelBuilder = null;

    static Map<Integer, Texture> rectTextures = new HashMap<Integer, Texture>();
    static Map<Integer, ModelInstance> rects = new HashMap<Integer, ModelInstance>();

    static Texture createTexture(int colourKey, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(8, 8, Format.RGB888);
        pixmap.setColor(new Color(r / 255.0f, g / 255.0f, b / 255.0f, 1f));
        pixmap.fillRectangle(0, 0, 8, 8);
        Texture texture = new Texture(pixmap);
        rectTextures.put(colourKey, texture);
        // System.out.println("new rect " + colourKey);
        return texture;
    }

    static ModelInstance createRect(int colourKey, int r, int g, int b, int x, int y, int w, int h) {
        if (modelBuilder == null) {
            modelBuilder = new ModelBuilder();
        }
        Material material = new Material(ColorAttribute.createDiffuse(new Color(r / 255.0f, g / 255.0f, b / 255.0f, 1f)));

        Model model = modelBuilder.createRect(x, 0, y, x + w, 0, y, x + w, 0, y + h, x, 0, y +h, 0, 0, 0, material, Usage.Position | Usage.Normal);
        ModelInstance instance = new ModelInstance(model);
        rects.put(colourKey, instance);
        return instance;
    }

    public PythonObject load(PythonObject name) {
        return PythonNone.NONE;
    }
}