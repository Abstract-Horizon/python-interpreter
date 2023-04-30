package org.ah.python.interpreter;

public class PythonSlice extends PythonObject {

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

    public static PythonSlice range(ThreadContext context, PythonObject from, PythonObject to) {
        PythonSlice slice = new PythonSlice();
        if (from != null) {
            slice.from = from.asInteger(context);
        }
        if (to != null) {
            slice.to = to.asInteger(context);
        }
        slice.step = 1;
        return slice;
    }


    protected PythonSlice() {
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
