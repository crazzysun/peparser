package com.pe.parser;

import com.pe.dll.petest.PEAnalyzerDll;

public class PEDllTest
{
	public static void main(String [] args)
	{
		PEAnalyzerDll.INSTANCE.LoadPEHeader("c:\\TestApp.exe");
		int headerCount = PEAnalyzerDll.INSTANCE.GetHeaderCount();
		
		System.out.println(headerCount);
		
		for (int i = 0; i < headerCount; i++)
		{
			String name = PEAnalyzerDll.INSTANCE.GetHeaderName(i);
			System.out.println(name);
		}
		
		
	}
}
