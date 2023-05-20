package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.ah.python.modules.BuiltInFunctions;
import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;
import org.junit.Before;
import org.junit.Test;

public class TestProxyAttributes extends BaseTestClass {

    private ByteArrayOutputStream res;

    private PythonType proxyType = new PythonType(PythonObject.TYPE, "TestModule");

    protected String result() {
        return new String(res.toByteArray());
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

        GlobalScope.MODULES.put("test", new Proxy() {
            public PythonType getType() { return proxyType; }
        });

        proxyType.__setattr__("ExternalObject", new Function() {
            public PythonObject execute(ThreadContext context, PythonObject[] args, Map<String, PythonObject> kwargs) {
                return new ExternalObject();
            }
        });
    }

    @Test public void canReferenceProxyAttribute() {
        executeLines(
            "import test",
            "x = test.ExternalObject()",
            "print(x.value)"
        );

        assertEquals("initialValue\n", result());
    }

    @Test public void canAssignToProxyAttribute() {
        executeLines(
            "import test",
            "x = test.ExternalObject()",
            "x.value = 'newValue'",
            "print(x.value)"
        );

        assertEquals("newValue\n", result());
    }
}
