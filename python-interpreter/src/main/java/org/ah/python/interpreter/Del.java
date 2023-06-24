package org.ah.python.interpreter;

import static org.ah.python.interpreter.PythonObject.arrayToString;

import java.util.List;

import org.ah.python.interpreter.ThreadContext.Executable;

public class Del implements Executable {

    private PythonObject evaluatedTargets[];
    private Executable targets[];

    private Del(List<Executable> targets) {
        this.targets = targets.toArray(new Executable[targets.size()]);
//        for (Executable target : targets) {
//            if (target instanceof Subscript) {
//                this.targets.add((Subscript)target);
//            } else {
//                throw new UnsupportedOperationException("Cannot delete object " +  target);
//            }
//        }
    }

    public String toString() {
        if (evaluatedTargets != null) {
            return "del " + arrayToString(evaluatedTargets, ",");
        } else {
            return "del " + arrayToString(targets, ",");
        }
    }
//
//    private Executable continuation = new Executable() {
//        @Override public void evaluate(ThreadContext context) {
//            for (int i = 0; i < targets.length; i++) {
//                evaluatedTargets[i] = context.popData();
//            }
//
//            for (PythonObject subsciptObject : evaluatedTargets) {
//                if (!(subsciptObject instanceof PythonSlice)) {
//                    throw new UnsupportedOperationException("Cannot delete object using " +  subsciptObject);
//                }
//                PythonSlice slice = (PythonSlice)subsciptObject;
//                PythonObject scope = slice.getScope();
//                PythonObject from = slice.getFrom();
//                PythonObject to = slice.getTo();
//                if (from != null && from.equals(to)) {
//                    scope.__delitem__(context, from);
//                } else {
//                    PythonSlice slice = PythonSlice.range(from.asInteger(), to.asInteger());
//                    scope.__delitem__(context, slice);
//                }
//            }
//        }
//    };

    @Override
    public void evaluate(ThreadContext context) {
        for (Executable executable : targets) {
            context.continuation(executable);
        }
    }

    public static Del createDel(List<Executable> references) {
        for (Executable reference : references) {
            if (reference instanceof Call) {
                Call call = (Call)reference;
                if (call.function instanceof Reference) {
                    Reference callReference = (Reference)call.function;
                    if (callReference.name.equals("__getitem__")) {
                        callReference.name = "__delitem__";
//                    } else if (callReference.name == "__getattr__") {
//                        callReference.name = "__delitem__";
                    } else {
                        throw new IllegalArgumentException("Cannot delete if not 'Call.Reference' is not '__getitem__' It is '" + callReference.name + "'!");
                    }
                }
            } else {
                throw new IllegalArgumentException("Cannot delete if not 'Call' with 'Reference'!");
            }
        }
        return new Del(references);
    }
}
