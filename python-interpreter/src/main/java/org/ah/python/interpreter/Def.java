package org.ah.python.interpreter;

import java.util.ArrayList;
import java.util.List;

public class Def extends PythonObject {

    private String name;
    private List<Reference> args = new ArrayList<Reference>();
    private boolean instanceMethod = false;

    private Suite method = new Suite();

    public static PythonObject RETURN = null;

    public Def(String name) {
        this.name = name;
    }

    public boolean isInstanceMethod() {
        return instanceMethod;
    }

    public Suite getSuite() {
        return method;
    }

    public List<Reference> getArguments() {
        return args;
    }

    public PythonObject __call__() {
        Scope scope = GlobalScope.currentScope();
        if (scope instanceof PythonClassType) {
            if (args.size() == 0) {
                throw new IllegalStateException("Class def with no parameters");
            }
            scope.__setattr__(name, new InstanceMethod<PythonObject>() {

                @Override public PythonObject __call__(PythonObject self, PythonObject[] args) {

                    if (args.length < Def.this.args.size() - 1) {
                        throw new IllegalArgumentException("Not enough parameters for " + name);
                    }

                    Scope parentScope = GlobalScope.currentScope();
                    Scope scope = new ExecutionScope(parentScope);
                    GlobalScope.pushScope(scope);
                    try {
                        Reference selfArgReference = Def.this.args.get(0);
                        selfArgReference.assign(self);

                        for (int i = 1; i < Def.this.args.size(); i++) {
                            Reference argReference = Def.this.args.get(i);
                            PythonObject argValue = args[i].dereference();

                            argReference.assign(argValue);
                        }
                        getSuite().__call__();
                        if (RETURN != null) {
                            Suite.BREAKOUT = false;
                            PythonObject ret = RETURN;
                            RETURN = null;
                            return ret;
                        }
                        return PythonNone.NONE;
                    } finally {
                        GlobalScope.popScope();
                    }
                }
            });
        } else {
            scope.__setattr__(name, new Function() {

              @Override public PythonObject __call__(PythonObject[] args) {

                  if (args.length < Def.this.args.size()) {
                      throw new IllegalArgumentException("Not enough parameters for " + name);
                  }

                  Scope parentScope = GlobalScope.currentScope();
                  Scope scope = new ExecutionScope(parentScope);
                  GlobalScope.pushScope(scope);
                  try {
                      for (int i = 0; i < Def.this.args.size(); i++) {
                          Reference argReference = Def.this.args.get(i);
                          PythonObject argValue = args[i].dereference();

                          argReference.assign(argValue);
                      }
                      getSuite().__call__();
                      if (RETURN != null) {
                          Suite.BREAKOUT = false;
                          PythonObject ret = RETURN;
                          RETURN = null;
                          return ret;
                      }
                      return PythonNone.NONE;
                  } finally {
                      GlobalScope.popScope();
                  }
              }
          });
        }
        return PythonNone.NONE;
    }

    public String toString() {
        return "def " + name + "(" + collectionToString(args, ", ") + "): " + method;
    }
}
