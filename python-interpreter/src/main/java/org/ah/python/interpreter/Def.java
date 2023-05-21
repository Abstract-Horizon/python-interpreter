package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Def extends PythonObject {

    private String name;
    private List<Reference> args = new ArrayList<Reference>();
    private boolean instanceMethod = false;

    private Block block = new Block();

    public static PythonObject RETURN = null;

    private ThreadContext.Executable continuation = new ThreadContext.Executable() {
        @Override public void evaluate(ThreadContext context) {
            final List<Reference> functionArgs = new ArrayList<Reference>();
            for (int i = 0; i < args.size(); i++) {
                Reference r = args.get(i);
                if (r.getScope() != null && !r.getScope().isConstant()) {
                    PythonObject o = context.popData();
                    Reference reference = new Reference(o, r.getName());
                    functionArgs.add(reference);
                } else {
                    functionArgs.add(r);
                }
            }

            System.out.println("Assigning def to " + context.currentScope + " with name " + name);

            Function function = new DefFunction(name, functionArgs, context.currentScope, block);

            context.currentScope.__setattr__(context, name, function);
        }
    };

    public Def(String name) {
        this.name = name;
    }

    public boolean isInstanceMethod() {
        return instanceMethod;
    }

    public Block getBlock() {
        return block;
    }

    public List<Reference> getArguments() {
        return args;
    }

    @Override public void evaluate(ThreadContext context) {
        PythonObject last = null;
        for (int i = args.size() - 1; i >= 0; i--) {
            Reference o = args.get(i);
            if (o.getScope() != null && !o.getScope().isConstant()) {
                if (last == null) {
                    last = o.getScope();
                } else {
                    context.pushPC(o);
                }
            }
        }

        if (last != null) {
            context.pushPC(continuation);
            last.evaluate(context);
            return;
        }

        continuation.evaluate(context);
    }
//
//    public PythonObject __call__(ThreadContext context) {
//        Scope scope = GlobalScope.currentScope();
//        if (scope instanceof PythonClassType) {
//            if (args.size() == 0) {
//                throw new IllegalStateException("Class def with no parameters");
//            }
//            scope.__setattr__(context, name, new InstanceMethod<PythonObject>() {
//
//                @Override public PythonObject __call__(ThreadContext context, PythonObject self, PythonObject[] args) {
//
//                    if (args.length < Def.this.args.size() - 1) {
//                        throw new IllegalArgumentException("Not enough parameters for " + name);
//                    }
//
//                    Scope parentScope = GlobalScope.currentScope();
//                    Scope scope = new ExecutionScope(parentScope);
//                    GlobalScope.pushScope(scope);
//                    try {
//                        Reference selfArgReference = Def.this.args.get(0);
//                        selfArgReference.assign(context, self);
//
//                        for (int i = 1; i < Def.this.args.size(); i++) {
//                            Reference argReference = Def.this.args.get(i);
//                            PythonObject argValue = args[i].dereference();
//
//                            argReference.assign(context, argValue);
//                        }
//                        getSuite().__call__(context);
//                        if (RETURN != null) {
//                            Suite.BREAKOUT = false;
//                            PythonObject ret = RETURN;
//                            RETURN = null;
//                            return ret;
//                        }
//                        return PythonNone.NONE;
//                    } finally {
//                        GlobalScope.popScope();
//                    }
//                }
//            });
//        } else {
//            scope.__setattr__(context, name, new Function() {
//
//              @Override public PythonObject __call__(ThreadContext context, PythonObject[] args) {
//
//                  if (args.length < Def.this.args.size()) {
//                      throw new IllegalArgumentException("Not enough parameters for " + name);
//                  }
//
//                  Scope parentScope = GlobalScope.currentScope();
//                  Scope scope = new ExecutionScope(parentScope);
//                  GlobalScope.pushScope(scope);
//                  try {
//                      for (int i = 0; i < Def.this.args.size(); i++) {
//                          Reference argReference = Def.this.args.get(i);
//                          PythonObject argValue = args[i].dereference();
//
//                          argReference.assign(context, argValue);
//                      }
//                      getSuite().__call__(context);
//                      if (RETURN != null) {
//                          Suite.BREAKOUT = false;
//                          PythonObject ret = RETURN;
//                          RETURN = null;
//                          return ret;
//                      }
//                      return PythonNone.NONE;
//                  } finally {
//                      GlobalScope.popScope();
//                  }
//              }
//          });
//        }
//        return PythonNone.NONE;
//    }

    public String toString() {
        return "def " + name + "(" + collectionToString(args, ", ") + "): "; //  + method;
    }

    public static class DefFunction extends Function {

        private String name;
        private List<Reference> functionArgs;
        private Scope scope;
        private Block block;

        public DefFunction(String name, List<Reference> functionArgs, Scope scope, Block block) {
            this.name = name;
            this.functionArgs = functionArgs;
            this.scope = scope;
            this.block = block;
        }

        private ThreadContext.Executable closeScopeContinuation = new ThreadContext.Executable() {
            @Override public void evaluate(ThreadContext context) {
                context.popData();
                context.currentScope.close();
                context.pushData(PythonNone.NONE);
            }
        };

        public void execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
            System.out.println("Executing function " + name + " with args " + functionArgs);

            // Bind arguments
            Frame frame = new Frame(context, scope);
            context.pushScope(frame);

            // int r = 0;
            int argsSize = args.length;
            for (int i = 0; i < functionArgs.size(); i++) {
                Reference r = functionArgs.get(i);
                String name = r.getName();
                if (i < argsSize) {
                    frame.__setattr__(context, name, args[i]);
                } else if (r.getScope() != null) {
                    frame.__setattr__(context, name, r.getScope());
                } else if (kwargs != null && kwargs.containsKey(name)) {
                    frame.__setattr__(context, name, kwargs.get(name));
                } else {
                    throw new IllegalArgumentException(name + " is not satisfied nor with default value");
                }
            }

            context.pushPC(closeScopeContinuation);

            List<ThreadContext.Executable> statements = block.getStatements();
            if (!(statements.get(statements.size() - 1) instanceof Return)) {
                statements.add(new Return(PythonNone.NONE));
            }
            block.evaluate(context);
        }
    };

}
