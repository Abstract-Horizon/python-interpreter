package org.ah.python.grammar;



import java.io.Reader;

public class PythonScanner {



    public PythonScanner() {
    }

    public PythonScanner(Reader r) {
        setReader(r);
    }

    public static int eof_sy = 0;
    public static int TOKEN_BOF = -3;
    public static int TOKEN_EOF = -2;
    public static int TOKEN_WHITESPACE = -1;
    public static Token BOF_TOKEN = newToken("BOF", TOKEN_BOF, -1, -1);
    public Token eofToken() { return newToken("EOF", TOKEN_EOF, line, pos); }

    protected Reader reader;
    protected StringBuffer buffer = new StringBuffer(256);
    protected StringBuffer read = new StringBuffer(256);
    protected int ptr = 0;
    protected boolean live = true;
    protected int line = 1;
    protected int pos = 0;

    protected int markedLine = 0;
    protected int markedPos = 0;

    protected int startLine;
    protected int startPos;

    public void setReader(Reader r) {
        reader = r;
    }

    public Reader getReader() {
        return reader;
    }

    protected int getNextChar() {
        int c;
        if (live) {
            try {
                c = reader.read();
                if (c > 0) {
                    buffer.append((char)c);
                    ptr = ptr+1;
                } else {
                    c = eof_sy;
                }
            } catch (Exception e) {
                c = eof_sy;
            }
        } else {
            int len = buffer.length();
            c = (int)buffer.charAt(ptr);
            ptr = ptr + 1;
            if (ptr == len) {
                live = true;
            }
        }
        if ((char)c == '\n') {
            pos = 0;
            line = line + 1;
        } else {
            pos = pos + 1;
        }
        if (c != eof_sy) {
            read.append((char)c);
        }
        return c;
    }

    protected void mark() {
        if (live) {
            if (buffer.length() > 0) {
                buffer.setLength(0);
            }
        } else {
            buffer.delete(0, ptr);
        }
        ptr = 0;
        markedLine = line;
        markedPos = pos;
    }

    protected void reset() {
        if ((buffer.length() > 0) && (ptr > 0)) {
            read.setLength(read.length()-ptr);
            live = false;
            ptr = 0;
        }
        line = markedLine;
        pos = markedPos;
    }
    public static final int TOKEN_COMMA = 46;

    public static final int TOKEN_DASH_GT = 47;

    public static final int TOKEN_LPAREN = 48;

    public static final int TOKEN_RPAREN = 49;

    public static final int TOKEN_LBRACK = 50;

    public static final int TOKEN_RBRACK = 51;

    public static final int TOKEN_LKBRACK = 52;

    public static final int TOKEN_RKBRACK = 53;

    public static final int TOKEN_COLON = 54;

    public static final int TOKEN_EQ = 55;

    public static final int TOKEN_STAR = 56;

    public static final int TOKEN_STARSTAR = 57;

    public static final int TOKEN_SEMICOLON = 58;

    public static final int TOKEN_DOT = 59;

    public static final int TOKEN_ELLIPSIS = 60;

    public static final int TOKEN_AT = 61;

    public static final int TOKEN_EQUAL = 62;

    public static final int TOKEN_NOT_EQUAL = 63;

    public static final int TOKEN_NOT_EQUAL2 = 64;

    public static final int TOKEN_GE = 65;

    public static final int TOKEN_GT = 66;

    public static final int TOKEN_LE = 67;

    public static final int TOKEN_LT = 68;

    public static final int TOKEN_OR = 69;

    public static final int TOKEN_XOR = 70;

    public static final int TOKEN_AND = 71;

    public static final int TOKEN_LSHIFT = 72;

    public static final int TOKEN_RSHIFT = 73;

    public static final int TOKEN_PLUS = 74;

    public static final int TOKEN_MINUS = 75;

    public static final int TOKEN_MOD = 76;

    public static final int TOKEN_SLASH = 77;

    public static final int TOKEN_SLASHSLASH = 78;

    public static final int TOKEN_TILDA = 79;

    public static final int TOKEN_NEWLINE = 83;

    public static final int TOKEN_COMMENTS = 84;

    public static final int TOKEN_NAME = 87;

    public static final int TOKEN_NUMBER = 88;

    public static final int TOKEN_STRING = 91;

    public static final int TOKEN_INDENT = 92;

    public static final int TOKEN_DEDENT = 93;

    int state = 1;
    int id = 0;
    boolean ok = true;
    int ch = 0;

    public Token getNextToken() {
        Token t = getNextTokenImpl();
        while (t.getId() == TOKEN_WHITESPACE) {
            t = getNextTokenImpl();
        }
        return t;
    }

    protected Token getNextTokenImpl() {
        startLine = line;
        startLine = line;
        startPos = pos;
        mark();
        read.setLength(0);
        return getNextTokenStateMachine();
    }

    protected Token getNextTokenStateMachine() {
        state = 1;
        id = 0;
        ok = true;
        ch = 0;
        while (ok) {
            ch = getNextChar();
            // System.out.println(state+": "+ch+" "+(char)ch);
// total nodes: 194
// 194 nodes
            if (state == 1) {
                if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=12)) || ((ch >= 14) && (ch <=32))) {
                    id = TOKEN_WHITESPACE;
                    mark();
                    state = 2;
                } else if ((ch == 10)) {
                    id = TOKEN_NEWLINE;
                    mark();
                    state = 3;
                } else if ((ch == 13)) {
                    id = TOKEN_WHITESPACE;
                    mark();
                    state = 4;
                } else if ((ch == 33)) {
                    state = 5;
                } else if ((ch == 34)) {
                    state = 9;
                } else if ((ch == 35)) {
                    state = 12;
                } else if ((ch == 37)) {
                    id = TOKEN_MOD;
                    mark();
                    state = 14;
                } else if ((ch == 38)) {
                    id = TOKEN_AND;
                    mark();
                    state = 16;
                } else if ((ch == 39)) {
                    state = 18;
                } else if ((ch == 40)) {
                    id = TOKEN_LPAREN;
                    mark();
                    state = 20;
                } else if ((ch == 41)) {
                    id = TOKEN_RPAREN;
                    mark();
                    state = 21;
                } else if ((ch == 42)) {
                    id = TOKEN_STAR;
                    mark();
                    state = 22;
                } else if ((ch == 43)) {
                    id = TOKEN_PLUS;
                    mark();
                    state = 26;
                } else if ((ch == 44)) {
                    id = TOKEN_COMMA;
                    mark();
                    state = 28;
                } else if ((ch == 45)) {
                    id = TOKEN_MINUS;
                    mark();
                    state = 29;
                } else if ((ch == 46)) {
                    id = TOKEN_DOT;
                    mark();
                    state = 32;
                } else if ((ch == 47)) {
                    id = TOKEN_SLASH;
                    mark();
                    state = 35;
                } else if (((ch >= 48) && (ch <=57))) {
                    id = TOKEN_NUMBER;
                    mark();
                    state = 39;
                } else if ((ch == 58)) {
                    id = TOKEN_COLON;
                    mark();
                    state = 41;
                } else if ((ch == 59)) {
                    id = TOKEN_SEMICOLON;
                    mark();
                    state = 42;
                } else if ((ch == 60)) {
                    id = TOKEN_LT;
                    mark();
                    state = 43;
                } else if ((ch == 61)) {
                    id = TOKEN_EQ;
                    mark();
                    state = 48;
                } else if ((ch == 62)) {
                    id = TOKEN_GT;
                    mark();
                    state = 50;
                } else if ((ch == 64)) {
                    id = TOKEN_AT;
                    mark();
                    state = 54;
                } else if (((ch >= 65) && (ch <=69)) || ((ch >= 71) && (ch <=77)) || ((ch >= 79) && (ch <=83)) || ((ch >= 85) && (ch <=90)) || (ch == 95) || (ch == 104) || ((ch >= 106) && (ch <=107)) || (ch == 109) || (ch == 113) || (ch == 115) || ((ch >= 117) && (ch <=118)) || (ch == 120) || (ch == 122)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 70)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 58;
                } else if ((ch == 78)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 63;
                } else if ((ch == 84)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 67;
                } else if ((ch == 91)) {
                    id = TOKEN_LBRACK;
                    mark();
                    state = 71;
                } else if ((ch == 92)) {
                    state = 72;
                } else if ((ch == 93)) {
                    id = TOKEN_RBRACK;
                    mark();
                    state = 73;
                } else if ((ch == 94)) {
                    id = TOKEN_XOR;
                    mark();
                    state = 74;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 76;
                } else if ((ch == 98)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 84;
                } else if ((ch == 99)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 89;
                } else if ((ch == 100)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 101;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 105;
                } else if ((ch == 102)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 116;
                } else if ((ch == 103)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 128;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 134;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 143;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 149;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 158;
                } else if ((ch == 112)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 160;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 164;
                } else if ((ch == 116)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 174;
                } else if ((ch == 119)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 177;
                } else if ((ch == 121)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 185;
                } else if ((ch == 123)) {
                    id = TOKEN_LKBRACK;
                    mark();
                    state = 190;
                } else if ((ch == 124)) {
                    id = TOKEN_OR;
                    mark();
                    state = 191;
                } else if ((ch == 125)) {
                    id = TOKEN_RKBRACK;
                    mark();
                    state = 193;
                } else if ((ch == 126)) {
                    id = TOKEN_TILDA;
                    mark();
                    state = 194;
                } else {
                    ok = false;
                }
            } else if (state == 2) {
                if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=32))) {
                    id = TOKEN_WHITESPACE;
                    mark();
                    state = 2;
                } else {
                    ok = false;
                }
            } else if (state == 3) {
                    ok = false;
            } else if (state == 4) {
                if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=32))) {
                    id = TOKEN_WHITESPACE;
                    mark();
                    state = 2;
                } else if ((ch == 10)) {
                    id = TOKEN_NEWLINE;
                    mark();
                    state = 3;
                } else {
                    ok = false;
                }
            } else if (state == 5) {
                if ((ch == 33)) {
                    state = 6;
                } else if ((ch == 61)) {
                    id = TOKEN_NOT_EQUAL;
                    mark();
                    state = 8;
                } else {
                    ok = false;
                }
            } else if (state == 6) {
                if ((ch == 33)) {
                    id = TOKEN_INDENT;
                    mark();
                    state = 7;
                } else {
                    ok = false;
                }
            } else if (state == 7) {
                    ok = false;
            } else if (state == 8) {
                    ok = false;
            } else if (state == 9) {
                if (((ch >= 32) && (ch <=33)) || ((ch >= 35) && (ch <=91)) || ((ch >= 93) && (ch <=127))) {
                    state = 9;
                } else if ((ch == 34)) {
                    id = TOKEN_STRING;
                    mark();
                    state = 10;
                } else if ((ch == 92)) {
                    state = 11;
                } else {
                    ok = false;
                }
            } else if (state == 10) {
                    ok = false;
            } else if (state == 11) {
                if ((ch == 34) || (ch == 92)) {
                    state = 9;
                } else {
                    ok = false;
                }
            } else if (state == 12) {
                if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=127))) {
                    state = 12;
                } else if ((ch == 10)) {
                    id = TOKEN_COMMENTS;
                    mark();
                    state = 13;
                } else {
                    ok = false;
                }
            } else if (state == 13) {
                    ok = false;
            } else if (state == 14) {
                if ((ch == 61)) {
                    id = 38;
                    mark();
                    state = 15;
                } else {
                    ok = false;
                }
            } else if (state == 15) {
                    ok = false;
            } else if (state == 16) {
                if ((ch == 61)) {
                    id = 39;
                    mark();
                    state = 17;
                } else {
                    ok = false;
                }
            } else if (state == 17) {
                    ok = false;
            } else if (state == 18) {
                if (((ch >= 32) && (ch <=38)) || ((ch >= 40) && (ch <=91)) || ((ch >= 93) && (ch <=127))) {
                    state = 18;
                } else if ((ch == 39)) {
                    id = TOKEN_STRING;
                    mark();
                    state = 10;
                } else if ((ch == 92)) {
                    state = 19;
                } else {
                    ok = false;
                }
            } else if (state == 19) {
                if ((ch == 39) || (ch == 92)) {
                    state = 18;
                } else {
                    ok = false;
                }
            } else if (state == 20) {
                    ok = false;
            } else if (state == 21) {
                    ok = false;
            } else if (state == 22) {
                if ((ch == 42)) {
                    id = TOKEN_STARSTAR;
                    mark();
                    state = 23;
                } else if ((ch == 61)) {
                    id = 36;
                    mark();
                    state = 25;
                } else {
                    ok = false;
                }
            } else if (state == 23) {
                if ((ch == 61)) {
                    id = 44;
                    mark();
                    state = 24;
                } else {
                    ok = false;
                }
            } else if (state == 24) {
                    ok = false;
            } else if (state == 25) {
                    ok = false;
            } else if (state == 26) {
                if ((ch == 61)) {
                    id = 34;
                    mark();
                    state = 27;
                } else {
                    ok = false;
                }
            } else if (state == 27) {
                    ok = false;
            } else if (state == 28) {
                    ok = false;
            } else if (state == 29) {
                if ((ch == 61)) {
                    id = 35;
                    mark();
                    state = 30;
                } else if ((ch == 62)) {
                    id = TOKEN_DASH_GT;
                    mark();
                    state = 31;
                } else {
                    ok = false;
                }
            } else if (state == 30) {
                    ok = false;
            } else if (state == 31) {
                    ok = false;
            } else if (state == 32) {
                if ((ch == 46)) {
                    state = 33;
                } else {
                    ok = false;
                }
            } else if (state == 33) {
                if ((ch == 46)) {
                    id = TOKEN_ELLIPSIS;
                    mark();
                    state = 34;
                } else {
                    ok = false;
                }
            } else if (state == 34) {
                    ok = false;
            } else if (state == 35) {
                if ((ch == 47)) {
                    id = TOKEN_SLASHSLASH;
                    mark();
                    state = 36;
                } else if ((ch == 61)) {
                    id = 37;
                    mark();
                    state = 38;
                } else {
                    ok = false;
                }
            } else if (state == 36) {
                if ((ch == 61)) {
                    id = 45;
                    mark();
                    state = 37;
                } else {
                    ok = false;
                }
            } else if (state == 37) {
                    ok = false;
            } else if (state == 38) {
                    ok = false;
            } else if (state == 39) {
                if ((ch == 46)) {
                    id = TOKEN_NUMBER;
                    mark();
                    state = 40;
                } else if (((ch >= 48) && (ch <=57))) {
                    id = TOKEN_NUMBER;
                    mark();
                    state = 39;
                } else {
                    ok = false;
                }
            } else if (state == 40) {
                if (((ch >= 48) && (ch <=57))) {
                    id = TOKEN_NUMBER;
                    mark();
                    state = 40;
                } else {
                    ok = false;
                }
            } else if (state == 41) {
                    ok = false;
            } else if (state == 42) {
                    ok = false;
            } else if (state == 43) {
                if ((ch == 60)) {
                    id = TOKEN_LSHIFT;
                    mark();
                    state = 44;
                } else if ((ch == 61)) {
                    id = TOKEN_LE;
                    mark();
                    state = 46;
                } else if ((ch == 62)) {
                    id = TOKEN_NOT_EQUAL2;
                    mark();
                    state = 47;
                } else {
                    ok = false;
                }
            } else if (state == 44) {
                if ((ch == 61)) {
                    id = 42;
                    mark();
                    state = 45;
                } else {
                    ok = false;
                }
            } else if (state == 45) {
                    ok = false;
            } else if (state == 46) {
                    ok = false;
            } else if (state == 47) {
                    ok = false;
            } else if (state == 48) {
                if ((ch == 61)) {
                    id = TOKEN_EQUAL;
                    mark();
                    state = 49;
                } else {
                    ok = false;
                }
            } else if (state == 49) {
                    ok = false;
            } else if (state == 50) {
                if ((ch == 61)) {
                    id = TOKEN_GE;
                    mark();
                    state = 51;
                } else if ((ch == 62)) {
                    id = TOKEN_RSHIFT;
                    mark();
                    state = 52;
                } else {
                    ok = false;
                }
            } else if (state == 51) {
                    ok = false;
            } else if (state == 52) {
                if ((ch == 61)) {
                    id = 43;
                    mark();
                    state = 53;
                } else {
                    ok = false;
                }
            } else if (state == 53) {
                    ok = false;
            } else if (state == 54) {
                if ((ch == 64)) {
                    state = 55;
                } else {
                    ok = false;
                }
            } else if (state == 55) {
                if ((ch == 64)) {
                    id = TOKEN_DEDENT;
                    mark();
                    state = 56;
                } else {
                    ok = false;
                }
            } else if (state == 56) {
                    ok = false;
            } else if (state == 57) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 58) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 59;
                } else {
                    ok = false;
                }
            } else if (state == 59) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 60;
                } else {
                    ok = false;
                }
            } else if (state == 60) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 61;
                } else {
                    ok = false;
                }
            } else if (state == 61) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 21;
                    mark();
                    state = 62;
                } else {
                    ok = false;
                }
            } else if (state == 62) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 63) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 64;
                } else {
                    ok = false;
                }
            } else if (state == 64) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 65;
                } else {
                    ok = false;
                }
            } else if (state == 65) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 19;
                    mark();
                    state = 66;
                } else {
                    ok = false;
                }
            } else if (state == 66) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 67) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 68;
                } else {
                    ok = false;
                }
            } else if (state == 68) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 117)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 69;
                } else {
                    ok = false;
                }
            } else if (state == 69) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 20;
                    mark();
                    state = 70;
                } else {
                    ok = false;
                }
            } else if (state == 70) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 71) {
                    ok = false;
            } else if (state == 72) {
                if ((ch == 10)) {
                    id = TOKEN_NEWLINE;
                    mark();
                    state = 3;
                } else if ((ch == 13)) {
                    id = TOKEN_WHITESPACE;
                    mark();
                    state = 4;
                } else {
                    ok = false;
                }
            } else if (state == 73) {
                    ok = false;
            } else if (state == 74) {
                if ((ch == 61)) {
                    id = 41;
                    mark();
                    state = 75;
                } else {
                    ok = false;
                }
            } else if (state == 75) {
                    ok = false;
            } else if (state == 76) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 77;
                } else if ((ch == 115)) {
                    id = 24;
                    mark();
                    state = 79;
                } else {
                    ok = false;
                }
            } else if (state == 77) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 100)) {
                    id = 29;
                    mark();
                    state = 78;
                } else {
                    ok = false;
                }
            } else if (state == 78) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 79) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 80;
                } else {
                    ok = false;
                }
            } else if (state == 80) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 81;
                } else {
                    ok = false;
                }
            } else if (state == 81) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 82;
                } else {
                    ok = false;
                }
            } else if (state == 82) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = 10;
                    mark();
                    state = 83;
                } else {
                    ok = false;
                }
            } else if (state == 83) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 84) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 85;
                } else {
                    ok = false;
                }
            } else if (state == 85) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 86;
                } else {
                    ok = false;
                }
            } else if (state == 86) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 87;
                } else {
                    ok = false;
                }
            } else if (state == 87) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=106)) || ((ch >= 108) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 107)) {
                    id = 4;
                    mark();
                    state = 88;
                } else {
                    ok = false;
                }
            } else if (state == 88) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 89) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 90;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 94;
                } else {
                    ok = false;
                }
            } else if (state == 90) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 91;
                } else {
                    ok = false;
                }
            } else if (state == 91) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 92;
                } else {
                    ok = false;
                }
            } else if (state == 92) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = 18;
                    mark();
                    state = 93;
                } else {
                    ok = false;
                }
            } else if (state == 93) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 94) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 95;
                } else {
                    ok = false;
                }
            } else if (state == 95) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 96;
                } else {
                    ok = false;
                }
            } else if (state == 96) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 97;
                } else {
                    ok = false;
                }
            } else if (state == 97) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 98;
                } else {
                    ok = false;
                }
            } else if (state == 98) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 117)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 99;
                } else {
                    ok = false;
                }
            } else if (state == 99) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 3;
                    mark();
                    state = 100;
                } else {
                    ok = false;
                }
            } else if (state == 100) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 101) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 102;
                } else {
                    ok = false;
                }
            } else if (state == 102) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 102)) {
                    id = 16;
                    mark();
                    state = 103;
                } else if ((ch == 108)) {
                    id = 1;
                    mark();
                    state = 104;
                } else {
                    ok = false;
                }
            } else if (state == 103) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 104) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 105) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=119)) || ((ch >= 121) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 106;
                } else if ((ch == 120)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 111;
                } else {
                    ok = false;
                }
            } else if (state == 106) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 107;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 109;
                } else {
                    ok = false;
                }
            } else if (state == 107) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 102)) {
                    id = 12;
                    mark();
                    state = 108;
                } else {
                    ok = false;
                }
            } else if (state == 108) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 109) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 13;
                    mark();
                    state = 110;
                } else {
                    ok = false;
                }
            } else if (state == 110) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 111) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=98)) || ((ch >= 100) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 99)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 112;
                } else {
                    ok = false;
                }
            } else if (state == 112) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 113;
                } else {
                    ok = false;
                }
            } else if (state == 113) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=111)) || ((ch >= 113) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 112)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 114;
                } else {
                    ok = false;
                }
            } else if (state == 114) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = 26;
                    mark();
                    state = 115;
                } else {
                    ok = false;
                }
            } else if (state == 115) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 116) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=110)) || ((ch >= 112) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 117;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 123;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 125;
                } else {
                    ok = false;
                }
            } else if (state == 117) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 118;
                } else {
                    ok = false;
                }
            } else if (state == 118) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 119;
                } else {
                    ok = false;
                }
            } else if (state == 119) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 120;
                } else {
                    ok = false;
                }
            } else if (state == 120) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 121;
                } else {
                    ok = false;
                }
            } else if (state == 121) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=120)) || (ch == 122)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 121)) {
                    id = 32;
                    mark();
                    state = 122;
                } else {
                    ok = false;
                }
            } else if (state == 122) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 123) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = 14;
                    mark();
                    state = 124;
                } else {
                    ok = false;
                }
            } else if (state == 124) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 125) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 126;
                } else {
                    ok = false;
                }
            } else if (state == 126) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=108)) || ((ch >= 110) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 109)) {
                    id = 7;
                    mark();
                    state = 127;
                } else {
                    ok = false;
                }
            } else if (state == 127) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 128) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 129;
                } else {
                    ok = false;
                }
            } else if (state == 129) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 130;
                } else {
                    ok = false;
                }
            } else if (state == 130) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || (ch == 97) || ((ch >= 99) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 98)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 131;
                } else {
                    ok = false;
                }
            } else if (state == 131) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 132;
                } else {
                    ok = false;
                }
            } else if (state == 132) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = 8;
                    mark();
                    state = 133;
                } else {
                    ok = false;
                }
            } else if (state == 133) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 134) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=108)) || ((ch >= 111) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 102)) {
                    id = 11;
                    mark();
                    state = 135;
                } else if ((ch == 109)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 136;
                } else if ((ch == 110)) {
                    id = 22;
                    mark();
                    state = 141;
                } else if ((ch == 115)) {
                    id = 23;
                    mark();
                    state = 142;
                } else {
                    ok = false;
                }
            } else if (state == 135) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 136) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=111)) || ((ch >= 113) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 112)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 137;
                } else {
                    ok = false;
                }
            } else if (state == 137) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 138;
                } else {
                    ok = false;
                }
            } else if (state == 138) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 139;
                } else {
                    ok = false;
                }
            } else if (state == 139) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = 33;
                    mark();
                    state = 140;
                } else {
                    ok = false;
                }
            } else if (state == 140) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 141) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 142) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 143) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 144;
                } else {
                    ok = false;
                }
            } else if (state == 144) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=108)) || ((ch >= 110) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 109)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 145;
                } else {
                    ok = false;
                }
            } else if (state == 145) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || (ch == 97) || ((ch >= 99) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 98)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 146;
                } else {
                    ok = false;
                }
            } else if (state == 146) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 100)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 147;
                } else {
                    ok = false;
                }
            } else if (state == 147) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = 27;
                    mark();
                    state = 148;
                } else {
                    ok = false;
                }
            } else if (state == 148) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 149) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 150;
                } else {
                    ok = false;
                }
            } else if (state == 150) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 151;
                } else if ((ch == 116)) {
                    id = 28;
                    mark();
                    state = 157;
                } else {
                    ok = false;
                }
            } else if (state == 151) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 152;
                } else {
                    ok = false;
                }
            } else if (state == 152) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 111)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 153;
                } else {
                    ok = false;
                }
            } else if (state == 153) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=98)) || ((ch >= 100) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 99)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 154;
                } else {
                    ok = false;
                }
            } else if (state == 154) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 155;
                } else {
                    ok = false;
                }
            } else if (state == 155) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = 9;
                    mark();
                    state = 156;
                } else {
                    ok = false;
                }
            } else if (state == 156) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 157) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 158) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = 30;
                    mark();
                    state = 159;
                } else {
                    ok = false;
                }
            } else if (state == 159) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 160) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 161;
                } else {
                    ok = false;
                }
            } else if (state == 161) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 162;
                } else {
                    ok = false;
                }
            } else if (state == 162) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = 2;
                    mark();
                    state = 163;
                } else {
                    ok = false;
                }
            } else if (state == 163) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 164) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 97)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 165;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 169;
                } else {
                    ok = false;
                }
            } else if (state == 165) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 166;
                } else {
                    ok = false;
                }
            } else if (state == 166) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 115)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 167;
                } else {
                    ok = false;
                }
            } else if (state == 167) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 6;
                    mark();
                    state = 168;
                } else {
                    ok = false;
                }
            } else if (state == 168) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 169) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 170;
                } else {
                    ok = false;
                }
            } else if (state == 170) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 117)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 171;
                } else {
                    ok = false;
                }
            } else if (state == 171) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 172;
                } else {
                    ok = false;
                }
            } else if (state == 172) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 110)) {
                    id = 5;
                    mark();
                    state = 173;
                } else {
                    ok = false;
                }
            } else if (state == 173) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 174) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 114)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 175;
                } else {
                    ok = false;
                }
            } else if (state == 175) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=120)) || (ch == 122)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 121)) {
                    id = 31;
                    mark();
                    state = 176;
                } else {
                    ok = false;
                }
            } else if (state == 176) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 177) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=103)) || ((ch >= 106) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 104)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 178;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 182;
                } else {
                    ok = false;
                }
            } else if (state == 178) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 179;
                } else {
                    ok = false;
                }
            } else if (state == 179) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 180;
                } else {
                    ok = false;
                }
            } else if (state == 180) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = 15;
                    mark();
                    state = 181;
                } else {
                    ok = false;
                }
            } else if (state == 181) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 182) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 116)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 183;
                } else {
                    ok = false;
                }
            } else if (state == 183) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=103)) || ((ch >= 105) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 104)) {
                    id = 25;
                    mark();
                    state = 184;
                } else {
                    ok = false;
                }
            } else if (state == 184) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 185) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 105)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 186;
                } else {
                    ok = false;
                }
            } else if (state == 186) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 101)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 187;
                } else {
                    ok = false;
                }
            } else if (state == 187) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 108)) {
                    id = TOKEN_NAME;
                    mark();
                    state = 188;
                } else {
                    ok = false;
                }
            } else if (state == 188) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else if ((ch == 100)) {
                    id = 17;
                    mark();
                    state = 189;
                } else {
                    ok = false;
                }
            } else if (state == 189) {
                if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                    id = TOKEN_NAME;
                    mark();
                    state = 57;
                } else {
                    ok = false;
                }
            } else if (state == 190) {
                    ok = false;
            } else if (state == 191) {
                if ((ch == 61)) {
                    id = 40;
                    mark();
                    state = 192;
                } else {
                    ok = false;
                }
            } else if (state == 192) {
                    ok = false;
            } else if (state == 193) {
                    ok = false;
            } else if (state == 194) {
                    ok = false;
            }
        }
        if ((id == 0) && (ch == 0)) {
            return eofToken();
        }
        reset();
        String s = read.toString();
        if (id == TOKEN_NAME) {

          return new TokenImpl(s, TOKEN_NAME, startLine, startPos);
       
        } else if (id == TOKEN_STRING) {
 
          StringBuilder buf = new StringBuilder();
          for (int i = 0; i < s.length(); i++) {
              char c = s.charAt(i);
              if (c == '\\') {
                  i++;
                  c = s.charAt(i + 1);
              }
              buf.append(c);
          }
          return new TokenImpl(buf.toString().substring(1, buf.length() - 1), TOKEN_STRING, startLine, startPos); 
      
        }
        return new TokenImpl(read.toString(), id, startLine, startPos);// 1
    }

    public interface Token {

        int getId();
        int getLine();
        int getPos();
        int getLength();

    }

    public static Token newToken(String s, int id, int line, int pos) { return new TokenImpl(s, id, line, pos); }

    public static class TokenImpl implements Token {

        protected int line;
        protected int pos;
        protected int id;
        protected String s;

        public TokenImpl(String s, int id, int line, int pos) {
            this.s = s;
            this.id = id;
            this.line = line;
            this.pos = pos;
        }

        public int getId() {
            return id;
        }

        public int getLine() {
            return line;
        }

        public int getPos() {
            return pos;
        }

        public int getLength() {
            return s.length();
        }

        public String toString() {
            return s;
        }

    }
}
