package com.pe.entity.parser;

import com.pe.entity.Bean;

/**
 * 多文件分析结果
 * @author FangZhiyang
 *
 */
public class MultiAnlysRslt implements Bean
{
	private static final long serialVersionUID = 1L;

	private String name;		//名称
	private String size;		//大小
	private String status;		//状态
	private String parentFolder;	//存放结果路径
	
	public String getParentFolder()
	{
		return parentFolder;
	}
	public void setParentFolder(String parentFolder)
	{
		this.parentFolder = parentFolder;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
