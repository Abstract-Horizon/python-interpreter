package org.ah.python.grammar;

import java.io.Reader;
import java.util.Stack;

public class PythonScannerFixed extends PythonScanner {

    private Stack<Integer> indents = new Stack<Integer>();
    private int currentIndent = 0;
    
    private Stack<Boolean> inStatements = new Stack<Boolean>();
    private boolean inStatement = true;
    private boolean afterNewLine = true;
    
    private Token waitingToken = null;
    
    public PythonScannerFixed() {
    }

    public PythonScannerFixed(Reader r) {
        super(r);
    }

    public boolean inStatement() {
        return inStatement;
    }
    
    public void setInStatement(boolean inStatement) {
        this.inStatement = inStatement;
    }
    
    public void pushNotInStatement() {
        inStatements.push(inStatement);
        inStatement = false;
    }
    
    public void popNotInStatement() {
        inStatement = inStatements.pop();
    }
    
    protected Token getNextTokenImpl() {
        if (inStatement) {
            if (waitingToken != null) {
                if (waitingToken.getPos() < currentIndent) {
                    currentIndent = indents.pop();
                    Token t = newToken("", TOKEN_DEDENT, waitingToken.getLine(), waitingToken.getPos());
                    return t;
                } else {
                    Token t = waitingToken;
                    waitingToken = null;
                    return t;
                }
            }
            Token t = super.getNextTokenImpl();

            if (t.getId() == TOKEN_COMMENTS) {
                t = newToken(t.toString(), TOKEN_NEWLINE, t.getLine(), t.getPos() + t.toString().length() - 1);
                afterNewLine = true;
            } else if (t.getId() == TOKEN_NEWLINE) {
                    afterNewLine = true;
            } else if (t.getId() == TOKEN_WHITESPACE) {
            } else {
                if (afterNewLine) {
                    if (t.getPos() < currentIndent) {
                        waitingToken = t;
                        t = newToken("", TOKEN_DEDENT, t.getLine(), t.getPos());
                        currentIndent = indents.pop();
                    } else if (t.getPos() > currentIndent) {
                        waitingToken = t;
                        t = newToken("", TOKEN_INDENT, t.getLine(), t.getPos());
                        indents.push(currentIndent);
                        currentIndent = t.getPos();
                    }
                    afterNewLine = false;
                }
            }
            return t;
        } else {
            Token t = super.getNextTokenImpl();

            if (t.getId() == TOKEN_NEWLINE) {
                afterNewLine = true;
                t = newToken(t.toString(), TOKEN_WHITESPACE, t.getLine(), t.getPos());
            } else if (t.getId() == TOKEN_COMMENTS) {
                  t = newToken("", TOKEN_WHITESPACE, t.getLine(), t.getPos());
                  afterNewLine = false;
            } else if (t.getId() == TOKEN_WHITESPACE) {
                afterNewLine = false;
            } else {
                afterNewLine = false;
            }

            return t;
        }
    }
}
