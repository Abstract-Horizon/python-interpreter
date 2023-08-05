package org.ah.python.interpreter;

import java.util.Iterator;
import java.util.Map;

public class PythonJavaIterator extends PythonObject implements PythonIteratorInterface {

    public static PythonClass PYTHON_ITERATOR_CLASS = new PythonClass("iterator") {
        {
            addMethod(new BuiltInBoundMethod("__next__") {
                public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    args[0].__next__(context);
                }
            });
            addMethod(new BuiltInBoundMethod("__iter__") {
                public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                    context.pushData(args[0]);
                }
            });

        }
    };

    private Iterator<PythonObject> it;

    public PythonJavaIterator(Iterator<PythonObject> it) {
        super(PYTHON_ITERATOR_CLASS);
        this.it = it;
    }

    public void __iter__(ThreadContext context) {
        context.pushData(this);
    }

    public void __next__(ThreadContext context) {
        context.pushData(next(context));
    }

    public PythonObject next(ThreadContext context) {
        if (it.hasNext()) {
            return it.next();
        } else {
            context.raise(new StopIteration());
            return PythonNone.NONE;
        }
    }
}
