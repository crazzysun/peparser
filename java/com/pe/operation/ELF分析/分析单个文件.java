package com.pe.operation.ELF分析;

import java.io.File;
import com.pe.elfParser.*;
import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class 分析单个文件 extends AbstractFileOperation implements Operation
{
	private String fileName;
	private Ielf elfFile;
	
	public void execute() throws Exception
	{
        File path = getWorkFile("");
		
		/** 如果文件名为空或者null，则抛出异常 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("文件已上传，但前台页面获取的文件名为空！");
		
		/** 装载PE文件分析结果 */
		File file = new File(path, fileName);
		elfFactory factory = new elfFactory();
		elfFile = factory.create(file.getAbsolutePath());
		if (elfFile.startParse())
		{
			if (elfFile == null) throw new UserException("文件" + fileName + "不是有效的ELF文件！");
			
			/** 生成页面文件 */
			CreateHTML html = new CreateHTML(elfFile,fileName);
			html.create();
		}
		else
			throw new UserException("文件" + fileName + "不是有效的ELF文件！");
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
