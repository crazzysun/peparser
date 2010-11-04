package com.pe.operation.PE分析;

import java.io.File;

import com.pe.UserException;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class 分析单个文件 extends AbstractFileOperation implements Operation
{
	private String fileName;
	private PEFile peFile;
	
	public void execute() throws Exception
	{
		File path = getWorkFile("");
		
		/** 如果文件名为空或者null，则抛出异常 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("文件已上传，但前台页面获取的文件名为空！");
		
		/** 装载PE文件分析结果 */
		File file = new File(path, fileName);
		LoadPEInfo loadPEInfo = new LoadPEInfo(file);
		peFile = loadPEInfo.Analyze();
		if (peFile == null) throw new UserException("文件" + fileName + "不是有效的PE文件！");
		
		/** 生成页面文件 */
		CreateHTML html = new CreateHTML(peFile);
		html.create();
	}

	public PEFile getPeFile()
	{
		return peFile;
	}

	public void setPeFile(PEFile peFile)
	{
		this.peFile = peFile;
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
