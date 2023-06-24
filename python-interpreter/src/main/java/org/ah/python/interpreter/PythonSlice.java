package org.ah.python.interpreter;

import org.ah.python.interpreter.ThreadContext.Executable;

public class PythonSlice extends PythonObject {

    public static PythonClass PYTHON_SLICE_CLASS = new PythonClass("slice");

    private Executable fromObject;
    private Executable toObject;
    private Executable stepObject;
    private boolean evaluated = false;
    private int from = 0;
    private int to = -1;
    private int step;

    public PythonSlice() {
        super(PYTHON_SLICE_CLASS);
    }

    public PythonSlice(Executable from, Executable to, Executable step) {
        super(PYTHON_SLICE_CLASS);
        this.fromObject = from;
        this.toObject = to;
        this.stepObject = step;
    }

    public static PythonSlice index(int index) {
        PythonSlice slice = new PythonSlice();
        slice.from = index;
        slice.to = index;
        slice.step = 1;
        slice.evaluated = true;
        return slice;
    }

    public static PythonSlice range(int from, int to) {
        PythonSlice slice = new PythonSlice();
        slice.from = from;
        slice.to = to;
        slice.step = 1;
        slice.evaluated = true;
        return slice;
    }

    public static PythonSlice range(int from, int to, int step) {
        PythonSlice slice = new PythonSlice();
        slice.from = from;
        slice.to = to;
        slice.step = step;
        slice.evaluated = true;
        return slice;
    }

    private Executable continuation = new Executable() {
        @Override public void evaluate(ThreadContext context) {
            if (stepObject != null) {
                step = context.popData().asInteger();
            }
            if (toObject != null) {
                to = context.popData().asInteger();
            }
            if (fromObject != null) {
                from = context.popData().asInteger();
            }
            evaluated = true;
            context.pushData(PythonSlice.this);
        }
    };

    public void evaluate(ThreadContext context) {
        if (evaluated) {
            context.pushData(this);
        } else {
            context.continuation(continuation);
            if (fromObject != null) {
                context.continuation(fromObject);
            }
            if (toObject != null) {
                context.continuation(toObject);
            }
            if (stepObject != null) {
                context.continuation(stepObject);
            }
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getStep() {
        return step;
    }



}
