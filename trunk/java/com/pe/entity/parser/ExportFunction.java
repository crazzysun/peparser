package com.pe.entity.parser;

import com.pe.entity.Bean;

/**
 * 导出表函数信息
 * @author FangZhiyang
 *
 */
public class ExportFunction implements Bean
{
	private static final long serialVersionUID = 1L;

	private String functionName;
	private String RVA;
	private String index;
	
	public String getFunctionName()
	{
		return functionName;
	}
	public void setFunctionName(String functionName)
	{
		this.functionName = functionName;
	}
	public String getRVA()
	{
		return RVA;
	}
	public void setRVA(String rva)
	{
		RVA = rva;
	}
	public String getIndex()
	{
		return index;
	}
	public void setIndex(String index)
	{
		this.index = index;
	}
}
