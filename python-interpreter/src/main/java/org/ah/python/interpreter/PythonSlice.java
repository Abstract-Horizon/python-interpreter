package org.ah.python.interpreter;

public class PythonSlice extends PythonObject {

    public static PythonClass PYTHON_SLICE_CLASS = new PythonClass("slice");

    private int from = 0;
    private int to = -1;
    private int step;

    public static PythonSlice index(int index) {
        PythonSlice slice = new PythonSlice();
        slice.from = index;
        slice.to = index;
        slice.step = 1;
        return slice;
    }

    public static PythonSlice range(int from, int to) {
        PythonSlice slice = new PythonSlice();
        slice.from = from;
        slice.to = to;
        slice.step = 1;
        return slice;
    }

    public static PythonSlice range(ThreadContext context, PythonNumber from, PythonNumber to) {
        PythonSlice slice = new PythonSlice();
        if (from != null) {
            slice.from = from.asInteger();
        }
        if (to != null) {
            slice.to = to.asInteger();
        }
        slice.step = 1;
        return slice;
    }


    protected PythonSlice() {
        super(PYTHON_SLICE_CLASS);
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
