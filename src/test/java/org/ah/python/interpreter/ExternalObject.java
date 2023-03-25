package org.ah.python.interpreter;

public class ExternalObject extends Proxy {

    public static PythonType externalObjectType = new PythonType(PythonObject.TYPE, "ExternalObject");

    static {
        externalObjectType.setAttribute("value", new ProxyAttribute<ExternalObject>() {

            @Override
            protected PythonObject attribute(ExternalObject self) {
                return PythonString.valueOf(self.value);
            }

            @Override
            protected void assign(ExternalObject self, PythonObject expr) {
                self.value = expr.asString();
            }

        });
    }

    public ExternalObject() {
    }

    public String value = "initialValue";

    public PythonType getType() { return externalObjectType; }


}