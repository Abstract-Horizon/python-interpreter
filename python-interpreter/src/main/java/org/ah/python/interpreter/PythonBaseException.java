package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PythonBaseException extends PythonObject {

    private List<PythonObject> args;
    private String exceptionName;

    public PythonBaseException() {
        this("PythonBaseException", new ArrayList<PythonObject>());
    }

    public PythonBaseException(String exeptionName) {
        this(exeptionName, new ArrayList<PythonObject>());
    }

    public PythonBaseException(String exceptionName, PythonObject... args) {
        this(exceptionName, Arrays.asList(args));
    }

    public PythonBaseException(String exceptionName, List<PythonObject> args) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.args = args;
        this.exceptionName = exceptionName;
    }

    public void __repr__(ThreadContext context) {
        context.pushData(PythonString.valueOf(asString()));
    }

    public String asString() {
        StringBuilder b = new StringBuilder();
        b.append(exceptionName);
        b.append("[");
        boolean first = true;
        for (PythonObject o : args) {
            if (first) { first = false; } else { b.append(", "); }
            b.append(o.asString());
        }
        b.append("]");
        return b.toString();
    }


    public static PythonBaseException exception(String exceptionName, PythonObject... args) {
        return new PythonBaseException(exceptionName, args);
    }
}
