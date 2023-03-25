package org.ah.python.grammar;

import java.io.Reader;

public class PythonScannerOK extends PythonScanner {

    int currentIndent = 1;
    
    public PythonScannerOK() {
    }

    public PythonScannerOK(Reader r) {
        super(r);
    }

    public static final int TOKEN_EOF = 46;

    public static final int TOKEN_NEWLINE = 47;

    public static final int TOKEN_COMMA = 48;

    public static final int TOKEN_DASH_GT = 49;

    public static final int TOKEN_LPAREN = 50;

    public static final int TOKEN_RPAREN = 51;

    public static final int TOKEN_LBRACK = 52;

    public static final int TOKEN_RBRACK = 53;

    public static final int TOKEN_LKBRACK = 54;

    public static final int TOKEN_RKBRACK = 55;

    public static final int TOKEN_COLON = 56;

    public static final int TOKEN_EQ = 57;

    public static final int TOKEN_STAR = 58;

    public static final int TOKEN_STARSTAR = 59;

    public static final int TOKEN_SEMICOLON = 60;

    public static final int TOKEN_DOT = 61;

    public static final int TOKEN_ELLIPSIS = 62;

    public static final int TOKEN_AT = 63;

    public static final int TOKEN_EQUAL = 64;

    public static final int TOKEN_NOT_EQUAL = 65;

    public static final int TOKEN_NOT_EQUAL2 = 66;

    public static final int TOKEN_GE = 67;

    public static final int TOKEN_GT = 68;

    public static final int TOKEN_LE = 69;

    public static final int TOKEN_LT = 70;

    public static final int TOKEN_OR = 71;

    public static final int TOKEN_XOR = 72;

    public static final int TOKEN_AND = 73;

    public static final int TOKEN_LSHIFT = 74;

    public static final int TOKEN_RSHIFT = 75;

    public static final int TOKEN_PLUS = 76;

    public static final int TOKEN_MINUS = 77;

    public static final int TOKEN_MOD = 78;

    public static final int TOKEN_SLASH = 79;

    public static final int TOKEN_SLASHSLASH = 80;

    public static final int TOKEN_TILDA = 81;

    public static final int TOKEN_NAME = 87;

    public static final int TOKEN_NUMBER = 88;

    public static final int TOKEN_STRING = 90;

    public static final int TOKEN_INDENT = 91;

    public static final int TOKEN_DEDENT = 92;

    public Token getNextToken() {
        startLine = line;
        startPos = pos;
        mark();
        read.setLength(0);
        int state = 1;
        int id = 0;
        boolean ok = true;
        int ch = 0;
        while (ok) {
            int cpos = pos;
            ch = getNextChar();
            // System.out.println(state+": "+ch+" "+(char)ch);
// total nodes: 191
// 191 nodes
            while (cpos == 0 && pos == 0) {
                ch = getNextChar();
                startLine = line;
                startPos = pos;
                mark();
                read.setLength(0);
            }

            if (pos == 1 && (ch == ' ' || ch == 9)) {
                while (ch == ' ' || ch == 9) {
                    mark();
                    ch = getNextChar();
                    if (ch == 13) {
                        ch = getNextChar();
                    }
                    if (ch == 10) {
                        startLine = line;
                        startPos = pos;
                        mark();
                        read.setLength(0);
                        ch = getNextChar();
                    }
                }
                if (pos - 1< currentIndent) {
                    currentIndent = pos - 1;
                    id = TOKEN_DEDENT;
                    ok = false;
                } else if (pos - 1 > currentIndent) {
                    currentIndent = pos - 1;
                    id = TOKEN_INDENT;
                    ok = false;
                } else {
                    startLine = line;
                    startPos = pos;
//                    mark();
                    read.setLength(0);
                }
            }
            if (ok) {   
                if (state == 1) {
                    if ((ch == 0)) {
                        id = TOKEN_EOF;
                        mark();
                        state = 2;
                    } else if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=12)) || ((ch >= 14) && (ch <=32))) {
                        startLine = line;
                        startPos = pos;
                        mark();
                        read.setLength(0);
                        state = 1;
                    } else if ((ch == 10)) {
                        id = TOKEN_NEWLINE;
                        mark();
                        ok = false;
                        state = 4;
                    } else if ((ch == 13)) {
                        startLine = line;
                        startPos = pos;
                        mark();
                        read.setLength(0);
                        state = 1;
                    } else if ((ch == 33)) {
                        state = 6;
                    } else if ((ch == 34)) {
                        state = 10;
                    } else if ((ch == 35)) {
                        state = 13;
                    } else if ((ch == 37)) {
                        id = TOKEN_MOD;
                        mark();
                        state = 14;
                    } else if ((ch == 38)) {
                        id = TOKEN_AND;
                        mark();
                        state = 16;
                    } else if ((ch == 40)) {
                        id = TOKEN_LPAREN;
                        mark();
                        state = 18;
                    } else if ((ch == 41)) {
                        id = TOKEN_RPAREN;
                        mark();
                        state = 19;
                    } else if ((ch == 42)) {
                        id = TOKEN_STAR;
                        mark();
                        state = 20;
                    } else if ((ch == 43)) {
                        id = TOKEN_PLUS;
                        mark();
                        state = 24;
                    } else if ((ch == 44)) {
                        id = TOKEN_COMMA;
                        mark();
                        state = 26;
                    } else if ((ch == 45)) {
                        id = TOKEN_MINUS;
                        mark();
                        state = 27;
                    } else if ((ch == 46)) {
                        id = TOKEN_DOT;
                        mark();
                        state = 30;
                    } else if ((ch == 47)) {
                        id = TOKEN_SLASH;
                        mark();
                        state = 33;
                    } else if (((ch >= 48) && (ch <=57))) {
                        id = TOKEN_NUMBER;
                        mark();
                        state = 37;
                    } else if ((ch == 58)) {
                        id = TOKEN_COLON;
                        mark();
                        state = 39;
                    } else if ((ch == 59)) {
                        id = TOKEN_SEMICOLON;
                        mark();
                        state = 40;
                    } else if ((ch == 60)) {
                        id = TOKEN_LT;
                        mark();
                        state = 41;
                    } else if ((ch == 61)) {
                        id = TOKEN_EQ;
                        mark();
                        state = 46;
                    } else if ((ch == 62)) {
                        id = TOKEN_GT;
                        mark();
                        state = 48;
                    } else if ((ch == 64)) {
                        id = TOKEN_AT;
                        mark();
                        state = 52;
                    } else if (((ch >= 65) && (ch <=69)) || ((ch >= 71) && (ch <=77)) || ((ch >= 79) && (ch <=83)) || ((ch >= 85) && (ch <=90)) || (ch == 95) || (ch == 104) || ((ch >= 106) && (ch <=107)) || (ch == 109) || (ch == 113) || (ch == 115) || ((ch >= 117) && (ch <=118)) || (ch == 120) || (ch == 122)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 70)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 56;
                    } else if ((ch == 78)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 61;
                    } else if ((ch == 84)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 65;
                    } else if ((ch == 91)) {
                        id = TOKEN_LBRACK;
                        mark();
                        state = 69;
                    } else if ((ch == 93)) {
                        id = TOKEN_RBRACK;
                        mark();
                        state = 70;
                    } else if ((ch == 94)) {
                        id = TOKEN_XOR;
                        mark();
                        state = 71;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 73;
                    } else if ((ch == 98)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 81;
                    } else if ((ch == 99)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 86;
                    } else if ((ch == 100)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 98;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 102;
                    } else if ((ch == 102)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 113;
                    } else if ((ch == 103)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 125;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 131;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 140;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 146;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 155;
                    } else if ((ch == 112)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 157;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 161;
                    } else if ((ch == 116)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 171;
                    } else if ((ch == 119)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 174;
                    } else if ((ch == 121)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 182;
                    } else if ((ch == 123)) {
                        id = TOKEN_LKBRACK;
                        mark();
                        state = 187;
                    } else if ((ch == 124)) {
                        id = TOKEN_OR;
                        mark();
                        state = 188;
                    } else if ((ch == 125)) {
                        id = TOKEN_RKBRACK;
                        mark();
                        state = 190;
                    } else if ((ch == 126)) {
                        id = TOKEN_TILDA;
                        mark();
                        state = 191;
                    } else {
                        ok = false;
                    }
                } else if (state == 2) {
                        ok = false;
                } else if (state == 3) {
                        ok = false;
                } else if (state == 4) {
                        ok = false;
                } else if (state == 5) {
                    if ((ch == 10)) {
                        id = TOKEN_NEWLINE;
                        mark();
                        state = 4;
                        ok = false;
                    } else {
                        ok = false;
                    }
                } else if (state == 6) {
                    if ((ch == 33)) {
                        state = 7;
                    } else if ((ch == 61)) {
                        id = TOKEN_NOT_EQUAL;
                        mark();
                        state = 9;
                    } else {
                        ok = false;
                    }
                } else if (state == 7) {
                    if ((ch == 33)) {
                        id = TOKEN_INDENT;
                        mark();
                        state = 8;
                    } else {
                        ok = false;
                    }
                } else if (state == 8) {
                        ok = false;
                } else if (state == 9) {
                        ok = false;
                } else if (state == 10) {
                    if (((ch >= 32) && (ch <=33)) || ((ch >= 35) && (ch <=91)) || ((ch >= 93) && (ch <=127))) {
                        state = 10;
                    } else if ((ch == 34)) {
                        id = TOKEN_STRING;
                        mark();
                        state = 11;
                    } else if ((ch == 92)) {
                        state = 12;
                    } else {
                        ok = false;
                    }
                } else if (state == 11) {
                        ok = false;
                } else if (state == 12) {
                    if ((ch == 34) || (ch == 92)) {
                        state = 10;
                    } else {
                        ok = false;
                    }
                } else if (state == 13) {
                    if (((ch >= 1) && (ch <=9)) || ((ch >= 11) && (ch <=127))) {
                        state = 13;
                    } else if ((ch == 10)) {
                        startLine = line;
                        startPos = pos;
                        mark();
                        read.setLength(0);
                        state = 1;
                    } else {
                        ok = false;
                    }
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
                        ok = false;
                } else if (state == 19) {
                        ok = false;
                } else if (state == 20) {
                    if ((ch == 42)) {
                        id = TOKEN_STARSTAR;
                        mark();
                        state = 21;
                    } else if ((ch == 61)) {
                        id = 36;
                        mark();
                        state = 23;
                    } else {
                        ok = false;
                    }
                } else if (state == 21) {
                    if ((ch == 61)) {
                        id = 44;
                        mark();
                        state = 22;
                    } else {
                        ok = false;
                    }
                } else if (state == 22) {
                        ok = false;
                } else if (state == 23) {
                        ok = false;
                } else if (state == 24) {
                    if ((ch == 61)) {
                        id = 34;
                        mark();
                        state = 25;
                    } else {
                        ok = false;
                    }
                } else if (state == 25) {
                        ok = false;
                } else if (state == 26) {
                        ok = false;
                } else if (state == 27) {
                    if ((ch == 61)) {
                        id = 35;
                        mark();
                        state = 28;
                    } else if ((ch == 62)) {
                        id = TOKEN_DASH_GT;
                        mark();
                        state = 29;
                    } else {
                        ok = false;
                    }
                } else if (state == 28) {
                        ok = false;
                } else if (state == 29) {
                        ok = false;
                } else if (state == 30) {
                    if ((ch == 46)) {
                        state = 31;
                    } else {
                        ok = false;
                    }
                } else if (state == 31) {
                    if ((ch == 46)) {
                        id = TOKEN_ELLIPSIS;
                        mark();
                        state = 32;
                    } else {
                        ok = false;
                    }
                } else if (state == 32) {
                        ok = false;
                } else if (state == 33) {
                    if ((ch == 47)) {
                        id = TOKEN_SLASHSLASH;
                        mark();
                        state = 34;
                    } else if ((ch == 61)) {
                        id = 37;
                        mark();
                        state = 36;
                    } else {
                        ok = false;
                    }
                } else if (state == 34) {
                    if ((ch == 61)) {
                        id = 45;
                        mark();
                        state = 35;
                    } else {
                        ok = false;
                    }
                } else if (state == 35) {
                        ok = false;
                } else if (state == 36) {
                        ok = false;
                } else if (state == 37) {
                    if ((ch == 46)) {
                        id = TOKEN_NUMBER;
                        mark();
                        state = 38;
                    } else if (((ch >= 48) && (ch <=57))) {
                        id = TOKEN_NUMBER;
                        mark();
                        state = 37;
                    } else {
                        ok = false;
                    }
                } else if (state == 38) {
                    if (((ch >= 48) && (ch <=57))) {
                        id = TOKEN_NUMBER;
                        mark();
                        state = 38;
                    } else {
                        ok = false;
                    }
                } else if (state == 39) {
                        ok = false;
                } else if (state == 40) {
                        ok = false;
                } else if (state == 41) {
                    if ((ch == 60)) {
                        id = TOKEN_LSHIFT;
                        mark();
                        state = 42;
                    } else if ((ch == 61)) {
                        id = TOKEN_LE;
                        mark();
                        state = 44;
                    } else if ((ch == 62)) {
                        id = TOKEN_NOT_EQUAL2;
                        mark();
                        state = 45;
                    } else {
                        ok = false;
                    }
                } else if (state == 42) {
                    if ((ch == 61)) {
                        id = 42;
                        mark();
                        state = 43;
                    } else {
                        ok = false;
                    }
                } else if (state == 43) {
                        ok = false;
                } else if (state == 44) {
                        ok = false;
                } else if (state == 45) {
                        ok = false;
                } else if (state == 46) {
                    if ((ch == 61)) {
                        id = TOKEN_EQUAL;
                        mark();
                        state = 47;
                    } else {
                        ok = false;
                    }
                } else if (state == 47) {
                        ok = false;
                } else if (state == 48) {
                    if ((ch == 61)) {
                        id = TOKEN_GE;
                        mark();
                        state = 49;
                    } else if ((ch == 62)) {
                        id = TOKEN_RSHIFT;
                        mark();
                        state = 50;
                    } else {
                        ok = false;
                    }
                } else if (state == 49) {
                        ok = false;
                } else if (state == 50) {
                    if ((ch == 61)) {
                        id = 43;
                        mark();
                        state = 51;
                    } else {
                        ok = false;
                    }
                } else if (state == 51) {
                        ok = false;
                } else if (state == 52) {
                    if ((ch == 64)) {
                        state = 53;
                    } else {
                        ok = false;
                    }
                } else if (state == 53) {
                    if ((ch == 64)) {
                        id = TOKEN_DEDENT;
                        mark();
                        state = 54;
                    } else {
                        ok = false;
                    }
                } else if (state == 54) {
                        ok = false;
                } else if (state == 55) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 56) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 57;
                    } else {
                        ok = false;
                    }
                } else if (state == 57) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 58;
                    } else {
                        ok = false;
                    }
                } else if (state == 58) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 59;
                    } else {
                        ok = false;
                    }
                } else if (state == 59) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 21;
                        mark();
                        state = 60;
                    } else {
                        ok = false;
                    }
                } else if (state == 60) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 61) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 62;
                    } else {
                        ok = false;
                    }
                } else if (state == 62) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 63;
                    } else {
                        ok = false;
                    }
                } else if (state == 63) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 19;
                        mark();
                        state = 64;
                    } else {
                        ok = false;
                    }
                } else if (state == 64) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 65) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 66;
                    } else {
                        ok = false;
                    }
                } else if (state == 66) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 117)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 67;
                    } else {
                        ok = false;
                    }
                } else if (state == 67) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 20;
                        mark();
                        state = 68;
                    } else {
                        ok = false;
                    }
                } else if (state == 68) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 69) {
                        ok = false;
                } else if (state == 70) {
                        ok = false;
                } else if (state == 71) {
                    if ((ch == 61)) {
                        id = 41;
                        mark();
                        state = 72;
                    } else {
                        ok = false;
                    }
                } else if (state == 72) {
                        ok = false;
                } else if (state == 73) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 74;
                    } else if ((ch == 115)) {
                        id = 24;
                        mark();
                        state = 76;
                    } else {
                        ok = false;
                    }
                } else if (state == 74) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 100)) {
                        id = 29;
                        mark();
                        state = 75;
                    } else {
                        ok = false;
                    }
                } else if (state == 75) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 76) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 77;
                    } else {
                        ok = false;
                    }
                } else if (state == 77) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 78;
                    } else {
                        ok = false;
                    }
                } else if (state == 78) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 79;
                    } else {
                        ok = false;
                    }
                } else if (state == 79) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = 10;
                        mark();
                        state = 80;
                    } else {
                        ok = false;
                    }
                } else if (state == 80) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 81) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 82;
                    } else {
                        ok = false;
                    }
                } else if (state == 82) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 83;
                    } else {
                        ok = false;
                    }
                } else if (state == 83) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 84;
                    } else {
                        ok = false;
                    }
                } else if (state == 84) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=106)) || ((ch >= 108) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 107)) {
                        id = 4;
                        mark();
                        state = 85;
                    } else {
                        ok = false;
                    }
                } else if (state == 85) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 86) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 87;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 91;
                    } else {
                        ok = false;
                    }
                } else if (state == 87) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 88;
                    } else {
                        ok = false;
                    }
                } else if (state == 88) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 89;
                    } else {
                        ok = false;
                    }
                } else if (state == 89) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = 18;
                        mark();
                        state = 90;
                    } else {
                        ok = false;
                    }
                } else if (state == 90) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 91) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 92;
                    } else {
                        ok = false;
                    }
                } else if (state == 92) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 93;
                    } else {
                        ok = false;
                    }
                } else if (state == 93) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 94;
                    } else {
                        ok = false;
                    }
                } else if (state == 94) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 95;
                    } else {
                        ok = false;
                    }
                } else if (state == 95) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 117)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 96;
                    } else {
                        ok = false;
                    }
                } else if (state == 96) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 3;
                        mark();
                        state = 97;
                    } else {
                        ok = false;
                    }
                } else if (state == 97) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 98) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 99;
                    } else {
                        ok = false;
                    }
                } else if (state == 99) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 102)) {
                        id = 16;
                        mark();
                        state = 100;
                    } else if ((ch == 108)) {
                        id = 1;
                        mark();
                        state = 101;
                    } else {
                        ok = false;
                    }
                } else if (state == 100) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 101) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 102) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=119)) || ((ch >= 121) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 103;
                    } else if ((ch == 120)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 108;
                    } else {
                        ok = false;
                    }
                } else if (state == 103) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 104;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 106;
                    } else {
                        ok = false;
                    }
                } else if (state == 104) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 102)) {
                        id = 12;
                        mark();
                        state = 105;
                    } else {
                        ok = false;
                    }
                } else if (state == 105) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 106) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 13;
                        mark();
                        state = 107;
                    } else {
                        ok = false;
                    }
                } else if (state == 107) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 108) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=98)) || ((ch >= 100) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 99)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 109;
                    } else {
                        ok = false;
                    }
                } else if (state == 109) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 110;
                    } else {
                        ok = false;
                    }
                } else if (state == 110) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=111)) || ((ch >= 113) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 112)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 111;
                    } else {
                        ok = false;
                    }
                } else if (state == 111) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = 26;
                        mark();
                        state = 112;
                    } else {
                        ok = false;
                    }
                } else if (state == 112) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 113) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=110)) || ((ch >= 112) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 114;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 120;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 122;
                    } else {
                        ok = false;
                    }
                } else if (state == 114) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 115;
                    } else {
                        ok = false;
                    }
                } else if (state == 115) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 116;
                    } else {
                        ok = false;
                    }
                } else if (state == 116) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 117;
                    } else {
                        ok = false;
                    }
                } else if (state == 117) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 118;
                    } else {
                        ok = false;
                    }
                } else if (state == 118) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=120)) || (ch == 122)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 121)) {
                        id = 32;
                        mark();
                        state = 119;
                    } else {
                        ok = false;
                    }
                } else if (state == 119) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 120) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = 14;
                        mark();
                        state = 121;
                    } else {
                        ok = false;
                    }
                } else if (state == 121) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 122) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 123;
                    } else {
                        ok = false;
                    }
                } else if (state == 123) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=108)) || ((ch >= 110) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 109)) {
                        id = 7;
                        mark();
                        state = 124;
                    } else {
                        ok = false;
                    }
                } else if (state == 124) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 125) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 126;
                    } else {
                        ok = false;
                    }
                } else if (state == 126) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 127;
                    } else {
                        ok = false;
                    }
                } else if (state == 127) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || (ch == 97) || ((ch >= 99) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 98)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 128;
                    } else {
                        ok = false;
                    }
                } else if (state == 128) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 129;
                    } else {
                        ok = false;
                    }
                } else if (state == 129) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = 8;
                        mark();
                        state = 130;
                    } else {
                        ok = false;
                    }
                } else if (state == 130) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 131) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=101)) || ((ch >= 103) && (ch <=108)) || ((ch >= 111) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 102)) {
                        id = 11;
                        mark();
                        state = 132;
                    } else if ((ch == 109)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 133;
                    } else if ((ch == 110)) {
                        id = 22;
                        mark();
                        state = 138;
                    } else if ((ch == 115)) {
                        id = 23;
                        mark();
                        state = 139;
                    } else {
                        ok = false;
                    }
                } else if (state == 132) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 133) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=111)) || ((ch >= 113) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 112)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 134;
                    } else {
                        ok = false;
                    }
                } else if (state == 134) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 135;
                    } else {
                        ok = false;
                    }
                } else if (state == 135) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 136;
                    } else {
                        ok = false;
                    }
                } else if (state == 136) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = 33;
                        mark();
                        state = 137;
                    } else {
                        ok = false;
                    }
                } else if (state == 137) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 138) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 139) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 140) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 141;
                    } else {
                        ok = false;
                    }
                } else if (state == 141) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=108)) || ((ch >= 110) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 109)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 142;
                    } else {
                        ok = false;
                    }
                } else if (state == 142) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || (ch == 97) || ((ch >= 99) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 98)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 143;
                    } else {
                        ok = false;
                    }
                } else if (state == 143) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 100)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 144;
                    } else {
                        ok = false;
                    }
                } else if (state == 144) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = 27;
                        mark();
                        state = 145;
                    } else {
                        ok = false;
                    }
                } else if (state == 145) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 146) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 147;
                    } else {
                        ok = false;
                    }
                } else if (state == 147) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 148;
                    } else if ((ch == 116)) {
                        id = 28;
                        mark();
                        state = 154;
                    } else {
                        ok = false;
                    }
                } else if (state == 148) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 149;
                    } else {
                        ok = false;
                    }
                } else if (state == 149) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=110)) || ((ch >= 112) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 111)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 150;
                    } else {
                        ok = false;
                    }
                } else if (state == 150) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=98)) || ((ch >= 100) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 99)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 151;
                    } else {
                        ok = false;
                    }
                } else if (state == 151) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 152;
                    } else {
                        ok = false;
                    }
                } else if (state == 152) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = 9;
                        mark();
                        state = 153;
                    } else {
                        ok = false;
                    }
                } else if (state == 153) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 154) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 155) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = 30;
                        mark();
                        state = 156;
                    } else {
                        ok = false;
                    }
                } else if (state == 156) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 157) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 158;
                    } else {
                        ok = false;
                    }
                } else if (state == 158) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 159;
                    } else {
                        ok = false;
                    }
                } else if (state == 159) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = 2;
                        mark();
                        state = 160;
                    } else {
                        ok = false;
                    }
                } else if (state == 160) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 161) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 98) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 97)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 162;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 166;
                    } else {
                        ok = false;
                    }
                } else if (state == 162) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 163;
                    } else {
                        ok = false;
                    }
                } else if (state == 163) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=114)) || ((ch >= 116) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 115)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 164;
                    } else {
                        ok = false;
                    }
                } else if (state == 164) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 6;
                        mark();
                        state = 165;
                    } else {
                        ok = false;
                    }
                } else if (state == 165) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 166) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 167;
                    } else {
                        ok = false;
                    }
                } else if (state == 167) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=116)) || ((ch >= 118) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 117)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 168;
                    } else {
                        ok = false;
                    }
                } else if (state == 168) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 169;
                    } else {
                        ok = false;
                    }
                } else if (state == 169) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=109)) || ((ch >= 111) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 110)) {
                        id = 5;
                        mark();
                        state = 170;
                    } else {
                        ok = false;
                    }
                } else if (state == 170) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 171) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=113)) || ((ch >= 115) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 114)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 172;
                    } else {
                        ok = false;
                    }
                } else if (state == 172) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=120)) || (ch == 122)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 121)) {
                        id = 31;
                        mark();
                        state = 173;
                    } else {
                        ok = false;
                    }
                } else if (state == 173) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 174) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=103)) || ((ch >= 106) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 104)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 175;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 179;
                    } else {
                        ok = false;
                    }
                } else if (state == 175) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 176;
                    } else {
                        ok = false;
                    }
                } else if (state == 176) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 177;
                    } else {
                        ok = false;
                    }
                } else if (state == 177) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = 15;
                        mark();
                        state = 178;
                    } else {
                        ok = false;
                    }
                } else if (state == 178) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 179) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=115)) || ((ch >= 117) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 116)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 180;
                    } else {
                        ok = false;
                    }
                } else if (state == 180) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=103)) || ((ch >= 105) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 104)) {
                        id = 25;
                        mark();
                        state = 181;
                    } else {
                        ok = false;
                    }
                } else if (state == 181) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 182) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=104)) || ((ch >= 106) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 105)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 183;
                    } else {
                        ok = false;
                    }
                } else if (state == 183) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=100)) || ((ch >= 102) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 101)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 184;
                    } else {
                        ok = false;
                    }
                } else if (state == 184) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=107)) || ((ch >= 109) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 108)) {
                        id = TOKEN_NAME;
                        mark();
                        state = 185;
                    } else {
                        ok = false;
                    }
                } else if (state == 185) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=99)) || ((ch >= 101) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else if ((ch == 100)) {
                        id = 17;
                        mark();
                        state = 186;
                    } else {
                        ok = false;
                    }
                } else if (state == 186) {
                    if (((ch >= 48) && (ch <=57)) || ((ch >= 65) && (ch <=90)) || (ch == 95) || ((ch >= 97) && (ch <=122))) {
                        id = TOKEN_NAME;
                        mark();
                        state = 55;
                    } else {
                        ok = false;
                    }
                } else if (state == 187) {
                        ok = false;
                } else if (state == 188) {
                    if ((ch == 61)) {
                        id = 40;
                        mark();
                        state = 189;
                    } else {
                        ok = false;
                    }
                } else if (state == 189) {
                        ok = false;
                } else if (state == 190) {
                        ok = false;
                } else if (state == 191) {
                        ok = false;
                }
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
          return new TokenImpl(buf.toString(), TOKEN_STRING, startLine, startPos); 
      
        }
        return new TokenImpl(read.toString(), id, startLine, startPos);// 1
    }
}
