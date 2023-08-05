package org.ah.python.modules;

import static org.ah.python.interpreter.PythonJavaIterator.PYTHON_ITERATOR_CLASS;
import static org.ah.python.interpreter.StopIteration.STOP_ITERATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ah.python.interpreter.BuiltInMethod;
import org.ah.python.interpreter.PythonBoolean;
import org.ah.python.interpreter.PythonInteger;
import org.ah.python.interpreter.PythonIteratorInterface;
import org.ah.python.interpreter.PythonJavaIterator;
import org.ah.python.interpreter.PythonList;
import org.ah.python.interpreter.PythonNumber;
import org.ah.python.interpreter.PythonObject;
import org.ah.python.interpreter.PythonSequence;
import org.ah.python.interpreter.PythonTuple;
import org.ah.python.interpreter.StopIteration;
import org.ah.python.interpreter.ThreadContext;
import org.ah.python.interpreter.StopIteration.StopIterationException;

public class IterToolsModule extends org.ah.python.interpreter.Module {



    public static class PythonPermutationsIterator extends PythonObject implements PythonIteratorInterface {

        private List<PythonObject> list;
        private int[] counters;

        public PythonPermutationsIterator(List<PythonObject> list, int r) {
            super(PYTHON_ITERATOR_CLASS);
            this.list = list;
            counters = new int[r];
            for (int i = 0; i < counters.length; i++) {
                counters[i] = 0;
            }
            counters[0] = -1;
        }

        public void __iter__(ThreadContext context) {
            context.pushData(this);
        }

        public void __next__(ThreadContext context) {
            if (nextCounters()) {
                PythonObject[] tupleValues = new PythonObject[counters.length];
                for (int i = counters.length - 1; i >= 0; i--) {
                    tupleValues[counters.length - i - 1] = list.get(counters[i]);
                }
                PythonTuple tuple = new PythonTuple(tupleValues);

                context.pushData(tuple);
            } else {
                context.raise(STOP_ITERATION);
            }
        }

        protected boolean nextCounters() {
            int l = list.size();

            for (int i = 0; i < counters.length; i++) {
                while (counters[i] < l - 1) {
                    counters[i] += 1;
                    if (allDifferenceCounters()) {
                        return true;
                    }
                }
                counters[i] = 0;
            }
            return false;
        }

        private boolean allDifferenceCounters() {
            for (int i = 0; i < counters.length; i++) {
                for (int j = 0; j < counters.length; j++) {
                    if (i != j && counters[i] == counters[j]) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    public IterToolsModule() {
        super("itertools");

        addMethod(new BuiltInMethod("permutations") {
            @Override public void __call__(ThreadContext context, Map<String, PythonObject> kwargs, PythonObject... args) {
                if (args.length < 1 || args.length > 2) {
                    throw new UnsupportedOperationException("At the moment itertools.pertunations requires one or two arguments");
                }

                if (!(args[0] instanceof PythonSequence)) {
                    throw new UnsupportedOperationException("At the moment itertools.pertunations is only supported on sequence object");
                }
                List<PythonObject> list = ((PythonSequence)args[0]).asList();

                int r = list.size();
                if (args.length == 2) {
                    if (!(args[1] instanceof PythonNumber)) {
                        throw new UnsupportedOperationException("At the moment itertools.pertunations requires number as second argument");
                    }

                    r = ((PythonNumber)args[1]).asInteger();
                }

                context.pushData(new PythonPermutationsIterator(list, r));
            }
        });
    }

    public static void main(String[] args) {
        List<PythonObject> input = new ArrayList<PythonObject>();
        input.add(PythonInteger.valueOf(-1));
        input.add(PythonInteger.valueOf(0));
        input.add(PythonInteger.valueOf(1));
        PythonPermutationsIterator it = new PythonPermutationsIterator(input, 2);

        ThreadContext context = new ThreadContext(null);

        try {
            it.__next__(context);
            PythonTuple tuple = (PythonTuple)context.popData();
            while (true) {
                System.out.println(tuple);
                it.__next__(context);
                tuple = (PythonTuple)context.popData();
            }
        } catch (StopIteration.StopIterationException ignore) {}
    }
}
