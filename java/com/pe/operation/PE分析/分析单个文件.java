package com.pe.operation.PE分析;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pe.UserException;
import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.parser.DataDirectory;
import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.ImportTable;
import com.pe.entity.parser.PEHeader;
import com.pe.entity.parser.SectionHeader;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class 分析单个文件 extends AbstractFileOperation implements Operation
{
	private String fileName;
	
	public void execute() throws Exception
	{
		File path = getWorkFile("");
		File file = new File(path, fileName);
		
		/** 如果文件名为空或者null，则抛出异常 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("文件已上传，但前台页面获取的文件名为空！");
		
		
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
