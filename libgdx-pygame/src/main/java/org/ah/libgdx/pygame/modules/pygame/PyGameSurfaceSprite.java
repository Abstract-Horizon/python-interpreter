package org.ah.libgdx.pygame.modules.pygame;

import java.util.List;

import org.ah.python.interpreter.InstanceMethod;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.PythonType;

import com.badlogic.gdx.graphics.g2d.Sprite;

class PyGameSurfaceSprite extends PythonObject {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PyGameImage.class);

    private Sprite sprite;
    private PythonTuple size;

    static {
        TYPE.setAttribute("convert", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self;
        }});
        TYPE.setAttribute("convert_alpha", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self;
        }});
        TYPE.setAttribute("width", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self.size().asList().get(0);
        }});
        TYPE.setAttribute("height", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self.size().asList().get(1);
        }});
        TYPE.setAttribute("get_width", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self.size().asList().get(0);
        }});
        TYPE.setAttribute("get_height", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self.size().asList().get(1);
        }});
        TYPE.setAttribute("size", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return self.size();
        }});
        TYPE.setAttribute("get_rect", new InstanceMethod<PyGameSurfaceSprite>() { @Override public PythonObject call0(PyGameSurfaceSprite self) {
            return new PyGameRect(0, 0, (int)self.sprite.getWidth(), (int)self.sprite.getHeight());
        }});
    }

    public PythonType getType() { return TYPE; }

    public PyGameSurfaceSprite(Sprite sprite) {
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