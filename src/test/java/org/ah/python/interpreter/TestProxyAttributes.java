package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.ah.python.modules.BuiltInFunctions;
import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;
import org.junit.Before;
import org.junit.Test;

public class TestProxyAttributes {

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
        
        proxyType.setAttribute("ExternalObject", new Function() {
            @Override public PythonObject call0() {
                return new ExternalObject();
            }
        });
    }

    @Test
    public void canReferenceProxyAttribute() {
        PythonObject module = Interpreter.convert(
                "import test\n"
              + "x = test.ExternalObject()\n"
              + "print(x.value)\n"
              );
        
        module.__call__();
        
        assertEquals("initialValue\n", result());
    }

    @Test
    public void canAssignToProxyAttribute() {
        PythonObject module = Interpreter.convert(
                "import test\n"
              + "x = test.ExternalObject()\n"
              + "x.value = 'newValue'\n"
              + "print(x.value)\n"
              );
        
        module.__call__();
        
        assertEquals("newValue\n", result());
    }
}
