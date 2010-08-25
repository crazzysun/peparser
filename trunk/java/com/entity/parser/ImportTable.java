package com.entity.parser;

import java.util.List;

import com.entity.Bean;

/**
 * 导入表
 * @author FangZhiyang
 *
 */
public class ImportTable implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<String> funtionName;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<String> getFuntionName()
	{
		return funtionName;
	}
	public void setFuntionName(List<String> funtionName)
	{
		this.funtionName = funtionName;
	}
}
