package org.ah.python.interpreter;

import java.util.List;

public class PythonListGenerator extends PythonObject {

    private PythonObject[] elements;
    private PythonClass targetPythonClass;

    public PythonListGenerator(final List<PythonObject> elements, PythonClass pythonClass) {
        this.elements = elements.toArray(new PythonObject[elements.size()]);
        this.targetPythonClass = pythonClass;
    }

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {

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
            return;
        }
        context.pushPC(continuation);

        for (int i = 0; i < elements.length - 1; i++) {
            context.pushPC(elements[i]);
        }

        elements[elements.length - 1].evaluate(context);
    }

//        final ArrayList<PythonObject> storedElements = new ArrayList<PythonObject>(elements);
//            return new Constructor() {
//                @Override public PythonObject __call__(ThreadContext context) {
//                    PythonList list = new PythonList();
//                    for (PythonObject o : storedElements) {
//                        PythonObject r = o.dereference();
//                        list.asList().add(r);
//                    }
//                    return list;
//                }
//                @Override public String toString() {
//                    return "CreateList" + storedElements;
//                }
//        };
//    };

}
