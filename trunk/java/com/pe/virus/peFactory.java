package com.pe.virus;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class peFactory {
	private PyObject peClass;


    public peFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from peParse import peParse");
        peClass = interpreter.get("peParse");
    }


    public Ipe create (String filename) {

        PyObject elfObject = peClass.__call__(new PyString(filename));
        return (Ipe)elfObject.__tojava__(Ipe.class);
    }
}
