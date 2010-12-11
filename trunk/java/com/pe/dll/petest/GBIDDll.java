package com.pe.dll.petest;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface GBIDDll extends Library
{
	GBIDDll INSTANCE = (GBIDDll) Native.loadLibrary("GBID", GBIDDll.class);
	
	public void GetAllRulesLib(String[] bounceFilePath, String rulesLibPath);
	public void GetNtvRulesLib(String[] bounceFilePath, String rulesLibPath);
	public void PlusLocal(String plusFilePath, String rulesLibPath, double[] results);
	public void PlusGlobal(String plusFilePath, String rulesLibPath, double[] results);
	public void DrvtvToNtv(String plusFilePath, String rulesLibPath, int[] trans);
	public double FileMatching(String path, String rulesLibPath);
}
