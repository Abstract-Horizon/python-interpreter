package org.ah.libgdx.pygame.modules.pygame;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonClass;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.modules.SysModule;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PyGameModule extends org.ah.python.interpreter.Module {

    public static final boolean ENABLE_SHAPE_RENDERER = true;
    public static final boolean DEBUG = false;

    public static final PythonBoolean[] KEYS;
    public static int MOUSE_X = 0;
    public static int MOUSE_Y = 0;
    public static boolean PRE_RUN = false;
    public static boolean flip = false;

    // protected static int createdSprites;

    static {
        KEYS = new PythonBoolean[256];
        for (int i = 0; i < 256; i++) {
            PyGameModule.KEYS[i] = FALSE;
        }
    }

    private BitmapFont bitmapFont;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;
    private String path;
    private ShapeType lastType;

    // protected static int startSpriteBatch;
    // protected static int startShapeBatch;


    private static final int BATCH_NONE = 0;
    private static final int BATCH_SPRITE = 1;
    private static final int BATCH_SHAPE = 2;

    private int batchState = BATCH_NONE;

    private static PythonClass EVENT_TYPE_CLASS = new PythonClass("pygame.event");

    public static PythonObject EVENT_TYPE_QUIT = new PythonObject(EVENT_TYPE_CLASS);
    public static PythonObject EVENT_TYPE_KEYDOWN = new PythonObject(EVENT_TYPE_CLASS);
    public static PythonObject EVENT_TYPE_KEYUP = new PythonObject(EVENT_TYPE_CLASS);
    public static PythonObject EVENT_TYPE_MOUSEMOTION = new PythonObject(EVENT_TYPE_CLASS);
    public static PythonObject EVENT_TYPE_MOUSEBUTTONDOWN = new PythonObject(EVENT_TYPE_CLASS);
    public static PythonObject EVENT_TYPE_MOUSEBUTTONUP = new PythonObject(EVENT_TYPE_CLASS);


    private static PyGameEvent event = new PyGameEvent();
    public static int DISPLAY_WIDTH = 0;
    public static int DISPLAY_HEIGHT = 0;

    public static final PyGameModule PYGAME_MODULE = new PyGameModule();

    public PyGameModule() {
        super("pygame");
        addMethod(new BuiltInMethod("init") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
        }});
        __setattr__("time", new PyGameTime());
        __setattr__("display", new PyGameDisplay());
        __setattr__("image", new PyGameImage());
        __setattr__("event", new PyGameEvent());
        __setattr__("key", new PyGameKey());
        __setattr__("font", new PyGameFontStatic());
        __setattr__("draw", new PyGameDraw());
        __setattr__("mixer", new PyGameMixer());
        __setattr__("transform", new PyGameTransform());
        __setattr__("sprite", new PyGameSprite());
        __setattr__("joystick", new PyGameJoystick());
        __setattr__("mouse", new PyGameMouse());
        __setattr__("math", new PyGameMath());


        __setattr__("QUIT", EVENT_TYPE_QUIT);
        __setattr__("MOUSEMOTION", EVENT_TYPE_MOUSEMOTION);
        __setattr__("MOUSEBUTTONDOWN", EVENT_TYPE_MOUSEBUTTONDOWN);
        __setattr__("MOUSEBUTTONUP", EVENT_TYPE_MOUSEBUTTONUP);
        __setattr__("KEYDOWN", EVENT_TYPE_KEYDOWN);
        __setattr__("KEYUP", EVENT_TYPE_KEYUP);
        __setattr__("Rect", PyGameRect.PYGAME_RECT_CLASS);
        addMethod(new BuiltInMethod("quit") { @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
            SysModule.systemBridge.exit(0);
        }});
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

    protected void addKey(String key, final int k) {
        __setattr__(key, PythonInteger.valueOf(k));
    }

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
        flip = true;
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

    public static void keyDown(int keycode) {
        PyGameModule.KEYS[keycode] = TRUE;
        event.addKeyDown(keycode);
    }

    public static void keyUp(int keycode) {
        PyGameModule.KEYS[keycode] = FALSE;
    }


    public static PythonTuple color(int r, int g, int b) {
        PythonTuple color = new PythonTuple();
        color.asList().add(PythonInteger.valueOf(r));
        color.asList().add(PythonInteger.valueOf(g));
        color.asList().add(PythonInteger.valueOf(b));
        return color;
    }
}