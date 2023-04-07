package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Attributes extends PythonObject {

    private Map<String, PythonObject> attributes;

    public static PythonClass classDict() {
        return new PythonClass("dict");
    }

    public static PythonClass CLASS_DICT = classDict();

    public Attributes() {
        attributes = new HashMap<String, PythonObject>();
        pythonClass = CLASS_DICT;
    }

    public Attributes(Map<String, PythonObject> attributes) {
        this.attributes = attributes;
        pythonClass = CLASS_DICT;
    }

    public PythonObject __getitem__(String attr) {
        if (attributes.containsKey(attr)) {
            return attributes.get(attr);
        }
        return PythonNone.NONE;
    }

    public void __setitem__(String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __delitem__(String attr) {
        attributes.remove(attr);
    }

    public PythonObject __getattr__(String attr) {
        if (attributes.containsKey(attr)) {
            return attributes.get(attr);
        }
        return PythonNone.NONE;
    }

    public void __setattr__(String attr, PythonObject o) {
        attributes.put(attr, o);
    }

    public void __delattr__(String attr) {
        attributes.remove(attr);
    }
}
