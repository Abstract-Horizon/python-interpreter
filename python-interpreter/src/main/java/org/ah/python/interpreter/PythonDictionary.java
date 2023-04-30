package org.ah.python.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PythonDictionary extends PythonObject {

    public static PythonType TYPE = new PythonType(PythonObject.TYPE, PythonDictionary.class);

    protected Map<PythonObject, PythonObject> map = new HashMap<PythonObject, PythonObject>();

    public PythonDictionary() {
    }

    public Map<PythonObject, PythonObject> asMap() {
        return map;
    }

    static {
        TYPE.__setattr__("copy", new InstanceMethod<PythonDictionary>() { @Override public PythonObject call0(PythonDictionary self, PythonObject arg) {
            PythonDictionary newDict = new PythonDictionary();
            for (Map.Entry<PythonObject, PythonObject> entry : self.map.entrySet()) {
                // TODO
                throw new UnsupportedOperationException("PythonDictionary.copy");
            }
            return newDict;
        }});
    }


    public static PythonObject constructor(final Map<PythonObject, PythonObject> values) {
        return new Constructor() {
            @Override public PythonObject __call__(ThreadContext context) {
                PythonDictionary dict = new PythonDictionary();
                for (Map.Entry<PythonObject, PythonObject> entry : values.entrySet()) {
                    PythonObject key = entry.getKey().dereference();
                    PythonObject value = entry.getValue();
                    if (value != null) {
                        value = value.dereference();
                    }
                    dict.asMap().put(key, value);
                }
                return dict;
            }
            @Override public String toString() {
                StringBuilder res = new StringBuilder();
                res.append("CreateDict{");
                boolean first = true;
                for (Map.Entry<PythonObject, PythonObject> e : values.entrySet()) {
                    if (first) { first = false; } else { res.append(", "); }
                    res.append(e.getKey()).append(": ").append(e.getValue());
                }
                res.append("}");
                return res.toString();
            }
        };
    }

    public static PythonObject constructor(final List<PythonObject> keys, final List<PythonObject> values) {
        return new Constructor() {
            @Override public PythonObject __call__(ThreadContext context) {
            PythonDictionary dict = new PythonDictionary();
            for (int i = 0; i < keys.size(); i++) {
                PythonObject key = keys.get(i).dereference();
                PythonObject value = values.get(i).dereference();
                dict.asMap().put(key, value);
            }
            return dict;
            }
            @Override public String toString() {
                StringBuilder res = new StringBuilder();
                res.append("CreateDict{");
                boolean first = true;
                for (int i = 0; i < keys.size(); i++) {

                    if (first) { first = false; } else { res.append(", "); }
                    res.append(keys.get(i)).append(": ").append(values.get(i));
                }
                res.append("}");
                return res.toString();
            }
        };
    };

    public boolean asBoolean() {
        return map.size() != 0;
    }

    public PythonInteger __len__(ThreadContext context) {
        return PythonInteger.valueOf(map.size());
    }

    public PythonObject __getitem__(ThreadContext context, PythonObject key) {
        PythonObject obj = map.get(key);
        if (obj == null) {
            throw new NoSuchElementException(key + " in " + this);
        }
        return obj;
    }

    public PythonObject __setitem__(ThreadContext context, PythonObject key, PythonObject value) {
        map.put(key, value);
        return PythonNone.NONE;
    }

    public PythonObject __delitem__(ThreadContext context, PythonObject key) {
        map.remove(key);
        return PythonNone.NONE;
    }

    public PythonObject __contains__(ThreadContext context, PythonObject value) {
        return PythonBoolean.valueOf(map.containsKey(value));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Dict {");
        boolean first = true;
        for (Map.Entry<PythonObject, PythonObject> entry : map.entrySet()) {
            if (first) { first = false; } else { res.append(", "); }
            res.append("\"" + entry.getKey() + "\" : " + entry.getValue());
        }
        res.append("}");
        return res.toString();
    }

}
