package com.pe.parser;

import java.io.IOException;

import com.pe.dll.petest.GBIDDll;

public class PEDllTest
{
	public static void main(String[] args) throws IOException
	{
		GBIDDll GBID = GBIDDll.INSTANCE;
		
		String bounceFilePath[] = new String[3];
		bounceFilePath[0] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce.int";
		bounceFilePath[1] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce-1.int";
		bounceFilePath[2] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce-2.int";
		
		String allLibFilePath = "d:/AllRulesLib.lib";
//		String ntvLibFilePath;
//		String plusFilePath;
//		double results[];
//		int trans[];
//		double matchRate;
		
		GBID.GetAllRulesLib(bounceFilePath, allLibFilePath);
	}
}
