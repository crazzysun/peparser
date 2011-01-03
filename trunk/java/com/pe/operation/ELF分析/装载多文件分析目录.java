package com.pe.operation.ELF分析;

import java.io.File;
import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.elfParser.ListDirFiles;
import com.pe.util.Zip;

/**
 * 分析多个文件时，首先加载这个目录下所有的文件名，传给前台
 * 以实现多文件分析中，分析一条，rpc返回一条
 * @author FangZhiyang
 *
 */
public class 装载多文件分析目录  extends AbstractFileOperation implements Operation
{
	private String folderPath;				//要分析的zip文件名
	private List<String> fileList;			//遍历过后的文件名数组
	private boolean isDepth;				//是否遍历子目录
	public void execute() throws Exception
	{
		File folder = unzipFolder();
		
		ListDirFiles.initalize();		
		
		/** 列出指定目录中所有文件 */
		for (File f : folder.listFiles())
		{
			fileList = ListDirFiles.listFile(f, isDepth);
		}
		if (fileList.isEmpty())
		{
			throw new UserException("指定目录中没有符合要求的文件!");
		}
	}

	private File unzipFolder() throws Exception
	{
		/** 取到文件名 */
		if (!folderPath.endsWith(".zip"))
			throw new Exception("您上传的文件格式不正确，必须为zip格式！");
		
		folderPath = folderPath.replace('\\', '/');
		int k = folderPath.lastIndexOf("/");
		if (k > 0) folderPath  = folderPath.substring(k + 1);
		
		/** 得到解压目录名，去掉“.zip” */
		String folderName = folderPath.substring(0, folderPath.length() - 4);
		String parentPath = getWorkFile("").getAbsolutePath();
		File oldfolder = new File(parentPath + File.separator + folderName);
		if(oldfolder.exists())
		{
			for (File f : oldfolder.listFiles())
			{
				f.delete();
			}
		}
		/** 在当前目录解压文件  */
		Zip.unzip(parentPath + File.separator + folderPath, parentPath + File.separator + folderName);
		
		File folder = new File(getWorkFile(""), folderName);
		return folder;
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
	
	public boolean getIsDepth()
	{
		return isDepth;
	}

	public void setIsDepth(boolean isDepth)
	{
		this.isDepth = isDepth;
	}
}
