package com.pe.operation.PE分析;

import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.parser.ListDirFiles;

public class 装载多文件分析目录 implements Operation
{
	private String folderPath;				//分析文件夹路径
	private String suffix;					//文件类型
	private boolean isDepth;				//是否遍历子目录
	
	private List<String> fileList;			//遍历过后的文件名数组
	
	public void execute() throws Exception
	{
		/** 列出指定目录中所有文件 */
		fileList = ListDirFiles.getListFiles(folderPath, suffix, isDepth);
		if (fileList.isEmpty())
		{
			throw new UserException("指定目录中没有符合要求的文件!");
		}
	}
	
	public String getFolderPath()
	{
		return folderPath;
	}

	public void setFolderPath(String folderPath)
	{
		this.folderPath = folderPath;
	}

	public List<String> getFileList()
	{
		return fileList;
	}

	public void setFileList(List<String> fileList)
	{
		this.fileList = fileList;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	public boolean isDepth()
	{
		return isDepth;
	}

	public void setDepth(boolean isDepth)
	{
		this.isDepth = isDepth;
	}
}
