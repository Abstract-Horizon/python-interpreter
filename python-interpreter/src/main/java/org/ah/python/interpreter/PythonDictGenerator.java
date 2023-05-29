package org.ah.python.interpreter;

import java.util.Map;

public class PythonDictGenerator extends PythonObject {

    private Map<PythonObject, PythonObject> elements;

    public PythonDictGenerator(final Map<PythonObject, PythonObject> elements) {
        this.elements = elements;
    }

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

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
            PythonObject[] zipped = new PythonObject[elements.size() * 2];
            int p = 0;
            for (Map.Entry<PythonObject, PythonObject> entry : elements.entrySet()) {
                zipped[p] = entry.getKey();
                zipped[p + 1] = entry.getValue();
                p = p + 2;
            }
            context.continuationWithEvaluate(continuation, zipped);
        }
    }
}
