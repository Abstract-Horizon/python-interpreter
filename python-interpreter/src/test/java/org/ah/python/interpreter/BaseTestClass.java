package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.ah.python.grammar.PythonParser;
import org.ah.python.grammar.PythonScannerFixed;
import org.ah.python.modules.BuiltInFunctions;
import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;
import org.junit.Before;

public class BaseTestClass {

    protected ThreadContext context;
    protected Module module;

    private ByteArrayOutputStream res;

    protected String result() {
        return new String(res.toByteArray());
    }

    protected void clearResult() {
        res = new ByteArrayOutputStream();
        BuiltInFunctions.setOutput(res);
    }

    @Before
    public void setUp() {
        module = new Module("__main__");
        module.__setattr__("__name__", PythonString.valueOf("__main__"));
        context = new ThreadContext(module);
        context.setCurrentScope(module);
    }

    @Before
    public void setup() {
        GlobalScope.reset();

        newmap(GlobalScope.MODULES)
            .name("sys").val(new SysModule())
            .name("math").val(new MathModule())
            .name("random").val(new RandomModule())
            .name("time").val(new TimeModule());

        res = new ByteArrayOutputStream();
        BuiltInFunctions.setOutput(res);
    }

    public void parseLines(String... lines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line).append('\n');
        }
        String code = stringBuilder.toString();

        PythonScannerFixed scanner = new PythonScannerFixed(new StringReader(code));
        PythonParser parser = new PythonParser(scanner);
        parser.setModule(module);
        module.setName("__main__");

        parser.next();
        parser.existing_module_file_input();
    }

    public AssertDSL executeLines(String... lines) {
        parseLines(lines);

        context.continuation(module.getBlock());

        for (int i = 0; i < 10000 && context.next(); i++) {}

        return new AssertDSL();
    }

    public void contextIsEmpty() {
        StringBuilder message = new StringBuilder();
        if (context.getDataStackLevel() > 0) {
            message.append("Data stack is not emtpy; " + PythonObject.arrayToString(context.dataStack)).append("\n");
        }
        if (!context.isPCStackEmpty()) {
            message.append("PC stack is not emtpy; " + context.pcStack).append("\n");
        }
        if (message.length() > 0) {
            assertTrue(message.toString(), false);
        }
    }

    public class AssertDSL {
        public AssertDSL() {}
        public void assertResult(String value) {
            assertEquals(value, result());
        }
    }
}
