package org.ah.python.grammar;

import java.io.StringReader;

public class CheckPythonScanner {

    public static void main(String[] args) throws Exception {
        
        String input = "< << <= < * ** *";
        
        PythonScanner scanner = new PythonScanner(new StringReader(input));
        
        PythonScanner.Token t = scanner.getNextToken();
        while (t.getId() != PythonScanner.TOKEN_EOF) {
            System.out.println(t);
            t = scanner.getNextToken();
        }
        
    }
    
}
