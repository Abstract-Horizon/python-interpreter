package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.ah.python.interpreter.StopIteration.StopIterationException;
import org.ah.python.interpreter.ThreadContext.CatchContinuation;
import org.ah.python.interpreter.ThreadContext.Executable;

public class Try implements Executable {

    public static class Except {
        protected Block block;

        public Except(Block block) {
            this.block = block;
        }
    }

    private Block block = new Block();
    private List<Except> excepts = new ArrayList<Except>();

    public Try() {
    }

    public Block getBlock() {
        return block;
    }

    public void addExcept(Except except) {
        excepts.add(except);
    }

    private class TryContinuation implements Executable, CatchContinuation {
        @Override public void evaluate(ThreadContext context) {
        }

        @Override public void evaluateException(ThreadContext context, PythonBaseException exception) {
            // TODO implement rest...
            context.continuation(excepts.get(excepts.size() - 1).block);
        }
    }


    private Executable catchContinuation = new TryContinuation();

    public void evaluate(ThreadContext context) {
        context.continuation(catchContinuation);
        context.continuation(block);
    }

    public String toString() {
        return "try: " + block.toString();
        // TODO add except blocks
    }
}
