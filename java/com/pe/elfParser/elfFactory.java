package com.pe.elfParser;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class elfFactory
{
	private PyObject elfClass;

	public elfFactory()
	{
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec("from elfParse import elfParse");
		elfClass = interpreter.get("elfParse");
	}

	public Ielf create(String filename)
	{

		PyObject elfObject = elfClass.__call__(new PyString(filename));
		return (Ielf) elfObject.__tojava__(Ielf.class);
	}
}
