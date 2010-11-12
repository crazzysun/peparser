package com.pe.entity.parser;

import com.pe.entity.Bean;

/**
 * ���ļ��������
 * @author FangZhiyang
 *
 */
public class MultiAnlysRslt implements Bean
{
	private static final long serialVersionUID = 1L;

	private String name;		//����
	private String path;		//·��
	private String size;		//��С
	private String status;		//״̬
	private String parentFolder;	//��Ž��·��
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getParentFolder()
	{
		return parentFolder;
	}
	public void setParentFolder(String parentFolder)
	{
		this.parentFolder = parentFolder;
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
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
