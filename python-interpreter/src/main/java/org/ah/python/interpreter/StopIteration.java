package org.ah.python.interpreter;

import java.util.ArrayList;

public class StopIteration extends PythonBaseException {

    @SuppressWarnings("serial")
    public static class StopIterationException extends RuntimeException {
    }

    public StopIteration() {
        super("StopIteration", new ArrayList<PythonObject>());
    }
}
