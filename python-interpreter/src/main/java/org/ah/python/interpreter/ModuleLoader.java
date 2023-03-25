package org.ah.python.interpreter;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;

import org.ah.python.grammar.PythonParser;
import org.ah.python.grammar.PythonScannerFixed;

public abstract class ModuleLoader {

    private String pathPrefix = "";

    public ModuleLoader(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    protected abstract String loadModuleSourceCodeFromName(String moduleName) throws IOException;

    public Module loadModule(String moduleName) {
        try {
            String pythonCode = loadModuleSourceCodeFromName(moduleName);

            PythonScannerFixed scanner = new PythonScannerFixed(new StringReader(pythonCode));
            PythonParser parser = new PythonParser(scanner);

            parser.next();
            parser.file_input();

            Module module = parser.getModule();
            module.__setattr__(PythonString.valueOf("__name__"), PythonString.valueOf("__main__"));
            module.__setattr__(PythonString.valueOf("__sourcecode__"), PythonString.valueOf(pythonCode));

            return module;
        } catch (IOException e) {
            throw new NoSuchElementException("Cannot load import " + moduleName + "; " + e.toString());
        } catch (Exception e) {
            throw new RuntimeException("Exception while loading module " +  moduleName, e);
        }

    }

    public String getPathPrefix() {
        return this.pathPrefix;
    }
}
