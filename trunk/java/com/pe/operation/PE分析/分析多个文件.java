package com.pe.operation.PE����;

import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.operation.Operation;

public class ��������ļ� implements Operation
{
	private String name;
	
	public void execute() throws Exception
	{
		PEAnalyzerDll.INSTANCE.LoadPEHeader("C:\\test\\1234.exe");
		name = PEAnalyzerDll.INSTANCE.GetRSCType(0);
		System.out.println(name);
		
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
