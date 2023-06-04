package org.ah.python.interpreter;

public class PythonMethodClass extends PythonClass {

    private PythonClass commonMethods = new PythonClass("parent methon class");

    public PythonMethodClass(String name) {
        super(name);
    }

    protected void populateCommonMethods() {
        // Overwrite so no common methods are set - TODO this is right
    }

    public void __getattr__(ThreadContext context, String name) {
        if (commonMethods.contains(name)) {
            context.pushData(commonMethods.getAttribute(name));
        } else {
            super.__getattr__(context, name);
        }
    }

}
