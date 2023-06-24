package org.ah.python.interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.ah.python.grammar.PythonParser;
import org.ah.python.grammar.PythonScannerFixed;

public class Interpreter {

    public static Module convert(String code) {
        return convert("__main__", code);
    }

    public static Module convertLines(String... lines) {
        StringBuilder code = new StringBuilder();
        for (String line : lines) {
            code.append(line).append('\n');
        }
        return convert("__main__", code.toString());
    }

    public static Module convert(String name, String code) {
        PythonScannerFixed scanner = new PythonScannerFixed(new StringReader(code));
        PythonParser parser = new PythonParser(scanner);

        parser.next();
        parser.file_input();

        Module module = parser.getModule();
        module.__setattr__("__name__", PythonString.valueOf(name));
        return module;
    }

    public static String loadString(Reader reader) {
        try {
            StringBuilder res = new StringBuilder();
            char[] buf = new char[10240];

            try {
                int r = reader.read(buf);
                while (r > 0) {
                    res.append(buf, 0, r);
                    r = reader.read(buf);
                }
            } finally {
                reader.close();
            }

            return res.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
