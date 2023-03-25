package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonInteger.ONE;
import static org.ah.python.interpreter.PythonInteger.THREE;
import static org.ah.python.interpreter.PythonInteger.TWO;
import static org.ah.python.interpreter.PythonInteger.ZERO;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

class PyGameDraw extends Proxy {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameDraw.class);

    public PyGameDraw() {
    }

    public PythonType getType() { return TYPE; }

    static {
        TYPE.setAttribute("rect", new Function() {
            @Override public PythonObject call0(PythonObject screen, PythonObject colour, PythonObject rect) {
                int r = colour.__getitem__(ZERO).dereference().asInteger();
                int g = colour.__getitem__(ONE).dereference().asInteger();
                int b = colour.__getitem__(TWO).dereference().asInteger();

                int x = rect.__getitem__(ZERO).dereference().asInteger();
                int y = rect.__getitem__(ONE).dereference().asInteger();
                int w = rect.__getitem__(TWO).dereference().asInteger();
                int h = rect.__getitem__(THREE).dereference().asInteger();

                int colourKey = r + (g << 8) + (b << 16);

                Texture texture = rectTextures.get(colourKey);
                if (texture == null) {
                    texture = createTexture(colourKey, r, g, b);
                }
                PyGameModule.PYGAME_MODULE.getSpriteBatch().draw(texture, x, y, w, h);
                return PythonNone.NONE;
            }

            @Override public PythonObject call0(PythonObject screen, PythonObject colour, PythonObject rect, PythonObject width) {
                if (PyGameModule.ENABLE_SHAPE_RENDERER) {
                    int r = colour.__getitem__(ZERO).dereference().asInteger();
                    int g = colour.__getitem__(ONE).dereference().asInteger();
                    int b = colour.__getitem__(TWO).dereference().asInteger();

                    int x = rect.__getitem__(ZERO).dereference().asInteger();
                    int y = rect.__getitem__(ONE).dereference().asInteger();
                    int w = rect.__getitem__(TWO).dereference().asInteger();
                    int h = rect.__getitem__(THREE).dereference().asInteger();

                    // int lw = width.asInteger();

                    ShapeRenderer shapeRenderer = PyGameModule.PYGAME_MODULE.getShapeRenderer(ShapeType.Line);

                    shapeRenderer.setColor(r, g, b, 1);
                    shapeRenderer.rect(x, y, w, h);

                    // TODO add line width
                }
                return PythonNone.NONE;
            }
        });
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