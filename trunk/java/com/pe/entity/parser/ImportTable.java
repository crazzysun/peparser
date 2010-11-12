package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * µº»Î±Ì
 * @author FangZhiyang
 *
 */
public class ImportTable implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<String> functionName;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<String> getFunctionName()
	{
		return functionName;
	}
	public void setFunctionName(List<String> functionName)
	{
		this.functionName = functionName;
	}

}
