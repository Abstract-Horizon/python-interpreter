package org.ah.python.interpreter;

import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonListGenerator extends PythonObject implements Executable {

    private PythonObject[] elements;
    private PythonClass targetPythonClass;

    public PythonListGenerator(final List<Executable> elements, PythonClass pythonClass) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.elements = elements.toArray(new PythonObject[elements.size()]);
        this.targetPythonClass = pythonClass;
    }

    private Executable continuation = new Executable() {

        @Override public void evaluate(ThreadContext context) {
            PythonSequence returnValue;
            if (targetPythonClass == PythonTuple.PYTHON_TUPLE_CLASS) {
                returnValue = new PythonTuple();
            } else {
                returnValue = new PythonList();
            }

            int resultElementsNumber = elements.length;

            for (int i = 0; i < resultElementsNumber; i++) {
                returnValue.asList().add(context.popData());
            }

            context.pushData(returnValue);
        }
    };

    public void evaluate(ThreadContext context) {
        if (elements.length == 0) {
            continuation.evaluate(context);
        } else {
            context.continuationWithEvaluate(continuation, elements);
        }
    }
}
