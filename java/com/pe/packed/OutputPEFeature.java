package com.pe.packed;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.pe.dll.petest.PEAnalyzerDll;

/**
 * 生成加壳判断时所需要的arff文件
 * 
 * @author FangZhiyang
 *
 */
public class OutputPEFeature
{
	public static void output(PEAnalyzerDll PE, String path) throws IOException
	{
		String fileName = PE.GetFileName();
		String str = "";
		str += "@relation " + fileName + "\n";
		str += "@attribute SS numeric\n";
		str += "@attribute NSS numeric\n";
		str += "@attribute RWX numeric\n";
		str += "@attribute XS numeric\n";
		str += "@attribute IAT numeric\n";
		str += "@attribute HE numeric\n";
		str += "@attribute CE numeric\n";
		str += "@attribute DE numeric\n";
		str += "@attribute FE numeric\n";
		str += "@attribute class {'not_packed','packed'}\n";
		str += "@data\n";
		str += String.format("%d, %d, %d, %d, %d, %.6f, %.6f, %.6f, %.6f, 'packed'", 
			PE.GetStandardSectionNumber(),
			PE.GetNonStandardSectionNumber(),
			PE.GetRWESectionNumber(),
			PE.GetExecutableSectionNumber(),
			PE.GetIATEntryNumber(),
			PE.GetHeaderEntropy(),
			PE.GetCodeSectionEntropy(),
			PE.GetDataSectionEntropy(),
			PE.GetFileEntropy());
		
		path += ".arff";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path));
		BufferedWriter bw = new BufferedWriter(out);
		bw.write(str);
		bw.close();
	}
}
