package org.ah.python.interpreter;

import static org.ah.python.interpreter.util.MapBuilder.newmap;

import java.io.ByteArrayOutputStream;

import org.ah.python.modules.BuiltInFunctions;
import org.ah.python.modules.MathModule;
import org.ah.python.modules.RandomModule;
import org.ah.python.modules.SysModule;
import org.ah.python.modules.TimeModule;
import org.junit.Before;

public class BaseTestClass {

    private ByteArrayOutputStream res;

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
    }

}
