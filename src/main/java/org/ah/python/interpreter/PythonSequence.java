package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonBoolean.FALSE;
import static org.ah.python.interpreter.PythonBoolean.TRUE;

public class PythonSequence extends PythonObject {

    public PythonBoolean __nonzero__() {
        if (__len__().asInteger() != 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public boolean asBoolean() {
        return __len__().asInteger() != 0;
    }


}
