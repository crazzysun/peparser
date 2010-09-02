package com.pe.operation.PE分析;

import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.operation.Operation;

public class 分析多个文件 implements Operation
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
