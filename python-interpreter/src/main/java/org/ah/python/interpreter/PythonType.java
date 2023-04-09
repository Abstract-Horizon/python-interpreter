package org.ah.python.interpreter;



public class PythonType extends Scope {

    private String name;
    private PythonType parent;

    public PythonType(Class<? extends PythonObject> cls) {
        name = cls.getName();
        int i = name.lastIndexOf('.');
        if (i >= 0) {
            name = name.substring(i + 1);
        }
    }

    public PythonType(PythonType parent, Class<? extends PythonObject> cls) {
        this(cls);
        this.parent = parent;
    }

    public PythonType(String name) {
        this.name = name;
    }

    public PythonType(PythonType parent, String name) {
        this(name);
        this.parent = parent;
    }

    protected void setParent(PythonType parent) {
        this.parent = parent;
    }

    protected PythonType getParent() {
        return parent;
    }

    protected String getName() {
        return name;
    }

    public PythonObject __getattr__(String name) {
        if (attributes != null && attributes.containsKey(name)) {
            return attributes.get(name);
        }
        if (parent != null) {
            return parent.__getattr__(name);
        }
        return null;
    }
}
