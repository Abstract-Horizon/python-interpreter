package org.ah.python.interpreter;

import java.util.Map;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonDictGenerator extends PythonObject implements Executable {

    private Map<Executable, Executable> elements;

    public PythonDictGenerator(final Map<Executable, Executable> elements) {
        super(PythonClass.PYTHON_INTERNAL_CLASS_NOT_DEFINED);
        this.elements = elements;
    }

    private Executable continuation = new Executable() {

        @Override public void evaluate(ThreadContext context) {
            PythonDictionary returnValue = new PythonDictionary();

            int resultElementsNumber = elements.size();

            for (int i = 0; i < resultElementsNumber; i++) {
                returnValue.asMap().put(context.popData(), context.popData());
            }

            context.pushData(returnValue);
        }
    };

    public void evaluate(ThreadContext context) {
        if (elements.size() == 0) {
            continuation.evaluate(context);
        } else {
            Executable[] zipped = new Executable[elements.size() * 2];
            int p = 0;
            for (Map.Entry<Executable, Executable> entry : elements.entrySet()) {
                zipped[p] = entry.getKey();
                zipped[p + 1] = entry.getValue();
                p = p + 2;
            }
            context.continuationWithEvaluate(continuation, zipped);
        }
    }
}
