package com.entity.parser;

import com.entity.Bean;

public class FileInfo implements Bean
{
	private static final long serialVersionUID = 1L;

	public String fileName;
	public String createTime;
	public String modifyTime;
	public String fileSize;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getModifyTime()
	{
		return modifyTime;
	}

	public void setModifyTime(String modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	public String getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(String fileSize)
	{
		this.fileSize = fileSize;
	}
}
