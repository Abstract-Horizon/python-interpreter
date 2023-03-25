package org.ah.libgdx.pygame.modules.pygame;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import org.ah.python.interpreter.Function;
import org.ah.python.interpreter.Proxy;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonNone;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.PythonType;
import org.ah.python.modules.SysModule;

import static org.ah.python.interpreter.PythonBoolean.FALSE;

public class PyGameModule extends Proxy {

    public static final boolean ENABLE_SHAPE_RENDERER = true;
    public static final boolean DEBUG = false;

    public static final PyGameModule PYGAME_MODULE = new PyGameModule();
    public static final PythonBoolean[] KEYS;
    public static boolean PRE_RUN = false;
//    protected static int createdSprites;

    static {
//        KEYS = new PythonList();
        KEYS = new PythonBoolean[256];
        for (int i = 0; i < 256; i++) {
//            PyGameModule.KEYS.asList().add(FALSE);
            PyGameModule.KEYS[i] = FALSE;
        }
    }

    private BitmapFont bitmapFont;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;
    private String path;
    private ShapeType lastType;

//    protected static int startSpriteBatch;
//    protected static int startShapeBatch;


    private static final int BATCH_NONE = 0;
    private static final int BATCH_SPRITE = 1;
    private static final int BATCH_SHAPE = 2;

    private int batchState = BATCH_NONE;

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameModule.class);

    public static PythonObject EVENT_TYPE_QUIT = new PythonObject();
    public static PythonObject EVENT_TYPE_MOUSEMOTION = new PythonObject();
    public static PythonObject EVENT_TYPE_MOUSEBUTTONDOWN = new PythonObject();
    public static PythonObject EVENT_TYPE_MOUSEBUTTONUP = new PythonObject();


    private static PyGameTime time = new PyGameTime();
    private static PyGameDisplay display = new PyGameDisplay();
    private static PyGameImage image = new PyGameImage();
    private static PyGameEvent event = new PyGameEvent();
    private static PyGameKey key = new PyGameKey();
    private static PyGameFontStatic font = new PyGameFontStatic();
    private static PyGameDraw draw = new PyGameDraw();
    private static PyGameMixer mixer = new PyGameMixer();
    private static PyGameTransform transform = new PyGameTransform();
    private static PyGameSprite sprite = new PyGameSprite();
    private static PyGameJoystick joystick = new PyGameJoystick();

    static {
        TYPE.setAttribute("init", new Function() { @Override public PythonObject call0() {
            return PythonNone.NONE;
        }});
        TYPE.setAttribute("time", time);
        TYPE.setAttribute("display", display);
        TYPE.setAttribute("image", image);
        TYPE.setAttribute("event", event);
        TYPE.setAttribute("key", key);
        TYPE.setAttribute("font", font);
        TYPE.setAttribute("draw", draw);
        TYPE.setAttribute("mixer", mixer);
        TYPE.setAttribute("transform", transform);
        TYPE.setAttribute("sprite", sprite);
        TYPE.setAttribute("joystick", joystick);
        TYPE.setAttribute("QUIT", EVENT_TYPE_QUIT);
        TYPE.setAttribute("MOUSEMOTION", EVENT_TYPE_MOUSEMOTION);
        TYPE.setAttribute("MOUSEBUTTONDOWN", EVENT_TYPE_MOUSEBUTTONDOWN);
        TYPE.setAttribute("MOUSEBUTTONUP", EVENT_TYPE_MOUSEBUTTONUP);
        TYPE.setAttribute("Rect", PyGameRect.TYPE);
        TYPE.setAttribute("quit", new Function() { @Override public PythonObject call0() {
            SysModule.systemBridge.exit(0);
            return PythonNone.NONE;
        }});
        TYPE.setAttribute("Color", new Function() {
            @Override public PythonObject call0(PythonObject name) {
                return PyGameModule.color(name);
            }
            @Override public PythonObject call0(PythonObject r, PythonObject g, PythonObject b) {
                return PyGameModule.color(r.asInteger(), g.asInteger(), b.asInteger());
            }
        });
        addKey("K_BACKSPACE", Keys.BACKSPACE);
        addKey("K_TAB", Keys.TAB);
        addKey("K_CLEAR", Keys.CLEAR);
        addKey("K_RETURN", Keys.ENTER);
        //addKey("K_PAUSE", Keys.PAUSE);
        addKey("K_ESCAPE", Keys.ESCAPE);
        addKey("K_SPACE", Keys.SPACE);
        //addKey("K_EXCLAIM", Keys.EXCLAIM);
        //addKey("K_QUOTEDBL", Keys.QUOTEDBL);
        //addKey("K_HASH", Keys.HASH);
        //addKey("K_DOLLAR", Keys.DOLLAR);
        //addKey("K_AMPERSAND", Keys.AMPERSAND);
        //addKey("K_QUOTE", Keys.QUOTE);
        //addKey("K_LEFTPAREN", Keys.LEFTPAREN);
        //addKey("K_RIGHTPAREN", Keys.RIGHTPAREN);
        //addKey("K_ASTERISK", Keys.ASTERISK);
        addKey("K_PLUS", Keys.PLUS);
        addKey("K_COMMA", Keys.COMMA);
        addKey("K_MINUS", Keys.MINUS);
        addKey("K_PERIOD", Keys.PERIOD);
        addKey("K_SLASH", Keys.SLASH);
        addKey("K_0", Keys.NUM_0);
        addKey("K_1", Keys.NUM_1);
        addKey("K_2", Keys.NUM_2);
        addKey("K_3", Keys.NUM_3);
        addKey("K_4", Keys.NUM_4);
        addKey("K_5", Keys.NUM_5);
        addKey("K_6", Keys.NUM_6);
        addKey("K_7", Keys.NUM_7);
        addKey("K_8", Keys.NUM_8);
        addKey("K_9", Keys.NUM_9);
        addKey("K_COLON", Keys.COLON);
        addKey("K_SEMICOLON", Keys.SEMICOLON);
        addKey("K_LESS", Keys.COMMA);
        addKey("K_EQUALS", Keys.EQUALS);
        //addKey("K_GREATER", Keys.);
        addKey("K_QUESTION", Keys.SLASH);
        addKey("K_AT", Keys.AT);
        addKey("K_LEFTBRACKET", Keys.LEFT_BRACKET);
        addKey("K_BACKSLASH", Keys.BACKSLASH);
        addKey("K_RIGHTBRACKET", Keys.RIGHT_BRACKET);
        addKey("K_CARET", Keys.BACKSLASH);
        addKey("K_UNDERSCORE", Keys.MINUS);
        addKey("K_BACKQUOTE", Keys.APOSTROPHE);
        addKey("K_a", Keys.A);
        addKey("K_b", Keys.B);
        addKey("K_c", Keys.C);
        addKey("K_d", Keys.D);
        addKey("K_e", Keys.E);
        addKey("K_f", Keys.F);
        addKey("K_g", Keys.G);
        addKey("K_h", Keys.H);
        addKey("K_i", Keys.I);
        addKey("K_j", Keys.J);
        addKey("K_k", Keys.K);
        addKey("K_l", Keys.L);
        addKey("K_m", Keys.M);
        addKey("K_n", Keys.N);
        addKey("K_o", Keys.O);
        addKey("K_p", Keys.P);
        addKey("K_q", Keys.Q);
        addKey("K_r", Keys.R);
        addKey("K_s", Keys.S);
        addKey("K_t", Keys.T);
        addKey("K_u", Keys.U);
        addKey("K_v", Keys.V);
        addKey("K_w", Keys.W);
        addKey("K_x", Keys.X);
        addKey("K_y", Keys.Y);
        addKey("K_z", Keys.Z);
        addKey("K_DELETE", Keys.DEL);
        addKey("K_KP0", Keys.NUMPAD_0);
        addKey("K_KP1", Keys.NUMPAD_1);
        addKey("K_KP2", Keys.NUMPAD_2);
        addKey("K_KP3", Keys.NUMPAD_3);
        addKey("K_KP4", Keys.NUMPAD_4);
        addKey("K_KP5", Keys.NUMPAD_5);
        addKey("K_KP6", Keys.NUMPAD_6);
        addKey("K_KP7", Keys.NUMPAD_7);
        addKey("K_KP8", Keys.NUMPAD_8);
        addKey("K_KP9", Keys.NUMPAD_9);
        //addKey("K_KP_PERIOD", Keys.KP_PERIOD);
        //addKey("K_KP_DIVIDE", Keys.KP_DIVIDE);
        //addKey("K_KP_MULTIPLY", Keys.KP_MULTIPLY);
        //addKey("K_KP_MINUS", Keys.KP_MINUS);
        //addKey("K_KP_PLUS", Keys.KP_PLUS);
        addKey("K_KP_ENTER", Keys.ENTER);
        //addKey("K_KP_EQUALS", Keys.KP_EQUALS);
        addKey("K_UP", Keys.UP);
        addKey("K_DOWN", Keys.DOWN);
        addKey("K_RIGHT", Keys.RIGHT);
        addKey("K_LEFT", Keys.LEFT);
        addKey("K_INSERT", Keys.INSERT);
        addKey("K_HOME", Keys.HOME);
        addKey("K_END", Keys.END);
        addKey("K_PAGEUP", Keys.PAGE_UP);
        addKey("K_PAGEDOWN", Keys.PAGE_DOWN);
        addKey("K_F1", Keys.F1);
        addKey("K_F2", Keys.F2);
        addKey("K_F3", Keys.F3);
        addKey("K_F4", Keys.F4);
        addKey("K_F5", Keys.F5);
        addKey("K_F6", Keys.F6);
        addKey("K_F7", Keys.F7);
        addKey("K_F8", Keys.F8);
        addKey("K_F9", Keys.F9);
        addKey("K_F10", Keys.F10);
        addKey("K_F11", Keys.F11);
        addKey("K_F12", Keys.F12);
        //addKey("K_F13", Keys.F13);
        //addKey("K_F14", Keys.F14);
        //addKey("K_F15", Keys.F15);
        //addKey("K_NUMLOCK", Keys.NUMLOCK);
        //addKey("K_CAPSLOCK", Keys.CAPSLOCK);
        //addKey("K_SCROLLOCK", Keys.SCROLLOCK);
        addKey("K_RSHIFT", Keys.SHIFT_RIGHT);
        addKey("K_LSHIFT", Keys.SHIFT_LEFT);
        addKey("K_RCTRL", Keys.CONTROL_RIGHT);
        addKey("K_LCTRL", Keys.CONTROL_LEFT);
        addKey("K_RALT", Keys.ALT_RIGHT);
        addKey("K_LALT", Keys.ALT_LEFT);
        //addKey("K_RMETA", Keys.RMETA);
        //addKey("K_LMETA", Keys.LMETA);
        //addKey("K_LSUPER", Keys.LSUPER);
        //addKey("K_RSUPER", Keys.RSUPER);
        //addKey("K_MODE", Keys.MODE);
        //addKey("K_HELP", Keys.HELP);
        //addKey("K_PRINT", Keys.PRINT);
        //addKey("K_SYSREQ", Keys.SYSREQ);
        //addKey("K_BREAK", Keys.BREAK);
        //addKey("K_MENU", Keys.MENU);
        addKey("K_POWER", Keys.POWER);
        //addKey("K_EURO", Keys.EURO);
    }

    protected static void addKey(String key, final int k) {
        TYPE.setAttribute(key, PythonInteger.valueOf(k));
    }

    private PyGameModule() {
    }

    @Override
    public PythonType getType() { return TYPE; }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public BitmapFont getFont() {
        return bitmapFont;
    }

    public void setFont(BitmapFont font) {
        this.bitmapFont = font;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public SpriteBatch getSpriteBatch() {
        if (ENABLE_SHAPE_RENDERER) {
            if (batchState == BATCH_SHAPE) {
                shapeRenderer.end();
                lastType = null;
            }
            if (batchState != BATCH_SPRITE) {
                spriteBatch.begin();
            }
            batchState = BATCH_SPRITE;
        }
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public ShapeRenderer getShapeRenderer(ShapeType shapeType) {
        if (ENABLE_SHAPE_RENDERER) {
            if (batchState == BATCH_SPRITE) {
                spriteBatch.end();
            }
            if (batchState != BATCH_SHAPE) {
                shapeRenderer.begin(shapeType);
            } else if (shapeType != lastType) {
                if (lastType != null) {
                    shapeRenderer.end();
                }
                lastType = shapeType;
                shapeRenderer.begin(shapeType);
            }
            batchState = BATCH_SHAPE;
        }
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void flip() {
        if (batchState == BATCH_SPRITE) {
            spriteBatch.end();
        }
        if (batchState == BATCH_SHAPE) {
            shapeRenderer.end();
        }
        batchState = BATCH_NONE;
    }

    public static PythonTuple color(PythonObject name) {
        String n = name.asString();
        if ("Green".equalsIgnoreCase(n)) {
            return color(0, 255, 0);
        } else if ("Red".equalsIgnoreCase(n)) {
            return color(255, 0, 0);
        } else if ("RoyalBlue".equalsIgnoreCase(n)) {
            return color(65, 105, 225);
        } else if ("DarkOliveGreen".equalsIgnoreCase(n)) {
            return color(85, 107, 47);
        } else if ("FireBrick".equalsIgnoreCase(n)) {
            return color(178, 32, 32);
        }
        return color(255, 255, 255);
    }

    public static PyGameEvent getPyGameEvent() {
        return event;
    }

    public static PythonTuple color(int r, int g, int b) {
        PythonTuple color = new PythonTuple();
        color.asList().add(PythonInteger.valueOf(r));
        color.asList().add(PythonInteger.valueOf(g));
        color.asList().add(PythonInteger.valueOf(b));
        return color;
    }

    public static class PyGamePreRunException extends RuntimeException {
        private int w;
        private int h;

        public PyGamePreRunException(int w, int h) {
            this.w = w;
            this.h = h;
        }

        public int getWidth() {
            return w;
        }

        public int getHeight() {
            return h;
        }
    }

}