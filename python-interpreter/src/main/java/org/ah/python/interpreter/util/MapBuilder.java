package org.ah.python.interpreter.util;

import java.util.HashMap;
import java.util.Map;

import org.ah.python.interpreter.PythonObject;

public class MapBuilder<T> {
    
    Map<String, T> map;
    
    public MapBuilder() {
        map = new HashMap<String, T>();
    }
    
    public MapBuilder(Map<String, T> map) {
        this.map = map;
    }

    String name;
    
    public Map<String, T> map() { return map; }
    
    public MapBuilder<T> name(String name) { this.name = name; return this; }
    public MapBuilder<T> func(T funct) { map.put(name, funct); return this; }
    public MapBuilder<T> val(T val) { map.put(name, val); return this; }

    public static MapBuilder<PythonObject> newmap() { return new MapBuilder<PythonObject>(); }
    public static <T> MapBuilder<T> newmap(Map<String, T> map) { return new MapBuilder<T>(map); }


}