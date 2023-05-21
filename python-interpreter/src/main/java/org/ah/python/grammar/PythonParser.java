package org.ah.python.grammar;



import org.ah.python.grammar.PythonScanner.Token;

import org.ah.python.interpreter.*;
import org.ah.python.interpreter.Module;
import java.util.*;

public class PythonParser {


    private Block currentBlock;
    private OperatorType operatorType;
    private CmpopType cmpopType;
    private PythonObject currentObject;

    private List<String> stringList = new ArrayList<String>();
    private Import currentImport;
    private List<PythonObject> currentList;

    private Module module;
    private boolean trailingComma = false;
    
    public Module getModule() { return module; }

    public void setModule(Module module) { this.module = module; }



    public PythonParser() {
    }

    public PythonParser(org.ah.python.grammar.PythonScannerFixed s) {
        setScanner(s);
    }

    protected org.ah.python.grammar.PythonScannerFixed scanner;
    protected Token t = PythonScanner.BOF_TOKEN;
    protected Token nt = t;
    protected int id;

    protected String string;

    public void setScanner(org.ah.python.grammar.PythonScannerFixed s) {
        this.scanner = s;
    }

    public org.ah.python.grammar.PythonScannerFixed getScanner() {
        return scanner;
    }

    public void parse(org.ah.python.grammar.PythonScannerFixed s) {
        setScanner(s);
        // parse();
    }

    public boolean cmpToken(int id, int token) {
        return id == token;
    }

    public void next() {
        t = nt;
        nt = scanner.getNextToken();
        id = nt.getId();
    }

    public void parse() throws ParserError {
        next(); // <-- here
            single_input();
    } 

    // public single_input<null> = (.k=1.) NEWLINE|simple_stmt|compound_stmt NEWLINE {NEWLINE};
    public void single_input() throws ParserError {
        if ((id == PythonScanner.TOKEN_NEWLINE)) {
            if (id == PythonScanner.TOKEN_NEWLINE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NEWLINE");
            }
        } else if (((id >= 1) && (id <=10)) || (id == 17) || ((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            simple_stmt();
        } else if ((id == 11) || ((id >= 14) && (id <=16)) || (id == 18) || (id == 25) || (id == 31) || (id == PythonScanner.TOKEN_AT)) {
            compound_stmt();
            if (id == PythonScanner.TOKEN_NEWLINE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NEWLINE");
            }
            while ((id == PythonScanner.TOKEN_NEWLINE)) {
                if (id == PythonScanner.TOKEN_NEWLINE) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NEWLINE");
                }
            } // while 
        } else {
            throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','if','for','while','def','yield','class','None','True','False','with','lambda','not','try','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,AT,PLUS,MINUS,TILDA,NEWLINE,NAME,NUMBER,STRING");
        }
    } // single_input

    // public file_input<null> = {(.k=1.) NEWLINE|stmt};
    public void file_input() throws ParserError {
 module = new Module(); currentBlock = module.getBlock(); 
        while (((id >= 1) && (id <=11)) || ((id >= 14) && (id <=21)) || (id == 25) || ((id >= 27) && (id <=28)) || (id == 31) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_ELLIPSIS) && (id <=PythonScanner.TOKEN_AT)) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || (id == PythonScanner.TOKEN_NEWLINE) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            if ((id == PythonScanner.TOKEN_NEWLINE)) {
                if (id == PythonScanner.TOKEN_NEWLINE) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NEWLINE");
                }
            } else if (((id >= 1) && (id <=11)) || ((id >= 14) && (id <=21)) || (id == 25) || ((id >= 27) && (id <=28)) || (id == 31) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_ELLIPSIS) && (id <=PythonScanner.TOKEN_AT)) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                stmt();
            } else {
                throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','if','for','while','def','yield','class','None','True','False','with','lambda','not','try','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,AT,PLUS,MINUS,TILDA,NEWLINE,NAME,NUMBER,STRING");
            }
        } // while 
    } // file_input

    // public existing_module_file_input<null> = {(.k=1.) NEWLINE|stmt};
    public void existing_module_file_input() throws ParserError {
 currentBlock = module.getBlock(); 
        while (((id >= 1) && (id <=11)) || ((id >= 14) && (id <=21)) || (id == 25) || ((id >= 27) && (id <=28)) || (id == 31) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_ELLIPSIS) && (id <=PythonScanner.TOKEN_AT)) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || (id == PythonScanner.TOKEN_NEWLINE) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            if ((id == PythonScanner.TOKEN_NEWLINE)) {
                if (id == PythonScanner.TOKEN_NEWLINE) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NEWLINE");
                }
            } else if (((id >= 1) && (id <=11)) || ((id >= 14) && (id <=21)) || (id == 25) || ((id >= 27) && (id <=28)) || (id == 31) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_ELLIPSIS) && (id <=PythonScanner.TOKEN_AT)) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                stmt();
            } else {
                throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','if','for','while','def','yield','class','None','True','False','with','lambda','not','try','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,AT,PLUS,MINUS,TILDA,NEWLINE,NAME,NUMBER,STRING");
            }
        } // while 
    } // existing_module_file_input

    // public eval_input<null> = testlist {NEWLINE};
    public void eval_input() throws ParserError {
        testlist();
        while ((id == PythonScanner.TOKEN_NEWLINE)) {
            if (id == PythonScanner.TOKEN_NEWLINE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NEWLINE");
            }
        } // while 
    } // eval_input

    // public decorator<null> = AT dotted_name [LPAREN [arglist] RPAREN] NEWLINE CODE;
    public void decorator() throws ParserError {
        if (id == PythonScanner.TOKEN_AT) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "AT");
        }
        dotted_name();
        if ((id == PythonScanner.TOKEN_LPAREN)) {
            if (id == PythonScanner.TOKEN_LPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LPAREN");
            }
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || ((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                arglist();
            } 
            if (id == PythonScanner.TOKEN_RPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RPAREN");
            }
        } 
        if (id == PythonScanner.TOKEN_NEWLINE) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NEWLINE");
        }
         throw new UnsupportedOperationException("decorator"); 
    } // decorator

    // public decorators<null> = decorator {decorator} CODE;
    public void decorators() throws ParserError {
        decorator();
        while ((id == PythonScanner.TOKEN_AT)) {
            decorator();
        } // while 
         throw new UnsupportedOperationException("decorators"); 
    } // decorators

    // public decorated<null> = decorators (.k=1.) classdef|funcdef CODE;
    public void decorated() throws ParserError {
        decorators();
        if ((id == 18)) {
            classdef();
        } else if ((id == 16)) {
            funcdef();
        } else {
            throw new ParserError(t, "'def','class'");
        }
         throw new UnsupportedOperationException("decorated"); 
    } // decorated

    // public funcdef<null> = "def" NAME CODE parameters CODE [DASH_GT test CODE] COLON CODE suite CODE;
    public void funcdef() throws ParserError {
        if (id == 16) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"def\"");
        }
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
         String defName = t.toString(); 
        parameters();
        
                 for (PythonObject arg : currentList) {
                     if (!(arg instanceof Reference)) { 
                         throw new IllegalArgumentException("Expected a varibale for function def argument but got " + arg);
                     }
                 }
                 Def def = new Def(defName, currentList.toArray(new Reference[currentList.size()]));
              
        if ((id == PythonScanner.TOKEN_DASH_GT)) {
            if (id == PythonScanner.TOKEN_DASH_GT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"->\"");
            }
            test();
             throw new UnsupportedOperationException("->"); 
        } 
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
         
                  Block savedBlock = currentBlock; currentBlock = def.getBlock();
              
        suite();
         
                  currentBlock = savedBlock; currentBlock.getStatements().add(def);
              
    } // funcdef

    // public parameters<null> = LPAREN CODE [typedargslist] RPAREN;
    public void parameters() throws ParserError {
        if (id == PythonScanner.TOKEN_LPAREN) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "LPAREN");
        }
         currentList = new ArrayList<PythonObject>(); 
        if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_NAME)) {
            typedargslist();
        } 
        if (id == PythonScanner.TOKEN_RPAREN) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "RPAREN");
        }
    } // parameters

    // public typedargslist<null> = (.k=1.) tfpdef CODE [EQ test CODE] {COMMA tfpdef CODE [EQ test CODE]} [COMMA [(.k=1.) STAR [tfpdef] {COMMA tfpdef [EQ test]} [COMMA STARSTAR tfpdef]|STARSTAR tfpdef]] CODE|STAR [tfpdef] {COMMA tfpdef [EQ test]} [COMMA STARSTAR tfpdef] CODE|STARSTAR tfpdef CODE;
    public void typedargslist() throws ParserError {
        if ((id == PythonScanner.TOKEN_NAME)) {
            tfpdef();
             List<PythonObject> args = new ArrayList<PythonObject>(); args.add(currentObject); 
            if ((id == PythonScanner.TOKEN_EQ)) {
                if (id == PythonScanner.TOKEN_EQ) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "EQ");
                }
                test();
                 throw new UnsupportedOperationException("named parameters"); 
            } 
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                tfpdef();
                 args.add(currentObject); 
                if ((id == PythonScanner.TOKEN_EQ)) {
                    if (id == PythonScanner.TOKEN_EQ) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "EQ");
                    }
                    test();
                     throw new UnsupportedOperationException("named parameters"); 
                } 
            } // while 
            if ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR))) {
                    if ((id == PythonScanner.TOKEN_STAR)) {
                        if (id == PythonScanner.TOKEN_STAR) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "STAR");
                        }
                        if ((id == PythonScanner.TOKEN_NAME)) {
                            tfpdef();
                        } 
                        while ((id == PythonScanner.TOKEN_COMMA)) {
                            if (id == PythonScanner.TOKEN_COMMA) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "COMMA");
                            }
                            tfpdef();
                            if ((id == PythonScanner.TOKEN_EQ)) {
                                if (id == PythonScanner.TOKEN_EQ) {
                                    next(); // <-- here 2
                                } else {
                                    throw new ParserError(t, nt, "EQ");
                                }
                                test();
                            } 
                        } // while 
                        if ((id == PythonScanner.TOKEN_COMMA)) {
                            if (id == PythonScanner.TOKEN_COMMA) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "COMMA");
                            }
                            if (id == PythonScanner.TOKEN_STARSTAR) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "\"**\"");
                            }
                            tfpdef();
                        } 
                    } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
                        if (id == PythonScanner.TOKEN_STARSTAR) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "\"**\"");
                        }
                        tfpdef();
                    } else {
                        throw new ParserError(t, "STAR,STARSTAR");
                    }
                } 
            } 
             currentList = args; 
        } else if ((id == PythonScanner.TOKEN_STAR)) {
            if (id == PythonScanner.TOKEN_STAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STAR");
            }
            if ((id == PythonScanner.TOKEN_NAME)) {
                tfpdef();
            } 
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                tfpdef();
                if ((id == PythonScanner.TOKEN_EQ)) {
                    if (id == PythonScanner.TOKEN_EQ) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "EQ");
                    }
                    test();
                } 
            } // while 
            if ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                if (id == PythonScanner.TOKEN_STARSTAR) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"**\"");
                }
                tfpdef();
            } 
             throw new UnsupportedOperationException("star parameters"); 
        } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
            if (id == PythonScanner.TOKEN_STARSTAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"**\"");
            }
            tfpdef();
             throw new UnsupportedOperationException("starstar parameters"); 
        } else {
            throw new ParserError(t, "STAR,STARSTAR,NAME");
        }
    } // typedargslist

    // public tfpdef<null> = NAME CODE [COLON test CODE];
    public void tfpdef() throws ParserError {
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
         currentObject = new Reference(null, PythonString.valueOf(t.toString())); 
        if ((id == PythonScanner.TOKEN_COLON)) {
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
            test();
             throw new UnsupportedOperationException("parameter types"); 
        } 
    } // tfpdef

    // public varargslist<null> = (.k=1.) vfpdef [EQ test] {COMMA vfpdef [EQ test]} [COMMA [(.k=1.) STAR [vfpdef] {COMMA vfpdef [EQ test]} [COMMA STARSTAR vfpdef]|STARSTAR vfpdef]]|STAR [vfpdef] {COMMA vfpdef [EQ test]} [COMMA STARSTAR vfpdef]|STARSTAR vfpdef;
    public void varargslist() throws ParserError {
        if ((id == PythonScanner.TOKEN_NAME)) {
            vfpdef();
            if ((id == PythonScanner.TOKEN_EQ)) {
                if (id == PythonScanner.TOKEN_EQ) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "EQ");
                }
                test();
            } 
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                vfpdef();
                if ((id == PythonScanner.TOKEN_EQ)) {
                    if (id == PythonScanner.TOKEN_EQ) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "EQ");
                    }
                    test();
                } 
            } // while 
            if ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR))) {
                    if ((id == PythonScanner.TOKEN_STAR)) {
                        if (id == PythonScanner.TOKEN_STAR) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "STAR");
                        }
                        if ((id == PythonScanner.TOKEN_NAME)) {
                            vfpdef();
                        } 
                        while ((id == PythonScanner.TOKEN_COMMA)) {
                            if (id == PythonScanner.TOKEN_COMMA) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "COMMA");
                            }
                            vfpdef();
                            if ((id == PythonScanner.TOKEN_EQ)) {
                                if (id == PythonScanner.TOKEN_EQ) {
                                    next(); // <-- here 2
                                } else {
                                    throw new ParserError(t, nt, "EQ");
                                }
                                test();
                            } 
                        } // while 
                        if ((id == PythonScanner.TOKEN_COMMA)) {
                            if (id == PythonScanner.TOKEN_COMMA) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "COMMA");
                            }
                            if (id == PythonScanner.TOKEN_STARSTAR) {
                                next(); // <-- here 2
                            } else {
                                throw new ParserError(t, nt, "\"**\"");
                            }
                            vfpdef();
                        } 
                    } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
                        if (id == PythonScanner.TOKEN_STARSTAR) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "\"**\"");
                        }
                        vfpdef();
                    } else {
                        throw new ParserError(t, "STAR,STARSTAR");
                    }
                } 
            } 
        } else if ((id == PythonScanner.TOKEN_STAR)) {
            if (id == PythonScanner.TOKEN_STAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STAR");
            }
            if ((id == PythonScanner.TOKEN_NAME)) {
                vfpdef();
            } 
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                vfpdef();
                if ((id == PythonScanner.TOKEN_EQ)) {
                    if (id == PythonScanner.TOKEN_EQ) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "EQ");
                    }
                    test();
                } 
            } // while 
            if ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                if (id == PythonScanner.TOKEN_STARSTAR) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"**\"");
                }
                vfpdef();
            } 
        } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
            if (id == PythonScanner.TOKEN_STARSTAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"**\"");
            }
            vfpdef();
        } else {
            throw new ParserError(t, "STAR,STARSTAR,NAME");
        }
    } // varargslist

    // public vfpdef<null> = NAME;
    public void vfpdef() throws ParserError {
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
    } // vfpdef

    // public stmt<null> = (.k=1.) simple_stmt|compound_stmt;
    public void stmt() throws ParserError {
        if (((id >= 1) && (id <=10)) || (id == 17) || ((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            simple_stmt();
        } else if ((id == 11) || ((id >= 14) && (id <=16)) || (id == 18) || (id == 25) || (id == 31) || (id == PythonScanner.TOKEN_AT)) {
            compound_stmt();
        } else {
            throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','if','for','while','def','yield','class','None','True','False','with','lambda','not','try','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,AT,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // stmt

    // public simple_stmt<null> = small_stmt {SEMICOLON small_stmt} [SEMICOLON] NEWLINE {NEWLINE};
    public void simple_stmt() throws ParserError {
        small_stmt();
        while ((id == PythonScanner.TOKEN_SEMICOLON)) {
            if (id == PythonScanner.TOKEN_SEMICOLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "SEMICOLON");
            }
            small_stmt();
        } // while 
        if ((id == PythonScanner.TOKEN_SEMICOLON)) {
            if (id == PythonScanner.TOKEN_SEMICOLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "SEMICOLON");
            }
        } 
        if (id == PythonScanner.TOKEN_NEWLINE) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NEWLINE");
        }
        while ((id == PythonScanner.TOKEN_NEWLINE)) {
            if (id == PythonScanner.TOKEN_NEWLINE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NEWLINE");
            }
        } // while 
    } // simple_stmt

    // public small_stmt<null> = (.k=1.) expr_stmt|del_stmt|pass_stmt CODE|flow_stmt|import_stmt|global_stmt|nonlocal_stmt CODE|assert_stmt CODE;
    public void small_stmt() throws ParserError {
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            expr_stmt();
        } else if ((id == 1)) {
            del_stmt();
        } else if ((id == 2)) {
            pass_stmt();
             throw new UnsupportedOperationException("pass_stmt"); 
        } else if (((id >= 3) && (id <=6)) || (id == 17)) {
            flow_stmt();
        } else if ((id == 7) || (id == 33)) {
            import_stmt();
        } else if ((id == 8)) {
            global_stmt();
        } else if ((id == 9)) {
            nonlocal_stmt();
             throw new UnsupportedOperationException("nonlocal_stmt"); 
        } else if ((id == 10)) {
            assert_stmt();
             throw new UnsupportedOperationException("assert_stmt"); 
        } else {
            throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','yield','None','True','False','lambda','not','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // small_stmt

    // public expr_stmt<null> = testlist_star_expr CODE (.k=1.) augassign (.k=1.) yield_expr CODE|testlist CODE|CODE {EQ (.k=1.) yield_expr CODE|testlist_star_expr CODE} CODE;
    public void expr_stmt() throws ParserError {
        testlist_star_expr();
         List<PythonObject> targets = currentList; 
        if (((id >= 34) && (id <=45))) {
            augassign();
            if ((id == 17)) {
                yield_expr();
                 throw new UnsupportedOperationException("yield_expr"); 
            } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                testlist();
                
                            if (currentList.size() != targets.size()) {
                                throw new UnsupportedOperationException("Target assignment list size " + targets.size() + " does not match values size " + currentList.size());
                            }
                            
                            for (int i = 0; i < targets.size(); i++) {
                                PythonObject target = targets.get(i);
                                // PythonObject value = currentList.get(i);
                                // BinaryOp binaryOp = new BinaryOp(target, value, operatorType);
                                
                                if (!(target instanceof Assignable)) {
                                    throw new UnsupportedOperationException("Illegal left side of assignment; " + target);
                                }
                                
                                // TODO Add this!!!
                                // PythonObject assign = Assign.createAssignment(target, binaryOp, true);
                                // currentSuite.asList().add(assign);
                            }
                         
            } else {
                throw new ParserError(t, "'yield','None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
            }
        } else if ((id == PythonScanner.TOKEN_EQ) || (id == PythonScanner.TOKEN_SEMICOLON) || (id == PythonScanner.TOKEN_NEWLINE)) {
            
                         if (targets.size() > 1) {
                             PythonObject tuple = new PythonListGenerator(targets, PythonTuple.PYTHON_TUPLE_CLASS);
                             targets.clear();
                             targets.add(tuple);
                         }
                    
            while ((id == PythonScanner.TOKEN_EQ)) {
                if (id == PythonScanner.TOKEN_EQ) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "EQ");
                }
                if ((id == 17)) {
                    yield_expr();
                     throw new UnsupportedOperationException("yield_expr"); 
                } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    testlist_star_expr();
                    
                            if (currentList.size() == 1) {
                                targets.add(currentList.get(0));
                            } else {
                                PythonObject tuple = new PythonListGenerator(currentList, PythonTuple.PYTHON_TUPLE_CLASS);
                                targets.add(tuple);
                            }
                            currentList.clear();
                        
                } else {
                    throw new ParserError(t, "'yield','None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
                }
            } // while 
            
                        while (targets.size() > 1) {
                            PythonObject assign = Assign.createAssignment(targets.get(targets.size() - 2), targets.get(targets.size() - 1), false);
                            targets.remove(targets.size() - 1);
                            targets.remove(targets.size() - 1);
                            targets.add(assign);
                        }
                        PythonObject lastTarget = targets.get(0);
                        if (lastTarget instanceof Assign) {
	                        ((Assign)lastTarget).setLastInstruction(true);
	                    }
                        currentBlock.getStatements().add(lastTarget);
                    
        }
    } // expr_stmt

    // public testlist_star_expr<null> = (.k=1.) test|star_expr CODE CODE {COMMA [(.k=1.) test CODE|star_expr CODE]} CODE;
    public void testlist_star_expr() throws ParserError {
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
        } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            star_expr();
             throw new UnsupportedOperationException("star_expr"); 
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
         List<PythonObject> args = new ArrayList<PythonObject>(); args.add(currentObject); 
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    test();
                     args.add(currentObject); 
                } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    star_expr();
                     throw new UnsupportedOperationException("star_expr"); 
                } else {
                    throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
                }
            } 
        } // while 
         currentList = args; 
    } // testlist_star_expr

    // public augassign<null> = (.k=1.) "+=" CODE|"-=" CODE|"*=" CODE|"/=" CODE|"%=" CODE|"&=" CODE|"|=" CODE|"^=" CODE|"<<=" CODE|">>=" CODE|"**=" CODE|"//=" CODE;
    public void augassign() throws ParserError {
        if ((id == 34)) {
            if (id == 34) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"+=\"");
            }
             operatorType = OperatorType.Add; 
        } else if ((id == 35)) {
            if (id == 35) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"-=\"");
            }
             operatorType = OperatorType.Sub; 
        } else if ((id == 36)) {
            if (id == 36) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"*=\"");
            }
             operatorType = OperatorType.Mult; 
        } else if ((id == 37)) {
            if (id == 37) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"/=\"");
            }
             operatorType = OperatorType.Div; 
        } else if ((id == 38)) {
            if (id == 38) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"%=\"");
            }
             operatorType = OperatorType.Mod; 
        } else if ((id == 39)) {
            if (id == 39) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"&=\"");
            }
             operatorType = OperatorType.BitAnd; 
        } else if ((id == 40)) {
            if (id == 40) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"|=\"");
            }
             operatorType = OperatorType.BitOr; 
        } else if ((id == 41)) {
            if (id == 41) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"^=\"");
            }
             operatorType = OperatorType.BitXor; 
        } else if ((id == 42)) {
            if (id == 42) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"<<=\"");
            }
             operatorType = OperatorType.LShift; 
        } else if ((id == 43)) {
            if (id == 43) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\">>=\"");
            }
             operatorType = OperatorType.RShift; 
        } else if ((id == 44)) {
            if (id == 44) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"**=\"");
            }
             operatorType = OperatorType.Pow; 
        } else if ((id == 45)) {
            if (id == 45) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"//=\"");
            }
             operatorType = OperatorType.FloorDiv; 
        } else {
            throw new ParserError(t, "'+=','-=','*=','/=','%=','&=','|=','^=','<<=','>>=','**=','//='");
        }
    } // augassign

    // public del_stmt<null> = "del" exprlist CODE;
    public void del_stmt() throws ParserError {
        if (id == 1) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"del\"");
        }
        exprlist();
        
                    currentObject = new Del(currentList);
                    currentBlock.getStatements().add(currentObject);
                
    } // del_stmt

    // public pass_stmt<null> = "pass";
    public void pass_stmt() throws ParserError {
        if (id == 2) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"pass\"");
        }
    } // pass_stmt

    // public flow_stmt<null> = (.k=1.) break_stmt|continue_stmt|return_stmt|raise_stmt|yield_stmt;
    public void flow_stmt() throws ParserError {
        if ((id == 4)) {
            break_stmt();
        } else if ((id == 3)) {
            continue_stmt();
        } else if ((id == 5)) {
            return_stmt();
        } else if ((id == 6)) {
            raise_stmt();
        } else if ((id == 17)) {
            yield_stmt();
        } else {
            throw new ParserError(t, "'continue','break','return','raise','yield'");
        }
    } // flow_stmt

    // public break_stmt<null> = "break" CODE;
    public void break_stmt() throws ParserError {
        if (id == 4) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"break\"");
        }
         currentBlock.getStatements().add(new Break()); 
    } // break_stmt

    // public continue_stmt<null> = "continue" CODE;
    public void continue_stmt() throws ParserError {
        if (id == 3) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"continue\"");
        }
         currentBlock.getStatements().add(new Continue()); 
    } // continue_stmt

    // public return_stmt<null> = "return" CODE [testlist] CODE;
    public void return_stmt() throws ParserError {
        if (id == 5) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"return\"");
        }
         currentObject = PythonNone.NONE; 
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            testlist();
        } 
         currentBlock.getStatements().add(new Return(currentObject)); 
    } // return_stmt

    // public yield_stmt<null> = yield_expr CODE;
    public void yield_stmt() throws ParserError {
        yield_expr();
         throw new UnsupportedOperationException("yield_stmt"); 
    } // yield_stmt

    // public raise_stmt<null> = "raise" [test ["from" test]] CODE;
    public void raise_stmt() throws ParserError {
        if (id == 6) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"raise\"");
        }
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
            if ((id == 7)) {
                if (id == 7) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"from\"");
                }
                test();
            } 
        } 
         throw new UnsupportedOperationException("raise_stmt"); 
    } // raise_stmt

    // public import_stmt<null> = (.k=1.) import_name|import_from CODE CODE;
    public void import_stmt() throws ParserError {
 currentImport = new Import(); 
        if ((id == 33)) {
            import_name();
        } else if ((id == 7)) {
            import_from();
             throw new UnsupportedOperationException("import_from"); 
        } else {
            throw new ParserError(t, "'from','import'");
        }
         currentBlock.getStatements().add(currentImport); 
    } // import_stmt

    // public import_name<null> = "import" dotted_as_names;
    public void import_name() throws ParserError {
        if (id == 33) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"import\"");
        }
        dotted_as_names();
    } // import_name

    // public import_from<null> = "from" (.k=1.) {(.k=1.) DOT|ELLIPSIS} dotted_name|(.k=1.) DOT|ELLIPSIS {(.k=1.) DOT|ELLIPSIS} "import" (.k=1.) STAR|LPAREN import_as_names RPAREN|import_as_names;
    public void import_from() throws ParserError {
        if (id == 7) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"from\"");
        }
        if (((id >= PythonScanner.TOKEN_DOT) && (id <=PythonScanner.TOKEN_ELLIPSIS)) || (id == PythonScanner.TOKEN_NAME)) {
            while (((id >= PythonScanner.TOKEN_DOT) && (id <=PythonScanner.TOKEN_ELLIPSIS))) {
                if ((id == PythonScanner.TOKEN_DOT)) {
                    if (id == PythonScanner.TOKEN_DOT) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "DOT");
                    }
                } else if ((id == PythonScanner.TOKEN_ELLIPSIS)) {
                    if (id == PythonScanner.TOKEN_ELLIPSIS) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "\"...\"");
                    }
                } else {
                    throw new ParserError(t, "DOT,ELLIPSIS");
                }
            } // while 
            dotted_name();
        } else if (((id >= PythonScanner.TOKEN_DOT) && (id <=PythonScanner.TOKEN_ELLIPSIS))) {
            if ((id == PythonScanner.TOKEN_DOT)) {
                if (id == PythonScanner.TOKEN_DOT) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "DOT");
                }
            } else if ((id == PythonScanner.TOKEN_ELLIPSIS)) {
                if (id == PythonScanner.TOKEN_ELLIPSIS) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"...\"");
                }
            } else {
                throw new ParserError(t, "DOT,ELLIPSIS");
            }
            while (((id >= PythonScanner.TOKEN_DOT) && (id <=PythonScanner.TOKEN_ELLIPSIS))) {
                if ((id == PythonScanner.TOKEN_DOT)) {
                    if (id == PythonScanner.TOKEN_DOT) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "DOT");
                    }
                } else if ((id == PythonScanner.TOKEN_ELLIPSIS)) {
                    if (id == PythonScanner.TOKEN_ELLIPSIS) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "\"...\"");
                    }
                } else {
                    throw new ParserError(t, "DOT,ELLIPSIS");
                }
            } // while 
        } else {
            throw new ParserError(t, "DOT,ELLIPSIS,NAME");
        }
        if (id == 33) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"import\"");
        }
        if ((id == PythonScanner.TOKEN_STAR)) {
            if (id == PythonScanner.TOKEN_STAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STAR");
            }
        } else if ((id == PythonScanner.TOKEN_LPAREN)) {
            if (id == PythonScanner.TOKEN_LPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LPAREN");
            }
            import_as_names();
            if (id == PythonScanner.TOKEN_RPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RPAREN");
            }
        } else if ((id == PythonScanner.TOKEN_NAME)) {
            import_as_names();
        } else {
            throw new ParserError(t, "LPAREN,STAR,NAME");
        }
    } // import_from

    // public import_as_name<null> = NAME ["as" NAME];
    public void import_as_name() throws ParserError {
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
        if ((id == 24)) {
            if (id == 24) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"as\"");
            }
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
        } 
    } // import_as_name

    // public dotted_as_name<null> = dotted_name CODE ["as" NAME CODE] CODE;
    public void dotted_as_name() throws ParserError {
        dotted_name();
         String as = null; 
        if ((id == 24)) {
            if (id == 24) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"as\"");
            }
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
             as = t.toString(); 
        } 
         currentImport.addImport(as, stringList); 
    } // dotted_as_name

    // public import_as_names<null> = import_as_name {COMMA import_as_name};
    public void import_as_names() throws ParserError {
        import_as_name();
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            import_as_name();
        } // while 
    } // import_as_names

    // public dotted_as_names<null> = dotted_as_name {COMMA dotted_as_name};
    public void dotted_as_names() throws ParserError {
        dotted_as_name();
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            dotted_as_name();
        } // while 
    } // dotted_as_names

    // public dotted_name<null> = NAME CODE {DOT NAME CODE};
    public void dotted_name() throws ParserError {
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
         stringList = new ArrayList<String>(); stringList.add(t.toString()); 
        while ((id == PythonScanner.TOKEN_DOT)) {
            if (id == PythonScanner.TOKEN_DOT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "DOT");
            }
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
             stringList.add(t.toString()); 
        } // while 
    } // dotted_name

    // public global_stmt<null> = "global" NAME CODE {COMMA NAME CODE} CODE;
    public void global_stmt() throws ParserError {
        if (id == 8) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"global\"");
        }
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
         stringList = new ArrayList<String>(); stringList.add(t.toString()); 
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
             stringList.add(t.toString()); 
        } // while 
         currentBlock.getStatements().add(new Global(stringList)); 
    } // global_stmt

    // public nonlocal_stmt<null> = "nonlocal" NAME {COMMA NAME};
    public void nonlocal_stmt() throws ParserError {
        if (id == 9) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"nonlocal\"");
        }
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
        } // while 
    } // nonlocal_stmt

    // public assert_stmt<null> = "assert" test [COMMA test];
    public void assert_stmt() throws ParserError {
        if (id == 10) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"assert\"");
        }
        test();
        if ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            test();
        } 
    } // assert_stmt

    // public compound_stmt<null> = (.k=1.) if_stmt|while_stmt|for_stmt|try_stmt CODE|with_stmt CODE|funcdef|classdef|decorated CODE;
    public void compound_stmt() throws ParserError {
        if ((id == 11)) {
            if_stmt();
        } else if ((id == 15)) {
            while_stmt();
        } else if ((id == 14)) {
            for_stmt();
        } else if ((id == 31)) {
            try_stmt();
             throw new UnsupportedOperationException("try_stmt"); 
        } else if ((id == 25)) {
            with_stmt();
             throw new UnsupportedOperationException("with_stmt"); 
        } else if ((id == 16)) {
            funcdef();
        } else if ((id == 18)) {
            classdef();
        } else if ((id == PythonScanner.TOKEN_AT)) {
            decorated();
             throw new UnsupportedOperationException("decorated"); 
        } else {
            throw new ParserError(t, "'if','for','while','def','class','with','try',AT");
        }
    } // compound_stmt

    // public if_stmt<null> = "if" test COLON CODE suite {"elif" test COLON CODE suite} ["else" COLON CODE suite] CODE;
    public void if_stmt() throws ParserError {
        if (id == 11) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"if\"");
        }
        test();
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        
                      If iff = new If(currentObject);
                      If originalIf = iff;
                      Block savedBlock = currentBlock; currentBlock = iff.getBlock();
                   
        suite();
        while ((id == 12)) {
            if (id == 12) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"elif\"");
            }
            test();
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
             
                      If elif = new If(currentObject);
                      iff.getElseBlock().getStatements().add(elif);
                      iff = elif;
                      currentBlock = elif.getBlock();
                   
            suite();
        } // while 
        if ((id == 13)) {
            if (id == 13) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"else\"");
            }
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
             currentBlock = iff.getElseBlock(); 
            suite();
        } 
        
                       currentBlock = savedBlock;
                       currentBlock.getStatements().add(originalIf);
                   
    } // if_stmt

    // public while_stmt<null> = "while" test COLON CODE suite ["else" COLON CODE suite] CODE;
    public void while_stmt() throws ParserError {
        if (id == 15) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"while\"");
        }
        test();
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        
                      While whle = new While(currentObject);
                      Block savedBlock = currentBlock; currentBlock = whle.getBlock();
                   
        suite();
        if ((id == 13)) {
            if (id == 13) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"else\"");
            }
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
             currentBlock = whle.getElseBlock(); 
            suite();
        } 
         currentBlock = savedBlock; currentBlock.getStatements().add(whle); 
    } // while_stmt

    // public for_stmt<null> = "for" exprlist CODE "in" testlist COLON CODE suite ["else" COLON CODE suite] CODE;
    public void for_stmt() throws ParserError {
        if (id == 14) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"for\"");
        }
        exprlist();
         PythonObject target = currentObject; 
        if (id == 22) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"in\"");
        }
        testlist();
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        
                      if (!(target instanceof Reference)) {
                          throw new UnsupportedOperationException("Illegal left side of assignment; " + target);
                      }
                      For fr = new For((Reference)target, currentObject);
                      Block savedBlock = currentBlock; currentBlock = fr.getBlock();
                   
        suite();
        if ((id == 13)) {
            if (id == 13) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"else\"");
            }
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
             currentBlock = fr.getElseBlock(); 
            suite();
        } 
         currentBlock = savedBlock; currentBlock.getStatements().add(fr); 
    } // for_stmt

    // public try_stmt<null> = "try" COLON suite (.k=1.) except_clause COLON suite {except_clause COLON suite} ["else" COLON suite] ["finally" COLON suite]|"finally" COLON suite;
    public void try_stmt() throws ParserError {
        if (id == 31) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"try\"");
        }
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        suite();
        if ((id == 26)) {
            except_clause();
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
            suite();
            while ((id == 26)) {
                except_clause();
                if (id == PythonScanner.TOKEN_COLON) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COLON");
                }
                suite();
            } // while 
            if ((id == 13)) {
                if (id == 13) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"else\"");
                }
                if (id == PythonScanner.TOKEN_COLON) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COLON");
                }
                suite();
            } 
            if ((id == 32)) {
                if (id == 32) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"finally\"");
                }
                if (id == PythonScanner.TOKEN_COLON) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COLON");
                }
                suite();
            } 
        } else if ((id == 32)) {
            if (id == 32) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"finally\"");
            }
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
            suite();
        } else {
            throw new ParserError(t, "'except','finally'");
        }
    } // try_stmt

    // public with_stmt<null> = "with" with_item {COMMA with_item} COLON suite;
    public void with_stmt() throws ParserError {
        if (id == 25) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"with\"");
        }
        with_item();
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            with_item();
        } // while 
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        suite();
    } // with_stmt

    // public with_item<null> = test ["as" expr];
    public void with_item() throws ParserError {
        test();
        if ((id == 24)) {
            if (id == 24) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"as\"");
            }
            expr();
        } 
    } // with_item

    // public except_clause<null> = "except" [test ["as" NAME]];
    public void except_clause() throws ParserError {
        if (id == 26) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"except\"");
        }
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
            if ((id == 24)) {
                if (id == 24) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"as\"");
                }
                if (id == PythonScanner.TOKEN_NAME) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NAME");
                }
            } 
        } 
    } // except_clause

    // public suite<null> = (.k=1.) simple_stmt|NEWLINE {NEWLINE} INDENT stmt {stmt} DEDENT;
    public void suite() throws ParserError {
        if (((id >= 1) && (id <=10)) || (id == 17) || ((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            simple_stmt();
        } else if ((id == PythonScanner.TOKEN_NEWLINE)) {
            if (id == PythonScanner.TOKEN_NEWLINE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NEWLINE");
            }
            while ((id == PythonScanner.TOKEN_NEWLINE)) {
                if (id == PythonScanner.TOKEN_NEWLINE) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NEWLINE");
                }
            } // while 
            if (id == PythonScanner.TOKEN_INDENT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"!!!\"");
            }
            stmt();
            while (((id >= 1) && (id <=11)) || ((id >= 14) && (id <=21)) || (id == 25) || ((id >= 27) && (id <=28)) || (id == 31) || (id == 33) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_ELLIPSIS) && (id <=PythonScanner.TOKEN_AT)) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                stmt();
            } // while 
            if (id == PythonScanner.TOKEN_DEDENT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"@@@\"");
            }
        } else {
            throw new ParserError(t, "'del','pass','continue','break','return','raise','from','global','nonlocal','assert','yield','None','True','False','lambda','not','import',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NEWLINE,NAME,NUMBER,STRING");
        }
    } // suite

    // public test<null> = (.k=1.) or_test ["if" CODE or_test CODE "else" test CODE]|lambdef CODE;
    public void test() throws ParserError {
        if (((id >= 19) && (id <=21)) || (id == 28) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            or_test();
            if ((id == 11)) {
                if (id == 11) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"if\"");
                }
                 PythonObject ifExpression = currentObject; 
                or_test();
                 PythonObject condition = currentObject; 
                if (id == 13) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"else\"");
                }
                test();
                 currentObject = new TernaryOperator(condition, ifExpression, currentObject); 
            } 
        } else if ((id == 27)) {
            lambdef();
             throw new UnsupportedOperationException("lambdef"); 
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // test

    // public test_nocond<null> = (.k=1.) or_test|lambdef_nocond;
    public void test_nocond() throws ParserError {
        if (((id >= 19) && (id <=21)) || (id == 28) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            or_test();
        } else if ((id == 27)) {
            lambdef_nocond();
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // test_nocond

    // public lambdef<null> = "lambda" [varargslist] COLON test;
    public void lambdef() throws ParserError {
        if (id == 27) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"lambda\"");
        }
        if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_NAME)) {
            varargslist();
        } 
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        test();
    } // lambdef

    // public lambdef_nocond<null> = "lambda" [varargslist] COLON test_nocond;
    public void lambdef_nocond() throws ParserError {
        if (id == 27) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"lambda\"");
        }
        if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_NAME)) {
            varargslist();
        } 
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        test_nocond();
    } // lambdef_nocond

    // public or_test<null> = and_test CODE {"or" and_test CODE} CODE;
    public void or_test() throws ParserError {
        and_test();
         List<PythonObject> list = new ArrayList<PythonObject>(); list.add(currentObject); 
        while ((id == 30)) {
            if (id == 30) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"or\"");
            }
            and_test();
             list.add(currentObject); 
        } // while 
         if (list.size() > 1) { currentObject = new BoolOp(list, BoolopType.Or); } 
    } // or_test

    // public and_test<null> = not_test CODE {"and" not_test CODE} CODE;
    public void and_test() throws ParserError {
        not_test();
         List<PythonObject> list = new ArrayList<PythonObject>(); list.add(currentObject); 
        while ((id == 29)) {
            if (id == 29) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"and\"");
            }
            not_test();
             list.add(currentObject); 
        } // while 
         if (list.size() > 1) { currentObject = new BoolOp(list, BoolopType.And); } 
    } // and_test

    // public not_test<null> = (.k=1.) "not" not_test CODE|comparison;
    public void not_test() throws ParserError {
        if ((id == 28)) {
            if (id == 28) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"not\"");
            }
            not_test();
             currentObject = new UnaryOp(currentObject, UnaryopType.Not); 
        } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            comparison();
        } else {
            throw new ParserError(t, "'None','True','False','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // not_test

    // public comparison<null> = star_expr CODE {comp_op CODE star_expr CODE} CODE;
    public void comparison() throws ParserError {
        star_expr();
        
                       PythonObject left = currentObject;
                       List<CmpopType> ops = new ArrayList<CmpopType>();
                       List<PythonObject> operands = new ArrayList<PythonObject>();
                   
        while (((id >= 22) && (id <=23)) || (id == 28) || ((id >= PythonScanner.TOKEN_EQUAL) && (id <=PythonScanner.TOKEN_LT))) {
            comp_op();
             ops.add(cmpopType); 
            star_expr();
             operands.add(currentObject); 
        } // while 
        
                     if (ops.size() > 1) {
                     } else if (ops.size() == 1) {
                         CmpopType cmpop = ops.get(0);
                         if (cmpop == CmpopType.Lt) {
                             currentObject = new Call(new Reference(left, "__lt__"), currentObject);
                         } else if (cmpop == CmpopType.Gt) {
                             currentObject = new Call(new Reference(left, "__gt__"), currentObject);
                         } else if (cmpop == CmpopType.Eq) {
                             currentObject = new Call(new Reference(left, "__eq__"), currentObject);
                         } else if (cmpop == CmpopType.GtE) {
                             currentObject = new Call(new Reference(left, "__ge__"), currentObject);
                         } else if (cmpop == CmpopType.LtE) {
                             currentObject = new Call(new Reference(left, "__le__"), currentObject);
                         } else if (cmpop == CmpopType.NotEq) {
                             currentObject = new Call(new Reference(left, "__ne__"), currentObject);
                         } else if (cmpop == CmpopType.In) {
                             currentObject = new Call(new Reference(left, "__contains__"), currentObject);
                         } else if (cmpop == CmpopType.NotIn) {
                             currentObject = new Call(
                                 new Reference(
                                     new Call(new Reference(left, "__contains__"), currentObject),
                                     "__not__"
                                 )
                             );
                         } else if (cmpop == CmpopType.Is) {
                             // TODO
                         } else if (cmpop == CmpopType.IsNot) {
                             // TODO
                         }
                     }
                   
    } // comparison

    // public comp_op<null> = (.k=1.) LT CODE|GT CODE|EQUAL CODE|GE CODE|LE CODE|NOT_EQUAL2 CODE|NOT_EQUAL CODE|"in" CODE|"not" CODE|"is" CODE|"is" "not" CODE;
    public void comp_op() throws ParserError {
        if ((id == PythonScanner.TOKEN_LT)) {
            if (id == PythonScanner.TOKEN_LT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LT");
            }
             cmpopType = CmpopType.Lt; 
        } else if ((id == PythonScanner.TOKEN_GT)) {
            if (id == PythonScanner.TOKEN_GT) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "GT");
            }
             cmpopType = CmpopType.Gt; 
        } else if ((id == PythonScanner.TOKEN_EQUAL)) {
            if (id == PythonScanner.TOKEN_EQUAL) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"==\"");
            }
             cmpopType = CmpopType.Eq; 
        } else if ((id == PythonScanner.TOKEN_GE)) {
            if (id == PythonScanner.TOKEN_GE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\">=\"");
            }
             cmpopType = CmpopType.GtE; 
        } else if ((id == PythonScanner.TOKEN_LE)) {
            if (id == PythonScanner.TOKEN_LE) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"<=\"");
            }
             cmpopType = CmpopType.LtE; 
        } else if ((id == PythonScanner.TOKEN_NOT_EQUAL2)) {
            if (id == PythonScanner.TOKEN_NOT_EQUAL2) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"<>\"");
            }
             cmpopType = CmpopType.NotEq; 
        } else if ((id == PythonScanner.TOKEN_NOT_EQUAL)) {
            if (id == PythonScanner.TOKEN_NOT_EQUAL) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"!=\"");
            }
             cmpopType = CmpopType.NotEq; 
        } else if ((id == 22)) {
            if (id == 22) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"in\"");
            }
             cmpopType = CmpopType.In; 
        } else if ((id == 28)) {
            if (id == 28) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"not\"");
            }
             cmpopType = CmpopType.NotIn; 
        } else if ((id == 23)) {
            if (id == 23) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"is\"");
            }
             cmpopType = CmpopType.Is; 
        } else if ((id == 23)) {
            if (id == 23) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"is\"");
            }
            if (id == 28) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"not\"");
            }
             cmpopType = CmpopType.IsNot; 
        } else {
            throw new ParserError(t, "'in','is','not',EQUAL,NOT_EQUAL,NOT_EQUAL2,GE,GT,LE,LT");
        }
    } // comp_op

    // public star_expr<null> = [STAR CODE] expr;
    public void star_expr() throws ParserError {
        if ((id == PythonScanner.TOKEN_STAR)) {
            if (id == PythonScanner.TOKEN_STAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STAR");
            }
             throw new UnsupportedOperationException("star_expr"); 
        } 
        expr();
    } // star_expr

    // public expr<null> = xor_expr CODE {OR xor_expr CODE};
    public void expr() throws ParserError {
        xor_expr();
         PythonObject left = currentObject; 
        while ((id == PythonScanner.TOKEN_OR)) {
            if (id == PythonScanner.TOKEN_OR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "OR");
            }
            xor_expr();
             currentObject = new Call(new Reference(left, "__or__"), currentObject); 
        } // while 
    } // expr

    // public xor_expr<null> = and_expr CODE {XOR and_expr CODE};
    public void xor_expr() throws ParserError {
        and_expr();
         PythonObject left = currentObject; 
        while ((id == PythonScanner.TOKEN_XOR)) {
            if (id == PythonScanner.TOKEN_XOR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "XOR");
            }
            and_expr();
             currentObject = new Call(new Reference(left, "__xor__"), currentObject); 
        } // while 
    } // xor_expr

    // public and_expr<null> = shift_expr CODE {AND shift_expr CODE};
    public void and_expr() throws ParserError {
        shift_expr();
         PythonObject left = currentObject; 
        while ((id == PythonScanner.TOKEN_AND)) {
            if (id == PythonScanner.TOKEN_AND) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "AND");
            }
            shift_expr();
             currentObject = new Call(new Reference(left, "__and__"), currentObject); 
        } // while 
    } // and_expr

    // public shift_expr<null> = arith_expr CODE {CODE (.k=1.) LSHIFT CODE|RSHIFT CODE arith_expr CODE};
    public void shift_expr() throws ParserError {
        arith_expr();
         PythonObject left = currentObject; 
        while (((id >= PythonScanner.TOKEN_LSHIFT) && (id <=PythonScanner.TOKEN_RSHIFT))) {
             OperatorType op; 
            if ((id == PythonScanner.TOKEN_LSHIFT)) {
                if (id == PythonScanner.TOKEN_LSHIFT) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"<<\"");
                }
                 op = OperatorType.LShift; 
            } else if ((id == PythonScanner.TOKEN_RSHIFT)) {
                if (id == PythonScanner.TOKEN_RSHIFT) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\">>\"");
                }
                 op = OperatorType.RShift; 
            } else {
                throw new ParserError(t, "LSHIFT,RSHIFT");
            }
            arith_expr();
            
                            if (op == OperatorType.LShift) {
                                currentObject = new Call(new Reference(left, "__lshift__"), currentObject);
                            } else {
                                currentObject = new Call(new Reference(left, "__rshift__"), currentObject);
                            }
                              
        } // while 
    } // shift_expr

    // public arith_expr<null> = term CODE {CODE (.k=1.) PLUS CODE|MINUS CODE term CODE};
    public void arith_expr() throws ParserError {
        term();
         PythonObject left = currentObject; 
        while (((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS))) {
             OperatorType op; 
            if ((id == PythonScanner.TOKEN_PLUS)) {
                if (id == PythonScanner.TOKEN_PLUS) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "PLUS");
                }
                 op = OperatorType.Add; 
            } else if ((id == PythonScanner.TOKEN_MINUS)) {
                if (id == PythonScanner.TOKEN_MINUS) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "MINUS");
                }
                 op = OperatorType.Sub; 
            } else {
                throw new ParserError(t, "PLUS,MINUS");
            }
            term();
             
                            if (op == OperatorType.Add) {
                                currentObject = new Call(new Reference(left, "__add__"), currentObject);
                            } else {
                                currentObject = new Call(new Reference(left, "__sub__"), currentObject);
                            }
                        
        } // while 
    } // arith_expr

    // public term<null> = factor CODE {CODE (.k=1.) STAR CODE|SLASH CODE|MOD CODE|SLASHSLASH CODE factor CODE};
    public void term() throws ParserError {
        factor();
         PythonObject left = currentObject; 
        while ((id == PythonScanner.TOKEN_STAR) || ((id >= PythonScanner.TOKEN_MOD) && (id <=PythonScanner.TOKEN_SLASHSLASH))) {
             OperatorType op; 
            if ((id == PythonScanner.TOKEN_STAR)) {
                if (id == PythonScanner.TOKEN_STAR) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "STAR");
                }
                 op = OperatorType.Mult; 
            } else if ((id == PythonScanner.TOKEN_SLASH)) {
                if (id == PythonScanner.TOKEN_SLASH) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "SLASH");
                }
                 op = OperatorType.Div; 
            } else if ((id == PythonScanner.TOKEN_MOD)) {
                if (id == PythonScanner.TOKEN_MOD) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "MOD");
                }
                 op = OperatorType.Mod; 
            } else if ((id == PythonScanner.TOKEN_SLASHSLASH)) {
                if (id == PythonScanner.TOKEN_SLASHSLASH) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"//\"");
                }
                 op = OperatorType.FloorDiv; 
            } else {
                throw new ParserError(t, "STAR,MOD,SLASH,SLASHSLASH");
            }
            factor();
            
                            if (op == OperatorType.Mult) {
                                currentObject = new Call(new Reference(left, "__mul__"), currentObject);
                            } else if (op == OperatorType.Div) {
                                currentObject = new Call(new Reference(left, "__div__"), currentObject);
                            } else if (op == OperatorType.Mod) {
                                currentObject = new Call(new Reference(left, "__mod__"), currentObject);
                            } else if (op == OperatorType.FloorDiv) {
                                currentObject = new Call(new Reference(left, "__floordiv__"), currentObject);
                            }
                    
        } // while 
    } // term

    // public factor<null> = (.k=1.) (.k=1.) PLUS CODE|MINUS CODE|TILDA CODE factor CODE|power;
    public void factor() throws ParserError {
 UnaryopType op; 
        if (((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA)) {
            if ((id == PythonScanner.TOKEN_PLUS)) {
                if (id == PythonScanner.TOKEN_PLUS) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "PLUS");
                }
                 op = UnaryopType.UAdd; 
            } else if ((id == PythonScanner.TOKEN_MINUS)) {
                if (id == PythonScanner.TOKEN_MINUS) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "MINUS");
                }
                 op = UnaryopType.USub; 
            } else if ((id == PythonScanner.TOKEN_TILDA)) {
                if (id == PythonScanner.TOKEN_TILDA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "TILDA");
                }
                 op = UnaryopType.Not; 
            } else {
                throw new ParserError(t, "PLUS,MINUS,TILDA");
            }
            factor();
            
                            if (op == UnaryopType.UAdd) {
                                currentObject = new Call(new Reference(currentObject, "__pos__"), currentObject);
                            } else if (op == UnaryopType.USub) {
                                currentObject = new Call(new Reference(currentObject, "__neg__"), currentObject);
                            } else if (op == UnaryopType.Not) {
                                currentObject = new Call(new Reference(currentObject, "__invert__"), currentObject);
                            }
                      
        } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            power();
        } else {
            throw new ParserError(t, "'None','True','False',LPAREN,LBRACK,LKBRACK,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // factor

    // public power<null> = atom CODE {(.k=1.) LPAREN CODE [arglist] RPAREN CODE|LBRACK subscriptlist RBRACK CODE|DOT NAME CODE} CODE [STARSTAR factor CODE];
    public void power() throws ParserError {
        atom();
         PythonObject atom = currentObject; 
        while ((id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_DOT)) {
            if ((id == PythonScanner.TOKEN_LPAREN)) {
                if (id == PythonScanner.TOKEN_LPAREN) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "LPAREN");
                }
                 currentList = new ArrayList<PythonObject>(); // since arglist is optional - to ensure empty list! 
                if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || ((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    arglist();
                } 
                if (id == PythonScanner.TOKEN_RPAREN) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "RPAREN");
                }
                 currentObject = new Call(atom, currentList.toArray(new PythonObject[currentList.size()])); atom = currentObject; 
            } else if ((id == PythonScanner.TOKEN_LBRACK)) {
                if (id == PythonScanner.TOKEN_LBRACK) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "LBRACK");
                }
                subscriptlist();
                if (id == PythonScanner.TOKEN_RBRACK) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "RBRACK");
                }
                 atom = currentObject; 
            } else if ((id == PythonScanner.TOKEN_DOT)) {
                if (id == PythonScanner.TOKEN_DOT) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "DOT");
                }
                if (id == PythonScanner.TOKEN_NAME) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "NAME");
                }
                 currentObject = new Reference(atom, PythonString.valueOf(t.toString())); atom = currentObject; 
            } else {
                throw new ParserError(t, "LPAREN,LBRACK,DOT");
            }
        } // while 
         PythonObject left = currentObject; 
        if ((id == PythonScanner.TOKEN_STARSTAR)) {
            if (id == PythonScanner.TOKEN_STARSTAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"**\"");
            }
            factor();
             currentObject = new Call(new Reference(left, "__pow__"), currentObject); left = currentObject; 
        } 
    } // power

    // public atom<null> = (.k=1.) CODE LPAREN [(.k=1.) yield_expr CODE|CODE testlist_comp CODE] CODE RPAREN|CODE LBRACK CODE [testlist_comp] CODE RBRACK|CODE LKBRACK CODE [test CODE [COLON test CODE] CODE (.k=1.) comp_for CODE|{COMMA [test CODE [COLON test CODE]] CODE}] CODE RKBRACK|NAME CODE|NUMBER CODE|STRING CODE {STRING CODE}|ELLIPSIS CODE|"None" CODE|"True" CODE|"False" CODE;
    public void atom() throws ParserError {
        if ((id == PythonScanner.TOKEN_LPAREN)) {
             scanner.pushNotInStatement(); 
            if (id == PythonScanner.TOKEN_LPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LPAREN");
            }
            if ((id == 17) || ((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                if ((id == 17)) {
                    yield_expr();
                     throw new UnsupportedOperationException("yield_expr"); 
                } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                     trailingComma = false; 
                    testlist_comp();
                     
                      if (trailingComma || currentList.size() > 1) {
                          currentObject = new PythonListGenerator(currentList, PythonTuple.PYTHON_TUPLE_CLASS);
                      } else {
                          currentObject = currentList.get(0);
                      }
                   
                } else {
                    throw new ParserError(t, "'yield','None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
                }
            } 
             scanner.popNotInStatement(); 
            if (id == PythonScanner.TOKEN_RPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RPAREN");
            }
        } else if ((id == PythonScanner.TOKEN_LBRACK)) {
             scanner.pushNotInStatement(); 
            if (id == PythonScanner.TOKEN_LBRACK) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LBRACK");
            }
             currentList = new ArrayList<PythonObject>(); // just in case we don't get to testlist_comp 
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                testlist_comp();
            } 
             
                currentObject = new PythonListGenerator(currentList,  PythonList.PYTHON_LIST_CLASS);
                scanner.popNotInStatement(); 
             
            if (id == PythonScanner.TOKEN_RBRACK) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RBRACK");
            }
        } else if ((id == PythonScanner.TOKEN_LKBRACK)) {
             scanner.pushNotInStatement(); 
            if (id == PythonScanner.TOKEN_LKBRACK) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LKBRACK");
            }
             Map<PythonObject, PythonObject> map = new LinkedHashMap<PythonObject, PythonObject>(); 
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                test();
                 PythonObject key = currentObject; PythonObject value = null; 
                if ((id == PythonScanner.TOKEN_COLON)) {
                    if (id == PythonScanner.TOKEN_COLON) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "COLON");
                    }
                    test();
                     value = currentObject; 
                } 
                 map.put(key, value); 
                if ((id == 14)) {
                    comp_for();
                     throw new UnsupportedOperationException("comp_for"); 
                } else if ((id == PythonScanner.TOKEN_COMMA) || (id == PythonScanner.TOKEN_RKBRACK)) {
                    while ((id == PythonScanner.TOKEN_COMMA)) {
                        if (id == PythonScanner.TOKEN_COMMA) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "COMMA");
                        }
                        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                            test();
                             key = currentObject; value = null; 
                            if ((id == PythonScanner.TOKEN_COLON)) {
                                if (id == PythonScanner.TOKEN_COLON) {
                                    next(); // <-- here 2
                                } else {
                                    throw new ParserError(t, nt, "COLON");
                                }
                                test();
                                 value = currentObject; 
                            } 
                        } 
                         map.put(key, value); 
                    } // while 
                }
            } 
            
               currentObject = PythonDictionary.constructor(map); 
               scanner.popNotInStatement();
             
            if (id == PythonScanner.TOKEN_RKBRACK) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RKBRACK");
            }
        } else if ((id == PythonScanner.TOKEN_NAME)) {
            if (id == PythonScanner.TOKEN_NAME) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NAME");
            }
            
		        String id = t.toString();
		        
		        if ("True".equals(id)) {
		            currentObject = PythonBoolean.TRUE;
		        } else if ("False".equals(id)) {
		            currentObject = PythonBoolean.FALSE;
		        } else if ("None".equals(id)) {
		            currentObject = PythonNone.NONE;
		        //} else if (BuiltInFunctions.isBuiltInFunction(id)) {
		        //    currentObject = BuiltInFunctions.getFunction(id);
		        } else {
                    currentObject = new Reference(null, PythonString.valueOf(id));
                }
             
        } else if ((id == PythonScanner.TOKEN_NUMBER)) {
            if (id == PythonScanner.TOKEN_NUMBER) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "NUMBER");
            }
            
               String n = t.toString();
               if (n.indexOf('.') > 0) {
                   currentObject = PythonFloat.valueOf(n);
               } else {
                   currentObject = PythonInteger.valueOf(n);
               }
             
        } else if ((id == PythonScanner.TOKEN_STRING)) {
            if (id == PythonScanner.TOKEN_STRING) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STRING");
            }
             currentObject = PythonString.valueOf(t.toString()); 
            while ((id == PythonScanner.TOKEN_STRING)) {
                if (id == PythonScanner.TOKEN_STRING) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "STRING");
                }
                 currentObject = PythonString.valueOf(((PythonString)currentObject).asString() + t.toString()); 
            } // while 
        } else if ((id == PythonScanner.TOKEN_ELLIPSIS)) {
            if (id == PythonScanner.TOKEN_ELLIPSIS) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"...\"");
            }
             throw new UnsupportedOperationException("ELLIPSIS"); 
        } else if ((id == 19)) {
            if (id == 19) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"None\"");
            }
             currentObject = PythonNone.NONE; 
        } else if ((id == 20)) {
            if (id == 20) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"True\"");
            }
             currentObject = PythonBoolean.TRUE; 
        } else if ((id == 21)) {
            if (id == 21) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"False\"");
            }
             currentObject = PythonBoolean.FALSE; 
        } else {
            throw new ParserError(t, "'None','True','False',LPAREN,LBRACK,LKBRACK,ELLIPSIS,NAME,NUMBER,STRING");
        }
    } // atom

    // public testlist_comp<null> = (.k=1.) test|star_expr CODE CODE (.k=1.) comp_for CODE|{COMMA CODE [(.k=1.) test CODE|star_expr CODE]} CODE;
    public void testlist_comp() throws ParserError {
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
        } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            star_expr();
             throw new UnsupportedOperationException("star_expr"); 
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
         List<PythonObject> args = new ArrayList<PythonObject>(); args.add(currentObject); 
        if ((id == 14)) {
            comp_for();
             throw new UnsupportedOperationException("comp_for"); 
        } else if ((id == PythonScanner.TOKEN_COMMA)) {
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                 trailingComma = true; 
                if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                        test();
                         args.add(currentObject); 
                    } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                        star_expr();
                         throw new UnsupportedOperationException("star_expr"); 
                    } else {
                        throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
                    }
                } 
            } // while 
        }
         currentList = args; 
    } // testlist_comp

    // public subscriptlist<null> = subscript {COMMA [subscript]};
    public void subscriptlist() throws ParserError {
        subscript();
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_COLON) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                subscript();
            } 
        } // while 
    } // subscriptlist

    // public subscript<null> = (.k=1.) test CODE|CODE [test CODE] COLON [test CODE] [sliceop] CODE;
    public void subscript() throws ParserError {
 PythonObject target = currentObject; 
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
             
                      currentObject = new Call(new Reference(target, "__getitem__"), currentObject);
                  
        } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_COLON) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
             
                   PythonObject from = null;
                   PythonObject to = null;
                
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                test();
                 from = currentObject; 
            } 
            if (id == PythonScanner.TOKEN_COLON) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COLON");
            }
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                test();
                 to = currentObject; 
            } 
            if ((id == PythonScanner.TOKEN_COLON)) {
                sliceop();
            } 
            
                      // currentObject = new Subscript(target, from, to);
                      if (from == null && to == null) {
                          // return target;
                          throw new UnsupportedOperationException("Subscript/Slice without parameters. How could this happen?");
                      } else if (from == null) {
                          throw new UnsupportedOperationException("Slice");
                      } else if (to == null) {
                          throw new UnsupportedOperationException("Slice");
                      } else {
                          currentObject = new Call(new Reference(target, "__getitem__"), from);
                      }
                  
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,COLON,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // subscript

    // public sliceop<null> = COLON [test] CODE;
    public void sliceop() throws ParserError {
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
        } 
         throw new UnsupportedOperationException("subscript slice"); 
    } // sliceop

    // public exprlist<null> = (.k=1.) expr|star_expr CODE CODE {COMMA [(.k=1.) expr CODE|star_expr CODE]} CODE;
    public void exprlist() throws ParserError {
        if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            expr();
        } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            star_expr();
             throw new UnsupportedOperationException("star_expr"); 
        } else {
            throw new ParserError(t, "'None','True','False',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
         List<PythonObject> args = new ArrayList<PythonObject>(); args.add(currentObject); 
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    expr();
                     args.add(currentObject); 
                } else if (((id >= 19) && (id <=21)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                    star_expr();
                     throw new UnsupportedOperationException("star_expr"); 
                } else {
                    throw new ParserError(t, "'None','True','False',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
                }
            } 
        } // while 
         currentList = args; 
    } // exprlist

    // public testlist<null> = test CODE {COMMA [test CODE]} CODE;
    public void testlist() throws ParserError {
        test();
         List<PythonObject> args = new ArrayList<PythonObject>(); args.add(currentObject); 
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                test();
                 args.add(currentObject); 
            } 
        } // while 
         currentList = args; 
    } // testlist

    // public classdef<null> = "class" NAME CODE [LPAREN CODE [arglist] RPAREN CODE] COLON CODE suite CODE;
    public void classdef() throws ParserError {
        if (id == 18) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"class\"");
        }
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
         PythonClassDef cls = new PythonClassDef(t.toString()); 
        if ((id == PythonScanner.TOKEN_LPAREN)) {
            if (id == PythonScanner.TOKEN_LPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "LPAREN");
            }
             currentList = new ArrayList<PythonObject>(); 
            if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || ((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR)) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
                arglist();
            } 
            if (id == PythonScanner.TOKEN_RPAREN) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "RPAREN");
            }
             cls.setParentArgs(currentList); 
        } 
        if (id == PythonScanner.TOKEN_COLON) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "COLON");
        }
         
                 Block savedBlock = currentBlock; currentBlock = cls.getBlock();
              
        suite();
         currentBlock = savedBlock; currentBlock.getStatements().add(cls); 
    } // classdef

    // public arglist<null> = (.k=1.) CODE argument CODE {COMMA argument CODE} [(.k=1.) STAR test {COMMA argument} [COMMA STARSTAR test] CODE|STARSTAR test CODE] CODE|STAR test {COMMA argument} [COMMA STARSTAR test] CODE|STARSTAR test CODE;
    public void arglist() throws ParserError {
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
             List<PythonObject> args = new ArrayList<PythonObject>(); 
            argument();
             args.add(currentObject); 
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                argument();
                 args.add(currentObject); 
            } // while 
            if (((id >= PythonScanner.TOKEN_STAR) && (id <=PythonScanner.TOKEN_STARSTAR))) {
                if ((id == PythonScanner.TOKEN_STAR)) {
                    if (id == PythonScanner.TOKEN_STAR) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "STAR");
                    }
                    test();
                    while ((id == PythonScanner.TOKEN_COMMA)) {
                        if (id == PythonScanner.TOKEN_COMMA) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "COMMA");
                        }
                        argument();
                    } // while 
                    if ((id == PythonScanner.TOKEN_COMMA)) {
                        if (id == PythonScanner.TOKEN_COMMA) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "COMMA");
                        }
                        if (id == PythonScanner.TOKEN_STARSTAR) {
                            next(); // <-- here 2
                        } else {
                            throw new ParserError(t, nt, "\"**\"");
                        }
                        test();
                    } 
                     throw new UnsupportedOperationException("star arguments"); 
                } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
                    if (id == PythonScanner.TOKEN_STARSTAR) {
                        next(); // <-- here 2
                    } else {
                        throw new ParserError(t, nt, "\"**\"");
                    }
                    test();
                     throw new UnsupportedOperationException("star star arguments"); 
                } else {
                    throw new ParserError(t, "STAR,STARSTAR");
                }
            } 
             currentList = args; 
        } else if ((id == PythonScanner.TOKEN_STAR)) {
            if (id == PythonScanner.TOKEN_STAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "STAR");
            }
            test();
            while ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                argument();
            } // while 
            if ((id == PythonScanner.TOKEN_COMMA)) {
                if (id == PythonScanner.TOKEN_COMMA) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "COMMA");
                }
                if (id == PythonScanner.TOKEN_STARSTAR) {
                    next(); // <-- here 2
                } else {
                    throw new ParserError(t, nt, "\"**\"");
                }
                test();
            } 
             throw new UnsupportedOperationException("star arguments"); 
        } else if ((id == PythonScanner.TOKEN_STARSTAR)) {
            if (id == PythonScanner.TOKEN_STARSTAR) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"**\"");
            }
            test();
             throw new UnsupportedOperationException("star star arguments"); 
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,STARSTAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // arglist

    // public argument<null> = (.k=1.) test [comp_for CODE]|test EQ test CODE;
    public void argument() throws ParserError {
        if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
            if ((id == 14)) {
                comp_for();
                 throw new UnsupportedOperationException("argument for comprehensions"); 
            } 
        } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            test();
            if (id == PythonScanner.TOKEN_EQ) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "EQ");
            }
            test();
             throw new UnsupportedOperationException("named arguments"); 
        } else {
            throw new ParserError(t, "'None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // argument

    // public comp_iter<null> = (.k=1.) comp_for|comp_if;
    public void comp_iter() throws ParserError {
        if ((id == 14)) {
            comp_for();
        } else if ((id == 11)) {
            comp_if();
        } else {
            throw new ParserError(t, "'if','for'");
        }
    } // comp_iter

    // public comp_for<null> = "for" exprlist "in" or_test [comp_iter];
    public void comp_for() throws ParserError {
        if (id == 14) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"for\"");
        }
        exprlist();
        if (id == 22) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"in\"");
        }
        or_test();
        if ((id == 11) || (id == 14)) {
            comp_iter();
        } 
    } // comp_for

    // public comp_if<null> = "if" test_nocond [comp_iter];
    public void comp_if() throws ParserError {
        if (id == 11) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"if\"");
        }
        test_nocond();
        if ((id == 11) || (id == 14)) {
            comp_iter();
        } 
    } // comp_if

    // public testlist1<null> = test {COMMA test};
    public void testlist1() throws ParserError {
        test();
        while ((id == PythonScanner.TOKEN_COMMA)) {
            if (id == PythonScanner.TOKEN_COMMA) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "COMMA");
            }
            test();
        } // while 
    } // testlist1

    // public encoding_decl<null> = NAME;
    public void encoding_decl() throws ParserError {
        if (id == PythonScanner.TOKEN_NAME) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "NAME");
        }
    } // encoding_decl

    // public yield_expr<null> = "yield" [yield_arg];
    public void yield_expr() throws ParserError {
        if (id == 17) {
            next(); // <-- here 2
        } else {
            throw new ParserError(t, nt, "\"yield\"");
        }
        if ((id == 7) || ((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            yield_arg();
        } 
    } // yield_expr

    // public yield_arg<null> = (.k=1.) "from" test|testlist;
    public void yield_arg() throws ParserError {
        if ((id == 7)) {
            if (id == 7) {
                next(); // <-- here 2
            } else {
                throw new ParserError(t, nt, "\"from\"");
            }
            test();
        } else if (((id >= 19) && (id <=21)) || ((id >= 27) && (id <=28)) || (id == PythonScanner.TOKEN_LPAREN) || (id == PythonScanner.TOKEN_LBRACK) || (id == PythonScanner.TOKEN_LKBRACK) || (id == PythonScanner.TOKEN_STAR) || (id == PythonScanner.TOKEN_ELLIPSIS) || ((id >= PythonScanner.TOKEN_PLUS) && (id <=PythonScanner.TOKEN_MINUS)) || (id == PythonScanner.TOKEN_TILDA) || ((id >= PythonScanner.TOKEN_NAME) && (id <=PythonScanner.TOKEN_NUMBER)) || (id == PythonScanner.TOKEN_STRING)) {
            testlist();
        } else {
            throw new ParserError(t, "'from','None','True','False','lambda','not',LPAREN,LBRACK,LKBRACK,STAR,ELLIPSIS,PLUS,MINUS,TILDA,NAME,NUMBER,STRING");
        }
    } // yield_arg



    public class ParserError extends RuntimeException {

        private int line = -1;
        private int pos = -1;
        private String rawMessage;

        public ParserError(String s) {
            super(s);
        }

        public ParserError(String s, Throwable t) {
            super(s, t);
        }

        public ParserError(Token t) {
            super(createMessage(t, null, null));
            line = t.getLine();
            pos = t.getPos();
        }

        public ParserError(Token t, String expected) {
            super(createMessage(t, null, expected));
            line = t.getLine();
            pos = t.getPos();
            rawMessage = "expected "+expected;
        }

        public ParserError(Token t, Token nt, String expected) {
            super(createMessage(t, nt, expected));
            line = t.getLine();
            pos = t.getPos();
            rawMessage = "expected "+expected;
        }

        public ParserError(Token t, Throwable tr) {
            super(createMessage(t, null, null), tr);
            line = t.getLine();
            pos = t.getPos();
        }

        public ParserError(Token t, String expected, Throwable tr) {
            super(createMessage(t, null, expected), tr);
            line = t.getLine();
            pos = t.getPos();
            rawMessage = "expected "+expected;
        }

        public int getLine() {
            return line;
        }

        public int getPos() {
            return pos;
        }

        public String getRawMessage() {
            return rawMessage;
        }

    }

    public static String createMessage(Token t, Token nt, String expected) {
        StringBuilder res = new StringBuilder();
        if (nt != null && nt != t && !nt.equals(t)) {
            res.append("Error after '").append(t).append("' (").append(t.getId()).append(")");
            res.append(" at '").append(nt).append("' (").append(t.getId()).append(")");
        } else {
            res.append("Error at/after '").append(t).append("' (").append(t.getId()).append(")");
        }
        res.append("[").append(t.getLine()).append(",").append(t.getPos()).append("]");
        if (expected != null && expected.length() > 0) {
            res.append(" expected ").append(expected);
        }
        return res.toString();
    }

}
