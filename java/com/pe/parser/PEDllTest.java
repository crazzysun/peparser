package com.pe.parser;

import com.pe.dll.petest.PEAnalyzerDll;

public class PEDllTest
{
	public static void main(String [] args) throws Exception
	{
		PEAnalyzerDll.INSTANCE.LoadPEHeader("C:\\test\\1234.exe");
		String name = PEAnalyzerDll.INSTANCE.GetRSCType(0);
		System.out.println(name);
		
		byte[] nameB = name.getBytes();
		System.out.println(new String(nameB, "gbk"));
////		byte[] bb = "中文".getBytes("utf-8");
//		byte[] b = {(byte)'\u00d7', (byte)'\u00d4', (byte)'\u00b6', (byte)'\u00a8', (byte)'\u00d2', (byte)'\u00e5', (byte)'\u00d7', (byte)'\u00ca', (byte)'\u00d4', (byte)'\u00b4'};
//		System.out.println(new String(b, "gbk"));
//		
////		byte —�1ￄ1�7�1�7��1ￄ1�7�1�7�char＄1�7�1�7�1�7�1�7//////		"佄1�7�1�7�1�7�1�7�gb码是＄1�7�1�7�1�7�1�7e3 ,unicode昄1�7�1�7�1�7�1�760
//		
//		System.out.println("D7%D4 B6%A8 D2%E5 D7%CA D4%B4");
//		System.out.println(nameB[0] == b[0]);
	}
}
