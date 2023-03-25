package org.ah.python.interpreter.util;

import org.ah.python.interpreter.CallableType;

public class UnsupportedFunction extends UnsupportedOperationException {

    private CallableType callableType;
    
    public UnsupportedFunction(CallableType callableType, String message) {
        super(message);
        this.callableType = callableType;
    }
    
    public String toString() {
        return callableType.toString() + " " + getMessage();
    }
    
}
