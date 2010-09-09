package com.pe.operation.PE分析;

import java.io.File;
import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.parser.ListDirFiles;

/**
 * 分析多个文件时，首先加载这个目录下所有的文件名，传个前台
 * 以实现多文件分析中，分析一条，rpc返回一条
 * @author FangZhiyang
 *
 */
public class 装载多文件分析目录 implements Operation
{
	private String folderPath;				//分析文件夹路径
	private List<String> suffix;			//文件类型
	private boolean isDepth;				//是否遍历子目录
	
	private List<String> fileList;			//遍历过后的文件名数组
	
	public void execute() throws Exception
	{
		File folder = new File(folderPath);
		if (folder == null || !folder.isDirectory())
		{
			throw new UserException("输入的文件夹路径\"" + folderPath + "\"有误，请检查后重新输入");
		}
		
		ListDirFiles.initalize();		
		
		/** 列出指定目录中所有文件 */
		for (File f : folder.listFiles())
		{
			fileList = ListDirFiles.listFile(f, suffix, isDepth);
		}
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

	public List<String> getSuffix()
	{
		return suffix;
	}

	public void setSuffix(List<String> suffix)
	{
		this.suffix = suffix;
	}

	public boolean getIsDepth()
	{
		return isDepth;
	}

	public void setIsDepth(boolean isDepth)
	{
		this.isDepth = isDepth;
	}
}
